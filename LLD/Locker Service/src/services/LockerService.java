package services;

import exceptions.InvalidStateException;
import models.Location;
import models.Locker;
import models.Slot;
import models.SlotSize;
import strategies.IOTPGenerationStrategy;
import strategies.ISlotAssignmentStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LockerService {
    private final Map<String, Locker> lockersMap;
    private ISlotAssignmentStrategy slotAssignmentStrategy;
    private final IOTPGenerationStrategy otpGenerationStrategy;
    private final Map<String, String> orderOTPMap;
    private final Map<String, Map<String, String>> orderSlotMap;
    public LockerService(IOTPGenerationStrategy otpGenerationStrategy) {
        lockersMap = new HashMap<>();
        orderOTPMap = new HashMap<>();
        orderSlotMap = new HashMap<>();
        this.otpGenerationStrategy = otpGenerationStrategy;
    }

    public void setSlotAssignmentStrategy(ISlotAssignmentStrategy slotAssignmentStrategy) {
        this.slotAssignmentStrategy = slotAssignmentStrategy;
    }

    public void createLocker(Location location) {
        Locker locker = new Locker(location);
        lockersMap.put(locker.getId(), locker);
    }

    public Locker getLockerById(String id) {
        return lockersMap.get(id);
    }

    public List<Locker> getAllLockers() {
        return new ArrayList<>(lockersMap.values());
    }

    public boolean assignSlot(Location location, SlotSize size, String orderId) throws InvalidStateException {
        Slot assignedSlot = slotAssignmentStrategy.assignSlot(location, size);
        if (assignedSlot == null) {
            return false;
        }
        synchronized (this) {
            assignedSlot.fill(orderId);
        }
        String otp = otpGenerationStrategy.generateOtp();
        assignedSlot.setOtp(otp);
        orderOTPMap.put(orderId, otp);
        if (!orderSlotMap.containsKey(orderId)) {
            orderSlotMap.put(orderId, new HashMap<>());
        }
        orderSlotMap.get(orderId).put(assignedSlot.getLockerId(), assignedSlot.getId());
        return true;
    }

    public boolean evictOrder(String otp) throws InvalidStateException {
        String orderId = orderOTPMap.get(otp);
        Map<String, String> lockerSlotMap = orderSlotMap.get(orderId);
        String lockerId = null;
        String slotId = null;

        for (Map.Entry<String, String> entry : lockerSlotMap.entrySet()) {
            lockerId = entry.getKey();
            slotId = entry.getValue();
            break;
        }
        Locker locker = lockersMap.get(lockerId);
        Slot slot = locker.getSlot(slotId);

        slot.evictOrder();
        orderOTPMap.remove(orderId);
        orderSlotMap.remove(orderId);
        return true;
    }
}
