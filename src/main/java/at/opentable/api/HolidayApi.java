package at.opentable.api;

import at.opentable.controller.HolidayController;
import at.opentable.dto.HolidayDTO;
import at.opentable.entity.Holiday;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/holidays")
public class HolidayApi {

    @Autowired
    private HolidayController holidayController;

    @PostMapping
    public ResponseEntity createHoliday(@RequestBody Holiday holiday) {
        if (this.holidayController.createHoliday(holiday)) {
            return new ResponseEntity(HttpStatus.OK);
        } else return new ResponseEntity(HttpStatus.FORBIDDEN);
    }

    @GetMapping
    public Iterable<HolidayDTO> findAll() {
        return this.holidayController.findAll();
    }

    @GetMapping(path = "/{id}")
    public Optional<Holiday> getHoliday(@PathVariable int id) {
        return this.holidayController.getHoliday(id);
    }

    @GetMapping(path = "/restaurant/{id}")
    public Iterable<HolidayDTO> findByRestaurant(@PathVariable int id) {
        return this.holidayController.findByRestaurant(id);
    }

    @PutMapping
    public ResponseEntity updateHoliday(@RequestBody Holiday holiday) {
        if (this.holidayController.updateHoliday(holiday).isPresent()) {
            return new ResponseEntity(HttpStatus.OK);
        } else return new ResponseEntity(HttpStatus.FORBIDDEN);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity deleteHoliday(@PathVariable int id) {
        if (this.holidayController.deleteHoliday(id)) {
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
    }

}
