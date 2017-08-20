package rs.fon.pzr.core.domain.model.studies;

import rs.fon.pzr.core.domain.guards.EmptyGuard;
import rs.fon.pzr.core.domain.guards.NullGuard;

import java.util.Collection;
import java.util.HashSet;

public class StudiesBuilder {
    private String name;
    private String nameShort;
    private final Collection<Course> courses = new HashSet<>();

    public StudiesBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public StudiesBuilder withNameShort(String nameShort) {
        this.nameShort = nameShort;
        return this;
    }

    public Studies build() {
        EmptyGuard.validateString("Studies name", name);
        EmptyGuard.validateString("Studies name short", nameShort);
        NullGuard.validate("Studies courses", courses);
        return new Studies(name, nameShort, courses);
    }
}