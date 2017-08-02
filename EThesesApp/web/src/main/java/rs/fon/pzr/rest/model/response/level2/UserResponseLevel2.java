package rs.fon.pzr.rest.model.response.level2;

import rs.fon.pzr.core.domain.model.thesis.Thesis;
import rs.fon.pzr.core.domain.model.user.UserEntity;

import java.util.List;
import java.util.stream.Collectors;

public class UserResponseLevel2 {

    private final Long id;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String studentsTranscript;
    private final boolean isAdmin;
    private final Long courseId;
    private final List<Long> thesisIDs;
    private final String biography;
    private final String interests;

    public UserResponseLevel2(UserEntity author) {
        id = author.getId();
        firstName = author.getFirstName();
        lastName = author.getLastName();
        email = author.getEmail().asString();
        studentsTranscript = author.getStudentsTranscript();
        isAdmin = author.isAdmin();
        if (author.getCourse() != null)
            courseId = author.getCourse().getId();
        else {
            courseId = null;
        }
        thesisIDs = author.getTheses().stream()
                .map(Thesis::getId)
                .collect(Collectors.toList());
        biography = author.getBiography();
        interests = author.getInterests();
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getStudentsTranscript() {
        return studentsTranscript;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public Long getCourseId() {
        return courseId;
    }

    public List<Long> getThesisIDs() {
        return thesisIDs;
    }

    public String getBiography() {
        return biography;
    }

    public String getInterests() {
        return interests;
    }
}
