package rs.fon.pzr.rest.model.response.level2;

import java.util.HashSet;
import java.util.Set;

public class CourseResponseLevel2 {
	
	private Long id;
	
	private String name;
	
	private String nameShort;
	
	private Set<Long> studiesIDs = new HashSet<>();

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

	public Set<Long> getStudiesIDs() {
		return studiesIDs;
	}

	public void setStudiesIDs(Set<Long> studiesIDs) {
		this.studiesIDs = studiesIDs;
	}
	
}
