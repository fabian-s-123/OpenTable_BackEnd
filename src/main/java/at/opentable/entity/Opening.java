package at.opentable.entity;

import javax.persistence.*;
import java.sql.Time;


@Entity
public class Opening {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", referencedColumnName = "id", nullable = false)
    private Restaurant restaurant;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Weekday weekday;
    @Column(name = "start_opening", nullable = false)
    private Time startOpening;
    @Column(name = "end_opening", nullable = false)
    private Time endOpening;


    public enum Weekday {
        MONDAY,
        TUESDAY,
        WEDNESDAY,
        THURSDAY,
        FRIDAY,
        SATURDAY,
        SUNDAY
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Weekday getWeekday() {
        return weekday;
    }

    public void setWeekday(Weekday weekday) {
        this.weekday = weekday;
    }

    public Time getStartOpening() {
        return startOpening;
    }

    public void setStartOpening(Time startOpening) {
        this.startOpening = startOpening;
    }

    public Time getEndOpening() {
        return endOpening;
    }

    public void setEndOpening(Time endOpening) {
        this.endOpening = endOpening;
    }
}


