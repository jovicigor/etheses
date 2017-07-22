package rs.fon.elab.pzr.core.service;

import java.util.List;

import rs.fon.elab.pzr.core.model.Studies;

public interface StudiesService {

	Studies getStudies(Long id);
	
	Studies getStudiesByName(String name);

	List<Studies> getAllStudies();

	Studies addStudies(Studies studies);
	
	Studies updateStudies(Studies studies);
	
	void removeStudies(Long studiesID);
	
}