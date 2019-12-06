package at.opentable.repository;

import at.opentable.entity.Teburu;
import org.springframework.data.jpa.repository.JpaRepository;
<<<<<<< HEAD
import org.springframework.data.jpa.repository.Query;

public interface TeburuRepository extends JpaRepository<Teburu, Integer> {

    @Query(value = "SELECT id FROM teburu WHERE restaurant_id = ?1", nativeQuery = true)
    Iterable<Integer> findAllTeburuByRestaurantIdRepo(int restaurantId);

    @Query(value = "SELECT * FROM teburu WHERE id = ?1", nativeQuery = true)
    Teburu findTeburuNoOptional(int teburuId);
=======

public interface TeburuRepository extends JpaRepository<Teburu, Integer> {
>>>>>>> af7c6081b295504781ef6e171882178530102db1
}
