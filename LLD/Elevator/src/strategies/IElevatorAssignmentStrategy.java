package strategies;

import models.Elevator;
import models.Request;
import services.ElevatorService;

public interface IElevatorAssignmentStrategy {
    ElevatorService getElevatorService(Request request);
    void addElevatorService(ElevatorService elevatorService);
}
