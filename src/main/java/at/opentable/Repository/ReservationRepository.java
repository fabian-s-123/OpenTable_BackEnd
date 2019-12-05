package at.opentable.repository;

import at.opentable.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface ReservationRepository extends JpaRepository<Reservation,Integer> {

    public Boolean checkTablaAvailability();

    public Reservation findByCustomerId(int id);

    public Reservation findByRestaurantDate(Date restaurantDate);

    public Reservation findByCustomerDate(Date customerDate);



}
