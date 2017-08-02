package rs.fon.pzr.rest.model.response.level2;

import rs.fon.pzr.core.domain.model.studies.Course;
import rs.fon.pzr.core.domain.model.studies.Studies;

import java.util.Set;
import java.util.stream.Collectors;

public class CourseResponseLevel2 {

    private final Long id;
    private final String name;
    private final String nameShort;
    private final Set<Long> studiesIDs;

    public CourseResponseLevel2(Course course) {
        id = course.getId();
        name = course.getName();
        nameShort = course.getNameShort();
        studiesIDs = course.getStudies().stream()
                .map(Studies::getId)
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
