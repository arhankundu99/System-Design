package services;

import models.Rider;

import java.util.HashMap;
import java.util.Map;

public class RiderService {
    Map<String, Rider> riderMap;
    public RiderService() {
        riderMap = new HashMap<>();
    }

    public Rider createRider(String name, String email) {
        Rider rider = new Rider(name, email);
        riderMap.put(rider.getId(), rider);
        return rider;
    }

    public Rider getRiderById(String id) {
        return riderMap.get(id);
    }
}
