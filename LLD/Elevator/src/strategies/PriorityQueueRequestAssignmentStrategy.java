package strategies;

import models.Elevator;
import models.Request;
import services.ElevatorService;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.concurrent.PriorityBlockingQueue;

public class PriorityQueueRequestAssignmentStrategy implements IRequestAssignmentStrategy{
    PriorityBlockingQueue<Request> upRequestQueue;
    PriorityBlockingQueue<Request> downRequestQueue;
    ElevatorService elevatorService;

    public PriorityQueueRequestAssignmentStrategy(ElevatorService elevatorService) {
        this.upRequestQueue = new PriorityBlockingQueue<>(11, new Comparator<Request>() {
            public int compare(Request r1, Request r2) {
                return r1.getDestinationFloor() - r2.getDestinationFloor();
            }
        });
        this.downRequestQueue = new PriorityBlockingQueue<>(11, new Comparator<Request>() {
            public int compare(Request r1, Request r2) {
                return r2.getDestinationFloor() - r1.getDestinationFloor();
            }
        });

        this.elevatorService = elevatorService;
    }
    public void addRequest(Request request) {
        Request currentRequest = elevatorService.getCurrentRequest();
        if (currentRequest == null) {
            int currentElevatorFloor = elevatorService.getCurrentFloor();
            int destinationFloor = request.getDestinationFloor();

            if (destinationFloor < currentElevatorFloor) {
                downRequestQueue.add(request);
            } else {
                upRequestQueue.add(request);
            }
        } else {
            int currentRequestDestinationFloor = currentRequest.getDestinationFloor();
            int destinationFloor = request.getDestinationFloor();

            if (destinationFloor < currentRequestDestinationFloor) {
                downRequestQueue.add(request);
            } else {
                upRequestQueue.add(request);
            }
        }
    }

    public void clearAllRequests() {
        upRequestQueue.clear();
        downRequestQueue.clear();
    }
    public Request getNextRequest() {
        Request candidateRequest1 = upRequestQueue.peek();
        Request candidateRequest2 = downRequestQueue.peek();

        if (candidateRequest1 == null) {
            downRequestQueue.poll();
            return candidateRequest2;
        }

        if (candidateRequest2 == null) {
            upRequestQueue.poll();
            return candidateRequest1;
        }

        int diff1 = candidateRequest1.getDestinationFloor() - elevatorService.getCurrentFloor();
        int diff2 = elevatorService.getCurrentFloor() - candidateRequest2.getDestinationFloor();
        if (diff1 <= diff2) {
            return upRequestQueue.poll();
        }
        return downRequestQueue.poll();
    }
}
