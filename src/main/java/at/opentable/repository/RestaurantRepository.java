package at.opentable.repository;

import at.opentable.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

    Optional<Restaurant> findOrderByName(String name);
}