package at.opentable.repository;

import at.opentable.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

public interface ReservationRepository extends JpaRepository<Reservation,Integer> {

    @Query(value = "")
    public Boolean checkTeburuAvailability();

    @Query(value="SELECT * FROM reservation WHERE id = ?1", nativeQuery = true)
    public Reservation findByCustomerId(int id);

    @Query(value="")
    public Reservation findByRestaurantDate(Date restaurantDate);

    @Query(value="")
    public Reservation findByCustomerDate(Date customerDate);



}
