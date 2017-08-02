package rs.fon.pzr.core.service;

import java.util.List;
import java.util.Optional;

import rs.fon.pzr.core.domain.model.studies.Studies;

public interface StudiesService {

    Optional<Studies> getStudies(Long id);

    Optional<Studies> getStudiesByName(String name);

    List<Studies> getAllStudies();

    Studies addStudies(Studies studies);

    Studies updateStudies(Studies studies);

    void removeStudies(Long studiesID);

}