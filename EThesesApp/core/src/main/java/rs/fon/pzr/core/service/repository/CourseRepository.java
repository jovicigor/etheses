package rs.fon.pzr.core.service.repository;

import rs.fon.pzr.core.domain.model.studies.Course;

import java.util.List;

public interface CourseRepository {
    Course findOne(Long id);


    Course findByName(String name);

    List<Course> findAll();

    boolean existsByName(String name);

    boolean existsByNameShort(String nameShort);

    Course save(Course course);

    Course findByNameShort(String nameShort);

    void delete(Course course);
}
