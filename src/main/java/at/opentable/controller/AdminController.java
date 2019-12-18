package at.opentable.controller;

import at.opentable.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import at.opentable.repository.AdminRepository;

import java.util.Optional;

@Controller
public class AdminController {

    @Autowired
    private AdminRepository adminRepository;

    public boolean createAdmin(Admin admin) {
        this.adminRepository.save(admin);
        System.out.println("Admin successfully created.");
        return true;
    }

    public Iterable<Admin> findAll() {
        return this.adminRepository.findAll();
    }

    public Optional<Admin> getAdmin (int id) {
        return this.adminRepository.findById(id);
    }

    public Iterable<Admin> getAdminsByRestaurantId(int restaurantId)
    {
        return this.adminRepository.findByRestaurantId(restaurantId);

    }

    public Admin getOwnerByRestaurantId(int restaurantId)
    {
        return this.adminRepository.findByRestaurantOwnerAbility(restaurantId);
    }

    public boolean updateAdmin(Admin admin) {
        this.adminRepository.saveAndFlush(admin);
        System.out.println("Admin successfully updated.");
        return true;
    }

    public boolean deleteAdmin(Admin admin) {
        this.adminRepository.delete(admin);
        System.out.println("Admin successfully deleted.");
        return true;
    }

    public boolean deleteAdminById(int id) {
        this.adminRepository.deleteById(id);
        System.out.println("Admin successfully deleted.");
        return true;
    }

}
