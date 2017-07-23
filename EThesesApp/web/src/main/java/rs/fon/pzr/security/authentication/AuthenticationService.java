package rs.fon.pzr.security.authentication;

import rs.fon.pzr.core.model.User;

public interface AuthenticationService {
    String authenticate(String username, String password);

    String validate(String ticket);

    User getTicketUser(String ticket);

    void invalidateTicket(String ticket);
}