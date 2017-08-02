package rs.fon.pzr.core.service;

import java.util.Optional;
import java.util.Set;

import rs.fon.pzr.core.domain.model.thesis.FieldOfStudy;

public interface FieldOfStudyService {

    Optional<FieldOfStudy> getFieldOfStudy(Long id);

    Set<FieldOfStudy> getAllFieldsOfStudy();

    FieldOfStudy addFieldOfStudy(String name);

    void removeFieldOfStudy(Long id);

    FieldOfStudy updateFieldOfStudy(FieldOfStudy fieldOfStudy);

}
