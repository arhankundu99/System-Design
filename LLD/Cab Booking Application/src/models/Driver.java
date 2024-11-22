package models;

import java.util.UUID;

public class Driver extends Person {
    private final String carRegistractionNumber;
    private Location location;
    private boolean isAvailable;
    public Driver(String name, String email, String carRegistractionNumber) {
        super(name, email);

        if (carRegistractionNumber == null || carRegistractionNumber.isEmpty()) {
            throw new IllegalArgumentException("carRegistractionNumber cannot be null or empty");
        }
        this.carRegistractionNumber = carRegistractionNumber;
        location = null;
        isAvailable = false;
    }

    public String getCarRegistractionNumber() {
        return carRegistractionNumber;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(int x, int y) {
        if (this.location == null) {
            this.location = new Location(x, y);
        } else {
            this.location.setX(x);
            this.location.setY(y);
        }
    }

    public void setIsAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public boolean isAvailable() {
        return isAvailable;
    }
}
