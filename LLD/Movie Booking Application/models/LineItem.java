package models;

public class LineItem {
    String id;
    String ticketId;
    String bookingId;

    public LineItem(String ticketId, String bookingId) {
        this.ticketId = ticketId;
        this.bookingId = bookingId;
    }

    public String getTicketId() {
        return ticketId;
    }

    public String getBookingId() {
        return bookingId;
    }
}