package at.opentable.controller;

import at.opentable.repository.ReservationRepository;
import at.opentable.entity.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
public class ReservationController {

    @Autowired
    private ReservationRepository reservationRepository;

    public boolean addReservation(Reservation reservation)
    {
        this.reservationRepository.save(reservation);
        System.out.println("Successfully saved!");
        return true;
    }

    public Optional<Reservation> getReservation(int id)
    {
        System.out.println("Successfully get Reservation");
        return this.reservationRepository.findById(id);
    }

    public Iterable<Reservation> getAll()
    {
        return this.reservationRepository.findAll();
    }

    public boolean updateReservation(Reservation reservation)
    {
        this.reservationRepository.saveAndFlush(reservation);
        System.out.println("Successfully Reservation update");
        return true;
    }








}
