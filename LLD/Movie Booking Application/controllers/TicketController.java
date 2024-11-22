package controllers;

import models.Ticket;
import services.TicketService;


public class TicketController {
    private TicketService ticketService;

    public TicketController() {
        ticketService = new TicketService();
    }

    public Ticket addTicket(String showId, String venueId, String screenId, String seatNum) {
        // Validation of input

        return this.ticketService.addTicket(showId, venueId, screenId, seatNum);
    }

    public List<Ticket> getAvailableTicketsForShow(String showId) {
        // Validation of input

        return this.ticketService.getAvailableTicketsForShow(showId);
    }

    public boolean lockTickets(List<String> ticketIds, String userId) {

        return this.ticketService.lockTickets(ticketIds, userId);
    }
}