import manager.RoomBookingManager;
import model.Room;

public class Main{
    public static void main(String[] args){
        RoomBookingManager roomBookingManager = new RoomBookingManager();

        Room room1 = new Room("Room 1");
        Room room2 = new Room("Room 2");

        roomBookingManager.addRoom(room1);
        roomBookingManager.addRoom(room2);

        Room bookedRoom1 = roomBookingManager.book(0, 10);
        Room bookedRoom2 = roomBookingManager.book(5, 10);
        Room bookedRoom3 = roomBookingManager.book(0, 4);
        
        System.out.println(bookedRoom1.getName());
        System.out.println(bookedRoom2.getName());
        System.out.println(bookedRoom3.getName());

        roomBookingManager.displayAllMeetings();
    }
}