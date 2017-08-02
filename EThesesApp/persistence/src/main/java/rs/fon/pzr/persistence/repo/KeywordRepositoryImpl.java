package rs.fon.pzr.persistence.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import rs.fon.pzr.core.repository.KeywordRepository;
import rs.fon.pzr.core.domain.model.thesis.Keyword;
import rs.fon.pzr.persistence.jpa.KeywordJpaRepository;

@Repository
public class KeywordRepositoryImpl implements KeywordRepository {

    private final KeywordJpaRepository jpaRepository;

    @Autowired
    public KeywordRepositoryImpl(KeywordJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Keyword findByValue(String value) {
        return jpaRepository.findByValue(value);
    }

    @Override
    public Keyword save(Keyword keyword) {
        return jpaRepository.save(keyword);
    }
}
