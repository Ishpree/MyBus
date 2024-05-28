package bus.example.busticket.helper;

import bus.example.busticket.dto.BookingsDTO;
import bus.example.busticket.dto.MyBookingDTO;
import bus.example.busticket.model.BusData;
import bus.example.busticket.model.SeatNo;
import bus.example.busticket.model.UserBookings;
import bus.example.busticket.repository.BusDataRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class HelpBookingsdto {

    @Autowired
    BusDataRepository busDataRepository;

    public static BookingsDTO createBookingsDto(BusData busData) {
        BookingsDTO b = new BookingsDTO();
        SeatNo seatNo = new SeatNo();
        seatNo.setMy_id(1);
        seatNo.setBooking_id(0);
        seatNo.setMy_seat("");
        SeatNo[] myList = {seatNo};
        List<SeatNo> seatNoList = Arrays.asList(myList);

        b.setBus_name(busData.getBus_name());
        b.setFilter_date(busData.getFilter_date());
        b.setFrom_location(busData.getFrom_location());
        b.setTo_destination(busData.getTo_destination());
        b.setPrice(busData.getPrice());
        b.setNoOfPeople(0);
        b.setTime(busData.getTime());
        b.setTotal(0.0);
        b.setAvailable_seats(busData.getAvailable_seats());
        b.setSeats(busData.getSeats());
        b.setSeat_nos(seatNoList);
        b.setVersion(busData.getVersion());
        b.setId(busData.getId());

       return b;
    }

    public static MyBookingDTO createBookingsDTO(UserBookings myBooking, BusData busData) {

        MyBookingDTO bk = new MyBookingDTO();
        bk.setId(myBooking.getId());
        bk.setBus_name(myBooking.getBus_name());
        bk.setFilter_date(myBooking.getFilter_date());
        bk.setFrom_location(myBooking.getFrom_location());
        bk.setTo_destination(myBooking.getTo_destination());
        bk.setNoOfPeople(myBooking.getNo_of_people());
        bk.setTime(myBooking.getTime());
        bk.setTotal(myBooking.getTotal());
        bk.setSeat_nos(myBooking.getSeat_nos());
        bk.setBus_id(myBooking.getBus_id());
        bk.setSeats(busData.getSeats());



        if(myBooking.isTripStatus()==true) {
            bk.setTripStatus("Active");
        }
        else {
            bk.setTripStatus("Cancelled");
        }

        return bk;
    }

}
