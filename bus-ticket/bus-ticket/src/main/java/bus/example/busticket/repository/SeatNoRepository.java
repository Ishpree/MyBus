package bus.example.busticket.repository;

import bus.example.busticket.model.SeatNo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.authentication.jaas.JaasPasswordCallbackHandler;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatNoRepository extends JpaRepository<SeatNo, Integer> {
}
