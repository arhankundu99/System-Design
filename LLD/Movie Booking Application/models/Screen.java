package models;
import java.util.UUID;

public class Screen {
    private String id;
    private String venueId;
    private String desc;
    private List<Seat> seats;

    public Screen(String venueId, String desc) {
        this.venueId = venueId;
        this.desc = desc;
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public String getVenueId() {
        return venueId;
    }

    public String getDesc() {
        return desc;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }
}