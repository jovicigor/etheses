package rs.fon.pzr.persistence.repository;

import org.springframework.data.repository.CrudRepository;

import rs.fon.pzr.persistence.model.ThesisComment;

public interface CommentRepository extends CrudRepository<ThesisComment, Long>{
	
	
}
