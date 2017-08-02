package rs.fon.pzr.core.repository;

import rs.fon.pzr.model.thesis.ThesisComment;

public interface CommentRepository {

    ThesisComment findOne(Long commentId);

    void delete(ThesisComment one);

    ThesisComment save(ThesisComment thesisComment);
}