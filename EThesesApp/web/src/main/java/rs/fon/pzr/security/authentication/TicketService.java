package rs.fon.pzr.security.authentication;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.fon.pzr.security.authentication.ticket.InMemoryTicketCash;
import rs.fon.pzr.security.authentication.ticket.SimpleTicket;

@Service
class TicketService {

    private final Logger logger = Logger.getLogger(TicketService.class);

    private final InMemoryTicketCash ticketCash;

    @Autowired
    public TicketService(InMemoryTicketCash ticketCash) {
        this.ticketCash = ticketCash;
    }

    SimpleTicket generateTicket(Long userId) {
        SimpleTicket ticket = new SimpleTicket(userId);
        ticketCash.addTicket(ticket);
        return ticket;
    }

    Long getTicketUserId(String ticket) {
        SimpleTicket inMemoryTicket = ticketCash.getTicket(ticket);
        if (inMemoryTicket != null) {
            return inMemoryTicket.getUserId();
        }
        return null;
    }

    void prolongTicket(String ticketId) {
        SimpleTicket ticket = ticketCash.getTicket(ticketId);
        if (ticket != null) {
            ticket.resetTicketExpiration();
        }
    }

    void invalidateTicket(String ticket) {
        ticketCash.remove(ticket);
    }

    public void invalidateOldTickets() {
        logger.debug("Invalidating old tickets.");
        ticketCash.removeExpired();
    }
}
