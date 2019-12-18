package at.opentable.api;

import at.opentable.controller.AdminController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/admins")

public class AdminApi {

    @Autowired
    private AdminController adminController;


}
