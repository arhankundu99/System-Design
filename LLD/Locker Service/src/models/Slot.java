package models;

import exceptions.InvalidStateException;

import java.util.UUID;

public class Slot {
    private final String id;
    SlotSize size;
    private final String lockerId;
    private String orderId;
    private String otp;

    public Slot(SlotSize size, String lockerId) {
        id = UUID.randomUUID().toString();
        this.size = size;
        this.lockerId = lockerId;
        this.orderId = null;
        this.otp = null;
    }

    public void fill(String orderId) throws InvalidStateException {
        if (orderId == null || orderId.isEmpty()) {
            throw new IllegalArgumentException("Order id is invalid.");
        }

        if (this.orderId != null && !this.orderId.isEmpty()) {
            throw new InvalidStateException("The slot is already full. Cannot place an order here.");
        }
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public SlotSize getSize() {
        return size;
    }

    public String getLockerId() {
        return lockerId;
    }

    public String getId() {
        return id;
    }

    public void evictOrder() throws InvalidStateException {
        if (orderId == null || orderId.isEmpty()) {
            throw new InvalidStateException("The slot is already empty.");
        }
        this.orderId = null;
    }

    public boolean isEmpty() {
        return orderId == null || orderId.isEmpty();
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getOtp() {
        return otp;
    }
}
