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

    public Optional<Reservation> findById(int id)
    {
        return this.reservationRepository.findById(id);
    }

    /**
     * Standard save function if teburuIdForReservation returns true.
     *
     * @param customerReservationDTO body is required for translating this object into a Reservation
     * @return boolean if creating a Reservation was successful
     */
    public boolean createCustomerReservation(CustomerReservationDTO customerReservationDTO) {
        Timestamp endTime = this.getEndTime(customerReservationDTO.getStartDateTime());
        Integer teburuId = this.teburuIdForReservation(endTime, customerReservationDTO.getRestaurantId(), customerReservationDTO.getGroupSize());
        if (!(teburuId == 0)) {
            Reservation reservationTemp = new Reservation(this.teburuController.getTeburu(teburuId).get(), customerReservationDTO.getStartDateTime(), endTime, this.customerController.getCustomer(customerReservationDTO.getCustomerId()).get(), customerReservationDTO.getGroupSize());
            this.reservationRepository.save(reservationTemp);
            return true;
        }
        return false;
    }


    /**
     * Checks if a reservation in a specific restaurant during a specific period of time is available (if there is no
     * other reservation during the default 2 hour period) and returns the Id of the Teburu which is available.
     *
     * @param time starting time of the reservation as Timestamp
     * @param restaurantId id of the restaurnt from the CustomerReservationDTO Object
     * @param groupSize int group size from the CustomerReservationDTO Object
     * @return the id of the first Teburu which available
     */
    private int teburuIdForReservation(Timestamp time, int restaurantId, int groupSize) {
        int teburuId = 0;
        Iterable<Integer> list = this.sortTable(this.teburuController.findAllTeburuByRestaurantId(restaurantId), groupSize);
        Timestamp timeEnd = this.getEndTime(time);

        for (Integer teburu : list) {
            Reservation temp = this.reservationRepository.checkReservationRepo(time, timeEnd, teburu);
            if (temp == null) {
                teburuId = teburu;
                break;
            }
        }
        return teburuId;
    }


    /**
     * Transforms Timestamp to long - adds 2 hours - and transforms it back to Timestamp to get the default
     * time a table will be blocked for after a successful reservation.
     *
     * @param time starting time of the reservation as Timestamp
     * @return end time of the reservation as Timestamp (2 hours after the starting time)
     */
    private Timestamp getEndTime(Timestamp time) {
        long endTimeLong = time.getTime() + (3600000*2);
        Timestamp endTime = new Timestamp(endTimeLong);
        return endTime;
    }

    /**
     * Sorts through a list of all tables (Teburu) of a Restaurant to get the tables with the right capacity.
     *
     * @param teburus list of all teburus of a restaurant
     * @param groupSize int groupsize from the CustomerReservationDTO
     * @return list of all suitable (free) tables of a restaurant for this specific reservation request
     */
    private Iterable<Integer> sortTable(Iterable<Integer> teburus, int groupSize) {
        List<Integer> list = new LinkedList<>();
        for (Integer teburu : teburus) {
            list.add(teburu);
        }
        for (int i = 0; i < list.size(); i++) {
            Integer teburu = list.get(i);
            if (this.teburuController.getTeburu(teburu).isPresent()) {
                Teburu tmpTeburu = (this.teburuController.getTeburu(teburu).get());
                if (tmpTeburu.getCapacity() < groupSize) {
                    list.remove(teburu);
                    i--;
                }
            }
        }
        for (int i = 0; i < list.size(); i++) {
            Integer teburu = list.get(i);
            if (this.teburuController.getTeburu(teburu).isPresent()) {
                Teburu tmpTeburu = (this.teburuController.getTeburu(teburu).get());
                if (tmpTeburu.getCapacity() > groupSize * 2) {
                    list.remove(teburu);
                    i--;
                }
            }
        }
        return list;
    }
}
