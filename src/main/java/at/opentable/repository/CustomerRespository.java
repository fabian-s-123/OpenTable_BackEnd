package at.opentable.repository;

import at.opentable.entity.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CustomerRespository extends CrudRepository<Customer, Integer> {
    public Optional<Customer> findByEmailContaining(String email);

    @Query(value="SELECT * FROM customer WHERE email LIKE :email LIMIT 1", nativeQuery=true)
    Optional<Customer> FindByEmail(@Param("email") String email);
}
