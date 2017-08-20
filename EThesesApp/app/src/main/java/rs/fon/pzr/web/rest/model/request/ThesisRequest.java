package rs.fon.pzr.web.rest.model.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

public class ThesisRequest {
    @NotEmpty
    private final String name;
    private final Integer grade;
    private final Date defenseDate;
    private final String description;
    private final String courseName;
    private final Long userId;
    private final String userName;
    private final String userEmail;
    private final Long mentorId;
    private final String mentorName;
    private final String mentorEmail;
    private final Set<String> tags;
    private final Set<String> fieldsOfStudy;

    @JsonCreator
    public ThesisRequest(@JsonProperty("name") String name,
                         @JsonProperty("grade") Integer grade,
                         @JsonProperty("defenseDate") Date defenseDate,
                         @JsonProperty("description") String description,
                         @JsonProperty("courseName") String courseName,
                         @JsonProperty("userId") Long userId,
                         @JsonProperty("userName") String userName,
                         @JsonProperty("userEmail") String userEmail,
                         @JsonProperty("mentorId") Long mentorId,
                         @JsonProperty("mentorName") String mentorName,
                         @JsonProperty("mentorEmail") String mentorEmail,
                         @JsonProperty("tags") Set<String> tags,
                         @JsonProperty("fieldsOfStudy") Set<String> fieldsOfStudy) {
        this.name = name;
        this.grade = grade;
        this.defenseDate = defenseDate;
        this.description = description;
        this.courseName = courseName;
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.mentorId = mentorId;
        this.mentorName = mentorName;
        this.mentorEmail = mentorEmail;
        this.tags = tags;
        this.fieldsOfStudy = fieldsOfStudy;
    }

    public String getName() {
        return name;
    }

    public Optional<Integer> getGrade() {
        return Optional.ofNullable(grade);
    }

    public Optional<Date> getDefenseDate() {
        return Optional.ofNullable(defenseDate);
    }

    public Optional<String> getDescription() {
        return Optional.ofNullable(description);
    }

    public Optional<String> getCourseName() {
        return Optional.ofNullable(courseName);
    }

    public Optional<Long> getUserId() {
        return Optional.ofNullable(userId);
    }

    public Optional<String> getUserName() {
        return Optional.ofNullable(userName);
    }

    public Optional<String> getUserEmail() {
        return Optional.ofNullable(userEmail);
    }

    public Optional<Long> getMentorId() {
        return Optional.ofNullable(mentorId);
    }

    public Optional<String> getMentorName() {
        return Optional.ofNullable(mentorName);
    }

    public Optional<String> getMentorEmail() {
        return Optional.ofNullable(mentorEmail);
    }

    public Set<String> getTags() {
        return tags;
    }

    public Set<String> getFieldsOfStudy() {
        return fieldsOfStudy;
    }
}
