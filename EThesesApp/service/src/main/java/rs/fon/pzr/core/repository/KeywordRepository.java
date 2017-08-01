package rs.fon.pzr.core.repository;

import rs.fon.pzr.model.thesis.Keyword;

public interface KeywordRepository {

    Keyword findByValue(String value);

    Keyword save(Keyword keyword);
}
