package bus.example.busticket.repository;

import bus.example.busticket.model.UserBookings;
import jakarta.persistence.Version;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingsRepository extends JpaRepository<UserBookings, Integer> {

    @Query(value="select u from UserBookings u where u.user_id=:userId")
    @Version
    List<UserBookings> findByUserId(int userId);
}
