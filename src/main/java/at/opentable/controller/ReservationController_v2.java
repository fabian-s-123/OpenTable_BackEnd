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
        Map<Integer, int[]> mapOfPossibleCombinations = new HashMap<>();
        for (int i = 0; i < listTempTeb.size() - 1; i++) {
            Teburu tempTeb1 = listTempTeb.get(i);
            for (int j = i + 1; j < listTempTeb.size(); j++) {
                Teburu tempTeb2 = listTempTeb.get(j);
                if (!(tempTeb1.equals(tempTeb2)) && tempTeb1.getCapacity() + tempTeb2.getCapacity() >= cuReDTO.getGroupSize()) {
                    mapOfPossibleCombinations.put(((tempTeb1.getCapacity() + tempTeb2.getCapacity()) - cuReDTO.getGroupSize()), new int[] {tempTeb1.getId(), tempTeb2.getId()});
                }
            }
        }

        if (mapOfPossibleCombinations.size() == 0) {
            return "no-seat";
        }

        int[] teburuIds = mapOfPossibleCombinations //access the values for key

        Teburu teburu1 = this.teburuController.getTeburu(teburuIds[0]).get();
        Teburu teburu2 = this. teburuController.getTeburu(teburuIds[1]).get();
        this.reservationRepository.save(new Reservation(teburu1, cuReDTO.getStartDateTime(), endTime, this.customerController.getCustomer(cuReDTO.getCustomerId()).get(), cuReDTO.getGroupSize()));
        this.reservationRepository.save(new Reservation(teburu2, cuReDTO.getStartDateTime(), endTime, this.customerController.getCustomer(cuReDTO.getCustomerId()).get(), cuReDTO.getGroupSize()));
        return "ok";
    }


    private boolean isStillAvailable(int tebId, Timestamp startTime, Timestamp endTime) {
        return (this.reservationRepository.checkReservationRepo(startTime, endTime, tebId)) == null;
    }


    private Timestamp getEndTime(Timestamp startTime) {
        long endTimeLong = startTime.getTime() + (3600000 * 2);
        return new Timestamp(endTimeLong);
    }
}
