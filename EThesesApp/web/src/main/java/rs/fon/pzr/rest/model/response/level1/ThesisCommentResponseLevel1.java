package rs.fon.pzr.rest.model.response.level1;

import rs.fon.pzr.rest.model.response.level2.ThesisResponseLevel2;
import rs.fon.pzr.rest.model.response.level2.UserResponseLevel2;

import java.util.Date;

public class ThesisCommentResponseLevel1 {

	private Long id;

	private String message;
	
	private Date datePosted;
	
	private UserResponseLevel2 author;
	
	private ThesisResponseLevel2 thesis;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getDatePosted() {
		return datePosted;
	}

	public void setDatePosted(Date datePosted) {
		this.datePosted = datePosted;
	}

	public UserResponseLevel2 getAuthor() {
		return author;
	}

	public void setAuthor(UserResponseLevel2 author) {
		this.author = author;
	}

	public ThesisResponseLevel2 getThesis() {
		return thesis;
	}

	public void setThesis(ThesisResponseLevel2 thesis) {
		this.thesis = thesis;
	}
	
}
