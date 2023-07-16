package strategies;

public interface IDriverMatchingStrategy{
    public String getMatchedDriver(int origin, int destination, DriverManager driverManager);
}