package rs.fon.elab.pzr.core.service;

import java.util.Map;
import java.util.Set;

import rs.fon.elab.pzr.core.model.Keyword;

public interface KeywordService {

	Keyword getKeyword(Long id);

	Keyword getKeywordByValue(String value);

	Set<Keyword> getAllKeywords();

	Keyword addKeyword(Keyword keyword);
	
	Keyword addBannedKeyword(String value);
	
	Keyword updateKeyword(Keyword keyword);

	void removeKeyword(Long id);

	Map<String, Integer> extractWordsWithCount(String text);
	
	Integer deleteUnConnectedUnBannedKeywords();


}
