package rs.fon.pzr.rest.model.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class StudiesRequest {
    private String name;
    private String nameShort;

    @JsonCreator
    public StudiesRequest(@JsonProperty("name") String name,
                          @JsonProperty("nameShort") String nameShort) {
        this.name = name;
        this.nameShort = nameShort;
    }

    public String getName() {
        return name;
    }

    public String getNameShort() {
        return nameShort;
    }
}
