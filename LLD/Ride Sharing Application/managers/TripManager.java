package managers;

import models.Trip;
import java.util.HashMap;
import managers.DriverManager;
import managers.RiderManager;
import exceptions.InvalidRiderIdException;
import exceptions.InvalidDriverIdException;
import exceptions.InvalidTripIdException;
import models.eTripStatus;

public class TripManager{
    private Map<String, Trip> trips;
    private DriverManager driverManager;
    private RiderManager riderManager;
    public TripManager(DriverManager driverManager, RiderManager riderManager){
        trips = new HashMap<>();
        this.driverManager = driverManager;
        this.riderManager = riderManager;
    }

    public String addTrip(String riderId, String driverId, int origin, int destination, int numSeats){
        Rider rider = riderManager.getRider(riderId);

        if(rider == null){
            throw new InvalidRiderIdException("No rider exists with the given id. Cannot create the trip.");
        }

        Driver driver = driverManager.getDriver(driverId);

        if(driver == null){
            throw new InvalidDriverIdException("No driver exisits with the given id. Cannot create the trip");
        }

        Trip trip = new Trip(riderid, driverId, origin, destination, numSeats);
        trips.put(trip.getId(), trip);
        return trip.getId();
    }

    public void completeTrip(String id){
        if(!trips.containsKey(id)){
            throw new InvalidTripIdException("No trip exists with this id. Cannot change the status of trip to completed.");
        }

        Trip trip = trips.get(id);
        trip.setTripStatus(eTripStatus.COMPLETED);
    }

    public void cancelTrip(String id){
        if(!trips.containsKey(id)){
            throw new InvalidTripIdException("No trip exists with this id. Cannot change the status of trip to cancelled.");
        }

        Trip trip = trips.get(id);
        trip.setTripStatus(eTripStatus.CANCELLED);
    }
}