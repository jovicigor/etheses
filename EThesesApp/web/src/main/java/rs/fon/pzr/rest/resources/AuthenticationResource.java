package rs.fon.pzr.rest.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rs.fon.pzr.model.user.UserEntity;
import rs.fon.pzr.rest.model.LoginData;
import rs.fon.pzr.rest.model.response.level1.UserResponseLevel1;
import rs.fon.pzr.rest.model.response.ticket.TicketResponse;
import rs.fon.pzr.rest.model.util.RestFactory;
import rs.fon.pzr.security.authentication.AuthenticationService;

@RestController
@RequestMapping(value = "/authentication")
public class AuthenticationResource {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationResource(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @GetMapping
    @ResponseBody
    public UserResponseLevel1 getAuthenticatedUser(@RequestHeader(value = "ticket", required = false) String ticket) {
        UserEntity user = authenticationService.getTicketUser(ticket);
        return RestFactory.createUserResponseLevel1(user);
    }

    @PostMapping
    @ResponseBody
    public TicketResponse authenticate(@RequestBody LoginData loginData) {
        String ticket = authenticationService.authenticate(
                loginData.getEmail(), loginData.getPassword());
        return new TicketResponse(ticket);

    }

    @DeleteMapping
    public void invalidateTicket(@RequestHeader(value = "ticket", required = false) String ticket) {
        authenticationService.invalidateTicket(ticket);
    }
}
