package at.opentable.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Holiday {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", referencedColumnName = "id", nullable = false)
    private Restaurant restaurant;

    @Column(name = "start_holiday", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date startHoliday;

    @Column(name = "end_holiday", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date endHoliday;

    private String comment;

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

    public Date getStartHoliday() {
        return startHoliday;
    }

    public void setStartHoliday(Date startHoliday) {
        this.startHoliday = startHoliday;
    }

    public Date getEndHoliday() {
        return endHoliday;
    }

    public void setEndHoliday(Date endHoliday) {
        this.endHoliday = endHoliday;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
