package rs.fon.pzr.web.rest.model.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Optional;
import java.util.Set;

public class CourseRequest {

    private final String name;
    private final String nameShort;
    private final Set<Long> studiesIDs;

    @JsonCreator
    public CourseRequest(@JsonProperty("name") String name,
                         @JsonProperty("nameShort") String nameShort,
                         @JsonProperty("studiesIDs") Set<Long> studiesIDs) {
        this.name = name;
        this.nameShort = nameShort;
        this.studiesIDs = studiesIDs;
    }

    public Optional<String> getName() {
        return Optional.ofNullable(name);
    }

    public Optional<String> getNameShort() {
        return Optional.ofNullable(nameShort);
    }

    public Set<Long> getStudiesIDs() {
        return studiesIDs;
    }
}
