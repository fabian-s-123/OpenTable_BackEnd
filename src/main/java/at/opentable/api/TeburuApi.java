package at.opentable.api;

import at.opentable.controller.TeburuController;
import at.opentable.entity.Teburu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/api")
public class TeburuApi {

    @Autowired
    TeburuController teburuController;

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

}
