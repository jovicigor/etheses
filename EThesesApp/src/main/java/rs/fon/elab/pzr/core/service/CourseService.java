package rs.fon.elab.pzr.core.service;

import java.util.List;

import rs.fon.elab.pzr.core.model.Course;


public interface CourseService {

	Course getCourse(Long id);
	
	Course getCourseByName(String name);

	List<Course> getAllCourses();

	Course addCourse(Course course);
	
	Course updateCourse(Course course);
	
	void removeCourse(Long courseID);
	
}