package at.opentable.controller;

import at.opentable.entity.Customer;
import at.opentable.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
public class CustomerController {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CustomerRepository customerRepository;

    public boolean createCustomer(Customer customer) {
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        this.customerRepository.save(customer);
        System.out.println("Customer successfully created.");
        return true;
    }

    public Iterable<Customer> findAll() {
        return this.customerRepository.findAll();
    }

    public Optional<Customer> getCustomer (int id) {
        return this.customerRepository.findById(id);
    }

    public boolean updateCustomer(Customer customer) {
        this.customerRepository.saveAndFlush(customer);
        System.out.println("Customer successfully updated.");
        return true;
    }

    public boolean deleteCustomer(Customer customer) {
        this.customerRepository.delete(customer);
        System.out.println("Customer successfully deleted.");
        return true;
    }

    public boolean deleteCustomerById(int id) {
        this.customerRepository.deleteById(id);
        System.out.println("Customer successfully deleted.");
        return true;
    }
}
