package rs.fon.pzr.model.studies;

import rs.fon.pzr.model.studies.CourseEntity;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "studies")
public class StudiesEntity {
    @Id
    @Column(name = "studies_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "name_short")
    private String nameShort;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "course_studies", joinColumns = {@JoinColumn(name = "studies_id")}, inverseJoinColumns = {@JoinColumn(name = "course_id")})
    private Set<CourseEntity> courses = new HashSet<>();

    protected StudiesEntity() {
    }

    StudiesEntity(String name, String nameShort, Collection<CourseEntity> courses) {
        this.name = name;
        this.nameShort = nameShort;
        this.courses = courses.stream().collect(Collectors.toSet());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameShort() {
        return nameShort;
    }

    public void setNameShort(String nameShort) {
        this.nameShort = nameShort;
    }

    public Collection<CourseEntity> getCourses() {
        return new HashSet<>(courses);
    }
}
