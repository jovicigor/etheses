package rs.fon.pzr.web.rest;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.parsing.Parser;
import com.jayway.restassured.response.Header;
import com.jayway.restassured.specification.RequestSpecification;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import rs.fon.pzr.App;
import rs.fon.pzr.web.rest.model.response.ticket.TicketResponse;
import rs.fon.pzr.web.rest.util.AuthenticationApi;

import java.io.File;
import java.io.IOException;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.http.ContentType.JSON;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = App.class)
public class ResourceTest {

    @LocalServerPort
    int port;

    private AuthenticationApi authenticationApi;

    @Before
    public void init() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
        RestAssured.defaultParser = Parser.JSON;

        String authenticationPath = String.format("%s:%d/%s/%s", RestAssured.baseURI, port, "e-theses", "authentication");
        authenticationApi = new AuthenticationApi(authenticationPath);
    }

    protected RequestSpecification givenJson() {
        return given().contentType(JSON);
    }

    protected RequestSpecification givenUser(String username, String password) throws IOException {
        TicketResponse loginResponse = authenticationApi.loginAs(username, password);
        return givenJson().and()
                .given().header(new Header("ticket", loginResponse.getTicket()));
    }

    protected String readJson(String fileName) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());
        return FileUtils.readFileToString(file);
    }
}
