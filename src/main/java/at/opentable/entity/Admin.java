package at.opentable.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

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

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="restaurant_id", referencedColumnName = "id",nullable = false)
    private Restaurant restaurant;

    @Column(name="email")
    private String email;

    @Column(name="is_owner")
    private Boolean isOwner;





}
