package rs.fon.pzr.rest.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rs.fon.pzr.persistence.model.UserEntity;
import rs.fon.pzr.security.authentication.AuthenticationService;
import rs.fon.pzr.rest.model.LoginData;
import rs.fon.pzr.rest.model.response.level1.UserResponseLevel1;
import rs.fon.pzr.rest.model.response.ticket.TicketResponse;
import rs.fon.pzr.rest.model.util.RestFactory;

@RestController
@RequestMapping(value = "/authentication")
public class AuthenticationResource {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationResource(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public
    @ResponseBody
    UserResponseLevel1 getAuthenticatedUser(
            @RequestHeader(value = "ticket", required = false) String ticket) {
        UserEntity user = authenticationService.getTicketUser(ticket);
        return RestFactory.createUserResponseLevel1(user);
    }

    @RequestMapping(method = RequestMethod.POST)
    public
    @ResponseBody
    TicketResponse authenticate(
            @RequestBody LoginData loginData) {
        String ticket = authenticationService.authenticate(
                loginData.getEmail(), loginData.getPassword());
        return new TicketResponse(ticket);

    }

    @RequestMapping(method = RequestMethod.DELETE)
    public void invalidateTicket(@RequestHeader(value = "ticket", required = false) String ticket) {
        authenticationService.invalidateTicket(ticket);
    }
}
