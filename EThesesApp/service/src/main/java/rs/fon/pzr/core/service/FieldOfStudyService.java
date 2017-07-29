package rs.fon.pzr.core.service;

import java.util.Optional;
import java.util.Set;

import rs.fon.pzr.model.thesis.FieldOfStudyEntity;

public interface FieldOfStudyService {

    Optional<FieldOfStudyEntity> getFieldOfStudy(Long id);

    Set<FieldOfStudyEntity> getAllFieldsOfStudy();

    FieldOfStudyEntity addFieldOfStudy(String name);

    void removeFieldOfStudy(Long id);

    FieldOfStudyEntity updateFieldOfStudy(FieldOfStudyEntity fieldOfStudy);

}
