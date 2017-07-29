package rs.fon.pzr.model.studies;

import rs.fon.pzr.guards.EmptyGuard;
import rs.fon.pzr.guards.NullGuard;

import java.util.Collection;
import java.util.HashSet;

public class CourseBuilder {
    private String name;
    private String nameShort;
    private Collection<Studies> studies = new HashSet<>();

    public CourseBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public CourseBuilder withNameShort(String nameShort) {
        this.nameShort = nameShort;
        return this;
    }

    public CourseBuilder withStudies(Collection<Studies> studies) {
        this.studies = studies;
        return this;
    }

    public Course build() {
        EmptyGuard.validateString("course name", name);
        EmptyGuard.validateString("course short name", nameShort);
        NullGuard.validate("course studies", studies);

        return new Course(name, nameShort, studies);
    }
}