package rs.fon.pzr.web.rest.model.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Optional;

public class FieldOfStudyRequest {
    private final String name;

    @JsonCreator
    public FieldOfStudyRequest(@JsonProperty("name") String name) {
        this.name = name;
    }

    public Optional<String> getName() {
        return Optional.ofNullable(name);
    }
}
