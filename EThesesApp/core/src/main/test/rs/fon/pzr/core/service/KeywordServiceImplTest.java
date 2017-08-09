package rs.fon.pzr.core.service;

import org.junit.Test;
import rs.fon.pzr.core.domain.model.thesis.Keyword;
import rs.fon.pzr.core.service.repository.KeywordRepository;

import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


public class KeywordServiceImplTest {

    @Test
    public void addKeyword_keywordWithSameValueExists_returnsIt() {
        String value = "ISIT";
        Keyword keyword = Keyword.createNotBannedKeyword(value);
        KeywordRepository repositoryMock = mock(KeywordRepository.class);
        when(repositoryMock.findByValue(keyword.getValue())).thenReturn(keyword);

        KeywordServiceImpl testee = new KeywordServiceImpl(repositoryMock);
        Keyword existing = testee.addKeyword(keyword);

        assertEquals(keyword.getValue(), existing.getValue());
        verify(repositoryMock, times(0)).save(any());
    }

    @Test
    public void addKeyword_keywordWithSameValueDoesntExist_savesIt() {
        String value = "ISIT";
        Keyword keyword = Keyword.createNotBannedKeyword(value);
        KeywordRepository repositoryMock = mock(KeywordRepository.class);
        when(repositoryMock.findByValue(keyword.getValue())).thenReturn(null);

        KeywordServiceImpl testee = new KeywordServiceImpl(repositoryMock);
        testee.addKeyword(keyword);

        verify(repositoryMock, times(1)).save(keyword);
    }

    @Test
    public void extractWordsWithCount_nullPassed_returnsEmptyMap() {
        String input = null;
        KeywordServiceImpl testee = new KeywordServiceImpl(null);

        Map<String, Integer> wordsWithCount = testee.extractWordsWithCount(input);

        assertTrue(wordsWithCount.isEmpty());
    }

    @Test
    public void extractWordsWithCount_emptyStringPassed_returnsEmptyMap() {
        String input = "";
        KeywordServiceImpl testee = new KeywordServiceImpl(null);

        Map<String, Integer> wordsWithCount = testee.extractWordsWithCount(input);

        assertTrue(wordsWithCount.isEmpty());
    }

    @Test
    public void extractWordsWithCount_corectlyParsesText() {
        String input = "text is text";
        KeywordServiceImpl testee = new KeywordServiceImpl(null);

        Map<String, Integer> wordsWithCount = testee.extractWordsWithCount(input);

        assertFalse(wordsWithCount.isEmpty());
        assertEquals(2, wordsWithCount.size());
        int textCount = wordsWithCount.get("text");
        assertEquals(2, textCount);
        int isCount = wordsWithCount.get("is");
        assertEquals(1, isCount);
    }
}