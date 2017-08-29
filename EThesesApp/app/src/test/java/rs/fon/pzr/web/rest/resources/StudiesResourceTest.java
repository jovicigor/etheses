package rs.fon.pzr.web.rest.resources;

import org.apache.http.HttpStatus;
import org.junit.Test;
import rs.fon.pzr.web.rest.ResourceTest;
import rs.fon.pzr.web.rest.model.request.StudiesRequest;

import java.io.IOException;

public class StudiesResourceTest extends ResourceTest {

    @Test
    public void addStudies_notLoggedIn_badRequest() throws IOException {
        StudiesRequest studiesRequest = new StudiesRequest("studies1", "st1");

        givenJson().body(studiesRequest)
                .when().post("/e-theses/studies")
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN);
    }

    @Test
    public void addStudies_loggedInAsUser_unauthorized() throws IOException {
        String studiesJson = readJson("studies1_st1.json");

        givenUser("jovic.i24@gmail.com", "Igorjovic24").body(studiesJson)
                .when().post("/e-theses/studies")
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN);
    }

    @Test
    public void addStudies_loggedInAsAdmin_ok() throws IOException {
        String studiesJson = readJson("studies2_st2.json");

        givenUser("admin@gmail.com", "Admin123").body(studiesJson)
                .when().post("/e-theses/studies")
                .then()
                .statusCode(HttpStatus.SC_OK);
    }
}