package rs.fon.pzr.core.service.repository;

import rs.fon.pzr.core.domain.model.thesis.ThesisComment;

public interface CommentRepository {

    ThesisComment findOne(Long commentId);

    void delete(ThesisComment one);

    ThesisComment save(ThesisComment thesisComment);
}
