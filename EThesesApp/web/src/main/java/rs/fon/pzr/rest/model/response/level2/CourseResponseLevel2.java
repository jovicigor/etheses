package rs.fon.pzr.rest.model.response.level2;

import rs.fon.pzr.model.CourseEntity;
import rs.fon.pzr.model.StudiesEntity;

import java.util.Set;
import java.util.stream.Collectors;

public class CourseResponseLevel2 {

    private final Long id;
    private final String name;
    private final String nameShort;
    private final Set<Long> studiesIDs;

    public CourseResponseLevel2(CourseEntity courseEntity) {
        id = courseEntity.getId();
        name = courseEntity.getName();
        nameShort = courseEntity.getNameShort();
        studiesIDs = courseEntity.getStudies().stream()
                .map(StudiesEntity::getId)
                .collect(Collectors.toSet());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNameShort() {
        return nameShort;
    }

    public Set<Long> getStudiesIDs() {
        return studiesIDs;
    }
}
