package models;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DriverLockService implements IDriverLockService {
    Map<String, DriverLock> driverLockMap;
    public DriverLockService() {
        driverLockMap = new HashMap<String, DriverLock>();
    }

    public synchronized boolean lockDriver(String driverId, String riderId, int ttl) {
        if (driverLockMap.containsKey(driverId)) {
            return false;
        }
        DriverLock driverLock = new DriverLock(riderId, ttl);
        driverLockMap.put(driverId, driverLock);
        return true;
    }

    public boolean isLocked(String driverId) {
        DriverLock driverLock = driverLockMap.get(driverId);
        if (driverLock == null) {
            return false;
        }

        long timeElapsed = (new Date()).getTime() - driverLock.getCreationTime().getTime();
        if (timeElapsed > driverLock.getTtl()) {
            return false;
        }
        return true;
    }

    public boolean isLocked(String driverId, String riderId) {
        if (!isLocked(driverId)) {
            return false;
        }

        DriverLock driverLock = driverLockMap.get(driverId);
        return driverLock.getRiderId().equals(riderId);
    }

    public boolean unlockDriver(String driverId, String riderId) {
        if (!isLocked(driverId)) {
            return false;
        }

        DriverLock driverLock = driverLockMap.get(driverId);
        if (driverLock.getRiderId().equals(riderId)) {
            driverLockMap.remove(driverId);
        }
        return true;
    }
}
