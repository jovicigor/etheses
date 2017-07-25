package rs.fon.pzr.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "thesis_keyword")
public class ThesisKeywordEntity {

	@Id
	@Column(name = "thesis_keyword_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "thesis_id")
	private ThesisEntity thesis;

	@ManyToOne
	@JoinColumn(name = "keyword_id")
	private KeywordEntity keyword;

	@Column(name = "count")
	private Integer count;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ThesisEntity getThesis() {
		return thesis;
	}

	public void setThesis(ThesisEntity thesis) {
		this.thesis = thesis;
	}

	public KeywordEntity getKeyword() {
		return keyword;
	}

	public void setKeyword(KeywordEntity keyword) {
		this.keyword = keyword;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

}
