package bus.example.busticket.repository;

import bus.example.busticket.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{


    @Query
    User findByEmail(String email);


}

