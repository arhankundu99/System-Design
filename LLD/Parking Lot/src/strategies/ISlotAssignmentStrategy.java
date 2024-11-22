package strategies;

import exceptions.NoFreeSlotAvailableException;

public interface ISlotAssignmentStrategy {
    public Integer getNextFreeSlotNumber() throws NoFreeSlotAvailableException;
    public Integer removeNextFreeSlot() throws NoFreeSlotAvailableException;
    public void addFreeSlot(int slotNumber);
}
