package rs.fon.pzr.web.rest.model.response.level2;

import rs.fon.pzr.core.domain.model.studies.Course;
import rs.fon.pzr.core.domain.model.studies.Studies;

import java.util.Set;
import java.util.stream.Collectors;

public class StudiesResponseLevel2 {
    private final Long id;
    private final String name;
    private final String nameShort;
    private final Set<Long> courseIDs;

    public StudiesResponseLevel2(Studies studies) {
        id = studies.getId();
        name = studies.getName();
        nameShort = studies.getNameShort();
        courseIDs = studies.getCourses().stream()
                .map(Course::getId)
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
