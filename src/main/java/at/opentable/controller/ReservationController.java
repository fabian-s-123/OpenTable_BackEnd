package at.opentable.controller;

import at.opentable.dto.CustomerReservationDTO;
import at.opentable.entity.Reservation;
import at.opentable.entity.Teburu;
import at.opentable.repository.ReservationRepository;
import at.opentable.repository.TeburuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Controller
public class ReservationController {

    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private TeburuRepository teburuRepository;
    @Autowired
    private RestaurantController restaurantController;
    @Autowired
    private CustomerController customerController;
    @Autowired
    private TeburuController teburuController;


    public Iterable<Reservation> findAll() {
        return this.reservationRepository.findAll();
    }

    public Optional<Reservation> findById(int id) {
        return this.reservationRepository.findById(id);
    }

    //TODO groupSize min value: 1

    /**
     * Save function if teburuIdForReservation is not null. Up to two tables can be used for a reservation. This gets
     * handled in teburuIdForReservation, which calls either sortTable or sortTableCombined through the function
     * teburuIdForReservationCombined.
     *
     * @param customerReservationDTO body is required for translating this object into a Reservation
     * @return boolean if creating a Reservation was successful
     */
    public boolean createCustomerReservation(CustomerReservationDTO customerReservationDTO) {
        Timestamp endTime = this.getEndTime(customerReservationDTO.getStartDateTime());
        List<Integer> teburuId = this.teburuIdForReservation(customerReservationDTO.getStartDateTime(), endTime, customerReservationDTO.getRestaurantId(), customerReservationDTO.getGroupSize());
        if (!(teburuId.size() == 0)) {
            for (Integer teburu : teburuId) {
                Reservation reservationTemp = new Reservation(this.teburuController.getTeburu(teburu).get(), customerReservationDTO.getStartDateTime(), endTime, this.customerController.getCustomer(customerReservationDTO.getCustomerId()).get(), customerReservationDTO.getGroupSize());
                this.reservationRepository.save(reservationTemp);
            }
            return true;
        }
        return false;
    }


    /**
     * Checks if a reservation in a specific restaurant during a specific period of time is available (if there is no
     * other reservation during the default 2 hour period) and returns the Id of the Teburu which is available. If
     * there is no single Teburu available, the function teburuIdForReservationCombined is called and returns a pair
     * of two tables with the min capacity of groupSize, which are available for reservation.
     *
     * @param time starting time of the reservation as Timestamp
     * @param restaurantId id of the restaurnt from the CustomerReservationDTO Object
     * @param groupSize int group size from the CustomerReservationDTO Object
     * @return the id of either the first Teburu available or two Teburu with combined capacity of min groupSize
     * available
     */
    private List<Integer> teburuIdForReservation(Timestamp time, Timestamp endTime, int restaurantId, int groupSize) {
        List<Integer> teburuId = new LinkedList<>();
        List<Integer> list = this.sortTable(this.teburuController.findAllTeburuByRestaurantId(restaurantId), groupSize);

        if (list.size() > 0) {
            for (Integer teburu : list) {
                Reservation temp = this.reservationRepository.checkReservationRepo(time, endTime, teburu);
                if (temp == null) {
                    teburuId.add(teburu);
                    return teburuId;
                }
            }
        } else {
            List<Integer> listCombined = this.teburuIdForReservationCombined(this.teburuController.findAllTeburuByRestaurantId(restaurantId), time, endTime, groupSize);
            if (listCombined.size() > 0) {
                teburuId.add(listCombined.get(0));
                teburuId.add(listCombined.get(1));
            }
        }
        return teburuId;
    }


    /**
     * Sorts through a list of all tables (Teburu) of a Restaurant to get the tables with the capacity >= groupSize.
     *
     * @param teburus list of all teburus of a restaurant
     * @param groupSize int groupSize from the CustomerReservationDTO
     * @return list of all suitable (free) teburuId's of a restaurant for this specific reservation request
     */
    private List<Integer> sortTable(Iterable<Integer> teburus, int groupSize) {
        List<Integer> list = new LinkedList<>();
        for (Integer teburu : teburus) {
            list.add(teburu);
        }
        for (int i = 0; i < list.size(); i++) {
            Integer teburu = list.get(i);
            if (this.teburuController.getTeburu(teburu).isPresent()) {
                Teburu tempTeburu = (this.teburuController.getTeburu(teburu).get());
                if (tempTeburu.getCapacity() < groupSize) {
                    list.remove(teburu);
                    i--;
                }
            }
        }
        for (int i = 0; i < list.size(); i++) {
            Integer teburu = list.get(i);
            if (this.teburuController.getTeburu(teburu).isPresent()) {
                Teburu tempTeburu = (this.teburuController.getTeburu(teburu).get());
                if (tempTeburu.getCapacity() > groupSize * 2) {
                    list.remove(teburu);
                    i--;
                }
            }
        }
        return list;
    }


    /**
     * Checks if there are two Teburus with a combined capacity of min the required groupSize and if they are are
     * available for reservation. Iterates through every possible combination in a restaurant.
     *
     * @param time starting time of the reservation as Timestamp
     * @param timeEnd ending time of the reservation as Timestamp
     * @param groupSize int group size from the CustomerReservationDTO Object
     * @return a list (size: 2) of teburuId's
     */
    private List<Integer> teburuIdForReservationCombined(Iterable<Integer> list, Timestamp time, Timestamp timeEnd, int groupSize) {
        List<Integer> listAllTeburus = new LinkedList<>();
        for (Integer teburu : list) {
            listAllTeburus.add(teburu);
        }

        List<Integer> teburuIdAvailable = new LinkedList<>();

        while (listAllTeburus.size() >= 2) {
            List<Integer> teburuId = this.sortTableCombined(listAllTeburus, groupSize);
            if (teburuId.size() > 0) {
                Reservation temp1 = this.reservationRepository.checkReservationRepo(time, timeEnd, teburuId.get(0));
                if (temp1 == null) {
                    Reservation temp2 = this.reservationRepository.checkReservationRepo(time, timeEnd, teburuId.get(1));
                    if (temp2 == null) {
                        teburuIdAvailable.add(teburuId.get(0));
                        teburuIdAvailable.add(teburuId.get(1));
                        return teburuIdAvailable;
                    } else {
                        listAllTeburus.remove(teburuId.get(1));
                    }
                } else {
                    listAllTeburus.remove(teburuId.get(0));
                }
            } else {
                return teburuIdAvailable;
            }
        }
        return teburuIdAvailable;
    }


    /**
     * Iterates through a list of all tables (Teburu) of a Restaurant to get the first pair of tables with a min
     * combined capacity of the required group size.
     *
     * @param teburus list of all teburus of a restaurant
     * @param groupSize int groupSize from the CustomerReservationDTO
     * @return pair of two suitable (free) teburuId's (with min combined capacity of groupSize) of a restaurant
     */
    private List<Integer> sortTableCombined(Iterable<Integer> teburus, int groupSize) {
        List<Integer> list = new LinkedList<>();
        for (Integer teburu : teburus) {
            list.add(teburu);
        }
        List<Integer> listTeburuId = new LinkedList<>();

        for (int i = 0; i < list.size() - 1; i++) {
            Integer teburu1 = list.get(i);
            for (int j = i + 1; j < list.size(); j++) {
                Integer teburu2 = list.get(j);
                if (this.teburuController.getTeburu(teburu1).isPresent() && this.teburuController.getTeburu(teburu2).isPresent()) {
                    Teburu tmpTeburu1 = (this.teburuController.getTeburu(teburu1).get());
                    Teburu tmpTeburu2 = (this.teburuController.getTeburu(teburu2).get());
                    if (tmpTeburu1.getCapacity() + tmpTeburu2.getCapacity() >= groupSize) {
                        listTeburuId.add(tmpTeburu1.getId());
                        listTeburuId.add(tmpTeburu2.getId());
                        return listTeburuId;
                    }
                }
            }
        }
        return listTeburuId;
    }


    /**
     * Transforms Timestamp to long - adds 2 hours - and transforms it back to Timestamp to get the default
     * time a table will be blocked for after a successful reservation.
     *
     * @param time starting time of the reservation as Timestamp
     * @return end time of the reservation as Timestamp (2 hours after the starting time)
     */
    private Timestamp getEndTime(Timestamp time) {
        long endTimeLong = time.getTime() + (3600000 * 2);
        Timestamp endTime = new Timestamp(endTimeLong);
        return endTime;
    }
}
