package models;
import java.util.UUID;

public class Seat {
    private String seatNum;
    private String id;
    private String screenId;

    public Seat(String screenId, String seatNum) {
        this.id = UUID.randomUUID().toString();
        this.screenId = screenId;
        this.seatNum = seatNum;
    }

    public String getSeatNum() {
        return seatNum;
    }

    public String getId() {
        return id;
    }

    public String getScreenId() {
        return screenId;
    }
}