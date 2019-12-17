package at.opentable.repository;

import at.opentable.entity.Opening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OpeningRepository extends JpaRepository<Opening, Integer> {

    @Query(value = "SELECT * FROM opening WHERE restaurant_id = ?1", nativeQuery = true)
    Iterable<Opening> findByRestaurant(int restaurant);
}
