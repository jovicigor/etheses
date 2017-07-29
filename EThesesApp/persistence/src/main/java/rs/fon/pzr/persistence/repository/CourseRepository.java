package rs.fon.pzr.persistence.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import rs.fon.pzr.model.studies.CourseEntity;

public interface CourseRepository extends CrudRepository<CourseEntity, Long> {
	
	List<CourseEntity> findAll();
	CourseEntity findByName(String name);
	CourseEntity findByNameShort(String nameShort);
}
