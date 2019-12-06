package at.opentable.controller;

import at.opentable.entity.Teburu;
import at.opentable.repository.TeburuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TeburuController {

    @Autowired
    private TeburuRepository teburuRepository;

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


    /**
     * hacky, potential removal
     * @param id
     * @return
     */
    public Teburu getTeburu2(int id) {
        return this.teburuRepository.findTeburuNoOptional(id);
    }


    public Iterable<Teburu> findAll() {
        return teburuRepository.findAll();
    }

    public boolean deleteTeburu(int id) {
        Optional<Teburu> optionalTeburu = getTeburu(id);
        if (optionalTeburu.isPresent()) {
            teburuRepository.deleteById(id);
            return true;
        } else return false;
    }


    /**
     * Returns a list with all Teburus a restaurant has
     *
     * @param restaurantId
     * @return Iterable<Teburu> per restaurant
     */
    public Iterable<Integer> findAllTeburuByRestaurantId(int restaurantId) {
        return this.teburuRepository.findAllTeburuByRestaurantIdRepo(restaurantId);
    }
}