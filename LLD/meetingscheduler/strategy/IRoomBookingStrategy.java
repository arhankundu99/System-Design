package strategy;
import model.Room;
import model.Meeting;
import java.util.*;

public interface IRoomBookingStrategy{
    public Room book(int startTime, int endTime, List<Room> rooms, Map<Room, List<Meeting>> roomToMeetingMap);
}