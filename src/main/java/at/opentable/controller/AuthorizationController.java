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
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Repository;

import java.security.Key;
import java.util.Optional;

@Controller
public class AuthorizationController {
    private Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    CustomerRespository customerRespository;

    public ResponseEntity login(AuthenticationDTO authenticationDTO) {
        AuthorizedCustomerDTO currentCustomer = new AuthorizedCustomerDTO();

        Optional<Customer> customer = customerRespository.FindByEmail(authenticationDTO.getEmail());
        System.out.println(customer);
        if(customer.isPresent()) {
            if(authenticationDTO.getPassword().equals(customer.get().getPassword())) {
                currentCustomer.setId(customer.get().getId());
                currentCustomer.setEmail(customer.get().getEmail());
                currentCustomer.setFirstName(customer.get().getFirstName());
                currentCustomer.setLastName(customer.get().getLastName());
                currentCustomer.setTelephone(customer.get().getTelephone());

                String jws = Jwts.builder().setSubject(currentCustomer.getEmail()).signWith(key).compact();
                currentCustomer.setJws(jws);
                return new ResponseEntity(modelMapper.map(currentCustomer, AuthorizedCustomerDTO.class), HttpStatus.OK);
            }
        }

        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    public boolean isAuthorized(String token) {
        try {
            String result = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody().getSubject();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
