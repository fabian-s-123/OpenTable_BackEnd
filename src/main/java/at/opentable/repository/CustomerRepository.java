package at.opentable.repository;

import at.opentable.entity.Customer;
import at.opentable.entity.Teburu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}
