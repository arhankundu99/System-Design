package strategy;
import model.Room;
import model.Meeting;
import java.util.*;
import exceptions.InvalidTimeException;

public class RoomBookingStrategy implements IRoomBookingStrategy{

    @Override
    public Room book(int startTime, int endTime, List<Room> rooms, Map<Room, List<Meeting>> roomToMeetingMap){
        if(startTime >= endTime){
            throw new InvalidTimeException("Start time should be less than end time.");
        }
        
        for(Room room: rooms){
            
            // check if this room has been booked for any meetings
            if(!roomToMeetingMap.containsKey(room)){
                
                roomToMeetingMap.put(room, new ArrayList<>());
                Meeting meeting = new Meeting(startTime, endTime);
                scheduleMeetingInRoom(roomToMeetingMap, room, meeting);
                return room;
            }

            // else, iterate through the meetings and check if a meeting can be accomodated
            boolean canBook = true;
            for(Meeting meeting: roomToMeetingMap.get(room)){
                if(!(meeting.getStartTime() >= endTime || meeting.getEndTime() <= startTime)){
                    canBook = false;
                }
            }

            if(canBook){
                Meeting meeting = new Meeting(startTime, endTime);
                scheduleMeetingInRoom(roomToMeetingMap, room, meeting);
                return room;
            }
        }
        return null;
    }

    private void scheduleMeetingInRoom(Map<Room, List<Meeting>> roomToMeetingMap, Room room, Meeting meeting){
        if(!roomToMeetingMap.containsKey(room)){
            roomToMeetingMap.put(room, new ArrayList<>());
        }

        roomToMeetingMap.get(room).add(meeting);
    }
}