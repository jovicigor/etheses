package rs.fon.elab.pzr.core.service;

import java.util.Set;

import rs.fon.elab.pzr.core.model.FieldOfStudy;

public interface FieldOfStudyService {

	FieldOfStudy getFieldOfStudy(Long id);
	
	FieldOfStudy getgetFieldOfStudyByName(String name);
	
	Set<FieldOfStudy> getAllFieldsOfStudy();
	
	FieldOfStudy addFieldOfStudy(String name);
	
	void removeFieldOfStudy(Long id);

	FieldOfStudy updateFieldOfStudy(FieldOfStudy fieldOfStudy);
	
}
