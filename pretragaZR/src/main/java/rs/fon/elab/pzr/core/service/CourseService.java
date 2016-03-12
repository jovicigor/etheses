package rs.fon.elab.pzr.core.service;

import java.util.List;
import java.util.Set;

import org.springframework.transaction.annotation.Transactional;

import rs.fon.elab.pzr.core.model.Course;
import rs.fon.elab.pzr.core.model.Subject;
import rs.fon.elab.pzr.core.repository.CourseRepository;
import rs.fon.elab.pzr.core.repository.SubjectRepository;

public interface CourseService {

	public abstract Course getCourse(Long id);
	
	public abstract Course getCourseByName(String name);

	public abstract List<Course> getAllCourses();

	public abstract Course addCourse(Course course);
	
	public abstract Course updateCourse(Course course);
	
	public abstract void removeCourse(Long courseID);
	
}