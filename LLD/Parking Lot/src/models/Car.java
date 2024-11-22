package models;

public class Car {
    private final String registrationNumber;
    private final String color;

    public Car(String registrationNumber, String color) {
        this.registrationNumber = registrationNumber;
        this.color = color;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public String getColor() {
        return color;
    }

    @Override
    public String toString() {
        return color + " " + registrationNumber;
    }
}
