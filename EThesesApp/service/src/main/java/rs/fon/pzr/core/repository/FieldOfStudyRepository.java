package rs.fon.pzr.core.repository;

import rs.fon.pzr.model.thesis.FieldOfStudy;

import java.util.Set;

public interface FieldOfStudyRepository {

    FieldOfStudy findOne(Long id);

    Set<FieldOfStudy> findAll();

    FieldOfStudy findByName(String name);

    FieldOfStudy save(FieldOfStudy fieldOfStudy);

    void delete(Long id);
}