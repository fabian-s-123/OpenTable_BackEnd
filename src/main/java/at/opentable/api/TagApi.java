package at.opentable.api;

import at.opentable.controller.TagController;
import at.opentable.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/tags")
public class TagApi {

    @Autowired
    private TagController tagController;

    @PostMapping
    public ResponseEntity createTag(@RequestBody Tag tag) {
        if (this.tagController.createTag(tag)) {
            return new ResponseEntity(HttpStatus.OK);
        } else return new ResponseEntity(HttpStatus.FORBIDDEN);
    }

    @GetMapping
    public Iterable<Tag> findAll() {
        return this.tagController.findAll();
    }

    @GetMapping(path = "/{id}")
    public Optional<Tag> getTag(@PathVariable int id) {
        return this.tagController.getTAg(id);
    }

    @PutMapping
    public ResponseEntity updateTag(@RequestBody Tag tag) {
        if (this.tagController.getTAg(tag.getId()).isPresent()) {
            return new ResponseEntity(HttpStatus.OK);
        } else return new ResponseEntity(HttpStatus.FORBIDDEN);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity deleteTag(@PathVariable int id) {
        if (this.tagController.getTAg(id).isPresent()) {
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
    }
}
