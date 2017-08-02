package rs.fon.pzr.rest.model.response.level1;

import rs.fon.pzr.core.domain.model.thesis.ThesisComment;
import rs.fon.pzr.rest.model.response.level2.ThesisResponseLevel2;
import rs.fon.pzr.rest.model.response.level2.UserResponseLevel2;

import java.util.Date;

public class ThesisCommentResponseLevel1 {

    private Long id;
    private String message;
    private Date datePosted;
    private UserResponseLevel2 author;
    private ThesisResponseLevel2 thesis;

    public ThesisCommentResponseLevel1(ThesisComment comment) {
        id = comment.getId();
        message = comment.getMessage();
        datePosted = comment.getDatePosted();
        author = new UserResponseLevel2(comment.getAuthor());
        thesis = new ThesisResponseLevel2(comment.getThesis());
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

    public UserResponseLevel2 getAuthor() {
        return author;
    }

    public ThesisResponseLevel2 getThesis() {
        return thesis;
    }
}
