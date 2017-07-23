package rs.fon.pzr.core.repository;

import org.springframework.data.repository.CrudRepository;

import rs.fon.pzr.core.model.ThesisComment;

public interface CommentRepository extends CrudRepository<ThesisComment, Long>{
	
	
}
