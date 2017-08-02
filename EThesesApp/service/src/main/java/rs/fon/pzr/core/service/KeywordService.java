package rs.fon.pzr.core.service;

import java.util.Map;

import rs.fon.pzr.core.domain.model.thesis.Keyword;

public interface KeywordService {

    Keyword addKeyword(Keyword keyword);

    Map<String, Integer> extractWordsWithCount(String text);
}
