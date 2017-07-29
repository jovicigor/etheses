package rs.fon.pzr.rest.model.response.level2;

import rs.fon.pzr.model.studies.CourseEntity;
import rs.fon.pzr.model.studies.StudiesEntity;

import java.util.Set;
import java.util.stream.Collectors;

public class StudiesResponseLevel2 {
    private final Long id;
    private final String name;
    private final String nameShort;
    private final Set<Long> courseIDs;

    public StudiesResponseLevel2(StudiesEntity studiesEntity) {
        id = studiesEntity.getId();
        name = studiesEntity.getName();
        nameShort = studiesEntity.getNameShort();
        courseIDs = studiesEntity.getCourses().stream()
                .map(CourseEntity::getId)
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

    public Set<Long> getCourseIDs() {
        return courseIDs;
    }
}
