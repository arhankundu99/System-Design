package models;
import java.util.UUID;
import java.util.Date;

public class Show {
    String id;
    String movieId;
    String venueId;
    String screenId;
    Date startTime;
    Date endTime;

    public Show(String movieId, String venueId, String screenId, Date startTime, Date endTime) {
        this.movieId = movieId;
        this.venueId = venueId;
        this.screenId = screenId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public String getMovieId() {
        return movieId;
    }

    public String getVenueId() {
        return venueId;
    }

    public String getScreenId() {
        return screenId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }
}