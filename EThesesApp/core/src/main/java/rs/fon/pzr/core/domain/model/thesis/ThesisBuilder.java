package rs.fon.pzr.core.domain.model.thesis;

import rs.fon.pzr.core.domain.model.studies.Course;
import rs.fon.pzr.core.domain.model.user.UserEntity;
import rs.fon.pzr.core.domain.type.Email;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;

public class ThesisBuilder {
    private static final String EMPTY = "";

    private String name;
    private Integer grade;
    private Date defenseDate;
    private String description = EMPTY;
    private Course course;
    private UserEntity user;
    private String userName = EMPTY;
    private Email userEmail;
    private UserEntity mentor;
    private String mentorName = EMPTY;
    private Email mentorEmail;
    private Collection<Tag> tags = Collections.emptyList();
    private Collection<FieldOfStudy> fieldOfStudies = Collections.emptyList();
    private Collection<ThesisKeyword> thesisKeywords = Collections.emptyList();

    public ThesisBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public ThesisBuilder withGrade(Integer grade) {
        this.grade = grade;
        return this;
    }

    public ThesisBuilder withDefenseDate(Date defenseDate) {
        this.defenseDate = defenseDate;
        return this;
    }

    public ThesisBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public ThesisBuilder withCourse(Course course) {
        this.course = course;
        return this;
    }

    public ThesisBuilder withUser(UserEntity user) {
        this.user = user;
        return this;
    }

    public ThesisBuilder withUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public ThesisBuilder withUserEmail(Email userEmail) {
        this.userEmail = userEmail;
        return this;
    }


    public ThesisBuilder withMentor(UserEntity mentor) {
        this.mentor = mentor;
        return this;
    }

    public ThesisBuilder withMentorName(String mentorName) {
        this.mentorName = mentorName;
        return this;
    }

    public ThesisBuilder withMentorEmail(Email mentorEmail) {
        this.mentorEmail = mentorEmail;
        return this;
    }

    public ThesisBuilder withTags(Collection<Tag> tags) {
        this.tags = tags;
        return this;
    }

    public ThesisBuilder withFieldOfStudies(Collection<FieldOfStudy> fieldOfStudies) {
        this.fieldOfStudies = fieldOfStudies;
        return this;
    }

    public Thesis build() {
//        TODO INTRODUCE GUARDS
        return new Thesis(name, grade, defenseDate, description, course, user, userName, userEmail, mentor, mentorName, mentorEmail, tags, fieldOfStudies, thesisKeywords);
    }
}