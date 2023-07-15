package manager;
import java.util.*;
import model.Room;
import model.Meeting;
import strategy.*;

public class RoomBookingManager{
    private IRoomBookingStrategy roomBookingStrategy;
    private List<Room> rooms;
    Map<Room, List<Meeting>> roomToMeetingMap;

    public RoomBookingManager(){
        roomBookingStrategy = new RoomBookingStrategy();
        rooms = new ArrayList<>();
        roomToMeetingMap = new HashMap<>();
    }

    public RoomBookingManager(IRoomBookingStrategy roomBookingStrategy){
        this.roomBookingStrategy = roomBookingStrategy;
        rooms = new ArrayList<>();
    }

    public void addRoom(Room room){
        rooms.add(room);
    }

    public Room book(int startTime, int endTime){
        return roomBookingStrategy.book(startTime, endTime, rooms, roomToMeetingMap);
    }

    public void displayAllMeetings(){
        System.out.println("\nDisplaying all the meetings.");
        for(Room room: roomToMeetingMap.keySet()){
            System.out.println("\n-------------\n");
            System.out.println("Room name: " + room.getName());
            
            for(Meeting meeting: roomToMeetingMap.get(room)){
                System.out.println(meeting);
                System.out.println();
            }
            System.out.println("\n-------------\n");
        }
    }
}