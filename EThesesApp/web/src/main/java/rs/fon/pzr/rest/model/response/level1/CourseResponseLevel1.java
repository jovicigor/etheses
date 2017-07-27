package rs.fon.pzr.rest.model.response.level1;

import rs.fon.pzr.model.CourseEntity;
import rs.fon.pzr.model.StudiesEntity;
import rs.fon.pzr.rest.model.response.level2.StudiesResponseLevel2;
import rs.fon.pzr.rest.model.util.RestFactory;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class CourseResponseLevel1 {

    private final Long id;
    private final String name;
    private final String nameShort;
    private final Set<StudiesResponseLevel2> studies;

    public CourseResponseLevel1(CourseEntity course) {
        id = course.getId();
        name = course.getName();
        nameShort = course.getNameShort();
        studies = course.getStudies().stream()
                .map(StudiesResponseLevel2::new)
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

    public Set<StudiesResponseLevel2> getStudies() {
        return studies;
    }
}
