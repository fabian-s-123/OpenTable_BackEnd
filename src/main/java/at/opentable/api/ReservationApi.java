package at.opentable.api;

import at.opentable.controller.ReservationController;
import at.opentable.entity.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/reservations")
public class ReservationApi {

    @Autowired
    private ReservationController reservationController;

    @PostMapping
    public ResponseEntity createReservation(@RequestBody Reservation reservation) {
        boolean success = this.reservationController.createReservation(reservation);
        if (success) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @GetMapping
    public Iterable<Reservation> findAll () {
        return this.reservationController.findAll();
    }
}
