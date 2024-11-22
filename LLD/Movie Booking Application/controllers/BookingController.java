package controllers;

import services.BookingService;
import models.Booking;

public class BookingController {
    private BookingService bookingService;

    public BookingController() {
        this.bookingService = new BookingService();
    }

    public Booking createBooking(String userId, List<String> ticketIds) {
        return this.bookingService.createBooking(userId, ticketIds);
    }

    public Booking completeBooking(String bookingId) {
        return this.bookingService.completeBooking(bookingId);
    }

    public Booking failBooking(String bookingId) {
        return this.bookingService.failBooking(bookingId);
    }
}