package at.opentable.repository;

import at.opentable.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

    List<Restaurant> findByNameContaining(String name);
    List<Restaurant> findByCityIgnoreCaseContainingOrZipIgnoreCaseContaining(String city, String zip);
}