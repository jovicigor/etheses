package rs.fon.elab.pzr.rest.model.response.level1;

import java.util.HashSet;
import java.util.Set;

import rs.fon.elab.pzr.rest.model.response.level2.StudiesResponseLevel2;

public class CourseResponseLevel1 {
	
	private Long id;
	
	private String name;
	
	private String nameShort;
	
	private Set<StudiesResponseLevel2> studies = new HashSet<>();

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

	public Set<StudiesResponseLevel2> getStudies() {
		return studies;
	}

	public void setStudies(Set<StudiesResponseLevel2> studies) {
		this.studies = studies;
	}		
}
