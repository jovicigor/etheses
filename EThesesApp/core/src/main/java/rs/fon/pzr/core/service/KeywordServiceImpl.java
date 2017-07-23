package rs.fon.pzr.core.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import rs.fon.pzr.core.exception.InvalidArgumentException;
import rs.fon.pzr.persistence.model.KeywordEntity;
import rs.fon.pzr.persistence.repository.KeywordRepository;

@Service
public class KeywordServiceImpl implements KeywordService {

    private final Logger logger = Logger.getLogger(KeywordServiceImpl.class);

    private final KeywordRepository keywordRepository;

    @Autowired
    public KeywordServiceImpl(KeywordRepository keywordRepository) {
        this.keywordRepository = keywordRepository;
    }

    @Override
    public KeywordEntity getKeyword(Long id) {
        return keywordRepository.findOne(id);
    }

    @Override
    public KeywordEntity getKeywordByValue(String value) {
        return keywordRepository.findByValue(value);
    }

    @Override
    public Set<KeywordEntity> getAllKeywords() {
        return keywordRepository.findAll();
    }

    @Override
    @Transactional
    public KeywordEntity addKeyword(KeywordEntity keyword) {
        keyword.setValue(keyword.getValue().toLowerCase());
        keyword.setValue(keyword.getValue().replaceAll("\\s+", ""));

        KeywordEntity existingKeyword = keywordRepository.findByValue(keyword
                .getValue());
        if (existingKeyword != null) {
            return existingKeyword;
        }
        return keywordRepository.save(keyword);
    }

    @Override
    @Transactional
    public KeywordEntity updateKeyword(KeywordEntity keyword) {
        KeywordEntity existingKeyword = keywordRepository.findOne(keyword.getId());
        if (existingKeyword == null) {
            throw new InvalidArgumentException("Ključna reč sa id-em "
                    + keyword.getId() + " ne postoji u bazi!");
        }
        return keywordRepository.save(keyword);
    }

    @Transactional
    @Override
    public KeywordEntity addBannedKeyword(String value) {
        value = value.toLowerCase();
        value = value.replaceAll("\\s+", "");

        KeywordEntity keyword = keywordRepository.findByValue(value);
        if (keyword != null) {
            keyword.setBanned(true);
            return keywordRepository.save(keyword);
        }
        keyword = new KeywordEntity();
        keyword.setValue(value);
        keyword.setBanned(true);
        return keywordRepository.save(keyword);
    }

    @Override
    @Transactional
    public void removeKeyword(Long id) {
        KeywordEntity keyword = keywordRepository.findOne(id);
        if (keyword == null) {
            throw new InvalidArgumentException("Ključna reč sa id-em " + id
                    + " ne postoji u bazi!");
        }
        if (keywordRepository.numberOfConnectedTheses(id) > 0) {
            throw new InvalidArgumentException(
                    "Ne može se obrisati ključna reč koja je povezana sa postojećim radovima");
        }
        keywordRepository.delete(id);
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

    @Override
    @Transactional
    public Integer deleteUnConnectedUnBannedKeywords() {
        logger.info("Deleting unconnected and unbanned keywords.");
        Integer numDeleted = 0;
        try {
            numDeleted = keywordRepository.deleteUnconnectedUnBannedKeywords();
        } catch (Exception e) {
            logger.debug(e.getMessage());
        }
        logger.info("Deleted " + numDeleted
                + " unconnected and unbanned keywords.");
        return numDeleted;
    }
}
