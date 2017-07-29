package rs.fon.pzr.persistence.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import rs.fon.pzr.model.thesis.Tag;

public interface TagRepository extends CrudRepository<Tag, Long> {

    Optional<Tag> findByValue(String value);

    Set<Tag> findAll();

}
