package rs.fon.pzr.persistence.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import rs.fon.pzr.core.repository.FieldOfStudyRepository;
import rs.fon.pzr.model.thesis.FieldOfStudy;
import rs.fon.pzr.persistence.jpa.FieldOfStudyJpaRepository;

import java.util.Set;

@Repository
public class FieldOfStudyRepositoryImpl implements FieldOfStudyRepository {

    private final FieldOfStudyJpaRepository jpaRepository;

    @Autowired
    public FieldOfStudyRepositoryImpl(FieldOfStudyJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public FieldOfStudy findOne(Long id) {
        return jpaRepository.findOne(id);
    }

    @Override
    public Set<FieldOfStudy> findAll() {
        return jpaRepository.findAll();
    }

    @Override
    public FieldOfStudy findByName(String name) {
        return jpaRepository.findByName(name);
    }

    @Override
    public FieldOfStudy save(FieldOfStudy fieldOfStudy) {
        return jpaRepository.save(fieldOfStudy);
    }

    @Override
    public void delete(Long id) {
        jpaRepository.delete(id);
    }
}
