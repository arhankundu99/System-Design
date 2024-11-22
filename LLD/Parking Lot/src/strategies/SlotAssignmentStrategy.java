package strategies;

import exceptions.NoFreeSlotAvailableException;

import java.util.PriorityQueue;

public class SlotAssignmentStrategy implements ISlotAssignmentStrategy {
    private final int maxCapacity;
    private PriorityQueue<Integer> unassignedSlots;
    public SlotAssignmentStrategy(int maxCapacity) {
        this.maxCapacity = maxCapacity;
        unassignedSlots = new PriorityQueue<>();
        for (int i = 1; i <= maxCapacity; i++) {
            unassignedSlots.add(i);
        }
    }

    @Override
    public Integer getNextFreeSlotNumber() throws NoFreeSlotAvailableException {
        if (unassignedSlots.isEmpty()) {
            throw new NoFreeSlotAvailableException("No free slots available in parking lot.");
        }
        return unassignedSlots.peek();
    }

    @Override
    public Integer removeNextFreeSlot() throws NoFreeSlotAvailableException {
        if (unassignedSlots.isEmpty()) {
            throw new NoFreeSlotAvailableException("No free slots available in parking lot.");
        }
        return unassignedSlots.poll();
    }

    public void addFreeSlot(int slotNumber) {
        unassignedSlots.add(slotNumber);
    }
}
