package bus.example.busticket.controller;

import bus.example.busticket.dto.BookingsDTO;
import bus.example.busticket.dto.RegisteredUserDTO;
import bus.example.busticket.dto.ReservationDTO;
import bus.example.busticket.helper.HelpBookingsdto;
import bus.example.busticket.model.*;
import bus.example.busticket.repository.BusDataRepository;
import bus.example.busticket.repository.SeatNoRepository;
import bus.example.busticket.repository.SeatRepository;
import bus.example.busticket.repository.UserRepository;
import bus.example.busticket.service.DefaultUserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static org.hibernate.internal.CoreLogging.logger;

@Controller
@RequestMapping("/api/Dashboard")
public class DashboardController {

    private DefaultUserService userService;

    @Autowired
    private BusDataRepository busDataRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private SeatNoRepository seatNoRepository;

    public DashboardController(DefaultUserService userService) {
        super();
        this.userService = userService;
    }
    private static final Logger logger = LoggerFactory.getLogger(DashboardController.class);


//    @GetMapping
//    public String displayDashboard(){
//
//    }

    @PostMapping
    public ResponseEntity<?> displayBus(@RequestBody ReservationDTO reservationDTO)
    {
        try {
            List<BusData> busData = userService.findBus(reservationDTO);
            if(busData.isEmpty()) {
                return new ResponseEntity<>("No buses found", HttpStatus.OK);
            }
            System.out.println(busData);
            String name=user_name();
            System.out.println(name);
            System.out.println(busData);
            Map<String, Object> response = new HashMap<>();
            response.put("username", name);
            response.put("busData", busData);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error fetching bus data", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<?> bookingPage(@PathVariable int id)
    {
        Optional<BusData> busdata = busDataRepository.findById(id);
        BookingsDTO b = HelpBookingsdto.createBookingsDto(busdata.get());
        String name=user_name();
        System.out.println(name);
        System.out.println(busdata);
        Map<String, Object> response = new HashMap<>();
        response.put("username", name);
        response.put("busData", busdata);
        response.put("BookingDTO", b);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PostMapping("/Booking")
    @Transactional
    public ResponseEntity<?> bookingPage(@RequestBody BookingsDTO bookingsDTO)
    {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        String username = securityContext.getAuthentication().getName();
        System.out.println(username);
        Optional<BusData> optionalBusData = busDataRepository.findById(bookingsDTO.getId());
        System.out.println(optionalBusData);
        
        if (!optionalBusData.isPresent()) {
            // Handle error if BusData not found
            return new ResponseEntity<>("Bus data not found", HttpStatus.NOT_FOUND);
        }
        BusData busData= optionalBusData.get();
        System.out.println(busData);
        System.out.println(bookingsDTO.getSeats());
        if (busData.getVersion() != bookingsDTO.getVersion()) {
            // Handle concurrency conflict
            return new ResponseEntity<>("Seat no longer available. Please try selecting another seat.", HttpStatus.CONFLICT);
        }
        busData.setVersion(busData.getVersion() + 1);
        busData.setSeats(bookingsDTO.getSeats());
        List<Seats>s= new ArrayList<>();
        for (SeatNo seatNo : bookingsDTO.getSeat_nos()) {
            for (Seats seat: busData.getSeats()) {
                if (seat.getSeatKey().getSeat_no() == seatNo.getMy_seat()) {
                    seat.setAvailability(false);
                    // Update availability to booked
                    System.out.println(seat.isAvailability());
                }
               seatRepository.save(seat);
            }
            System.out.println(seatNo);
        }
        busData.setAvailable_seats(bookingsDTO.getAvailable_seats());
        busDataRepository.save(busData);
        UserBookings userBookings = userService.addBookings(bookingsDTO, username, busData);
        System.out.println(userBookings);
        int bookingId = userBookings.getId();
        System.out.println(bookingId);
        for(SeatNo seatNo : bookingsDTO.getSeat_nos()) {
            seatNo.setBooking_id(bookingId);
            seatNoRepository.save(seatNo);
        }
        Map<String, Object> response = new HashMap<>();
        response.put("BookingsDTO", bookingsDTO);
        response.put("booking_id", bookingId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    String user_name()
    {
        SecurityContext securityContext = SecurityContextHolder.getContext();
//      UserDetails userDetails = (UserDetails) securityContext.getAuthentication().getPrincipal();//        String username=userDetails.getUsername();
        String username = securityContext.getAuthentication().getName();
        System.out.println(username);
        User user = userRepository.findByEmail(username);
        String name= user.getName();
        System.out.println(name);
        return name;
    }

}
