package rs.fon.pzr.core.service;

import java.util.List;

import rs.fon.pzr.persistence.model.StudiesEntity;

public interface StudiesService {

	StudiesEntity getStudies(Long id);
	
	StudiesEntity getStudiesByName(String name);

	List<StudiesEntity> getAllStudies();

	StudiesEntity addStudies(StudiesEntity studies);
	
	StudiesEntity updateStudies(StudiesEntity studies);
	
	void removeStudies(Long studiesID);
	
}