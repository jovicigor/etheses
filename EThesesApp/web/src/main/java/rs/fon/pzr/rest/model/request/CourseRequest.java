package rs.fon.pzr.rest.model.request;

import java.util.Optional;
import java.util.Set;

public class CourseRequest {

    private String name;

    private String nameShort;

    private Set<Long> studiesIDs;

    public Optional<String> getName() {
        return Optional.ofNullable(name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public Optional<String> getNameShort() {
        return Optional.ofNullable(nameShort);
    }

    public void setNameShort(String nameShort) {
        this.nameShort = nameShort;
    }

    public Set<Long> getStudiesIDs() {
        return studiesIDs;
    }

    public void setStudiesIDs(Set<Long> studiesIDs) {
        this.studiesIDs = studiesIDs;
    }

}
