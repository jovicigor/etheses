package rs.fon.pzr.core.service;

import java.util.Set;

import rs.fon.pzr.model.FieldOfStudyEntity;

public interface FieldOfStudyService {

    FieldOfStudyEntity getFieldOfStudy(Long id);

    Set<FieldOfStudyEntity> getAllFieldsOfStudy();

    FieldOfStudyEntity addFieldOfStudy(String name);

    void removeFieldOfStudy(Long id);

    FieldOfStudyEntity updateFieldOfStudy(FieldOfStudyEntity fieldOfStudy);

}
