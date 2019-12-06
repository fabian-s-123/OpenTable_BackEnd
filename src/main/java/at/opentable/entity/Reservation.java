package at.opentable.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "table_id", referencedColumnName = "id", nullable = false)
    private Teburu teburu;

    @Column(name = "start_date_time", nullable = false)
    //@Temporal(TemporalType.TIMESTAMP)
    private Timestamp startDateTime;

    @Column(name = "end_date_time", nullable = false)
    //@Temporal(TemporalType.TIMESTAMP)
    private Timestamp endDateTime;

    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "group_size", nullable = false)
    private int groupSize;

    public Reservation () {
    }

    public Reservation(Teburu teburu, Timestamp startDateTime, Timestamp endDateTime, Customer customer, int groupSize) {
        this.teburu = teburu;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.customer = customer;
        this.groupSize = groupSize;
    }

    public Reservation(int id, Optional<Teburu> teburu, Timestamp time, Timestamp endTime, Optional<Customer> customer, int groupSize) {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Teburu getTeburu() {
        return teburu;
    }

    public void setTeburu(Teburu teburu) {
        this.teburu = teburu;
    }

    public Timestamp getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(Timestamp startDateTime) {
        this.startDateTime = startDateTime;
    }

    public Timestamp getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(Timestamp endDateTime) {
        this.endDateTime = endDateTime;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public int getGroupSize() {
        return groupSize;
    }

    public void setGroupSize(int groupSize) {
        this.groupSize = groupSize;
    }
}
