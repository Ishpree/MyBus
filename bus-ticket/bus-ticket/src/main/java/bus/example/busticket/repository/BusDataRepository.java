package bus.example.busticket.repository;
import bus.example.busticket.model.BusData;
import bus.example.busticket.model.Role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface BusDataRepository extends JpaRepository<BusData, Integer> {

        @Query(value = "select b.* from reservation b where b.to_destination =:to and b.from_location =:from and b.filter_date =:date order By b.filter_date desc", nativeQuery = true)
        List<BusData> findByToFromAndDate(String to , String from, String date);

//    select b.* from reservation b inner join seats s on b.id = s.bus_id where b.to_destination =:to and b.from_location =:from and b.filter_date =:date order By b.filter_date desc", nativeQuery = true)

    }

