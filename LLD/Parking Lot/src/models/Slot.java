package models;

import exceptions.SlotAlreadyFullException;

public class Slot {
    private Car car;
    private int slotNumber;

    public Slot(int slotNumber) {
        this.slotNumber = slotNumber;
    }

    public Car getCar() {
        return car;
    }

    public boolean isFree() {
        return car == null;
    }

    public void setCar(Car car) throws SlotAlreadyFullException {
        if (isFree()) {
            this.car = car;
        } else {
            throw new SlotAlreadyFullException("Cannot park in the current slot with slot num: " + slotNumber + " as this slot is already being used.");
        }
    }

    public void unassignCar() {
        this.car = null;
    }

    @Override
    public String toString() {
        return slotNumber + " " + car.toString();
    }
}
