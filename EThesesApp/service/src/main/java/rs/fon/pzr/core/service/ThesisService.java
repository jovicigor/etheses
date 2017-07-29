package rs.fon.pzr.core.service;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import rs.fon.pzr.model.thesis.TFileEntity;
import rs.fon.pzr.model.thesis.ThesisEntity;
import rs.fon.pzr.model.thesis.ThesisComment;

public interface ThesisService {

    Optional<ThesisEntity> getThesis(Long id);

    List<ThesisEntity> getThesisByUserId(Long userId);

    List<ThesisEntity> getAllThesis();

    Page<ThesisEntity> advancedSearch(Integer pageNumber, Integer pageSize, String thesisName, List<String> tagValues, Long matchLimit, String courseName, String studiesName, String sortField, List<String> fieldValues, Long fieldsMatchLimit, List<String> descriptionKeys, Long descriptionKeyLimit);

    ThesisEntity addThesis(ThesisEntity thesis);

    ThesisEntity updateThesis(ThesisEntity thesis);

    void removeThesis(Long thesisId);

    TFileEntity addFile(Long thesisId, MultipartFile file);

    File getThesisFile(Long thesisId);

    File getFileById(Long fileId);

    Set<TFileEntity> getAllFileRecords();

    void removeFile(Long fileId);

    //chiled record services

    Set<ThesisComment> getAllComments(Long thesisId);

    ThesisComment addComment(ThesisComment thesisComment);

    void removeComment(Long commentId);


}
