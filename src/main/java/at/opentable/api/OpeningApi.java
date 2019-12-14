package at.opentable.api;

import at.opentable.controller.OpeningController;
import at.opentable.dto.OpeningDTO;
import at.opentable.entity.Opening;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/openings")
public class OpeningApi {

    @Autowired
    private OpeningController openingController;

    @PostMapping
    public ResponseEntity createOpening(@RequestBody Opening opening) {
        if (this.openingController.createOpening(opening)) {
            return new ResponseEntity(HttpStatus.OK);
        } else return new ResponseEntity(HttpStatus.FORBIDDEN);
    }


    @GetMapping
    public Iterable<OpeningDTO> findAll() {
        return this.openingController.findAll();
    }

    @GetMapping(path = "/{id}")
    public Optional<Opening> getOpening(@PathVariable int id) {
        return this.openingController.getOpening(id);
    }

    @GetMapping(path = "/restaurant/{id}")
    public Iterable<OpeningDTO> findByRestaurant(@PathVariable int id) {
        return this.openingController.findByRestaurant(id);
    }

    @PutMapping
    public ResponseEntity updateOpening(@RequestBody Opening opening) {
        if (this.openingController.updateOpening(opening).isPresent()) {
            return new ResponseEntity(HttpStatus.OK);
        } else return new ResponseEntity(HttpStatus.FORBIDDEN);
    }


/*    @PutMapping
    public void updateOpening(@RequestBody Opening opening) {
        this.openingController.updateOpening(opening);
    }*/

    @DeleteMapping(path = "/{id}")
    public ResponseEntity deleteOpening(@PathVariable int id) {
        if (this.openingController.deleteOpening(id)) {
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
    }
}
