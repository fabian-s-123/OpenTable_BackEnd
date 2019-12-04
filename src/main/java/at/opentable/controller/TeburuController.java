package at.opentable.controller;

import at.opentable.entity.Teburu;
import at.opentable.repository.TeburuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TeburuController {

    @Autowired
    TeburuRepository teburuRepository;

    public void createTeburu(Teburu teburu) {
        teburuRepository.save(teburu);
    }

    public Optional updateTeburu(Teburu teburu) {
        Optional<Teburu> optionalTeburu = getTeburu(teburu.getId());
        if (optionalTeburu.isPresent()) {
            teburuRepository.save(teburu);
            return Optional.of(getTeburu(teburu.getId()));
        }
        return Optional.empty();
    }

    public Optional getTeburu(int id) {
        return Optional.of(teburuRepository.findById(id));
    }

    public Iterable<Teburu> findAll() {
        return teburuRepository.findAll();
    }

}
