package bus.example.busticket.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="user_bookings")
public class UserBookings {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int no_of_people ;

    private boolean tripStatus;

    private int bus_id;

    public boolean isTripStatus() {
        return tripStatus;
    }

    public void setTripStatus(boolean tripStatus) {
        this.tripStatus = tripStatus;
    }

    private Double total ;

    public List<SeatNo> getSeat_nos() {
        return seat_nos;
    }

    public void setSeat_nos(List<SeatNo> seat_nos) {
        this.seat_nos = seat_nos;
    }

    private String filter_date;

    private String to_destination;


    private String from_location;

    @OneToMany(mappedBy = "booking_id", fetch = FetchType.LAZY)
    @JsonManagedReference
    List<SeatNo> seat_nos;

    public String getBus_name() {
        return bus_name;
    }

    public void setBus_name(String bus_name) {
        this.bus_name = bus_name;
    }

    public String getFilter_date() {
        return filter_date;
    }

    public void setFilter_date(String filter_date) {
        this.filter_date = filter_date;
    }

    public String getFrom_location() {
        return from_location;
    }

    public void setFrom_location(String from_location) {
        this.from_location = from_location;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNo_of_people() {
        return no_of_people;
    }

    public void setNo_of_people(int no_of_people) {
        this.no_of_people = no_of_people;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTo_destination() {
        return to_destination;
    }

    public void setTo_destination(String to_destination) {
        this.to_destination = to_destination;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }



    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    private String bus_name;

    private int user_id;

    private String time;


    public int getBus_id() {
        return bus_id;
    }

    public void setBus_id(int bus_id) {
        this.bus_id = bus_id;
    }
}
