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

    private List<Integer> listOfAllTebId = new LinkedList<>();
    private List<Teburu> listOfTeb = new LinkedList<>();

    public String createCustomerReservation(CustomerReservationDTO cuReDTO) {
        Iterable<Integer> iterableOfAllTebId = teburuController.findAllTeburuByRestaurantId(cuReDTO.getRestaurantId());
        iterableOfAllTebId.forEach(listOfAllTebId::add);// adding the TebId's to the list

        Timestamp endTime = this.getEndTime(cuReDTO.getStartDateTime());

        for (Integer id : listOfAllTebId) {
            listOfTeb.add(this.teburuController.getTeburu(id).get());
        }



        //if one Teb is enough
        Optional<Teburu> tempTeb = listOfTeb.stream()
                .filter(e -> e.getCapacity() >= cuReDTO.getGroupSize())
                .filter(e -> this.isStillAvailable(e.getId(), cuReDTO.getStartDateTime(), endTime))
                .findFirst();
        if (tempTeb.isPresent()) {
            this.reservationRepository.save(new Reservation(tempTeb.get(), cuReDTO.getStartDateTime(), endTime, this.customerController.getCustomer(cuReDTO.getCustomerId()).get(), cuReDTO.getGroupSize()));
            return "ok";
        }

        //if more Teb are needed
        List<Teburu> listTempTeb = listOfTeb.stream()
                .filter(e -> this.isStillAvailable(e.getId(), cuReDTO.getStartDateTime(), endTime))
                .sorted(Comparator.comparingInt(Teburu::getCapacity))
                .collect(Collectors.toList());
        //removes Teb with smallest capacity if this.capacity + Teb with largest capacity < groupSize
        for (int i = 0; i < listTempTeb.size(); i++) {
             if ((listTempTeb.get(i).getCapacity() + listTempTeb.get(listTempTeb.size()-1).getCapacity()) < cuReDTO.getGroupSize()) {
                 listTempTeb.remove(i);
                 i--;
            }
        }
        //finds all possible combinations
        List<int[]> listOfPossibleCombinations = new ArrayList<>();
        for (int i = 0; i < listTempTeb.size() - 1; i++) {
            Teburu tempTeb1 = listTempTeb.get(i);
            for (int j = 1; j < listTempTeb.size(); j++) {
                Teburu tempTeb2 = listTempTeb.get(j);
                if (tempTeb1.getCapacity() + tempTeb2.getCapacity() >= cuReDTO.getGroupSize()) {
                    listOfPossibleCombinations.add(new int[] {cuReDTO.getGroupSize()-(tempTeb1.getCapacity()+tempTeb2.getCapacity()), tempTeb1.getId(), tempTeb2.getId()});
                }
            }
        }
        return "no-seat";
        //picks the one with the least amount of unused seats
        listOfPossibleCombinations



        return "no-seat";
    }

    private boolean isStillAvailable(int tebId, Timestamp startTime, Timestamp endTime) {
        return (this.reservationRepository.checkReservationRepo(startTime, endTime, tebId)) == null;
    }

    private Timestamp getEndTime(Timestamp startTime) {
        long endTimeLong = startTime.getTime() + (3600000 * 2);
        return new Timestamp(endTimeLong);
    }
}
