package rs.fon.pzr.model.user;

import rs.fon.pzr.guards.NullGuard;
import rs.fon.pzr.model.thesis.Thesis;
import rs.fon.pzr.model.studies.Course;
import rs.fon.pzr.type.Email;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user")
public class UserEntity {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private UserCredentials userCredentials;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "biography")
    private String biography;

    @Column(name = "interests")
    private String interests;

    @Column(name = "students_transcript")
    private String studentsTranscript;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<Thesis> theses;

    protected UserEntity() {
    }

    private UserEntity(UserCredentials userCredentials) {
        String empty = "";
        firstName = empty;
        lastName = empty;
        biography = empty;
        studentsTranscript = empty;
        interests = empty;
        course = null;
        theses = new HashSet<>();
        this.userCredentials = userCredentials;
    }

    public static UserEntity createUserWithCredentials(UserCredentials userCredentials) {
        NullGuard.validate("user credentials", userCredentials);
        return new UserEntity(userCredentials);
    }

    public Long getId() {
        return id;
    }

    public Email getEmail() {
        return userCredentials.getEmail();
    }

    public String getPassword() {
        return userCredentials.getPassword();
    }

    public boolean isAdmin() {
        return userCredentials.isAdmin();
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getBiography() {
        return biography;
    }

    public String getInterests() {
        return interests;
    }

    public String getStudentsTranscript() {
        return studentsTranscript;
    }

    public Course getCourse() {
        return course;
    }

    public Set<Thesis> getTheses() {
        return theses;
    }

    public void setFirstName(String firstName) {
        NullGuard.validate("user first name", firstName);
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        NullGuard.validate("user last name", lastName);
        this.lastName = lastName;
    }

    public void setInterests(String interests) {
        NullGuard.validate("interests", interests);
        this.interests = interests;
    }

    public void setBiography(String biography) {
        NullGuard.validate("biography", biography);
        this.biography = biography;
    }

    public void setStudentsTranscript(String studentsTranscript) {
        NullGuard.validate("students transcript", studentsTranscript);
        this.studentsTranscript = studentsTranscript;
    }

    public void makeAdmin() {
        userCredentials.makeAdmin();
    }

    public void removeAdmin() {
        userCredentials.removeAdmin();
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public void setTheses(Set<Thesis> theses) {
        this.theses = theses;
    }
}
