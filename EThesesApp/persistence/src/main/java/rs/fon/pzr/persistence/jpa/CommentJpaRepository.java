package rs.fon.pzr.persistence.jpa;

import org.springframework.data.repository.CrudRepository;
import rs.fon.pzr.model.thesis.ThesisComment;

public interface CommentJpaRepository extends CrudRepository<ThesisComment, Long>{
	
	
}
