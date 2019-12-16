package at.opentable.controller;

import at.opentable.dto.CustomerReservationDTO;
import at.opentable.entity.Reservation;
import at.opentable.entity.Teburu;
import at.opentable.Repository.ReservationRepository;
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

    /**
     * Save function if teburuIdForReservation returns true.
     *
     * @param customerReservationDTO body is required for translating this object into a Reservation
     * @return boolean if creating a Reservation was successful
     */
    public boolean createCustomerReservation(CustomerReservationDTO customerReservationDTO) {
        Timestamp endTime = this.getEndTime(customerReservationDTO.getStartDateTime());
        List<Integer> teburuId = this.teburuIdForReservation(endTime, customerReservationDTO.getRestaurantId(), customerReservationDTO.getGroupSize());
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
     * @param time         starting time of the reservation as Timestamp
     * @param restaurantId id of the restaurnt from the CustomerReservationDTO Object
     * @param groupSize    int group size from the CustomerReservationDTO Object
     * @return the id of the first Teburu which available
     */
    private List<Integer> teburuIdForReservation(Timestamp time, int restaurantId, int groupSize) {
        List<Integer> teburuId = null;
        Iterable<Integer> list = this.sortTable(this.teburuController.findAllTeburuByRestaurantId(restaurantId), groupSize);
        Timestamp timeEnd = this.getEndTime(time);

        for (Integer teburu : list) {
            Reservation temp = this.reservationRepository.checkReservationRepo(time, timeEnd, teburu);
            if (temp == null) {
                teburuId.add(teburu);
                break;
            } else {
                List<Integer> listCombined = this.teburuIdForReservationCombined(time, timeEnd, restaurantId, groupSize);
                if (listCombined.size() > 0) {
                    teburuId.add(listCombined.get(0));
                    teburuId.add(listCombined.get(1));
                }
            }
        }
        return teburuId;
    }


    /**
     *
     *
     * @param time
     * @param timeEnd
     * @param restaurantId
     * @param groupSize
     * @return
     */
    private List<Integer> teburuIdForReservationCombined(Timestamp time, Timestamp timeEnd, int restaurantId, int groupSize) {
        Iterable<Integer> list = this.teburuController.findAllTeburuByRestaurantId(restaurantId);
        List<Integer> teburuId = this.sortTableCombined(list, groupSize);
        List<Integer> teburuIdAvailable = null;
        for (Integer i = 0; i < teburuId.size(); i++) {
            Reservation temp = this.reservationRepository.checkReservationRepo(time, timeEnd, teburuId.get(i));
            if (temp == null) {
                Reservation temp2 = this.reservationRepository.checkReservationRepo(time, timeEnd, teburuId.get(i + 1));
                if (temp2 == null) {
                    teburuIdAvailable.add(temp.getId());
                    teburuIdAvailable.add(temp.getId());
                }
            }
        }
        return teburuIdAvailable;
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


    /**
     * Sorts through a list of all tables (Teburu) of a Restaurant to get the tables with the right capacity
     * >= groupSize.
     *
     * @param teburus   list of all teburus of a restaurant
     * @param groupSize int groupSize from the CustomerReservationDTO
     * @return list of all suitable (free) teburuId's of a restaurant for this specific reservation request
     */
    private Iterable<Integer> sortTable(Iterable<Integer> teburus, int groupSize) {
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
     * Iterates through a list of all tables (Teburu) of a Restaurant to get the first pair of tables with a min
     * combined capacity of the required group size.
     *
     * @param teburus   list of all teburus of a restaurant
     * @param groupSize int groupSize from the CustomerReservationDTO
     * @return pair of two suitable (free) teburuId's (with min combined capacity of groupSize) of a restaurant
     */
    private List<Integer> sortTableCombined(Iterable<Integer> teburus, int groupSize) {
        List<Integer> list = new LinkedList<>();
        for (Integer teburu : teburus) {
            list.add(teburu);
        }
        List<Integer> listTeburuId = null;
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
                        break;
                    }
                }
            }
        }
        return listTeburuId;
    }
}
