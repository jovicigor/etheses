package rs.fon.pzr.persistence.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "course")
public class CourseEntity {

	@Id
	@Column(name = "course_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@Column(name = "name")
    private String name;
	
	@Column(name = "name_short")
    private String nameShort;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "course_studies", joinColumns = { @JoinColumn(name = "course_id") }, inverseJoinColumns = { @JoinColumn(name = "studies_id") })
    private Set<StudiesEntity> studies = new HashSet<>();
	
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

	public String getNameShort() {
		return nameShort;
	}

	public void setNameShort(String nameShort) {
		this.nameShort = nameShort;
	}

	public Set<StudiesEntity> getStudies() {
		return studies;
	}

	public void setStudies(Set<StudiesEntity> studies) {
		this.studies = studies;
	}	
}
