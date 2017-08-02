package rs.fon.pzr.persistence.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import rs.fon.pzr.core.service.repository.TagRepository;
import rs.fon.pzr.core.domain.model.thesis.Tag;
import rs.fon.pzr.persistence.jpa.TagJpaRepository;

import java.util.Optional;
import java.util.Set;

@Repository
public class TagRepositoryImpl implements TagRepository {

    private final TagJpaRepository jpaRepository;

    @Autowired
    public TagRepositoryImpl(TagJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Set<Tag> findAll() {
        return jpaRepository.findAll();
    }

    @Override
    public Optional<Tag> findByValue(String value) {
        return jpaRepository.findByValue(value);
    }

    @Override
    public Tag save(Tag tag) {
        return jpaRepository.save(tag);
    }

    @Override
    public Tag findOne(Long id) {
        return jpaRepository.findOne(id);
    }

    @Override
    public void delete(Long id) {
        jpaRepository.delete(id);
    }
}
