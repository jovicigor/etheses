package rs.fon.pzr.rest.model.response.level1;

import rs.fon.pzr.model.FieldOfStudyEntity;
import rs.fon.pzr.model.TFileEntity;
import rs.fon.pzr.model.TagEntity;
import rs.fon.pzr.model.ThesisEntity;
import rs.fon.pzr.rest.model.response.level2.CourseResponseLevel2;
import rs.fon.pzr.rest.model.response.level2.ThesisCommentResponseLevel2;
import rs.fon.pzr.rest.model.response.level2.UserResponseLevel2;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ThesisResponseLevel1 {

    private final Long id;
    private final String name;
    private final Integer grade;
    private final Date datePosted;
    private final Date defenseDate;
    private final String description;
    private final CourseResponseLevel2 course;
    private final UserResponseLevel2 user;
    private final String userName;
    private final String userEmail;
    private final Integer viewCount;
    private final UserResponseLevel2 mentor;
    private final String mentorName;
    private final String mentorEmail;
    private final Set<TagResponse> tags;
    private final Set<FieldOfStudyResponse> fieldsOfStudy;
    private final Set<ThesisCommentResponseLevel2> comments;
    private final TFileEntity file;

    public ThesisResponseLevel1(ThesisEntity thesis) {
        id = thesis.getId();
        name = thesis.getName();
        grade = thesis.getGrade();
        datePosted = thesis.getDatePosted();
        defenseDate = thesis.getDefenseDate();
        description = thesis.getDescription();
        if (thesis.getCourse() != null)
            course = new CourseResponseLevel2(thesis.getCourse());
        else
            course = null;
        if (thesis.getUser() != null)
            user = new UserResponseLevel2(thesis.getUser());
        else
            user = null;
        userName = thesis.getUserName();
        userEmail = thesis.getUserEmail();
        viewCount = thesis.getViewCount();
        if (thesis.getMentor() != null)
            mentor = new UserResponseLevel2(thesis.getMentor());
        else
            mentor = null;
        mentorName = thesis.getName();
        mentorEmail = thesis.getMentorEmail();
        tags = thesis.getTags().stream()
                .map(TagResponse::new)
                .collect(Collectors.toSet());
        fieldsOfStudy = thesis.getFieldOfStudies().stream()
                .map(FieldOfStudyResponse::new)
                .collect(Collectors.toSet());
        comments = thesis.getComments().stream()
                .map(ThesisCommentResponseLevel2::new)
                .collect(Collectors.toSet());
        file = thesis.getFile();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getGrade() {
        return grade;
    }

    public Date getDatePosted() {
        return datePosted;
    }

    public Date getDefenseDate() {
        return defenseDate;
    }

    public String getDescription() {
        return description;
    }

    public CourseResponseLevel2 getCourse() {
        return course;
    }

    public UserResponseLevel2 getUser() {
        return user;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public UserResponseLevel2 getMentor() {
        return mentor;
    }

    public String getMentorName() {
        return mentorName;
    }

    public String getMentorEmail() {
        return mentorEmail;
    }

    public Set<TagResponse> getTags() {
        return tags;
    }

    public Set<FieldOfStudyResponse> getFieldsOfStudy() {
        return fieldsOfStudy;
    }

    public Set<ThesisCommentResponseLevel2> getComments() {
        return comments;
    }

    public TFileEntity getFile() {
        return file;
    }
}
