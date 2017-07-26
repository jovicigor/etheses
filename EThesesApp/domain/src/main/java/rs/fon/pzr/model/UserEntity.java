package rs.fon.pzr.model;

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

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Embedded
    private UserLogin userLogin;

    @Column(name = "students_transcript")
    private String studentsTranscript;

    @Column(name = "is_admin")
    private boolean isAdmin;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private CourseEntity course;

    @Column(name = "biography")
    private String biography;

    @Column(name = "interests")
    private String interests;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<ThesisEntity> theses = new HashSet<>();

    protected UserEntity() {
    }

    public UserEntity(String email, String password) {
        this.userLogin = new UserLogin(email, password);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return userLogin.getEmail();
    }

    public String getPassword() {
        return userLogin.getPassword();
    }

    public void setPassword(String password) {
        userLogin.setPassword(password);
    }

    public String getStudentsTranscript() {
        return studentsTranscript;
    }

    public void setStudentsTranscript(String studentsTranscript) {
        this.studentsTranscript = studentsTranscript;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public CourseEntity getCourse() {
        return course;
    }

    public void setCourse(CourseEntity course) {
        this.course = course;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getInterests() {
        return interests;
    }

    public void setInterests(String interests) {
        this.interests = interests;
    }

    public Set<ThesisEntity> getTheses() {
        return theses;
    }

    public void setTheses(Set<ThesisEntity> theses) {
        this.theses = theses;
    }
}
