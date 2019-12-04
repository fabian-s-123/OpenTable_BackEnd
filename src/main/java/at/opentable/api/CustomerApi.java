package at.opentable.api;

import at.opentable.controller.AuthorizationController;
import at.opentable.dto.AuthenticationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CustomerApi {

    @Autowired
    private AuthorizationController authorizationController;

    @PostMapping("/auth/login")
    public ResponseEntity login(@RequestBody AuthenticationDTO authenticationDTO) {
        return authorizationController.login(authenticationDTO);
    }
}
