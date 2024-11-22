package strategies;

import models.Driver;
import models.Location;

public interface IMatchMakingStrategy {
    public Driver getMatchedDriver(Location sourceLocation, int threshold, String riderId);
}
