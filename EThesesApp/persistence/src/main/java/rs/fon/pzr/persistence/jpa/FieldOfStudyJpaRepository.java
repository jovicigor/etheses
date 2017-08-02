package rs.fon.pzr.persistence.jpa;

import org.springframework.data.repository.CrudRepository;
import rs.fon.pzr.core.domain.model.thesis.FieldOfStudy;

import java.util.Set;

public interface FieldOfStudyJpaRepository extends CrudRepository<FieldOfStudy, Long> {
	
	FieldOfStudy findByName(String name);
	Set<FieldOfStudy> findAll();

}
