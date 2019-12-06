package at.opentable.repository;

import at.opentable.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    @Query(value = "SELECT * FROM reservation WHERE start_date_time >= ?1 AND end_date_time < ?2 AND table_id = ?3", nativeQuery = true)
    Reservation checkReservationRepo (Timestamp timeStart, Timestamp timeEnd, int tableId);
}
