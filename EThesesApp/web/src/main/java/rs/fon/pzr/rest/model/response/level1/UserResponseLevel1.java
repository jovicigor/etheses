package rs.fon.pzr.rest.model.response.level1;

import rs.fon.pzr.core.domain.model.user.UserEntity;
import rs.fon.pzr.rest.model.response.level2.CourseResponseLevel2;
import rs.fon.pzr.rest.model.response.level2.ThesisResponseLevel2;

import java.util.Set;
import java.util.stream.Collectors;

public class UserResponseLevel1 {

    private final Long id;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String studentsTranscript;
    private final String biography;
    private final String interests;
    private final boolean isAdmin;
    private final CourseResponseLevel2 course;
    private final Set<ThesisResponseLevel2> theses;

    public UserResponseLevel1(UserEntity user) {
        id = user.getId();
        firstName = user.getFirstName();
        lastName = user.getLastName();
        email = user.getEmail().asString();
        studentsTranscript = user.getStudentsTranscript();
        isAdmin = user.isAdmin();
        if (user.getCourse() != null) {
            course = new CourseResponseLevel2(user.getCourse());
        } else {
            course = null;
        }
        theses = user.getTheses().stream()
                .map(ThesisResponseLevel2::new)
                .collect(Collectors.toSet());
        biography = user.getBiography();
        interests = user.getInterests();
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

    public CourseResponseLevel2 getCourse() {
        return course;
    }

    public Set<ThesisResponseLevel2> getTheses() {
        return theses;
    }

    public String getBiography() {
        return biography;
    }

    public String getInterests() {
        return interests;
    }
}
