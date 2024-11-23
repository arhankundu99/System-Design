package services;

import models.Elevator;
import models.ElevatorStatus;
import models.Request;
import strategies.IRequestAssignmentStrategy;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ElevatorService {
    private final Elevator elevator;
    // ReentrantLock is reentrant, meaning the same thread can acquire the lock multiple times without causing a deadlock.
    Lock currentRequestLock = new ReentrantLock();
    // See: https://jenkov.com/tutorials/java-concurrency/locks.html
    private IRequestAssignmentStrategy requestAssignmentStrategy;
    Request currentRequest;
    public ElevatorService() {
        this.elevator = new Elevator();
        requestAssignmentStrategy = null;

    }

    public void setRequestAssignmentStrategy(IRequestAssignmentStrategy requestAssignmentStrategy) {
        this.requestAssignmentStrategy = requestAssignmentStrategy;
    }

    public void addRequest(Request request) {
        this.requestAssignmentStrategy.addRequest(request);
    }

    public void processRequests() {
        Thread requestProcessorThread = new Thread(new ElevatorRequestProcessor());
        requestProcessorThread.start();
    }

    public void setCurrentRequest(Request request) {
        currentRequestLock.lock();
        try {
            this.currentRequest = request;
        } catch (Exception e) {
            System.out.println("Error while setting current request");
        } finally {
            currentRequestLock.unlock();
        }
    }

    public Request getCurrentRequest() {
        currentRequestLock.lock();
        Request currentRequest = null;
        try {
            currentRequest = this.currentRequest;
        } catch (Exception e) {
            System.out.println("Error while getting current request");
        } finally {
            currentRequestLock.unlock();
        }
        return currentRequest;
    }

    public int getCurrentFloor() {
        return this.elevator.getCurrentFloor();
    }

    private class ElevatorRequestProcessor implements Runnable {
        public void run() {
            while (true) {
                Request nextRequest = requestAssignmentStrategy.getNextRequest();
                if (nextRequest != null) {
                    try {
                        setCurrentRequest(nextRequest);
                        elevator.go(nextRequest.getDestinationFloor());
                        setCurrentRequest(null);
                    } catch (InterruptedException e) {
                        System.out.println(e.getMessage());
                    }
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}
