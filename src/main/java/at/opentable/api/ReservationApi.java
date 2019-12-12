package at.opentable.api;

import at.opentable.controller.ReservationController;
import at.opentable.dto.CustomerReservationDTO;
import at.opentable.entity.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@CrossOrigin
@RestController
@RequestMapping(path = "/api/reservations")
public class ReservationApi {

    @Autowired
    private ReservationController reservationController;

    @PostMapping
    public ResponseEntity createReservation(@RequestBody CustomerReservationDTO customerReservationDTO) {
        boolean success = this.reservationController.createCustomerReservation(customerReservationDTO);
        if (success) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @GetMapping
    public Iterable<Reservation> findAll () {
        return this.reservationController.findAll();
    }

    @GetMapping(path="/{id}")
    public Optional<Reservation> getReservation (@PathVariable int id) {
        return this.reservationController.findById(id);
    }

}
