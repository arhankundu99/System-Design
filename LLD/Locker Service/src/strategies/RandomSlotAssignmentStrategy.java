package strategies;

import models.Location;
import models.Locker;
import models.Slot;
import models.SlotSize;
import services.LockerService;

import java.util.List;

public class RandomSlotAssignmentStrategy implements ISlotAssignmentStrategy {
    LockerService lockerService;
    private static final int THRESHOLD = 20;
    public RandomSlotAssignmentStrategy(LockerService lockerService) {
        this.lockerService = lockerService;
    }
    public Slot assignSlot(Location location, SlotSize size) {
        List<Locker> lockers = lockerService.getAllLockers();

        for (Locker locker : lockers) {
            if (locker.getLocation().getDistance(location) <= THRESHOLD) {
                List<Slot> availableSlots = locker.getEmptySlots(size);
                if (availableSlots.isEmpty()) {
                    continue;
                }

                int index = (int) (Math.random() * availableSlots.size());
                return availableSlots.get(index);
            }
        }
        return null;
    }
}
