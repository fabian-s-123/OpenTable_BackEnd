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

    @Column(name="password")
    private String password;

    @ManyToMany
    @JoinColumn(name="restaurant_id", referencedColumnName = "id",nullable = false)
    private List<Restaurant> restaurants;

    @Column(name="email")
    private String email;

    @Column(name="is_owner")
    private Boolean isOwner;



}
