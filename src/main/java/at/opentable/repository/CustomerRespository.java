package at.opentable.repository;

import at.opentable.entity.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CustomerRespository extends CrudRepository<Customer, Integer> {
    public Optional<Customer> findByEmail(String email);
}
