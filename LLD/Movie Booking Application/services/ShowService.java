package services;
import java.util.HashMap;
import java.util.Map;
import models.Show;
import exceptions.NotFoundException;
public class ShowService {
    private Map<String, Show> showMap;

    public Show getShowById(String id) {
        if (showMap.containsKey(id)) {
            return showMap.get(id);
        } else {
            throw NotFoundException("Show is not found for the given id");
        }
    }

    public Show addShow(String movieId, String venueId, String screenId, Date startTime, Date endTime) {
        Show show = new Show(movieId, venueId, screenId, startTime, endTime);
        showMap.put(show.getId(), show);
        return show;
    }

    
}