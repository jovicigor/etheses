package rs.fon.pzr.model;

import rs.fon.pzr.guards.EmptyGuard;
import rs.fon.pzr.guards.NullGuard;

import java.util.Collection;
import java.util.HashSet;

public class CourseEntityBuilder {
    private String name;
    private String nameShort;
    private Collection<StudiesEntity> studies = new HashSet<>();

    public CourseEntityBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public CourseEntityBuilder withNameShort(String nameShort) {
        this.nameShort = nameShort;
        return this;
    }

    public CourseEntityBuilder withStudies(Collection<StudiesEntity> studies) {
        this.studies = studies;
        return this;
    }

    public CourseEntity build() {
        EmptyGuard.validateString("course name", name);
        EmptyGuard.validateString("course short name", nameShort);
        NullGuard.validate("course studies", studies);

        return new CourseEntity(name, nameShort, studies);
    }
}