package rs.fon.pzr.model.thesis;

import rs.fon.pzr.guards.EmptyGuard;
import rs.fon.pzr.guards.NullGuard;
import rs.fon.pzr.model.user.UserEntity;

import java.util.Date;

public class ThesisCommentBuilder {
    private String message;
    private UserEntity author;
    private ThesisEntity thesis;

    public ThesisCommentBuilder withMessage(String message) {
        this.message = message;
        return this;
    }

    public ThesisCommentBuilder withAuthor(UserEntity author) {
        this.author = author;
        return this;
    }

    public ThesisCommentBuilder withThesis(ThesisEntity thesis) {
        this.thesis = thesis;
        return this;
    }

    public ThesisComment build() {
        EmptyGuard.validateString("comment message", message);
        NullGuard.validate("comment author", author);
        NullGuard.validate("comment thesis", thesis);

        Date datePosted = new Date();
        return new ThesisComment(message, datePosted, author, thesis);
    }
}