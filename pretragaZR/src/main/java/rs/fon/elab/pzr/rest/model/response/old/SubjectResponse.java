package rs.fon.elab.pzr.rest.model.response.old;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;

public class SubjectResponse {
	
	protected Long id;
	
	protected String name;
	
	protected Set<InnerCourse> courses = new HashSet<InnerCourse>();	

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

	public Set<InnerCourse> getCourses() {
		return courses;
	}

	public void setCourses(Set<InnerCourse> courses) {
		this.courses = courses;
	}

	public class InnerCourse{
		
		protected Long id;
		
		protected String name;
		
		protected String nameShort;
		
		public InnerCourse(){
			
		}
		
		public InnerCourse(Long id, String name, String nameShort){
			this.id = id;
			this.name = name;
			this.nameShort = nameShort;
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

		public String getNameShort() {
			return nameShort;
		}

		public void setNameShort(String nameShort) {
			this.nameShort = nameShort;
		}		
	}
	
	public void addCourse(Long id, String name, String nameShort) {
		courses.add(new InnerCourse(id,name,nameShort));
	}

	
	
}
