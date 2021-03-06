package at.opentable.api;

import at.opentable.controller.RestaurantController;
import at.opentable.dto.Image;
import at.opentable.entity.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping(path = "/api")
public class RestaurantApi {

    @Autowired
    private RestaurantController restaurantController;

    @PostMapping("/restaurants")
    public void createRestaurant(@RequestBody Restaurant restaurant) {
        restaurantController.createRestaurant(restaurant);
    }

    @PutMapping("/restaurants")
    public Optional updateRestaurant(@RequestBody Restaurant restaurant) {
        return Optional.of(restaurantController.updateRestaurant(restaurant));
    }

    @GetMapping("/restaurants")
    public Iterable<Restaurant> getAll() {
        return restaurantController.findAll();
    }

    @GetMapping("/restaurants/{id}")
    public Optional getRestaurant(@PathVariable int id) {
        return Optional.of(restaurantController.getRestaurant(id));
    }

    @GetMapping("/restaurants/name={name}")
    public List<Restaurant> findRestaurantByName(@PathVariable String name) {return restaurantController.findRestaurantByName(name);}

    @GetMapping("/restaurants/city={city}/zip={zip}")
    public List<Restaurant> findRestaurantByCityOrZip(@PathVariable String city, @PathVariable String zip) {
        return restaurantController.findRestaurantByCityOrZip(city, zip);
    }

    @DeleteMapping("/restaurants/{id}")
    public ResponseEntity deleteRestaurant(@PathVariable int id) {
        if(restaurantController.deleteRestaurant(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}
