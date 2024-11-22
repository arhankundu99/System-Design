package models;

import java.util.UUID;
import java.util.Date;
import java.util.List;

import exceptions.*;

public class Booking {
    private String id;
    private String userId;
    private BookingStatus bookingStatus;
    private Date createdAt;
    private Date updatedAt;
    private List<LineItem> lineItems; // Omit this when we are saving to DB

    public Booking(String userId) {
        this.id = UUID.randomUUID().toString();
        this.userId = userId;
        this.bookingStatus = BookingStatus.CREATED;
        this.createdAt = new Date();
        this.lineItems = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public List<LineItem> getLineItems() {
        return lineItems;
    }

    public void confirmBooking() {
        if (this.bookingStatus == BookingStatus.CREATED) {
            this.bookingStatus = BookingStatus.COMPLETED;
            this.updatedAt = new Date();
        } else {
            throw InvalidStateException("The status must be in created state to be confirmed.");
        }
    }

    public void failBooking() {
         if (this.bookingStatus == BookingStatus.CREATED) {
            this.bookingStatus = BookingStatus.FAILED;
            this.updatedAt = new Date();
        } else {
            throw InvalidStateException("The status must be in created state to be failed.");
        }
    }

    public void expireBooking() {
         if (this.bookingStatus == BookingStatus.COMPLETED) {
            this.bookingStatus = BookingStatus.EXPIRED;
            this.updatedAt = new Date();
        } else {
            throw InvalidStateException("The status must be in completed state to be expired.");
        }
    }
}