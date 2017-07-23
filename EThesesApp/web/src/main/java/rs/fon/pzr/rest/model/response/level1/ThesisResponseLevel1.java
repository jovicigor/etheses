package rs.fon.pzr.rest.model.response.level1;

import rs.fon.pzr.persistence.model.FieldOfStudyEntity;
import rs.fon.pzr.persistence.model.TFileEntity;
import rs.fon.pzr.persistence.model.TagEntity;
import rs.fon.pzr.rest.model.response.level2.CourseResponseLevel2;
import rs.fon.pzr.rest.model.response.level2.ThesisCommentResponseLevel2;
import rs.fon.pzr.rest.model.response.level2.UserResponseLevel2;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class ThesisResponseLevel1 {
	private Long id;

	private String name;

	private Integer grade;

	private Date datePosted;

	private Date defenseDate;

	private String description;

	private CourseResponseLevel2 course;

	private UserResponseLevel2 user;

	private String userName;

	private String userEmail;
	
	private Integer viewCount = 0;

	private UserResponseLevel2 mentor;
	
	private String mentorName;

	private String mentorEmail;

	private Set<TagEntity> tags = new HashSet<>();
	
	private Set<FieldOfStudyEntity> fieldsOfStudy = new HashSet<>();

	private Set<ThesisCommentResponseLevel2> comments = new HashSet<>();

	private TFileEntity file;

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
	

	public CourseResponseLevel2 getCourse() {
		return course;
	}

	public void setCourse(CourseResponseLevel2 course) {
		this.course = course;
	}

	public UserResponseLevel2 getUser() {
		return user;
	}

	public void setUser(UserResponseLevel2 user) {
		this.user = user;
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

	public UserResponseLevel2 getMentor() {
		return mentor;
	}

	public void setMentor(UserResponseLevel2 mentor) {
		this.mentor = mentor;
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

	public Set<TagEntity> getTags() {
		return tags;
	}

	public void setTags(Set<TagEntity> tags) {
		this.tags = tags;
	}	

	public Set<FieldOfStudyEntity> getFieldsOfStudy() {
		return fieldsOfStudy;
	}

	public void setFieldsOfStudy(Set<FieldOfStudyEntity> fieldsOfStudy) {
		this.fieldsOfStudy = fieldsOfStudy;
	}

	public TFileEntity getFile() {
		return file;
	}

	public void setFile(TFileEntity file) {
		this.file = file;
	}

	public Set<ThesisCommentResponseLevel2> getComments() {
		return comments;
	}

	public void setComments(Set<ThesisCommentResponseLevel2> comments) {
		this.comments = comments;
	}

	public Integer getViewCount() {
		return viewCount;
	}

	public void setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
	}	

}
