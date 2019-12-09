package at.opentable.controller;

import at.opentable.entity.Restaurant;
import at.opentable.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
public class RestaurantController {

    @Autowired
    RestaurantRepository restaurantRepository;

    public void createRestaurant(Restaurant restaurant) {
        restaurantRepository.save(restaurant);
    }

    public Optional updateRestaurant(Restaurant restaurant) {
        Optional<Restaurant> optionalRestaurant = getRestaurant(restaurant.getId());
        if (optionalRestaurant.isPresent()) {
            restaurantRepository.save(restaurant);
            return Optional.of(getRestaurant(restaurant.getId()));
        }
        return Optional.empty();
    }



    public Optional getRestaurant(int id) {
        return Optional.of(restaurantRepository.findById(id));
    }

    public Iterable<Restaurant> findAll() {
        return restaurantRepository.findAll();
    }

    public boolean deleteRestaurant(int id) {
        Optional<Restaurant> optionalRestaurant = getRestaurant(id);
        if (optionalRestaurant.isPresent()) {
            restaurantRepository.deleteById(id);
            return true;
        } else return false;
    }
}
