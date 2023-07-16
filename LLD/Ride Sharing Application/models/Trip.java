package models;
import models.eTripStatus;
import java.util.UUID;

public class Trip{
    private String riderId;
    private String driverId;
    private int origin;
    private int destination;
    private int numSeats;
    private eTripStatus tripStatus;
    private String id;
    
    public Trip(String riderid, String driverId, int origin, int destination, int numSeats){
        this.riderid = riderid;
        this.driverId = driverId;
        this.origin = origin;
        this.destination = destination;
        this.numSeats = numSeats;
        this.tripStatus = eTripStatus.ACTIVE;
        this.id = UUID.randomUUID().toString();
    }

    public String getRiderId(){
        return riderid;
    }

    public String getDriverId(){
        return driverId;
    }

    public int getOrigin(){
        return origin;
    }

    public int getDestination(){
        return destination;
    }

    public int getNumSeats(){
        return numSeats;
    }

    public eTripStatus getTripStatus(){
        return tripStatus;
    }

    public String getId(){
        return id;
    }

    public void setTripStatus(eTripStatus tripStatus){
        this.tripStatus = tripStatus;
    }
}