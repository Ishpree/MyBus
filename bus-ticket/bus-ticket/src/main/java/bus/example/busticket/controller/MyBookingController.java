package bus.example.busticket.controller;

import bus.example.busticket.dto.BookingsDTO;
import bus.example.busticket.dto.MyBookingDTO;
import bus.example.busticket.helper.HelpBookingsdto;
import bus.example.busticket.model.*;
import bus.example.busticket.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Controller
@RequestMapping("/api/MyBooking")
public class MyBookingController {


    @Autowired
    BookingsRepository bookingsRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SeatNoRepository seatNoRepository;

    @Autowired
    BusDataRepository busDataRepository;

    @Autowired
    SeatRepository seatRepository;


    @GetMapping
    public ResponseEntity<?>getAllBookings()
    {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        String username = securityContext.getAuthentication().getName();
        User user =userRepository.findByEmail(username);
        List<MyBookingDTO> bks = new ArrayList<MyBookingDTO>();
        List<UserBookings> b= bookingsRepository.findByUserId(user.getId());
        for (UserBookings bookings : b) {
            Optional<BusData> optionalBusData = busDataRepository.findById(bookings.getBus_id());
            BusData busData= optionalBusData.get();
            MyBookingDTO bkks = HelpBookingsdto.createBookingsDTO(bookings,busData);
            bks.add(bkks);
        }
        Map<String, Object> response = new HashMap<>();
        response.put("username", user.getName());
        response.put("MyBookingDTO", bks);
        System.out.println(b);
        System.out.println(user.getName());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/cancel")
    public ResponseEntity<?>cancelBookings(@RequestBody MyBookingDTO myBookingDTO) {
        Optional<UserBookings> booking = bookingsRepository.findById(myBookingDTO.getId());
        Optional<BusData> optionalBusData = busDataRepository.findById(myBookingDTO.getBus_id());
        BusData busData= optionalBusData.get();
        System.out.println(optionalBusData);
        if (booking.get().isTripStatus() == false) {
            setData(booking.get());
        }
        else {
            setData(booking.get());
            booking.get().setTripStatus(false);
            bookingsRepository.save(booking.get());
            for(SeatNo seatNo : booking.get().getSeat_nos()) {
                seatNoRepository.deleteById(seatNo.getMy_id());
            }
            busData.setSeats(myBookingDTO.getSeats());
            List<Seats>s= new ArrayList<>();
            int c=0;
            for (SeatNo seatNo : myBookingDTO.getSeat_nos()) {
                c++;
                for (Seats seat : busData.getSeats()) {
                    if (seat.getSeatKey().getSeat_no() == seatNo.getMy_seat()) {
                        seat.setAvailability(true);
                        // Update availability to booked
                        System.out.println(seat.isAvailability());
                    }
                    seatRepository.save(seat);
                }
            }
            busData.setAvailable_seats(myBookingDTO.getAvailable_seats()+c);
            busDataRepository.save(busData);
        }
         return new ResponseEntity<>("Cancellation Successful", HttpStatus.OK);
    }

        private void setData(UserBookings booking) {
        Optional<User> users = userRepository.findById(booking.getUser_id());
        List<UserBookings> bs = bookingsRepository.findByUserId(users.get().getId());
        Collections.reverse(bs);

    }
    }




