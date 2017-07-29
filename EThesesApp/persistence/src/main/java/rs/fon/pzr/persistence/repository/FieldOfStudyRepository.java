package rs.fon.pzr.persistence.repository;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import rs.fon.pzr.model.thesis.FieldOfStudyEntity;

public interface FieldOfStudyRepository extends CrudRepository<FieldOfStudyEntity, Long> {
	
	FieldOfStudyEntity findByName(String name);
	Set<FieldOfStudyEntity> findAll();

}
