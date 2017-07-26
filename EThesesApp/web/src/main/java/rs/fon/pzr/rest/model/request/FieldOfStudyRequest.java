package rs.fon.pzr.rest.model.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FieldOfStudyRequest {
    private String name;

    @JsonCreator
    public FieldOfStudyRequest(@JsonProperty("name") String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
