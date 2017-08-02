package rs.fon.pzr.security.authentication;

import rs.fon.pzr.core.domain.model.user.UserEntity;

public interface AuthenticationService {
    String authenticate(String username, String password);

    String validate(String ticket);

    UserEntity getTicketUser(String ticket);

    void invalidateTicket(String ticket);
}
