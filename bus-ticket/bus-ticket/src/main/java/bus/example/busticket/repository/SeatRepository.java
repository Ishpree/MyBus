package bus.example.busticket.repository;

import bus.example.busticket.model.BusData;
import bus.example.busticket.model.Seats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatRepository extends JpaRepository<Seats, Integer> {




}
