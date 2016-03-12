package rs.fon.elab.pzr.core.service;

import java.util.List;

import rs.fon.elab.pzr.core.model.Subject;
import rs.fon.elab.pzr.core.repository.SubjectRepository;

public interface SubjectService {

	public abstract Subject getSubject(Long id);
	
	public abstract Subject getSubjectByName(String name);

	public abstract List<Subject> getAllSubjects();

	public abstract Subject addSubject(Subject subject);
	
	public abstract Subject updateSubject(Subject subject);	
	
	public abstract void removeSubject(Long subjectID);
	
}