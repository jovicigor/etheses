package rs.fon.pzr.rest.model.response.level2;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import rs.fon.pzr.core.model.FieldOfStudy;
import rs.fon.pzr.core.model.TFile;
import rs.fon.pzr.core.model.Tag;

public class ThesisResponseLevel2 {
	private Long id;

	private String name;

	private Integer grade;

	private Date datePosted;

	private Date defenseDate;

	private String description;

	private Long courseId;

	private Long userId;

	private String userName;

	private String userEmail;
	
	private Integer viewCount = 0;

	private Long mentorId;
	
	private String mentorName;

	private String mentorEmail;

	private Set<Tag> tags = new HashSet<>();
	
	private Set<FieldOfStudy> fieldsOfStudy = new HashSet<>();

	private Set<Long> commentIDs = new HashSet<>();

	private TFile file;

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

	public Long getCourseId() {
		return courseId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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

	public Long getMentorId() {
		return mentorId;
	}

	public void setMentorId(Long mentorId) {
		this.mentorId = mentorId;
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

	public Set<Tag> getTags() {
		return tags;
	}

	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}	

	public Set<FieldOfStudy> getFieldsOfStudy() {
		return fieldsOfStudy;
	}

	public void setFieldsOfStudy(Set<FieldOfStudy> fieldsOfStudy) {
		this.fieldsOfStudy = fieldsOfStudy;
	}

	public Set<Long> getCommentIDs() {
		return commentIDs;
	}

	public void setCommentIDs(Set<Long> commentIDs) {
		this.commentIDs = commentIDs;
	}

	public TFile getFile() {
		return file;
	}

	public void setFile(TFile file) {
		this.file = file;
	}

	public Integer getViewCount() {
		return viewCount;
	}

	public void setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
	}	
}
