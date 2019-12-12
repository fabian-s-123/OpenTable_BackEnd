package at.opentable.api;

import at.opentable.controller.ReservationController;
import at.opentable.dto.CustomerReservationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(path="/api/customerReservations")
public class CustomerReservationApi {

    @Autowired
    private ReservationController reservationController;


    /**
     * timestamp format example : "startDateTime":"2019-12-30T18:00:00.000+0100",
     */

    @PostMapping
    public ResponseEntity createCustomerReservation(@RequestBody CustomerReservationDTO customerReservationDTO) {
        boolean success = this.reservationController.createCustomerReservation(customerReservationDTO);
        if (success) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}
