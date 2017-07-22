package rs.fon.elab.pzr.core.service.authentication;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.fon.elab.pzr.core.model.ticket.InMemoryTicketCash;
import rs.fon.elab.pzr.core.model.ticket.SimpleTicket;

@Service
public class TicketService {

    private final Logger logger = Logger.getLogger(TicketService.class);

    @Autowired
    private InMemoryTicketCash ticketCash;

    public SimpleTicket generateTicket(Long userId) {
        SimpleTicket ticket = new SimpleTicket(userId);
        ticketCash.addTicket(ticket);
        return ticket;
    }

    public Long getTicketUserId(String ticket) {
        SimpleTicket inMemoryTicket = ticketCash.getTicket(ticket);
        if (inMemoryTicket != null) {
            return inMemoryTicket.getUserId();
        }
        return null;
    }

    public void prolongTicket(String ticketId) {
        SimpleTicket ticket = ticketCash.getTicket(ticketId);
        if (ticket != null) {
            ticket.resetTicketExpiration();
        }
    }

    public void invalidateTicket(String ticket) {
        ticketCash.remove(ticket);
    }

    public void invalidateOldTickets() {
        logger.debug("Invalidating old tickets.");
        ticketCash.removeExpired();
    }

    public InMemoryTicketCash getTicketCash() {
        return ticketCash;
    }

    public void setTicketCash(InMemoryTicketCash ticketCash) {
        this.ticketCash = ticketCash;
    }
}
