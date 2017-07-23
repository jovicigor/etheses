package rs.fon.pzr.rest.model.response.level1;

import java.util.Set;

import rs.fon.pzr.rest.model.response.level2.CourseResponseLevel2;
import rs.fon.pzr.rest.model.response.level2.ThesisResponseLevel2;

public class UserResponseLevel1 {

	private Long id;

	private String firstName;

	private String lastName;

	private String email;

	private String studentsTranscript;

	private boolean isAdmin;

	private CourseResponseLevel2 course;

	private Set<ThesisResponseLevel2> theses;

	private String biography;

	private String interests;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getStudentsTranscript() {
		return studentsTranscript;
	}

	public void setStudentsTranscript(String studentsTranscript) {
		this.studentsTranscript = studentsTranscript;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public CourseResponseLevel2 getCourse() {
		return course;
	}

	public void setCourse(CourseResponseLevel2 course) {
		this.course = course;
	}

	public Set<ThesisResponseLevel2> getTheses() {
		return theses;
	}

	public void setTheses(Set<ThesisResponseLevel2> theses) {
		this.theses = theses;
	}

	public String getBiography() {
		return biography;
	}

	public void setBiography(String biography) {
		this.biography = biography;
	}

	public String getInterests() {
		return interests;
	}

	public void setInterests(String interests) {
		this.interests = interests;
	}
}
