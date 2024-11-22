package models;

public interface IDriverLockService {
    boolean lockDriver(String driverId, String riderId, int ttl);
    boolean isLocked(String driverId);
    boolean isLocked(String driverId, String riderId);
    boolean unlockDriver(String driverId, String riderId);
}
