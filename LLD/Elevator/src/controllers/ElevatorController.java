package controllers;

import models.Request;
import services.ElevatorService;
import strategies.IElevatorAssignmentStrategy;
import strategies.PriorityQueueRequestAssignmentStrategy;

public class ElevatorController {
    IElevatorAssignmentStrategy elevatorAssignmentStrategy;
    public ElevatorController(IElevatorAssignmentStrategy elevatorAssignmentStrategy) {
        this.elevatorAssignmentStrategy = elevatorAssignmentStrategy;
    }

    public void addRequest(Request request) {
        ElevatorService elevatorService = this.elevatorAssignmentStrategy.getElevatorService(request);
        elevatorService.addRequest(request);
    }

    public void addElevatorService() {
        ElevatorService elevatorService = new ElevatorService();
        PriorityQueueRequestAssignmentStrategy requestAssignmentStrategy = new PriorityQueueRequestAssignmentStrategy(elevatorService);
        elevatorService.setRequestAssignmentStrategy(requestAssignmentStrategy);
        this.elevatorAssignmentStrategy.addElevatorService(elevatorService);
        elevatorService.processRequests();
    }
}
