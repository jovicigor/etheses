package rs.fon.pzr.core.service;

import java.util.List;

import rs.fon.pzr.persistence.model.CourseEntity;


public interface CourseService {

    CourseEntity getCourse(Long id);

    CourseEntity getCourseByName(String name);

    List<CourseEntity> getAllCourses();

    CourseEntity addCourse(CourseEntity course);

    CourseEntity updateCourse(CourseEntity course);

    void removeCourse(Long courseID);

}