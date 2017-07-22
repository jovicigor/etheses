package rs.fon.elab.pzr.core.repository;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import rs.fon.elab.pzr.core.model.FieldOfStudy;

public interface FieldOfStudyRepository extends CrudRepository<FieldOfStudy, Long> {
	
	FieldOfStudy findByName(String name);
	Set<FieldOfStudy> findAll();

}
