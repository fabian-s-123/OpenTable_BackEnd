package at.opentable.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @OneToMany
    @JoinColumn(name="restaurant_id", referencedColumnName = "id",nullable = false)
    private List<Restaurant> restaurants;

    private String email;

    private Boolean isAdmin;



}
