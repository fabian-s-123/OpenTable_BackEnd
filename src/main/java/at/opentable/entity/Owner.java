package at.opentable.entity;

import javax.persistence.*;

@Entity
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    private int restaurantId;

}
