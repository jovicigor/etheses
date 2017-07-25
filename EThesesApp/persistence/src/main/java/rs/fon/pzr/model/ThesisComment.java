package rs.fon.pzr.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="thesis_comment")
public class ThesisComment {
	@Id
	@Column(name = "thesis_comment_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name="comment_message")
	private String message;
	
	@Column(name = "date_posted")
	private Date datePosted;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private UserEntity author;
	
	@ManyToOne
	@JoinColumn(name = "thesis_id")
	private ThesisEntity thesis;

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

	public UserEntity getAuthor() {
		return author;
	}

	public void setAuthor(UserEntity author) {
		this.author = author;
	}

	public ThesisEntity getThesis() {
		return thesis;
	}

	public void setThesis(ThesisEntity thesis) {
		this.thesis = thesis;
	}	
	
}
