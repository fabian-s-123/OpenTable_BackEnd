package at.opentable.Repository;

import at.opentable.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.util.Date;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {


    @Query(value = "SELECT * FROM reservation WHERE start_date_time >= ?1 AND end_date_time < ?2 AND table_id = ?3", nativeQuery = true)
    Reservation checkReservationRepo (Timestamp timeStart, Timestamp timeEnd, int teburuId);

    @Query(value="SELECT ?1 FROM teburu  WHERE restaurant_id = ?2",nativeQuery = true)
    Boolean checkTeburuAvailibility(int teburuId,int restaurantId);
    //die Tabelle reservation anstatt teburu abfragen?

    @Query(value="SELECT * FROM reservation WHERE id = ?1",nativeQuery = true)
    Reservation findByCustomerId(int id);

    @Query(value="SELECT * FROM reservation WHERE start_date_time >= ?1 AND end_date_time < ?2",nativeQuery = true)
    Reservation findByRestaurantDate(Date dateStart, Date dateEnd); // Time anstatt Date vielleicht?

    @Query(value="SELECT * FROM reservation WHERE start_date_time >= ?1 AND end_date_time < ?2 AND customer_id = ?3",nativeQuery = true)
    Reservation findByCustomerDate(Date dateStart, Date dateEnd, int customerId);

}
