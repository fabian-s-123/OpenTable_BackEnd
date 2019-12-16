package at.opentable.controller;

import at.opentable.entity.Restaurant;
import at.opentable.entity.Teburu;
import at.opentable.repository.TeburuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TeburuController {

    @Autowired
    private TeburuRepository teburuRepository;
    @Autowired
    private RestaurantController restaurantController;

    public void createTeburu(Teburu teburu) {
        Teburu objEntity = teburuRepository.save(teburu);
        Optional<Restaurant> optionalRestaurant = this.restaurantController.getRestaurant(teburu.getRestaurant().getId());
        if (optionalRestaurant.isPresent()) {
            Restaurant restaurant = optionalRestaurant.get();
            restaurant.getTeburu().add(objEntity);
            this.restaurantController.updateRestaurant(restaurant);
        }
    }

    public Optional updateTeburu(Teburu teburu) {
        Optional<Teburu> optionalTeburu = getTeburu(teburu.getId());
        if (optionalTeburu.isPresent()) {
            teburuRepository.save(teburu);
            return Optional.of(getTeburu(teburu.getId()));
        }
        return Optional.empty();
    }

    public Optional<Teburu> getTeburu(int id) {
        return teburuRepository.findById(id);
    }

    public Iterable<Teburu> findAll() {
        return teburuRepository.findAll();
    }

    public boolean deleteTeburu(int id) {
        Teburu teburu = getTeburu(id).get();
        Optional<Restaurant> optionalRestaurant = this.restaurantController.getRestaurant(teburu.getRestaurant().getId());
        if (getTeburu(id).isPresent()) {
            Restaurant restaurant = optionalRestaurant.get();
            restaurant.getTeburu().remove(teburu);
            this.restaurantController.updateRestaurant(restaurant);
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

