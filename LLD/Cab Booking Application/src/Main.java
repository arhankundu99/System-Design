import models.DriverLockService;
import services.DriverService;
import services.RiderService;
import services.TripService;
import strategies.IMatchMakingStrategy;
import strategies.MatchMakingStrategy;

public class Main {
    public static void main(String[] args) {
        DriverLockService driverLockService = new DriverLockService();

        DriverService driverService = new DriverService();
        RiderService riderService = new RiderService();

        IMatchMakingStrategy matchMakingStrategy = new MatchMakingStrategy(driverService, driverLockService);
        TripService tripService = new TripService(matchMakingStrategy, driverLockService);

        driverService.createDriver("Driver 1", "driver1@gmail.com", "KJ-123");
        riderService.createRider("Rider 1", "rider1@gmail.com");

    }
}
