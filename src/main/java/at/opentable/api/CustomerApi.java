package at.opentable.api;

import at.opentable.controller.CustomerController;
import at.opentable.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping(path="/api/customers")
public class CustomerApi {

    @Autowired
    private CustomerController customerController;

    @PostMapping
    public ResponseEntity createCustomer(@RequestBody Customer customer) {
        boolean success = this.customerController.createCustomer(customer);
        if (success) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @GetMapping
    public Iterable<Customer> findAll() {
        return this.customerController.findAll();
    }

    @GetMapping(path="/{id}")
    public Optional<Customer> getCustomer(@PathVariable int id){
        return this.customerController.getCustomer(id);
    }

    @PutMapping
    public ResponseEntity updateCustomer(@RequestBody Customer customer) {
        boolean success = this.customerController.updateCustomer(customer);
        if (success) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @DeleteMapping
    public ResponseEntity deleteCustomer(@RequestBody Customer customer){
        boolean success = this.customerController.deleteCustomer(customer);
        if (success) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @DeleteMapping(path="/{id}")
    public ResponseEntity deleteCustomerById(@PathVariable int id) {
        boolean success = this.customerController.deleteCustomerById(id);
        if (success) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}
