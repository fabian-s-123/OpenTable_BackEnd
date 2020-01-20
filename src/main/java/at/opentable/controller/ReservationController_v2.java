package at.opentable.controller;

import at.opentable.dto.CustomerReservationDTO;
import at.opentable.entity.Reservation;
import at.opentable.entity.Teburu;
import at.opentable.repository.ReservationRepository;
import at.opentable.repository.TeburuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
public class ReservationController_v2 {

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
    @Autowired
    private AuthorizationController authorizationController;

    public String createCustomerReservation(String jwt, CustomerReservationDTO cuReDTO) {
        boolean isAuthorized = this.authorizationController.isAuthorized(jwt);
        if (isAuthorized) {

            //populating the lists of TebId & Teb(Objects)
            List<Integer> listOfAllTebId = toList(this.teburuController.findAllTeburuByRestaurantId(cuReDTO.getRestaurantId()));
            List<Teburu> listOfAllTeb = new LinkedList<>();
            for (Integer id : listOfAllTebId) listOfAllTeb.add(this.teburuController.getTeburu(id).get());

            //endTime: startTime + 2 hrs
            Timestamp endTime = this.getEndTime(cuReDTO.getStartDateTime());

            //if one Teb holds the capacity required -> make reservation and return
            Optional<Teburu> tempTeb = listOfAllTeb.stream()
                    .sorted(Comparator.comparingInt(Teburu::getCapacity))
                    .filter(teb -> teb.getCapacity() >= cuReDTO.getGroupSize())
                    .filter(teb -> (teb.getCapacity() - cuReDTO.getGroupSize()) <= 2)
                    .filter(teb -> this.isStillAvailable(teb.getId(), cuReDTO.getStartDateTime(), endTime))
                    .findFirst();
            if (tempTeb.isPresent()) {
                Reservation reservation = new Reservation(tempTeb.get(), cuReDTO.getStartDateTime(), endTime, this.customerController.getCustomer(cuReDTO.getCustomerId()).get(), cuReDTO.getGroupSize());
                this.reservationRepository.save(reservation);
                return "ok";
            }
            //else try a combination of two different Teb
            return combineTebForReservation(cuReDTO, listOfAllTeb, endTime);
        }
        return "not-authorized";
    }


    private <T> List<T> toList(final Iterable<T> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }


    private Timestamp getEndTime(Timestamp startTime) {
        long endTimeLong = startTime.getTime() + (3600000 * 2);
        return new Timestamp(endTimeLong);
    }


    private boolean isStillAvailable(int tebId, Timestamp startTime, Timestamp endTime) {
        return (this.reservationRepository.checkReservationRepo(startTime, endTime, tebId)) == null;
    }


    private String combineTebForReservation(CustomerReservationDTO cuReDTO, List<Teburu> listOfAllTeb, Timestamp endTime) {
        List<Teburu> listOfTebFiltered = listOfAllTeb.stream()
                .filter(teb -> this.isStillAvailable(teb.getId(), cuReDTO.getStartDateTime(), endTime))
                .sorted(Comparator.comparingInt(Teburu::getCapacity))
                .collect(Collectors.toList());

        //finds all possible combinations of two Teb
        Map<Integer, int[]> mapOfPossibleCombinations = new HashMap<>();
        for (int i = 0; i < listOfTebFiltered.size() - 1; i++) {
            Teburu tempTeb1 = listOfTebFiltered.get(i);
            for (int j = i + 1; j < listOfTebFiltered.size(); j++) {
                Teburu tempTeb2 = listOfTebFiltered.get(j);
                if (!(tempTeb1.equals(tempTeb2)) && tempTeb1.getCapacity() + tempTeb2.getCapacity() >= cuReDTO.getGroupSize()) {
                    mapOfPossibleCombinations.put(((tempTeb1.getCapacity() + tempTeb2.getCapacity()) - cuReDTO.getGroupSize()), new int[]{tempTeb1.getId(), tempTeb2.getId()});
                }
            }
        }
        if (mapOfPossibleCombinations.size() == 0) return "no-seat";

        int key = (Integer) mapOfPossibleCombinations.keySet().toArray()[0];    //first map entry holds the one with the smallest amount of wasted space
        int[] tebIds = mapOfPossibleCombinations.get(key);                      //values assigned tot the first key - the two tebId's required for the reservation

        Teburu teb1 = this.teburuController.getTeburu(tebIds[0]).get();         //Teb 1 for reservation
        Teburu teb2 = this.teburuController.getTeburu(tebIds[1]).get();         //Teb 2 for reservation

        Reservation reservationTeb1 = new Reservation(teb1, cuReDTO.getStartDateTime(), endTime, this.customerController.getCustomer(cuReDTO.getCustomerId()).get(), cuReDTO.getGroupSize());
        Reservation reservationTeb2 = new Reservation(teb2, cuReDTO.getStartDateTime(), endTime, this.customerController.getCustomer(cuReDTO.getCustomerId()).get(), cuReDTO.getGroupSize());
        this.reservationRepository.save(reservationTeb1);
        this.reservationRepository.save(reservationTeb2);
        return "ok";
    }
}
