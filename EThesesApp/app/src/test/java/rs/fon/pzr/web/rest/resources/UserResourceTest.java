package rs.fon.pzr.web.rest.resources;

import org.apache.http.HttpStatus;
import org.junit.Test;
import rs.fon.pzr.web.rest.ResourceTest;
import rs.fon.pzr.web.rest.model.LoginData;

import java.io.IOException;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class UserResourceTest extends ResourceTest {

    private static final String USERS_PATH = "/e-theses/users/";
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String EMAIL = "email";
    private static final String STUDENTS_TRANSCRIPT = "studentsTranscript";
    private static final String COURSE_NAME = "course.name";
    private static final String ID = "id";
    private static final String SIZE = "size()";
    private static final String COURSE = "course";

    @Test
    public void getUsers_returnsAllExistingUsers() throws IOException {
        given().when().get(USERS_PATH).
                then()
                .body(SIZE, greaterThan(0));
    }

    @Test
    public void getUser_returnsCorrectUserInfo() throws IOException {
        long userId = 2L;
        givenJson().when().get(USERS_PATH + userId)
                .then()
                .body(FIRST_NAME, equalTo("Igor"))
                .body(LAST_NAME, equalTo("Jovic"))
                .body(EMAIL, equalTo("jovic.i24@gmail.com"))
                .body(STUDENTS_TRANSCRIPT, equalTo("122/12"))
                .body(COURSE_NAME, equalTo("Informacioni sistemi i tehnologije"));
    }

    @Test
    public void addUser_invalidEmail_badRequest() throws IOException {
        LoginData loginData = new LoginData();
        loginData.setEmail("invalidemail");
        loginData.setPassword("pass");

        givenJson().body(loginData)
                .when().post(USERS_PATH)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    public void addUser_invalidPassword_badRequest() throws IOException {
        LoginData loginData = new LoginData();
        loginData.setEmail("jovic.i24@gmail.com");
        loginData.setPassword("invaldpass");

        givenJson().body(loginData)
                .when().post(USERS_PATH)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    public void addUser_validUsernameAndPassword_returnsNewUser() throws IOException {
        LoginData loginData = new LoginData();
        loginData.setEmail("nonExistingAdmin@gmail.com");
        loginData.setPassword("Admin123");

        givenJson().body(loginData)
                .when().post(USERS_PATH)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body(ID, equalTo(4))
                .body(FIRST_NAME, isEmptyOrNullString())
                .body(LAST_NAME, isEmptyOrNullString())
                .body(EMAIL, equalTo("nonExistingAdmin@gmail.com"))
                .body(STUDENTS_TRANSCRIPT, isEmptyOrNullString())
                .body(COURSE, isEmptyOrNullString());
    }

    @Test
    public void addUser_validUsernameAndPasswordAndUserExists_badRequest() throws IOException {
        LoginData loginData = new LoginData();
        loginData.setEmail("admin@gmail.com");
        loginData.setPassword("Admin123");

        givenJson().body(loginData)
                .when().post(USERS_PATH)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }
}