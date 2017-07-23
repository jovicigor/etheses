package rs.fon.pzr.core.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import rs.fon.pzr.core.model.Course;

public interface CourseRepository extends CrudRepository<Course, Long> {
	
	List<Course> findAll();
	Course findByName(String name);
	Course findByNameShort(String nameShort);
}