package at.opentable.controller;

import at.opentable.Repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ReservationController {

    @Autowired
    private ReservationRepository reservationRepostiroy;

    public boolean addReservation(Reservetaion reservation)

}
