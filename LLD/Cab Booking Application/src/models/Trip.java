package models;

import exceptions.InvalidStateException;

import java.util.UUID;

public class Trip {
    private final String id;
    private final Rider rider;
    private Driver driver;

    private final Location sourceLocation;
    private final Location destinationLocation;
    private TripStatus status;

    public Trip(Rider rider, Driver driver, Location sourceLocation, Location destinationLocation) {
        id = UUID.randomUUID().toString();
        this.rider = rider;
        this.driver = driver;
        this.sourceLocation = sourceLocation;
        this.destinationLocation = destinationLocation;
        status = TripStatus.CREATED;
    }

    public Rider getRider() {
        return rider;
    }

    public Driver getDriver() {
        return driver;
    }

    public Location getSourceLocation() {
        return sourceLocation;
    }

    public Location getDestinationLocation() {
        return destinationLocation;
    }

    public String getId() {
        return id;
    }

    public TripStatus getStatus() {
        return status;
    }

    public void setInProgressStatus() throws InvalidStateException {
        if (status == TripStatus.CREATED) {
            status = TripStatus.IN_PROGRESS;
        } else {
            throw new InvalidStateException("Trip status must be in created state to be changed to in progress");
        }
    }

    public void setCancelledStatus() throws InvalidStateException {
        if (status == TripStatus.CREATED) {
            status = TripStatus.CANCELLED;
        } else {
            throw new InvalidStateException("Trip status must be in created state to be changed to cancelled");
        }
    }

    public void setCompletedStatus() throws InvalidStateException {
        if (status == TripStatus.IN_PROGRESS) {
            status = TripStatus.COMPLETED;
        } else {
            throw new InvalidStateException("Trip status must be in in progress state to be changed to completed");
        }
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }
}
