package rs.fon.pzr.model;

import javax.persistence.*;

@Entity
@Table(name = "keyword")
public class KeywordEntity {

    @Id
    @Column(name = "keyword_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "value")
    private String value;

    @Column(name = "is_banned")
    private boolean banned;

    protected KeywordEntity() {
    }

    private KeywordEntity(String value, boolean banned) {
        if (value == null) {
            throw new IllegalStateException("Keyword value cannot be null");
        }
        value = value.toLowerCase().replaceAll("\\s+", "");
        this.value = value;
        this.banned = banned;
    }

    public static KeywordEntity createNotBannedKeyword(String value) {
        return new KeywordEntity(value, false);
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
