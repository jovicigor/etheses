package rs.fon.pzr.persistence.jpa;

import org.springframework.data.repository.CrudRepository;
import rs.fon.pzr.core.domain.model.studies.Course;

import java.util.List;

public interface CourseJpaRepository extends CrudRepository<Course, Long> {

    List<Course> findAll();

    Course findByName(String name);

    Course findByNameShort(String nameShort);

    boolean existsByName(String name);

    boolean existsByNameShort(String nameShort);
}
