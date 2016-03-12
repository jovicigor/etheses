package rs.fon.elab.pzr.core.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import rs.fon.elab.pzr.core.model.Course;
import rs.fon.elab.pzr.core.model.Subject;

public interface SubjectRepository extends CrudRepository<Subject, Long> {
	
	public List<Subject> findAll();
	public Subject findByName(String name);
}
