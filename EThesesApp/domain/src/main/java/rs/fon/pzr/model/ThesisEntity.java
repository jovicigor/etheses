package rs.fon.pzr.model;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "thesis")
public class ThesisEntity {


    @Id
    @Column(name = "thesis_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "grade")
    private Integer grade;

    @Column(name = "date_posted")
    private Date datePosted;

    @Column(name = "defense_date")
    private Date defenseDate;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private CourseEntity course;

    @ManyToOne
    @JoinColumn(name = "file_id")
    private TFileEntity file;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "view_count")
    private Integer viewCount = 0;

    @ManyToOne
    @JoinColumn(name = "user_mentor_id")
    private UserEntity mentor;

    @Column(name = "mentor_name")
    private String mentorName;

    @Column(name = "mentor_email")
    private String mentorEmail;

    @OneToMany(mappedBy = "thesis", fetch = FetchType.EAGER)
    private Set<ThesisComment> comments = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "thesis_tag", joinColumns = {@JoinColumn(name = "thesis_id")}, inverseJoinColumns = {@JoinColumn(name = "tag_id")})
    private Set<TagEntity> tags = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "thesis_field_of_study", joinColumns = {@JoinColumn(name = "thesis_id")}, inverseJoinColumns = {@JoinColumn(name = "field_of_study_id")})
    private Set<FieldOfStudyEntity> fieldOfStudies = new HashSet<>();

    @OneToMany(mappedBy = "thesis", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ThesisKeywordEntity> thesisKeywords = new HashSet<>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Date getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(Date datePosted) {
        this.datePosted = datePosted;
    }

    public Date getDefenseDate() {
        return defenseDate;
    }

    public void setDefenseDate(Date defenseDate) {
        this.defenseDate = defenseDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CourseEntity getCourse() {
        return course;
    }

    public void setCourse(CourseEntity course) {
        this.course = course;
    }

    public UserEntity getMentor() {
        return mentor;
    }

    public void setMentor(UserEntity mentor) {
        this.mentor = mentor;
    }

    public Set<ThesisComment> getComments() {
        return comments;
    }

    public void setComments(Set<ThesisComment> comments) {
        this.comments = comments;
    }

    public Set<TagEntity> getTags() {
        return tags;
    }

    public void setTags(Set<TagEntity> tags) {
        this.tags = tags;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public TFileEntity getFile() {
        return file;
    }

    public void setFile(TFileEntity file) {
        this.file = file;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public Set<ThesisKeywordEntity> getThesisKeywords() {
        return thesisKeywords;
    }

    public void setThesisKeywords(Set<ThesisKeywordEntity> thesisKeywords) {
        this.thesisKeywords = thesisKeywords;
    }

    public Set<FieldOfStudyEntity> getFieldOfStudies() {
        return fieldOfStudies;
    }

    public void setFieldOfStudies(Set<FieldOfStudyEntity> fieldOfStudies) {
        this.fieldOfStudies = fieldOfStudies;
    }

    public String getMentorName() {
        return mentorName;
    }

    public void setMentorName(String mentorName) {
        this.mentorName = mentorName;
    }

    public String getMentorEmail() {
        return mentorEmail;
    }

    public void setMentorEmail(String mentorEmail) {
        this.mentorEmail = mentorEmail;
    }
}
