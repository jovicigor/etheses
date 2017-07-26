package rs.fon.pzr.model;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "course")
public class CourseEntity {

    @Id
    @Column(name = "course_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "name_short")
    private String nameShort;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "course_studies", joinColumns = {@JoinColumn(name = "course_id")}, inverseJoinColumns = {@JoinColumn(name = "studies_id")})
    private Set<StudiesEntity> studies = new HashSet<>();

    protected CourseEntity() {
    }

    public CourseEntity(String name, String nameShort, Set<StudiesEntity> studies) {
        this.name = name;
        this.nameShort = nameShort;
        this.studies = studies;
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

    public Collection<StudiesEntity> getStudies() {
        return new HashSet<>(studies);
    }

    public void updateStudies(Collection<StudiesEntity> studies) {
        this.studies = studies.stream()
                .collect(Collectors.toSet());
    }
}
