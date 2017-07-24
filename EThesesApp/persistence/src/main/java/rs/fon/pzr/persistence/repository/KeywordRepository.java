package rs.fon.pzr.persistence.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import rs.fon.pzr.persistence.model.KeywordEntity;

public interface KeywordRepository extends CrudRepository<KeywordEntity, Long> {
    KeywordEntity findByValue(String value);

    Set<KeywordEntity> findAll();

    @Query("SELECT count(tk.keyword) from ThesisKeywordEntity tk where tk.keyword.id=?1")
    Long numberOfConnectedTheses(Long keywordId);

    @Modifying
    @Transactional
    @Query("DELETE from KeywordEntity k where k.banned is false and k.id not in(SELECT distinct tk.keyword.id from ThesisKeywordEntity tk)")
    Integer deleteUnconnectedUnBannedKeywords();

}
