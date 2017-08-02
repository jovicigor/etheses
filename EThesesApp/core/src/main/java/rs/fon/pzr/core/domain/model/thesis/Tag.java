package rs.fon.pzr.core.domain.model.thesis;

import rs.fon.pzr.core.domain.guards.EmptyGuard;

import javax.persistence.*;


@Entity
@Table(name = "tag")
public class Tag {

    @Id
    @Column(name = "tag_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "value")
    private String value;

    protected Tag() {
    }

    private Tag(String value) {
        this.value = value;
    }

    public static Tag createTag(String value) {
        EmptyGuard.validateString("tag value", value);
        return new Tag(value);
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
