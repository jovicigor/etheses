package rs.fon.pzr.model.thesis;

import rs.fon.pzr.guards.NullGuard;

import javax.persistence.*;

@Entity
@Table(name = "field_of_study")
public class FieldOfStudy {

    @Id
    @Column(name = "field_of_study_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    public static FieldOfStudy createFieldOfStudy(String name) {
        NullGuard.validate("field of study name", name);
        return new FieldOfStudy(name);
    }

    protected FieldOfStudy() {
    }

    private FieldOfStudy(String name) {
        this.name = name;
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
}
