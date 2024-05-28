package bus.example.busticket.service;

import bus.example.busticket.dto.BookingsDTO;
import bus.example.busticket.dto.RegisteredUserDTO;
import bus.example.busticket.dto.ReservationDTO;
import bus.example.busticket.model.BusData;
import bus.example.busticket.model.User;
import bus.example.busticket.model.UserBookings;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;


//UserDetailsService is used by DaoAuthenticationProvider to retrieve a username, password
public interface DefaultUserService extends UserDetailsService {

    User save(RegisteredUserDTO registeredUserDTO);

    List<BusData> findBus(ReservationDTO reservationDTO);
    UserBookings addBookings(BookingsDTO bookingDTO, String username, BusData busData);
}
