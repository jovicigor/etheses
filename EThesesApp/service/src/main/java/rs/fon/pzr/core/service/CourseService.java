package rs.fon.pzr.core.service;

import java.util.List;
import java.util.Optional;

import rs.fon.pzr.core.domain.model.studies.Course;


public interface CourseService {

    Optional<Course> getCourse(Long id);

    Optional<Course> getCourseByName(String name);

    List<Course> getAllCourses();

    Course addCourse(Course course);

    Course updateCourse(Course course);

    void removeCourse(Long courseID);

}