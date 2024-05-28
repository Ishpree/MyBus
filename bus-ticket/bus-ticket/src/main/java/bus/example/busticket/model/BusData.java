package bus.example.busticket.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name= "Reservation")
public class BusData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

//    @DateTimeFormat(pattern = "MM-DD-YYYY")
    private String filter_date;

    private String to_destination;

    private String from_location;

    private Double price;

    private String bus_name;

    private String time;

    private int total_seats;


    public int getAvailable_seats() {
        return available_seats;
    }

    public void setAvailable_seats(int available_seats) {
        this.available_seats = available_seats;
    }

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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }


    public List<Seats> getSeats() {
        return seats;
    }

    public void setSeats(List<Seats> seats) {
        this.seats = seats;
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

    public int getTotal_seats() {
        return total_seats;
    }

    public void setTotal_seats(int total_seats) {
        this.total_seats = total_seats;
    }

//    public Long getVersion() {
//        return version;
//    }
//
//    public void setVersion(Long version) {
//        this.version = version;
//    }

        @OneToMany(mappedBy = "seatKey.bus_id", fetch = FetchType.LAZY)
        @JsonManagedReference//This is used to prevent infinite loop when accessing bus data
        private List<Seats> seats;

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    private int available_seats;

    @Version
    private Long version;

}