package bus.example.busticket.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name="seat_no")
public class SeatNo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int my_id;

    private int booking_id;

    public String getMy_seat() {
        return my_seat;
    }

    public void setMy_seat(String my_seat) {
        this.my_seat = my_seat;
    }

    public int getMy_id() {
        return my_id;
    }

    public void setMy_id(int my_id) {
        this.my_id = my_id;
    }

    private String my_seat;

    public int getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(int booking_id) {
        this.booking_id = booking_id;
    }

    @ManyToOne
    @JoinColumn(name="id")
    @JsonBackReference //This is used to prevent a circular reference which causes an infinite loop when accessing bus data
    private UserBookings userBookings;

    public UserBookings getUserBookings() {
        return userBookings;
    }

    public void setUserBookings(UserBookings userBookings) {
        this.userBookings = userBookings;
    }
}
