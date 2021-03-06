package rs.fon.pzr.persistence.jpa;

import java.util.Set;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import rs.fon.pzr.core.domain.model.thesis.Keyword;

public interface KeywordJpaRepository extends CrudRepository<Keyword, Long> {
    Keyword findByValue(String value);

    Set<Keyword> findAll();

    @Query("SELECT count(tk.keyword) from ThesisKeyword tk where tk.keyword.id=?1")
    Long numberOfConnectedTheses(Long keywordId);

    @Modifying
    @Transactional
    @Query("DELETE from Keyword k where k.banned is false and k.id not in(SELECT distinct tk.keyword.id from ThesisKeyword tk)")
    Integer deleteUnconnectedUnBannedKeywords();

}
