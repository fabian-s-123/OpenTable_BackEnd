package at.opentable.repository;

import at.opentable.entity.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface HolidayRepository extends JpaRepository<Holiday, Integer> {

    @Query(value = "SELECT * FROM holiday WHERE restaurant_id = ?1", nativeQuery = true)
    Iterable<Holiday> findByRestaurant(int restaurant);
}
