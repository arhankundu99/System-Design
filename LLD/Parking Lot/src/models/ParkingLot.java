package models;

import exceptions.SlotAlreadyFullException;

public class ParkingLot {
    Slot[] slots;
    public ParkingLot(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException("Capacity must be greater than 0");
        }
        slots = new Slot[capacity];
        for (int i = 0; i < capacity; i++) {
            slots[i] = new Slot(i + 1);
        }
    }

    public Slot getSlot(int slotNumber) {
        return slots[slotNumber - 1];
    }

    public Slot[] getSlots() {
        return slots;
    }

    public synchronized void parkCar(int slotNumber, Car car) throws SlotAlreadyFullException {
        if (slotNumber < 1 || slotNumber > slots.length) {
            throw new IllegalArgumentException("Slot number must be between 1 and capacity");
        }
        Slot slot = getSlot(slotNumber);
        slot.setCar(car);
    }

    public void unparkCar(int slotNumber) {
        if (slotNumber < 1 || slotNumber > slots.length) {
            throw new IllegalArgumentException("Slot number must be between 1 and capacity");
        }
        Slot slot = getSlot(slotNumber);
        slot.unassignCar();
    }
}
