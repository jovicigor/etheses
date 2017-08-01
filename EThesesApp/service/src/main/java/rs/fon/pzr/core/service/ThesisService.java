package rs.fon.pzr.core.service;

import org.springframework.web.multipart.MultipartFile;
import rs.fon.pzr.core.page.ThesisPage;
import rs.fon.pzr.model.thesis.TFile;
import rs.fon.pzr.model.thesis.Thesis;
import rs.fon.pzr.model.thesis.ThesisComment;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ThesisService {

    Optional<Thesis> getThesis(Long id);

    List<Thesis> getThesisByUserId(Long userId);

    List<Thesis> getAllThesis();

    ThesisPage advancedSearch(Integer pageNumber, Integer pageSize, String thesisName, List<String> tagValues, Long matchLimit, String courseName, String studiesName, String sortField, List<String> fieldValues, Long fieldsMatchLimit, List<String> descriptionKeys, Long descriptionKeyLimit);

    Thesis addThesis(Thesis thesis);

    Thesis updateThesis(Thesis thesis);

    void removeThesis(Long thesisId);

    TFile addFile(Long thesisId, MultipartFile file);

    File getThesisFile(Long thesisId);

    File getFileById(Long fileId);

    Set<TFile> getAllFileRecords();

    void removeFile(Long fileId);

    Set<ThesisComment> getAllComments(Long thesisId);

    ThesisComment addComment(ThesisComment thesisComment);

    void removeComment(Long commentId);

    ThesisComment getComment(long commentId);
}
