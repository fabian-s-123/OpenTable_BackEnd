package at.opentable.api;

import at.opentable.controller.ReservationController_v2;
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
    private ReservationController_v2 reservationController_v2;


    /**
     * timestamp format example : "startDateTime":"2019-12-30T18:00:00.000+0100",
     */

    @PostMapping
    public ResponseEntity createCustomerReservation(@RequestHeader (name="Authorization") String bearer, @RequestBody CustomerReservationDTO customerReservationDTO) {
        String[] tokens = bearer.split(" ");
        String jwt;
        if (tokens.length > 1 && tokens[1] != null) {
            jwt = tokens[1];
        } else return new ResponseEntity<>("something-went-wrong", HttpStatus.BAD_REQUEST);

        String result = this.reservationController_v2.createCustomerReservation(jwt, customerReservationDTO);
        switch (result) {
            case "ok":
                return new ResponseEntity<>("success", HttpStatus.OK);
            case "not-authorized":
                return new ResponseEntity<>("not-authorized", HttpStatus.FORBIDDEN);
            case "no-seat":
                return new ResponseEntity<>("no-tables-available", HttpStatus.FORBIDDEN);
            case "group-size":
                return new ResponseEntity<>("group-size-too-big", HttpStatus.FORBIDDEN);
            default:
                return new ResponseEntity<>("something-went-wrong", HttpStatus.BAD_REQUEST);
        }
    }
}
