package at.opentable.api;

import at.opentable.controller.TeburuController;
import at.opentable.entity.Teburu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

<<<<<<< HEAD
@CrossOrigin
=======
>>>>>>> af7c6081b295504781ef6e171882178530102db1
@RestController
@RequestMapping(path = "/api")
public class TeburuApi {

    @Autowired
<<<<<<< HEAD
    private TeburuController teburuController;
=======
    TeburuController teburuController;
>>>>>>> af7c6081b295504781ef6e171882178530102db1

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
<<<<<<< HEAD
}
=======
}
>>>>>>> af7c6081b295504781ef6e171882178530102db1
