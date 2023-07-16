package strategies;
import strategies.IDriverMatchingStrategy;
import managers.DriverManager;
import exceptions.NoAvailableDriversException;

public class DriverMatchingStrategy implements IDriverMatchingStrategy{
    @Override
    public String getMatchedDriver(int origin, int destination, DriverManager driverManager){
        List<String> availableDriverIds = driverManager.getAvailableDriversId();
        if(availableDriverIds == null || availableDriverIds.size() == 0){
            throw new NoAvailableDriversException("No available drivers to be matched.");
        }
        return availableDriverIds.get(0);
    }
}