package bus.example.busticket.repository;

import bus.example.busticket.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>{


    @Query
    Role findByRole(String name);
}
