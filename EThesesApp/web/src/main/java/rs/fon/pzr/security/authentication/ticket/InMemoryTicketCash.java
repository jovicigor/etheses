package rs.fon.pzr.security.authentication.ticket;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class InMemoryTicketCash {
    private static final Map<String, SimpleTicket> ticketCash = new HashMap<>();

    public void addTicket(SimpleTicket ticket) {
        ticketCash.put(ticket.getTicket(), ticket);
    }

    public SimpleTicket getTicket(String ticketId) {
        return ticketCash.get(ticketId);
    }

    public void remove(String ticket) {
        ticketCash.remove(ticket);
    }

    public void removeExpired() {
        for (Map.Entry<String, SimpleTicket> entry : ticketCash.entrySet()) {
            if (entry.getValue().isExpired()) {
                ticketCash.remove(entry.getKey());
            }
        }
    }
}
