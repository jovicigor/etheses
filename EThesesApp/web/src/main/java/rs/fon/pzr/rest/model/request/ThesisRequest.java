package rs.fon.pzr.rest.model.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.Set;

public class ThesisRequest {

    private String name;
    private Integer grade;
    private Date defenseDate;
    private String description;
    private String courseName;
    private Long userId;
    private String userName;
    private String userEmail;
    private Long mentorId;
    private String mentorName;
    private String mentorEmail;
    private Set<String> tags;
    private Set<String> fieldsOfStudy;

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

    public Integer getGrade() {
        return grade;
    }

    public Date getDefenseDate() {
        return defenseDate;
    }

    public String getDescription() {
        return description;
    }

    public String getCourseName() {
        return courseName;
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

    public Long getMentorId() {
        return mentorId;
    }

    public String getMentorName() {
        return mentorName;
    }

    public String getMentorEmail() {
        return mentorEmail;
    }

    public Set<String> getTags() {
        return tags;
    }

    public Set<String> getFieldsOfStudy() {
        return fieldsOfStudy;
    }
}
