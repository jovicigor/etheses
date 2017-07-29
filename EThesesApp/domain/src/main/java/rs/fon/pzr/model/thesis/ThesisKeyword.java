package rs.fon.pzr.model.thesis;

import javax.persistence.*;

@Entity
@Table(name = "thesis_keyword")
public class ThesisKeyword {

	@Id
	@Column(name = "thesis_keyword_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "thesis_id")
	private Thesis thesis;

	@ManyToOne
	@JoinColumn(name = "keyword_id")
	private Keyword keyword;

	@Column(name = "count")
	private Integer count;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Thesis getThesis() {
		return thesis;
	}

	public void setThesis(Thesis thesis) {
		this.thesis = thesis;
	}

	public Keyword getKeyword() {
		return keyword;
	}

	public void setKeyword(Keyword keyword) {
		this.keyword = keyword;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

}
