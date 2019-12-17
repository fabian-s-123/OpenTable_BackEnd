package at.opentable.controller;

import at.opentable.entity.Restaurant;
import at.opentable.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
public class RestaurantController {

    @Autowired
    private RestaurantRepository restaurantRepository;

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
        return restaurantRepository.findById(id);
    }

    public Iterable<Restaurant> findAll() {
        return restaurantRepository.findAll();
    }

    public List<Restaurant> findRestaurantByName(String name) {return restaurantRepository.findByNameContaining(name);}

    public List<Restaurant> findRestaurantByCityOrZip(String city, String zip) {return restaurantRepository.findByCityIgnoreCaseContainingOrZipIgnoreCaseContaining(city, zip);}

    public boolean deleteRestaurant(int id) {
        Optional<Restaurant> optionalRestaurant = getRestaurant(id);
        if (optionalRestaurant.isPresent()) {
            restaurantRepository.deleteById(id);
            return true;
        } else return false;
    }
}