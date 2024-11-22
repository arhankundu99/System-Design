package models;
import java.util.UUID;
public class Ticket {
    String id;
    String showId;
    String venueId;
    String screenId;
    boolean isBooked;
    String seatNum;

    public Ticket(String showId, String venueId, String screenId, String seatNum) {
        this.id = UUID.randomUUID().toString();
        this.showId = showId;
        this.venueId = venueId;
        this.screenId = screenId;
        this.seatNum = seatNum;
        this.isBooked = false;
    }

    public String getId() {
        return id;
    }

    public String getShowId() {
        return showId;
    }

    public String getVenueId() {
        return venueId;
    }

    public boolean getIsBooked() {
        return isBooked;
    }

    public String getScreenId() {
        return screenId;
    }

    public String getSeatNum() {
        return seatNum;
    }

    public void setIsBooked(boolean isBooked) {
        this.isBooked = isBooked;
    }
}