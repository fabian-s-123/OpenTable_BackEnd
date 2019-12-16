package at.opentable.controller;

import at.opentable.dto.OpeningDTO;
import at.opentable.entity.Opening;
import at.opentable.repository.OpeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class OpeningController {

    @Autowired
    private OpeningRepository openingRepository;

    public Iterable<OpeningDTO> findByRestaurant(int id) {
        Iterable<Opening> openings = openingRepository.findByRestaurant(id);
        ArrayList<OpeningDTO> openingDTOS = new ArrayList<>();
        for (Opening opening: openings) {
            openingDTOS.add(new OpeningDTO(opening));
        } return openingDTOS;
    }

    public boolean createOpening (Opening opening) {
        this.openingRepository.save(opening);
        return true;
    }


    public Optional<Opening> getOpening(int id) {
        return this.openingRepository.findById(id);
    }

    public Iterable<OpeningDTO> findAll() {
        Iterable<Opening> openings = this.openingRepository.findAll();
        ArrayList<OpeningDTO> openingDTOS = new ArrayList<>();
        for (Opening opening : openings) {
            openingDTOS.add(new OpeningDTO(opening));
        }
        return openingDTOS;
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
