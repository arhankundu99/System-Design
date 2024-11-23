import controllers.ElevatorController;
import models.Request;
import models.RequestSource;
import strategies.ElevatorAssignmentStrategy;

public class Main {
    public static void main(String[] args) {

        ElevatorController elevatorController = new ElevatorController(new ElevatorAssignmentStrategy());
        elevatorController.addElevatorService();
        elevatorController.addRequest(new Request(1, 5, RequestSource.INSIDE_ELEVATOR));

        elevatorController.addRequest(new Request(2, 3, RequestSource.INSIDE_ELEVATOR));
    }
}
