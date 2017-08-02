package rs.fon.pzr.persistence.jpa;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import rs.fon.pzr.core.domain.model.thesis.Tag;

public interface TagJpaRepository extends CrudRepository<Tag, Long> {

    Optional<Tag> findByValue(String value);

    Set<Tag> findAll();

}
