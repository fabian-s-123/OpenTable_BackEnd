package at.opentable.repository;

import at.opentable.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Integer> {


    @Query(value="SELECT * FROM admin WHERE restaurant_id = ?1",nativeQuery = true)
    Iterable<Admin> findByRestaurantId(int id);

    @Query(value="SELECT * FROM admin WHERE restaurant_id = ?1 AND is_owner = 1",nativeQuery = true)
    Admin findByRestaurantOwnerAbility(int id);
}
