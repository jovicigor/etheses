package rs.fon.pzr.model.thesis;

import rs.fon.pzr.guards.EmptyGuard;

import javax.persistence.*;


@Entity
@Table(name = "tag")
public class TagEntity {

    @Id
    @Column(name = "tag_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "value")
    private String value;

    protected TagEntity() {
    }

    private TagEntity(String value) {
        this.value = value;
    }

    public static TagEntity createTag(String value) {
        EmptyGuard.validateString("tag value", value);
        return new TagEntity(value);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }
}
