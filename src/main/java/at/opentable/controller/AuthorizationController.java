package at.opentable.controller;

import at.opentable.dto.AuthenticationDTO;
import at.opentable.dto.AuthorizedCustomerDTO;
import at.opentable.entity.Customer;
import at.opentable.repository.CustomerRespository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
public class AuthorizationController {
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    CustomerRespository customerRespository;

    public ResponseEntity login(AuthenticationDTO authenticationDTO) {
        Optional<Customer> customer = customerRespository.findByEmail(authenticationDTO.getEmail());
        if(customer.isPresent()) {
            if(authenticationDTO.getPassword().equals(customer.get().getPassword())) {
                return  new ResponseEntity(modelMapper.map(customer.get(), AuthorizedCustomerDTO.class), HttpStatus.OK);
            }
        }

        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
}
