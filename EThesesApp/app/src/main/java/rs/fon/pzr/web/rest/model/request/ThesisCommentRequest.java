package rs.fon.pzr.web.rest.model.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Optional;

public class ThesisCommentRequest {

    private final String message;

    @JsonCreator
    public ThesisCommentRequest(@JsonProperty("message") String message) {
        this.message = message;
    }

    public Optional<String> getMessage() {
        return Optional.ofNullable(message);
    }
}
