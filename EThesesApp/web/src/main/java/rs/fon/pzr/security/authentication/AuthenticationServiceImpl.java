package rs.fon.pzr.security.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import rs.fon.pzr.core.exception.AuthenticationException;
import rs.fon.pzr.core.exception.InvalidTicketException;
import rs.fon.pzr.persistence.model.User;
import rs.fon.pzr.core.service.UserService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final TicketService ticketService;
    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationServiceImpl(UserService userService, TicketService ticketService) {
        this.userService = userService;
        this.ticketService = ticketService;
        passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public String authenticate(String email, String password) throws AuthenticationException {
        if (email == null || password == null) {
            throw new AuthenticationException();
        }
        User user = userService.getUser(email);
        if (user == null) {
            throw new AuthenticationException();
        }

        String encodedPassword = user.getPassword();
        boolean matches = passwordEncoder.matches(password, encodedPassword);
        if (!matches) {
            throw new AuthenticationException();
        }
        return ticketService.generateTicket(user.getId()).getTicket();

    }

    @Override
    public String validate(String ticket) throws InvalidTicketException {
        if (ticket == null) {
            throw new InvalidTicketException("Must provide a valid ticket!");
        }
        Long userId = ticketService.getTicketUserId(ticket);
        if (userId == null) {
            throw new InvalidTicketException("Provided ticket is not valid! Your ticket may be expired.");
        }
        ticketService.prolongTicket(ticket);
        User user = userService.getUser(userId);
        return user.getEmail();

    }

    @Override
    public User getTicketUser(String ticket) {
        if (ticket == null) {
            throw new InvalidTicketException("Must provide a valid ticket!");
        }
        Long userId = ticketService.getTicketUserId(ticket);
        if (userId == null) {
            throw new InvalidTicketException("Must provide a valid ticket!");
        }
        return userService.getUser(userId);
    }

    @Override
    public void invalidateTicket(String ticket) {
        if (ticket == null) {
            return;
        }
        ticketService.invalidateTicket(ticket);
    }
}
