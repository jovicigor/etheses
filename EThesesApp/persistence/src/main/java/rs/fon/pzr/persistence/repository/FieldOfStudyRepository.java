package rs.fon.pzr.persistence.repository;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import rs.fon.pzr.model.thesis.FieldOfStudy;

public interface FieldOfStudyRepository extends CrudRepository<FieldOfStudy, Long> {
	
	FieldOfStudy findByName(String name);
	Set<FieldOfStudy> findAll();

}
