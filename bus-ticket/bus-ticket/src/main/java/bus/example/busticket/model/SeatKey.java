package bus.example.busticket.model;

import jakarta.persistence.Embeddable;
import org.springframework.data.annotation.Id;

@Embeddable
public class SeatKey {
    public int getBus_id() {
        return bus_id;
    }

    public void setBus_id(int bus_id) {
        this.bus_id = bus_id;
    }

    public String getSeat_no() {
        return seat_no;
    }

    public void setSeat_no(String seat_no) {
        this.seat_no = seat_no;
    }

    @Id
    private int bus_id;

    @Id
    private String seat_no;

}
