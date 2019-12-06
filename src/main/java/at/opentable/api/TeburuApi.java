package at.opentable.api;

import at.opentable.controller.TeburuController;
import at.opentable.entity.Teburu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping(path = "/api")
public class TeburuApi {

    @Autowired
    private TeburuController teburuController;

    @PostMapping("/teburus")
    public void createTeburu(@RequestBody Teburu teburu) {
        teburuController.createTeburu(teburu);
    }

    @PutMapping("/teburus")
    public Optional updateTeburu(@RequestBody Teburu teburu) {
        return Optional.of(teburuController.updateTeburu(teburu));
    }

    @GetMapping("/teburus")
    public Iterable<Teburu> getAll() {
        return teburuController.findAll();
    }

    @GetMapping("/teburus/{id}")
    public Optional getTeburu(@PathVariable int id) {
        return Optional.of(teburuController.getTeburu(id));
    }

    @DeleteMapping("/teburus/{id}")
    public ResponseEntity deleteTeburu(@PathVariable int id) {
        if(teburuController.deleteTeburu(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}