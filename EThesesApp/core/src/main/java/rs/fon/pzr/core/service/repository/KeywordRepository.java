package rs.fon.pzr.core.service.repository;

import rs.fon.pzr.core.domain.model.thesis.Keyword;

public interface KeywordRepository {

    Keyword findByValue(String value);

    Keyword save(Keyword keyword);
}
