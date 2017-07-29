package rs.fon.pzr.persistence.repository;

import org.springframework.data.repository.CrudRepository;

import rs.fon.pzr.model.thesis.ThesisComment;

public interface CommentRepository extends CrudRepository<ThesisComment, Long>{
	
	
}
