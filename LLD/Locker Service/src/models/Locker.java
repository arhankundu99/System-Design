package models;

import java.util.*;

public class Locker {
    private final Map<String, Slot> slotsMap;
    private final String id;
    private final Location location;
    public Locker(Location location) {
        this.slotsMap = new HashMap<>();
        this.id = UUID.randomUUID().toString();
        this.location = location;
    }

    public void addSlot(SlotSize size) {
        Slot slot = new Slot(size, this.id);
        this.slotsMap.put(slot.getId(), slot);
    }

    public void removeSlot(String slotId) {
        this.slotsMap.remove(slotId);
    }


    public List<Slot> getFilledSlots() {
        List<Slot> slots = new ArrayList<>();
        for (Slot slot : this.slotsMap.values()) {
            if (!slot.isEmpty()) {
                slots.add(slot);
            }
        }
        return slots;
    }

    public List<Slot> getEmptySlots() {
        List<Slot> slots = new ArrayList<>();
        for (Slot slot : this.slotsMap.values()) {
            if (slot.isEmpty()) {
                slots.add(slot);
            }
        }
        return slots;
    }

    public List<Slot> getEmptySlots(SlotSize size) {
        List<Slot> slots = new ArrayList<>();
        for (Slot slot : this.slotsMap.values()) {
            if (slot.isEmpty() && slot.getSize() == size) {
                slots.add(slot);
            }
        }
        return slots;
    }

    public Location getLocation() {
        return location;
    }
    public String getId() {
        return id;
    }

    public Slot getSlot(String slotId) {
        return this.slotsMap.get(slotId);
    }
}
