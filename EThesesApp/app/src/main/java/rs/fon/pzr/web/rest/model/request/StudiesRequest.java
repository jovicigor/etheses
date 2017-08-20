package rs.fon.pzr.web.rest.model.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Optional;

public class StudiesRequest {
    private String name;
    private String nameShort;

    @JsonCreator
    public StudiesRequest(@JsonProperty("name") String name,
                          @JsonProperty("nameShort") String nameShort) {
        this.name = name;
        this.nameShort = nameShort;
    }

    public Optional<String> getName() {
        return Optional.ofNullable(name);
    }

    public Optional<String> getNameShort() {
        return Optional.ofNullable(nameShort);
    }
}
