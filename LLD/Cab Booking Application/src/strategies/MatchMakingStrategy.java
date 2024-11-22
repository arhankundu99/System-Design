package strategies;

import models.Driver;
import models.DriverLockService;
import models.Location;
import services.DriverService;

import java.util.List;

public class MatchMakingStrategy implements IMatchMakingStrategy {
    DriverService driverService;
    DriverLockService driverLockService;
    int lockTTL;
    public MatchMakingStrategy(DriverService driverService, DriverLockService driverLockService) {
        this.driverService = driverService;
        this.driverLockService = driverLockService;
        this.lockTTL = 10000;
    }

    public Driver getMatchedDriver(Location sourceLocation, int threshold, String riderId) {
        List<Driver> availableDrivers = driverService.getNearbyAvailableDrivers(sourceLocation, threshold);
        for (Driver driver : availableDrivers) {
            if (!driverLockService.isLocked(driver.getId())) {
                driverLockService.lockDriver(driver.getId(), riderId, lockTTL);
                return driver;
            }
        }
        return null;
    }
}
