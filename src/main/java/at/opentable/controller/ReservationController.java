package at.opentable.controller;

import at.opentable.entity.Reservation;
import at.opentable.entity.Teburu;
import at.opentable.repository.ReservationRepository;
import at.opentable.repository.TeburuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

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


    /**
     * Standard save function if TeburuIdForReservation returns true.
     *
     * @param time marks reservation starting time as Timestamp
     * @param restaurantId
     * @param customerId
     * @param groupSize
     * @return boolean if creation was successful
     */
    public boolean createReservation(Reservation reservation) {
        Integer teburuId = this.teburuIdForReservation(reservation.getStartDateTime(), reservation.getTeburu().getId(), reservation.getGroupSize());
        if (!(teburuId == 0)) {
            Reservation reservationTemp = new Reservation(this.teburuController.getTeburu2(teburuId), reservation.getStartDateTime(), this.getEndTime(reservation.getStartDateTime()), reservation.getCustomer(), reservation.getGroupSize());
            this.reservationRepository.save(reservationTemp);
            return true;
        }
        return false;
    }


    /**
     * Checks if a reservation in a specific restaurant during a specific period of time is available (if there is no
     * other reservation during the default 2 hour period) and returns the Id of the Teburu which is available.
     *
     * @param time         marks the reservation starting time as Timestamp
     * @param restaurantId
     * @return int => id of a Teburu which is available at @param restaurantId; returns 0 if not
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
     * Transforms Timestamp to LocalDateTime - adds 2 hours - and transforms it back to Timestamp to get the default
     * time a table will be blocked for after a successful reservation.
     *
     * @param time the preferred time of reservation as Timestamp
     * @return the @param time + 2 hours as Timestamp
     */
    private Timestamp getEndTime(Timestamp time) {
        LocalDateTime ldt = time.toLocalDateTime();
        LocalDateTime ldtEnd = ldt.plusHours(2);
        return Timestamp.valueOf(ldtEnd);
    }


    /**
     * Sorts through a list of all tables (Teburu) of a Restaurant to get the tables with the right capacity.
     *
     * @param teburus   result of function call findAll() from TeburuController (Iterable<Teburu>)
     * @param groupSize the amount of people the customer wants to make a reservation for as int
     * @return a list of Teburus that fit the @param groupSize
     */
    private Iterable<Integer> sortTable(Iterable<Integer> teburus, int groupSize) {
        List<Integer> list = new LinkedList<>();
        for (Integer teburu : teburus) {
            list.add(teburu);
        }
        for (int i = 0; i < list.size(); i++) {
            Integer teburu = list.get(i);
            if (this.teburuController.getTeburu2(teburu).getCapacity() < groupSize) {
                list.remove(teburu);
                i--;
            }
        }
        for (int i = 0; i < list.size(); i++) {
            Integer teburu = list.get(i);
            if (this.teburuController.getTeburu2(teburu).getCapacity() > groupSize * 2) {
                list.remove(teburu);
                i--;
            }
        }
        return list;
    }
}
