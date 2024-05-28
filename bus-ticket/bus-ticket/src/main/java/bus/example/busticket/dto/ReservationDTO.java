package bus.example.busticket.dto;

import java.sql.Date;

    public class ReservationDTO {

        private String filter_date;

        private String to_destination;

        private String from_location;

        public String getTo_destination() {
            return to_destination;
        }

        public void setTo_destination(String to_destination) {
            this.to_destination = to_destination;
        }

        public String getFrom_location() {
            return from_location;
        }

        public void setFrom_location(String from_location) {
            this.from_location = from_location;
        }

        public String getFilter_date() {
            return filter_date;
        }

        public void setFilter_date(String filter_date) {
            this.filter_date = filter_date;
        }
    }

