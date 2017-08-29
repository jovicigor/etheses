package rs.fon.pzr.web.rest.util;

import org.springframework.boot.test.web.client.TestRestTemplate;
import rs.fon.pzr.web.rest.model.LoginData;
import rs.fon.pzr.web.rest.model.response.ticket.TicketResponse;

import java.io.IOException;

public class AuthenticationApi {

    private String basePath;

    public AuthenticationApi(String basePath) {
        this.basePath = basePath;
    }

    public TicketResponse loginAs(String email, String password) throws IOException {
        LoginData loginData = loginDataFrom(email, password);

        return request().postForObject(basePath, loginData, TicketResponse.class);
    }

    private static TestRestTemplate request() {
        return new TestRestTemplate();
    }

    private static LoginData loginDataFrom(String username, String pass) {
        LoginData loginData = new LoginData();
        loginData.setEmail(username);
        loginData.setPassword(pass);

        return loginData;
    }
}
