package services;

import exceptions.InvalidStateException;
import models.*;
import strategies.IMatchMakingStrategy;
import strategies.MatchMakingStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TripService {
    private final Map<String, Trip> tripMap;
    private final IMatchMakingStrategy matchMakingStrategy;
    private final DriverLockService driverLockService;
    public TripService(IMatchMakingStrategy matchMakingStrategy, DriverLockService driverLockService) {
        tripMap = new HashMap<>();
        this.matchMakingStrategy = matchMakingStrategy;
        this.driverLockService = driverLockService;
    }

    public void createTrip(Rider rider, Location sourceLocation, Location destinationLocation) throws InvalidStateException {
        Trip trip = new Trip(rider, null, sourceLocation, destinationLocation);
        tripMap.put(trip.getId(), trip);

        Driver driver = matchMakingStrategy.getMatchedDriver(sourceLocation, 10, rider.getId());
        trip.setDriver(driver);
        trip.setInProgressStatus();
    }

    public Trip getTripById(String id) {
        Trip trip = tripMap.get(id);
        return trip;
    }

    public List<Trip> getAllTripsOfARider(String riderId) {
        List<Trip> trips = new ArrayList<>();
        for (Trip trip : tripMap.values()) {
            if (trip.getRider().getId().equals(riderId)) {
                trips.add(trip);
            }
        }
        return trips;
    }

    public List<Trip> getAllTripsOfADriver(String driverId) {
        List<Trip> trips = new ArrayList<>();
        for (Trip trip : tripMap.values()) {
            if (trip.getDriver().getId().equals(driverId)) {
                trips.add(trip);
            }
        }
        return trips;
    }

    public void setDriverInTrip(String id, Driver driver) {
        Trip trip = tripMap.get(id);
        trip.setDriver(driver);
    }

    public void cancelTrip(String id) throws InvalidStateException {
        Trip trip = tripMap.get(id);
        trip.setCancelledStatus();
        driverLockService.unlockDriver(trip.getDriver().getId(), trip.getRider().getId());

    }

    public void completeTrip(String id) throws InvalidStateException {
        Trip trip = tripMap.get(id);
        trip.setInProgressStatus();
        driverLockService.unlockDriver(trip.getDriver().getId(), trip.getRider().getId());
    }
}
