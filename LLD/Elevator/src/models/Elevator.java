package models;

import java.util.UUID;

public class Elevator {
    private final String id;
    private ElevatorStatus status;
    private int currentFloor;
    private int maxFloor;

    public Elevator(int maxFloor, int currentFloor) {
        if (maxFloor < 0 || currentFloor < 0) {
            throw new IllegalArgumentException("Max floor and current floor must be greater than 0");
        }
        this.id = UUID.randomUUID().toString();
        this.currentFloor = currentFloor;
        this.status = ElevatorStatus.IDLE;
        this.maxFloor = maxFloor;
    }

    public Elevator() {
        this.id = UUID.randomUUID().toString();
        this.status = ElevatorStatus.IDLE;
        this.currentFloor = 0;
        this.maxFloor = 10;
    }

    public ElevatorStatus getStatus() {
        return status;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public String getId() {
        return id;
    }

    public void goUp() {
        if (currentFloor == maxFloor) {
            throw new IllegalStateException("Cannot go up after max floor");
        }
        this.currentFloor++;
    }

    public void goDown() {
        if (currentFloor == 0) {
            throw new IllegalStateException("Cannot go down from the base floor");
        }
        this.currentFloor--;
    }

    public void go(int destinationFloor) throws InterruptedException {
        if (currentFloor < destinationFloor) {
            this.status = ElevatorStatus.GOING_UP;
            for (int floor = currentFloor; floor <= destinationFloor; floor++) {
                System.out.println("Current floor: " + floor);
                this.currentFloor = floor;
                Thread.sleep(1000);
            }
        } else if (currentFloor > destinationFloor) {
            this.status = ElevatorStatus.GOING_DOWN;
            for (int floor = currentFloor; floor >= destinationFloor; floor--) {
                System.out.println("Current floor: " + floor);
                this.currentFloor = floor;
                Thread.sleep(1000);
            }
        }
        this.status = ElevatorStatus.IDLE;
        System.out.println("Reached destination floor: " + destinationFloor);
    }

    public void setStatus(ElevatorStatus status) {
        this.status = status;
    }

    public void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
    }

    public void setMaxFloor(int maxFloor) {
        this.maxFloor = maxFloor;
    }

    public int getMaxFloor() {
        return maxFloor;
    }
}
