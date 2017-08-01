package rs.fon.pzr.persistence.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import rs.fon.pzr.core.repository.CommentRepository;
import rs.fon.pzr.model.thesis.ThesisComment;
import rs.fon.pzr.persistence.jpa.CommentJpaRepository;

@Repository
public class CommentRepositoryImpl implements CommentRepository {

    private final CommentJpaRepository jpaRepository;

    @Autowired
    public CommentRepositoryImpl(CommentJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public ThesisComment findOne(Long commentId) {
        return jpaRepository.findOne(commentId);
    }

    @Override
    public void delete(ThesisComment one) {
        jpaRepository.delete(one);
    }

    @Override
    public ThesisComment save(ThesisComment thesisComment) {
        return jpaRepository.save(thesisComment);
    }
}
