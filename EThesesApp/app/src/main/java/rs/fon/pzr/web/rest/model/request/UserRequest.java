package rs.fon.pzr.web.rest.model.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserRequest {

    private final String firstName;
    private final String lastName;
    private final String studentsTranscript;
    private final String courseName;
    private final String biography;
    private final String interests;

    @JsonCreator
    public UserRequest(@JsonProperty("firstName") String firstName,
                       @JsonProperty("lastName") String lastName,
                       @JsonProperty("studentsTranscript") String studentsTranscript,
                       @JsonProperty("courseName") String courseName,
                       @JsonProperty("biography") String biography,
                       @JsonProperty("interests") String interests) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.studentsTranscript = studentsTranscript;
        this.courseName = courseName;
        this.biography = biography;
        this.interests = interests;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getStudentsTranscript() {
        return studentsTranscript;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getBiography() {
        return biography;
    }

    public String getInterests() {
        return interests;
    }
}
