package models;

import java.util.Date;

public class DriverLock {
    int ttl;
    String riderId;
    Date creationTime;

    public DriverLock(String riderId, int ttl) {
        this.ttl = ttl;
        this.riderId = riderId;
        creationTime = new Date();
    }

    public String getRiderId() {
        return riderId;
    }

    public int getTtl() {
        return ttl;
    }

    public Date getCreationTime() {
        return creationTime;
    }
}
