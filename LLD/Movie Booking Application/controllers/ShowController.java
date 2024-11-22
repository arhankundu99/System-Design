package controllers;
import java.util.List;
import java.util.ArrayList;
import exceptions.*;
import services.ShowService;

public class ShowController {

    ShowService showService;
    public ShowController() {
        showService = new ShowService();
    }

    public Show getShowById(String id) {
        if (id == null || id.length() == 0) {
            throw InvalidArgumentException("Id of a show cannot be null");
        }
        return showService.getShowById(id);
    }

    public Show addShow(String movieId, String venueId, String screenId, Date startTime, Date endTime) {
        if (name.length() == 0 || desc.length() == 0 || venueId.length() == 0 || startTime == null || endTime == null) {
            throw new InvalidArgumentException("The parameters for creating an Show should not be empty.");
        }
        return showService.addShow(movieId, venueId, screenId, startTime, endTime);
    }

}