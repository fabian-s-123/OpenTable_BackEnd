package at.opentable.controller;

import at.opentable.entity.Opening;
import at.opentable.repository.OpeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
public class OpeningController {

    @Autowired
    private OpeningRepository openingRepository;

    public Iterable<Opening> findByRestaurant(int id) {
        return openingRepository.findByRestaurant(id);
    }

    public boolean createOpening (Opening opening) {
        this.openingRepository.save(opening);
        return true;
    }

    // try with AND without optional as return
    public Optional<Opening> getOpening(int id) {
        return this.openingRepository.findById(id);
    }

    public Iterable<Opening> findAll() {
        return this.openingRepository.findAll();
    }

    public Optional<Opening> updateOpening(Opening opening) {
        Optional<Opening> optionalOpening = getOpening(opening.getId());
        if (optionalOpening.isPresent()) {
        this.openingRepository.saveAndFlush(opening);
        return this.getOpening(opening.getId()); } else {
            return Optional.empty();
        }
    }

    public boolean deleteOpening(int id) {
        this.openingRepository.deleteById(id);
        return true;
    }
}
