package rs.fon.pzr.model;

import javax.persistence.*;

@Entity
@Table(name = "field_of_study")
public class FieldOfStudyEntity {

    @Id
    @Column(name = "field_of_study_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    protected FieldOfStudyEntity() {
    }

    public FieldOfStudyEntity(String name) {
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
