package rs.fon.pzr.core.repository;

import rs.fon.pzr.model.thesis.Tag;

import java.util.Optional;
import java.util.Set;

public interface TagRepository {
    Set<Tag> findAll();

    Optional<Tag> findByValue(String value);

    Tag save(Tag tag);

    Tag findOne(Long id);

    void delete(Long id);
}
