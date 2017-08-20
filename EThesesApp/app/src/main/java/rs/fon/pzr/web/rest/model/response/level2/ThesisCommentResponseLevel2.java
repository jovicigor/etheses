package rs.fon.pzr.web.rest.model.response.level2;

import rs.fon.pzr.core.domain.model.thesis.ThesisComment;

import java.util.Date;

public class ThesisCommentResponseLevel2 {

    private final Long id;
    private final String message;
    private final Date datePosted;
    private final Long authorId;
    private final Long thesisId;

    public ThesisCommentResponseLevel2(ThesisComment thesisComment) {
        id = thesisComment.getId();
        message = thesisComment.getMessage();
        datePosted = thesisComment.getDatePosted();
        authorId = thesisComment.getAuthor().getId();
        thesisId = thesisComment.getThesis().getId();
    }

    public Long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public Date getDatePosted() {
        return datePosted;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public Long getThesisId() {
        return thesisId;
    }
}
