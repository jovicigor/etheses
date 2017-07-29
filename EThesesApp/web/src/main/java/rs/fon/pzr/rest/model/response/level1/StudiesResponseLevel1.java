package rs.fon.pzr.rest.model.response.level1;

import rs.fon.pzr.model.studies.StudiesEntity;
import rs.fon.pzr.rest.model.response.level2.CourseResponseLevel2;

import java.util.Set;
import java.util.stream.Collectors;

public class StudiesResponseLevel1 {

    private final Long id;
    private final String name;
    private final String nameShort;
    private final Set<CourseResponseLevel2> courses;

    public StudiesResponseLevel1(StudiesEntity studies) {
        id = studies.getId();
        name = studies.getName();
        nameShort = studies.getNameShort();
        courses = studies.getCourses().stream()
                .map(CourseResponseLevel2::new)
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

    public Set<CourseResponseLevel2> getCourses() {
        return courses;
    }
}
