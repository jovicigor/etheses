package rs.fon.pzr.core.service;

import java.io.File;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import rs.fon.pzr.core.model.TFile;
import rs.fon.pzr.core.model.Thesis;
import rs.fon.pzr.core.model.ThesisComment;

public interface ThesisService {
	
	//TODO: advanced search methods
	
	Thesis getThesis(Long id);
	
	List<Thesis> getThesisByUserId(Long userId);

	List<Thesis> getAllThesis();
	
	Page<Thesis> advancedSearch(Integer pageNumber, Integer pageSize, String thesisName, List<String> tagValues, Long matchLimit, String courseName, String studiesName, String sortField, List<String> fieldValues, Long fieldsMatchLimit, List<String> descriptionKeys, Long descriptionKeyLimit);

	Thesis addThesis(Thesis thesis);
	
	Thesis updateThesis(Thesis thesis);
	
	void removeThesis(Long thesisId);
	
	TFile addFile(Long thesisId, MultipartFile file);
	
	File getThesisFile(Long thesisId);
	
	File getFileById(Long fileId);
	
	Set<TFile> getAllFileRecords();
	
	void removeFile(Long fileId);
	
	//chiled record services
	
	Set<ThesisComment> getAllComments(Long thesisId);
	
	ThesisComment addComment(ThesisComment thesisComment);
	
	void removeComment(Long commentId);
	
	
	
}