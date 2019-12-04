package at.opentable.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "table_id", referencedColumnName = "id", nullable = false)
    private Teburu teburu;

    @Column(name = "start_date_time", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date startDateTime;

    @Column(name = "end_date_time", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date endDateTime;

    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "group_size", nullable = false)
    private int groupSize;

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

    public Date getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(Date startDateTime) {
        this.startDateTime = startDateTime;
    }

    public Date getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(Date endDateTime) {
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
