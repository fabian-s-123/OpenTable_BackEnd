package at.opentable.controller;

import at.opentable.Repository.CustomerRespository;
import at.opentable.dto.AuthenticationDTO;
import at.opentable.dto.AuthorizedCustomerDTO;
import at.opentable.entity.Customer;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Optional;

@Controller
public class AuthorizationController {
    private Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    CustomerRespository customerRepository;

    public ResponseEntity login(AuthenticationDTO authenticationDTO) {
        AuthorizedCustomerDTO currentCustomer = new AuthorizedCustomerDTO();

        Optional<Customer> customer = customerRepository.findByEmail(authenticationDTO.getEmail());

        if(customer.isPresent()) {
            if(authenticationDTO.getPassword().equals(customer.get().getPassword())) {
                currentCustomer.setId(customer.get().getId());

                String jws = Jwts.builder().setSubject(currentCustomer.getId().toString()).signWith(key).compact();
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
