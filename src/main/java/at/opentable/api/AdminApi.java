package at.opentable.api;

import at.opentable.controller.AdminController;
import at.opentable.dto.AdminDTO;
import at.opentable.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/admins")

public class AdminApi {

    @Autowired
    private AdminController adminController;


    @PostMapping
    public ResponseEntity<Admin> createAdmin(@RequestBody Admin admin)
    {
        boolean success = this.adminController.createAdmin(admin);
        if (success) {
            return new ResponseEntity<Admin>(HttpStatus.OK);
        }
        return new ResponseEntity<Admin>(HttpStatus.FORBIDDEN);
    }
    

    @GetMapping
    public Iterable<Admin> findAll() {
        return this.adminController.findAll();
    }

    @GetMapping(path = "/{id}")
    public Optional<Admin> getAdmin(@PathVariable int id) {
        return this.adminController.getAdmin(id);
    }
    //http://localhost:8080/api/admins?restaurantId=x
    @GetMapping
    public Iterable<Admin> getAdminsByRestaurantId(@RequestParam (required = false) int restaurantId)
    {
        return this.adminController.getAdminsByRestaurantId(restaurantId);
    }

    //http://localhost:8080/api/admins/owner?restaurantId=x
    @GetMapping(path="/owner")
    public Optional<Admin> getOwnerByRestaurantId(@RequestParam(required = false) int restaurantId)
    {

    }


    @PutMapping
    public ResponseEntity<Admin> updateAdmin(@RequestBody Admin admin) {
        boolean success = this.adminController.updateAdmin(admin);
        if (success) {
            return new ResponseEntity<Admin>(HttpStatus.OK);
        }
        return new ResponseEntity<Admin>(HttpStatus.FORBIDDEN);
    }

    @DeleteMapping
    public ResponseEntity<Admin> deleteAdmin(@RequestBody Admin admin) {
        boolean success = this.adminController.deleteAdmin(admin);
        if (success) {
            return new ResponseEntity<Admin>(HttpStatus.OK);
        }
        return new ResponseEntity<Admin>(HttpStatus.FORBIDDEN);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Admin> deleteAdminById(@PathVariable int id) {
        boolean success = this.adminController.deleteAdminById(id);
        if (success) {
            return new ResponseEntity<Admin>(HttpStatus.OK);
        }
        return new ResponseEntity<Admin>(HttpStatus.FORBIDDEN);
    }



}
