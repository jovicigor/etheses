package rs.fon.pzr.core.service;

import java.util.List;
import java.util.Optional;

import rs.fon.pzr.model.studies.StudiesEntity;

public interface StudiesService {

    Optional<StudiesEntity> getStudies(Long id);

    Optional<StudiesEntity> getStudiesByName(String name);

    List<StudiesEntity> getAllStudies();

    StudiesEntity addStudies(StudiesEntity studies);

    StudiesEntity updateStudies(StudiesEntity studies);

    void removeStudies(Long studiesID);

}