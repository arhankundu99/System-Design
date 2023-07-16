package managers;
import models.Rider;

public class RiderManager{
    private Map<String, Rider> riders;

    public RiderManager(){
        riders = new HashMap<>();
    }

    public String addRider(String name){
        Rider rider = new Rider(name);
        riders.put(rider.getId(), rider);
        return rider.getId();
    }

    public Rider getRider(String id){
        return riders.getOrDefault(id, null);
    }
}