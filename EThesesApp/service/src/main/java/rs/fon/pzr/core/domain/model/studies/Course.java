package rs.fon.pzr.core.domain.model.studies;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "course")
public class Course {

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
    private Set<Studies> studies = new HashSet<>();

    protected Course() {
    }

    Course(String name, String nameShort, Collection<Studies> studies) {
        this.name = name;
        this.nameShort = nameShort;
        this.studies = studies.stream().collect(Collectors.toSet());
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

    public Collection<Studies> getStudies() {
        return new HashSet<>(studies);
    }

    public void updateStudies(Collection<Studies> studies) {
        this.studies = studies.stream()
                .collect(Collectors.toSet());
    }
}
