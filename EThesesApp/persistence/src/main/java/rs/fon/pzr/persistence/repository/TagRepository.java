package rs.fon.pzr.persistence.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import rs.fon.pzr.model.TagEntity;

public interface TagRepository extends CrudRepository<TagEntity, Long> {

    Optional<TagEntity> findByValue(String value);

    Set<TagEntity> findAll();

}
