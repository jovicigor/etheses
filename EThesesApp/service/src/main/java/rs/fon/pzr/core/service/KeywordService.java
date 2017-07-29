package rs.fon.pzr.core.service;

import java.util.Map;

import rs.fon.pzr.model.thesis.KeywordEntity;

public interface KeywordService {

    KeywordEntity addKeyword(KeywordEntity keyword);

    Map<String, Integer> extractWordsWithCount(String text);
}
