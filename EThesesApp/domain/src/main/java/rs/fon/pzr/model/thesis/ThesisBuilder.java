package rs.fon.pzr.model.thesis;

import rs.fon.pzr.guards.NullGuard;
import rs.fon.pzr.model.studies.Course;
import rs.fon.pzr.model.user.UserEntity;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

public class ThesisBuilder {
    private String name;
    private Integer grade;
    private Date defenseDate;
    private String description;
    private Course course;
    private UserEntity user;
    private String userName;
    private String userEmail;
    private UserEntity mentor;
    private String mentorName;
    private String mentorEmail;
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

    public ThesisBuilder withUserEmail(String userEmail) {
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

    public ThesisBuilder withMentorEmail(String mentorEmail) {
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