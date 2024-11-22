package services;

public class BookingService {
    ConcurrentHashMap<String, Booking> bookingMap;
    ITicketLockingService ticketLockingService;
    public BookingService(ITicketLockingService ticketLockingService) {
        this.ticketLockingService = ticketLockingService;
        bookingMap = new ConcurrentHashMap<>();
    }

    public Booking createBooking(String userId, List<String> ticketIds) {
        for (String ticketId: ticketIds) {
            if (!ticketLockingService.isLockAcquired(ticketId, userId)) {
                return null;
            }
        }

        for (String ticketId: ticketIds) {
            ticketLockingService.extendLockTTLIfNecessary(ticketId, userId, thresoldTTL);
        }

        Booking booking = new Booking(userId);
        
        List<LineItem> lineItems = new ArrayList<>();
        for (String ticketId: ticketIds) {
            lineItems.add(new LineItem(ticketId, booking.getBookingId()));
        }

        booking.setLineItems(lineItems);
        bookingMap.put(bookng.getBookingId(), booking);
        return booking;
    }

    public Booking completeBooking(String bookingId, String paymentId) {
        Booking booking = bookingMap.get(bookingId);
        if (booking == null) {
            throw new InvalidBookingException("Could not complete booking. Invalid booking id");
        }

        // Verify payment using the payment id

        // Check if the tickets are locked are of the same user
        List<LineItem> lineItems = booking.getLineItems();
        for (LineItem lineItem: lineItems) {
            if (!ticketLockingService.isLockAcquired(lineItem.getTicketId(), booking.getUserId())) {
                throw new InvalidBookingException("Lock is not acquired on the ticket id by the user.")
            }
        }
        booking.confirmBooking();
        List<String> ticketIds = new ArrayList<>();
        for (LineItem lineItem: lineItems) {
            ticketIds.add(lineItem.getTicketId());
        }
        ticketLockingService.unlockTickets(ticketIds);
        return booking;
    }
}