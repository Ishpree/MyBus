package bus.example.busticket.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.data.annotation.Id;

import java.util.List;

@Entity
@Table(name="seats")
public class Seats {

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    @EmbeddedId
    private SeatKey seatKey;

    private boolean availability;


    @ManyToOne
    @JoinColumn(name="id")
    @JsonBackReference //This is used to prevent a circular reference which causes an infinite loop when accessing bus data
    private BusData busData;

    public BusData getBusData() {
        return busData;
    }

    public void setBusData(BusData busData) {
        this.busData = busData;
    }

    public SeatKey getSeatKey() {
        return seatKey;
    }

    public void setSeatKey(SeatKey seatKey) {
        this.seatKey = seatKey;
    }
}
