package bus.example.busticket.dto;

import bus.example.busticket.model.SeatNo;
import bus.example.busticket.model.Seats;
import jakarta.persistence.Version;
import lombok.Data;

import java.util.List;


public class BookingsDTO {
    private String filter_date;


    private String to_destination;

    private String from_location;

    private int id;

    private String tripStatus;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getNoOfPeople() {
        return noOfPeople;
    }

    public void setNoOfPeople(int noOfPeople) {
        this.noOfPeople = noOfPeople;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
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

    public List<Seats> getSeats() {
        return seats;
    }

    public void setSeats(List<Seats> seats) {
        this.seats = seats;
    }

    private Double price;

    public int getAvailable_seats() {
        return available_seats;
    }

    public void setAvailable_seats(int available_seats) {
        this.available_seats = available_seats;
    }


    private String bus_name;

    private String time;

    public Long getVersion() {
        return version;
    }

    public List<SeatNo> getSeat_nos() {
        return seat_nos;
    }

    public void setSeat_nos(List<SeatNo> seat_nos) {
        this.seat_nos = seat_nos;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getTripStatus() {
        return tripStatus;
    }

    public void setTripStatus(String tripStatus) {
        this.tripStatus = tripStatus;
    }

    private int noOfPeople ;

    private Double total ;

    private List<Seats> seats;

    private int available_seats;

    private List<SeatNo>seat_nos;

    @Version
    private Long version;
}
