package rs.fon.pzr.security.authentication.ticket;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Date;

public class SimpleTicket {

    private static final int TICKET_DURATION = 1; //in minutes

    private String ticket;

    private Long userId;

    private Date validTo;
    private final SecureRandom random = new SecureRandom();

    public SimpleTicket(Long userId) {
        this.ticket = nextTicket();
        this.userId = userId;
    }

    private String nextTicket() {
        return new BigInteger(130, random).toString(32);
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getValidTo() {
        return validTo;
    }

    public void setValidTo(Date validTo) {
        this.validTo = validTo;
    }

    public void resetTicketExpiration() {
        this.validTo = new Date(System.currentTimeMillis() + TICKET_DURATION * 60 * 1000);
    }

    boolean isExpired() {
        return new Date().after(validTo);
    }

}
