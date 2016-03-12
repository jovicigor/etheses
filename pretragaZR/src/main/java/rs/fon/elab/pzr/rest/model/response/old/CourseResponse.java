package rs.fon.elab.pzr.rest.model.response.old;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import rs.fon.elab.pzr.core.model.Subject;

public class CourseResponse {
	
	protected Long id;
	
	protected String name;
	
	protected String nameShort;
	
	protected Set<InnerSubject> subjects = new HashSet<InnerSubject>();	
	
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

	public Set<InnerSubject> getSubjects() {
		return subjects;
	}

	public void setSubjects(Set<InnerSubject> subjects) {
		this.subjects = subjects;
	}

	public class InnerSubject{
		
		protected Long id;
		
		protected String name;
		
		public InnerSubject(){
			
		}
		
		public InnerSubject(Long id, String name){
			this.id = id;
			this.name = name;
		}
		
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
		
	}
	
	public void addSubject(Long id,String name){
		subjects.add(new InnerSubject(id,name));
	}
	
	
}
