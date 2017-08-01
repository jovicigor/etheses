package rs.fon.pzr.model.thesis;

import rs.fon.pzr.model.studies.Course;
import rs.fon.pzr.model.user.UserEntity;
import rs.fon.pzr.type.Email;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "thesis")
public class Thesis {

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
    private Course course;

    @ManyToOne
    @JoinColumn(name = "file_id")
    private TFile file;

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
    private Set<Tag> tags = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "thesis_field_of_study", joinColumns = {@JoinColumn(name = "thesis_id")}, inverseJoinColumns = {@JoinColumn(name = "field_of_study_id")})
    private Set<FieldOfStudy> fieldOfStudies = new HashSet<>();

    @OneToMany(mappedBy = "thesis", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ThesisKeyword> thesisKeywords = new HashSet<>();

    protected Thesis() {
    }

    public Thesis(String name, Integer grade, Date datePosted, String description, Course course, UserEntity user, String userName, Email userEmail, UserEntity mentor, String mentorName, Email mentorEmail, Collection<Tag> tags, Collection<FieldOfStudy> fieldOfStudies, Collection<ThesisKeyword> thesisKeywords) {
        this.name = name;
        this.grade = grade;
        this.datePosted = datePosted;
        this.defenseDate = new Date();
        this.description = description;
        this.course = course;
        this.user = user;
        this.userName = userName;
        if (userEmail != null) {
            this.userEmail = userEmail.asString();
        }
        this.viewCount = 0;
        this.mentor = mentor;
        this.mentorName = mentorName;
        if (userEmail != null) {
            this.mentorEmail = mentorEmail.asString();
        }
        this.comments = comments.stream().collect(Collectors.toSet());
        this.tags = tags.stream().collect(Collectors.toSet());
        this.fieldOfStudies = fieldOfStudies.stream().collect(Collectors.toSet());
        this.thesisKeywords = thesisKeywords.stream().collect(Collectors.toSet());
    }

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
        thesisKeywords.clear();
        this.description = description;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public UserEntity getMentor() {
        return mentor;
    }

    public void setMentor(UserEntity mentor) {
        this.mentor = mentor;
    }

    public Collection<ThesisComment> getComments() {
        return new HashSet<>(comments);
    }

    public Collection<Tag> getTags() {
        return new HashSet<>(tags);
    }

    public void setTags(Collection<Tag> tags) {
        this.tags = tags.stream().collect(Collectors.toSet());
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public TFile getFile() {
        return file;
    }

    public void setFile(TFile file) {
        this.file = file;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Optional<Email> getUserEmail() {
        if (Email.isValid(userEmail))
            return Optional.ofNullable(Email.fromString(userEmail));
        else
            return Optional.empty();
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void updateViewCount() {
        this.viewCount++;
    }

    public Collection<FieldOfStudy> getFieldOfStudies() {
        return new HashSet<>(fieldOfStudies);
    }

    public void setFieldOfStudies(Set<FieldOfStudy> fieldOfStudies) {
        this.fieldOfStudies = fieldOfStudies;
    }

    public String getMentorName() {
        return mentorName;
    }

    public void setMentorName(String mentorName) {
        this.mentorName = mentorName;
    }

    public Optional<Email> getMentorEmail() {
        if (Email.isValid(mentorEmail))
            return Optional.ofNullable(Email.fromString(mentorEmail));
        else
            return Optional.empty();
    }

    public void setMentorEmail(String mentorEmail) {
        this.mentorEmail = mentorEmail;
    }

    public void addKeyword(Keyword keyword, int keywordFrequency) {
        ThesisKeyword thesisKeywod = new ThesisKeyword();
        thesisKeywod.setCount(keywordFrequency);
        thesisKeywod.setKeyword(keyword);
        thesisKeywod.setThesis(this);
        this.thesisKeywords.add(thesisKeywod);
    }
}
