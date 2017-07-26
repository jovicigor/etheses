package rs.fon.pzr.model;

import javax.persistence.*;

@Entity
@Table(name = "keyword")
public class KeywordEntity {
	
	@Id
	@Column(name = "keyword_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@Column(name = "value")
    private String value;
	
	@Column(name = "is_banned")
    private boolean banned;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public boolean getBanned() {
		return banned;
	}

	public void setBanned(boolean banned) {
		this.banned = banned;
	}
	
}