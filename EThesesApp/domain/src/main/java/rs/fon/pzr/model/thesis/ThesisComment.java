package rs.fon.pzr.model.thesis;

import rs.fon.pzr.model.user.UserEntity;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "thesis_comment")
public class ThesisComment {
    @Id
    @Column(name = "thesis_comment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "comment_message")
    private String message;

    @Column(name = "date_posted")
    private Date datePosted;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity author;

    @ManyToOne
    @JoinColumn(name = "thesis_id")
    private ThesisEntity thesis;

    protected ThesisComment() {
    }

    public ThesisComment(String message, Date datePosted, UserEntity author, ThesisEntity thesis) {
        this.message = message;
        this.datePosted = datePosted;
        this.author = author;
        this.thesis = thesis;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public Date getDatePosted() {
        return datePosted;
    }

    public UserEntity getAuthor() {
        return author;
    }

    public ThesisEntity getThesis() {
        return thesis;
    }
}
