package rs.fon.pzr.core.service;

import java.util.List;
import java.util.Optional;

import rs.fon.pzr.model.CourseEntity;


public interface CourseService {

    Optional<CourseEntity> getCourse(Long id);

    Optional<CourseEntity> getCourseByName(String name);

    List<CourseEntity> getAllCourses();

    CourseEntity addCourse(CourseEntity course);

    CourseEntity updateCourse(CourseEntity course);

    void removeCourse(Long courseID);

}