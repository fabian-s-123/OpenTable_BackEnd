package at.opentable.api;

import at.opentable.controller.AuthorizationController;
import at.opentable.dto.AuthenticationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class AuthorizationApi {

    @Autowired
    private AuthorizationController authorizationController;

    @PostMapping("/auth/login")
    public ResponseEntity login(@RequestBody AuthenticationDTO authenticationDTO) {
        return authorizationController.login(authenticationDTO);
    }
}
