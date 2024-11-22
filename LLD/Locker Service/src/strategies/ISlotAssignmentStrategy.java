package strategies;

import models.Location;
import models.Locker;
import models.Slot;
import models.SlotSize;

public interface ISlotAssignmentStrategy {
    Slot assignSlot(Location location, SlotSize size);
}
