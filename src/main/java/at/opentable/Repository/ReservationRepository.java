package at.opentable.Repository;

import at.opentable.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation,Integer> {

    public Boolean checkTablaAvailability();

    public Reservation findByCustomerId(int id);

    public Reservation findByRestaurantDate();

}
