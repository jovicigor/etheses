package rs.fon.pzr.core.service;

import java.util.Map;
import java.util.Set;

import rs.fon.pzr.model.KeywordEntity;

public interface KeywordService {

    KeywordEntity addKeyword(KeywordEntity keyword);

    Map<String, Integer> extractWordsWithCount(String text);
}
