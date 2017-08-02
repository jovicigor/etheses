package rs.fon.pzr.persistence.jpa;

import org.springframework.data.repository.CrudRepository;
import rs.fon.pzr.core.domain.model.thesis.ThesisComment;

public interface CommentJpaRepository extends CrudRepository<ThesisComment, Long>{
	
	
}
