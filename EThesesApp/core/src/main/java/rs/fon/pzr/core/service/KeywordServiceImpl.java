package rs.fon.pzr.core.service;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import rs.fon.pzr.core.repository.KeywordRepository;
import rs.fon.pzr.core.domain.model.thesis.Keyword;

@Service
public class KeywordServiceImpl implements KeywordService {

    private final KeywordRepository keywordRepository;

    @Autowired
    public KeywordServiceImpl(KeywordRepository keywordRepository) {
        this.keywordRepository = keywordRepository;
    }

    @Override
    @Transactional
    public Keyword addKeyword(Keyword keyword) {
        Keyword existingKeyword = keywordRepository.findByValue(keyword
                .getValue());
        if (existingKeyword != null) {
            return existingKeyword;
        }
        return keywordRepository.save(keyword);
    }

    public Map<String, Integer> extractWordsWithCount(String input) {
        Map<String, Integer> words = new HashMap<>();
        Pattern p = Pattern.compile("[\\w']+");
        Matcher m = p.matcher(input);

        while (m.find()) {
            String nextWord = input.substring(m.start(), m.end());
            if (words.containsKey(nextWord)) {
                words.put(nextWord, words.get(nextWord) + 1);
            } else {
                words.put(input.substring(m.start(), m.end()), 1);
            }
        }
        return words;
    }
}
