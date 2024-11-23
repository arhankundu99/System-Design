package strategies;

import models.Request;
import services.ElevatorService;

import java.util.ArrayList;
import java.util.List;

public class ElevatorAssignmentStrategy implements IElevatorAssignmentStrategy {
    List<ElevatorService> elevatorServiceList;
    public ElevatorAssignmentStrategy() {
        elevatorServiceList = new ArrayList<>();
    }
    public ElevatorService getElevatorService(Request request) {
        return elevatorServiceList.getFirst();
    }

    public void addElevatorService(ElevatorService elevatorService) {
        elevatorServiceList.add(elevatorService);
    }
}
