package rs.fon.pzr.core.service;

import java.util.Map;
import java.util.Set;

import rs.fon.pzr.persistence.model.KeywordEntity;

public interface KeywordService {

	KeywordEntity getKeyword(Long id);

	KeywordEntity getKeywordByValue(String value);

	Set<KeywordEntity> getAllKeywords();

	KeywordEntity addKeyword(KeywordEntity keyword);
	
	KeywordEntity addBannedKeyword(String value);
	
	KeywordEntity updateKeyword(KeywordEntity keyword);

	void removeKeyword(Long id);

	Map<String, Integer> extractWordsWithCount(String text);
	
	Integer deleteUnConnectedUnBannedKeywords();


}
