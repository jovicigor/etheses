package rs.fon.pzr.rest.model.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserRequest {

    private String firstName;
    private String lastName;
    private String studentsTranscript;
    private String courseName;
    private String biography;
    private String interests;

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
