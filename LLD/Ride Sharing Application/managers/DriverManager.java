package managers;
import models.Driver;
import exceptions.InvalidDriverIdException;
import java.util.HashMap;

public class DriverManager{
    private Map<String, Driver> drivers;
    private Map<String, boolean> driversAvailibility;

    public DriverManager(){
        drivers = new HashMap<>();
        driversAvailibility = new HashMap<>();
    }

    public String addDriver(String name){
        Driver driver = new Driver(name);
        drivers.put(driver.getId(), driver);
        driversAvailibility.put(driver.getId(), true);
        return driver.getId();
    }

    public Driver getDriver(String id){
        return drivers.getOrDefault(id, null);
    }

    public boolean setAvailibility(String id, boolean status){
        if(!drivers.containsKey(id)){
            throw new InvalidDriverIdException("Cannot set the availibility of invalid driver.");
        }

        driversAvailibility.put(id, status);
    }

    public List<String> getAvailableDriversId(){
        return driversAvailibility.keySet();
    }
}