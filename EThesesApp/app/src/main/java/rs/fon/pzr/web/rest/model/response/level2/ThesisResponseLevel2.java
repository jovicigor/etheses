package rs.fon.pzr.web.rest.model.response.level2;

import rs.fon.pzr.core.domain.model.thesis.TFile;
import rs.fon.pzr.core.domain.model.thesis.Thesis;
import rs.fon.pzr.core.domain.model.thesis.ThesisComment;
import rs.fon.pzr.web.rest.model.response.level1.FieldOfStudyResponse;
import rs.fon.pzr.web.rest.model.response.level1.TagResponse;
import rs.fon.pzr.core.domain.type.Email;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

public class ThesisResponseLevel2 {
    private final Long id;
    private final String name;
    private final Integer grade;
    private final Date datePosted;
    private final Date defenseDate;
    private final String description;
    private final Long courseId;
    private final Long userId;
    private final String userName;
    private final String userEmail;
    private final Integer viewCount;
    private final Long mentorId;
    private final String mentorName;
    private final String mentorEmail;
    private final Set<TagResponse> tags;
    private final Set<FieldOfStudyResponse> fieldsOfStudy;
    private final Set<Long> commentIDs;
    private final TFile file;

    public ThesisResponseLevel2(Thesis thesis) {
        id = thesis.getId();
        name = thesis.getName();
        grade = thesis.getGrade();
        datePosted = thesis.getDatePosted();
        defenseDate = thesis.getDefenseDate();
        description = thesis.getDescription();
        if (thesis.getCourse() != null)
            courseId = thesis.getCourse().getId();
        else
            courseId = null;
        if (thesis.getUser() != null)
            userId = thesis.getUser().getId();
        else
            userId = null;
        userName = thesis.getUserName();
        userEmail = thesis.getUserEmail().map(Email::asString).orElse("");
        viewCount = thesis.getViewCount();
        if (thesis.getMentor() != null)
            mentorId = thesis.getMentor().getId();
        else
            mentorId = null;
        mentorName = thesis.getMentorName();
        mentorEmail = thesis.getMentorEmail().map(Email::asString).orElse("");
        tags = thesis.getTags().stream()
                .map(TagResponse::new)
                .collect(Collectors.toSet());
        fieldsOfStudy = thesis.getFieldOfStudies().stream()
                .map(FieldOfStudyResponse::new)
                .collect(Collectors.toSet());
        commentIDs = thesis.getComments().stream()
                .map(ThesisComment::getId)
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

    public Long getCourseId() {
        return courseId;
    }

    public Long getUserId() {
        return userId;
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

    public Long getMentorId() {
        return mentorId;
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

    public Set<Long> getCommentIDs() {
        return commentIDs;
    }

    public TFile getFile() {
        return file;
    }
}
