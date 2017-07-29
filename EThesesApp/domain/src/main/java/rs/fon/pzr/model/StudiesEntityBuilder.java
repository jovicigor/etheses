package rs.fon.pzr.model;

import rs.fon.pzr.guards.EmptyGuard;
import rs.fon.pzr.guards.NullGuard;

import java.util.Collection;
import java.util.HashSet;

public class StudiesEntityBuilder {
    private String name;
    private String nameShort;
    private Collection<CourseEntity> courses = new HashSet<>();

    public StudiesEntityBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public StudiesEntityBuilder withNameShort(String nameShort) {
        this.nameShort = nameShort;
        return this;
    }

    public StudiesEntity build() {
        EmptyGuard.validateString("Studies name", name);
        EmptyGuard.validateString("Studies name short", nameShort);
        NullGuard.validate("Studies courses", courses);
        return new StudiesEntity(name, nameShort, courses);
    }
}