package at.opentable.controller;

import at.opentable.dto.CustomerReservationDTO;
import at.opentable.entity.Teburu;
import at.opentable.repository.ReservationRepository;
import at.opentable.repository.TeburuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
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


    //createCustomerReservation
        //getEndTime - d
        //getAvailableTebId - d
            //sortTebCapacity - d
            //combineCapacityOfTeb
                //sortTebCombined


    public String createCustomerReservation(CustomerReservationDTO customerReservationDTO) {
        Iterable<Integer> iterableOfAllTebId =  teburuController.findAllTeburuByRestaurantId(customerReservationDTO.getRestaurantId());
        iterableOfAllTebId.forEach(listOfAllTebId::add);// adding the TebId's to the list

        for (int i = 0; i < listOfAllTebId.size(); i++) {
             listOfTeb.add(this.teburuController.getTeburu(listOfAllTebId.get(i)).get());
        }

        Teburu tempTeburu = listOfTeb.stream()
                .filter(e -> e.getCapacity() >= customerReservationDTO.getGroupSize())
                .findFirst()
                .get();

        System.out.println(tempTeburu.toString());

        return "a";
    }

    private Timestamp getEndTime(Timestamp startTime) {
        long endTimeLong = startTime.getTime() + (3600000 * 2);
        return new Timestamp(endTimeLong);
    }
}
