package services;

import models.Driver;
import models.Location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DriverService {
    Map<String, Driver> driverMap;
    public DriverService() {
        driverMap = new HashMap<>();
    }

    public Driver createDriver(String name, String email, String carRegistrationNumber) {
        Driver driver = new Driver(name, email, carRegistrationNumber);
        driverMap.put(name, driver);
        return driver;
    }

    public Driver getDriverById(String id) {
        return driverMap.get(id);
    }

    public List<Driver> getNearbyAvailableDrivers(Location sourceLocation, int threshold) {
        List<Driver> nearbyAvailableDrivers = new ArrayList<>();
        for (Driver driver : driverMap.values()) {
            if (driver.getLocation().getDistance(sourceLocation) <= threshold && driver.isAvailable()) {
                nearbyAvailableDrivers.add(driver);
            }
        }
        return nearbyAvailableDrivers;
    }
}
