package rs.fon.pzr.core.domain.model.thesis;

import javax.persistence.*;

@Entity
@Table(name = "file")
public class TFile {

    @Id
    @Column(name = "file_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "file_name")
    private String fileName;

    protected TFile() {
    }

    public TFile(String fileName) {
        this.fileName = fileName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }
}
