package services;

import models.Ticket;

public class TicketService {
    Map<String, Ticket> ticketMap;
    ITicketLockingService ticketLockingService;

    public TicketService(ITicketLockingService ticketLockingService) {
        ticketMap = new HashMap<>();
        this.ticketLockingService = ticketLockingService;
    }

    public Ticket addTicket(String showId, String venueId, String screenId, String seatNum) {
        Ticket ticket = new Ticket(showId, venueId, screenId, seatNum);
        ticketMap.put(ticket.getId(), ticket);
        return ticket;
    }

    public List<Ticket> getAvailableTicketsForShow(String showId) {
        List<Ticket> availableTicketsForShow = new ArrayList<>();
        for (String ticketId: ticketMap.keySet()) {
            Ticket ticket = ticketMap.get(ticketId);

            // Check if the ticket is not booked and the ticket is not locked
            if (ticket.getShowId().equals(showId) && !(ticket.getIsBooked() || this.ticketLockingService.isLockAcquired(ticketId))) {
                availableTicketsForShow.add(ticket);
            }
        }
        return availableTicketsForShow;
    }

    public boolean lockTickets(List<String> ticketIds, String userId) {
        return this.ticketLockingService.lockTickets(ticketIds, userId);
    }

    public boolean unlockTickets(List<String> ticketIds, String userId) {
        return this.ticketLockingService.unlockTickets(ticketIds, userId);
    }

    public boolean extendLockTTL(List<String> ticketIds, String userId) {
        return this.ticketLockingService.extendLockTTL(ticketIds, userId);
    }
}