package at.opentable.controller;

<<<<<<< HEAD
=======
import at.opentable.entity.Restaurant;
>>>>>>> af7c6081b295504781ef6e171882178530102db1
import at.opentable.entity.Teburu;
import at.opentable.repository.TeburuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TeburuController {

    @Autowired
<<<<<<< HEAD
    private TeburuRepository teburuRepository;
=======
    TeburuRepository teburuRepository;
>>>>>>> af7c6081b295504781ef6e171882178530102db1

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

<<<<<<< HEAD

    /**
     * hacky, potential removal
     * @param id
     * @return
     */
    public Teburu getTeburu2(int id) {
        return this.teburuRepository.findTeburuNoOptional(id);
    }


=======
>>>>>>> af7c6081b295504781ef6e171882178530102db1
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
<<<<<<< HEAD


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
=======
}
>>>>>>> af7c6081b295504781ef6e171882178530102db1
