package services;

import exceptions.NoFreeSlotAvailableException;
import exceptions.SlotAlreadyFullException;
import models.Car;
import models.ParkingLot;
import models.Slot;
import strategies.ISlotAssignmentStrategy;

import java.util.ArrayList;
import java.util.List;

public class ParkingLotService {
    int maxCapacity;
    ParkingLot parkingLot;
    ISlotAssignmentStrategy slotAssignmentStrategy;
    public ParkingLotService(int maxCapacity, ISlotAssignmentStrategy slotAssignmentStrategy) {
        parkingLot = new ParkingLot(maxCapacity);
        this.slotAssignmentStrategy = slotAssignmentStrategy;
    }

    public Integer parkCar(Car car) throws NoFreeSlotAvailableException, SlotAlreadyFullException {
        Integer nextFreeSlotNumber = this.slotAssignmentStrategy.getNextFreeSlotNumber();
        parkingLot.parkCar(nextFreeSlotNumber, car);
        this.slotAssignmentStrategy.removeNextFreeSlot();
        return nextFreeSlotNumber;
    }

    public Integer unparkCar(int slotNumber) throws IllegalArgumentException {
        parkingLot.unparkCar(slotNumber);
        this.slotAssignmentStrategy.addFreeSlot(slotNumber);
        return slotNumber;
    }

    public void createParkingLot(int maxCapacity) {
        parkingLot = new ParkingLot(maxCapacity);
    }

    public List<Slot> getUnavailableSlots() {
        List<Slot> unavailableSlots = new ArrayList<>();
        Slot[] slots = parkingLot.getSlots();
        for (Slot slot : slots) {
            if (!slot.isFree()) {
                unavailableSlots.add(slot);
            }
        }
        return unavailableSlots;
    }
}
