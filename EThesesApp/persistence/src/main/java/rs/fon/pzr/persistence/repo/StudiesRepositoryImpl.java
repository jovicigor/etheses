package rs.fon.pzr.persistence.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import rs.fon.pzr.core.repository.StudiesRepository;
import rs.fon.pzr.model.studies.Course;
import rs.fon.pzr.model.studies.Studies;
import rs.fon.pzr.persistence.jpa.StudiesJpaRepository;

import java.util.List;

@Repository
public class StudiesRepositoryImpl implements StudiesRepository {

    private final StudiesJpaRepository jpaRepository;

    @Autowired
    public StudiesRepositoryImpl(StudiesJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Studies findOne(Long id) {
        return jpaRepository.findOne(id);
    }

    @Override
    public Studies findByName(String name) {
        return jpaRepository.findByName(name);
    }

    @Override
    public List<Studies> findAll() {
        return jpaRepository.findAll();
    }

    @Override
    public Studies findByNameShort(String nameShort) {
        return jpaRepository.findByNameShort(nameShort);
    }

    @Override
    public Studies save(Studies studies) {
        return jpaRepository.save(studies);
    }

    @Override
    public void delete(Studies studies) {
        jpaRepository.delete(studies);
    }
}
