package rs.fon.pzr.persistence.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import rs.fon.pzr.core.service.repository.CourseRepository;
import rs.fon.pzr.core.domain.model.studies.Course;
import rs.fon.pzr.persistence.jpa.CourseJpaRepository;

import java.util.List;

@Repository
public class CourseRepositoryImpl implements CourseRepository {

    private final CourseJpaRepository jpaRepository;

    @Autowired
    public CourseRepositoryImpl(CourseJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Course findOne(Long id) {
        return jpaRepository.findOne(id);
    }

    @Override
    public Course findByName(String name) {
        return jpaRepository.findByName(name);
    }

    @Override
    public List<Course> findAll() {
        return jpaRepository.findAll();
    }

    @Override
    public boolean existsByName(String name) {
        return jpaRepository.existsByName(name);
    }

    @Override
    public boolean existsByNameShort(String nameShort) {
        return jpaRepository.existsByNameShort(nameShort);
    }

    @Override
    public Course save(Course course) {
        return jpaRepository.save(course);
    }

    @Override
    public Course findByNameShort(String nameShort) {
        return jpaRepository.findByNameShort(nameShort);
    }

    @Override
    public void delete(Course course) {
        jpaRepository.delete(course);
    }
}
