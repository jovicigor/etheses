package rs.fon.pzr.core.service;

import org.junit.Test;
import rs.fon.pzr.core.domain.model.thesis.Tag;
import rs.fon.pzr.core.exception.InvalidArgumentException;
import rs.fon.pzr.core.service.repository.TagRepository;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class TagServiceImplTest {

    @Test
    public void addTag_tagExists_returnsIt() {
        String value = "tag";
        Tag tag = Tag.createTag(value);
        TagRepository tagRepositoryMock = mock(TagRepository.class);
        when(tagRepositoryMock.findByValue(value)).thenReturn(Optional.of(tag));

        TagService testee = new TagServiceImpl(tagRepositoryMock);
        Tag existingTag = testee.addTag(value);

        assertEquals(tag.getValue(), existingTag.getValue());
        verify(tagRepositoryMock, times(0)).save(any());
    }

    @Test
    public void addTag_tagDoesntExist_saveIt() {
        String value = "tag";
        TagRepository tagRepositoryMock = mock(TagRepository.class);
        when(tagRepositoryMock.findByValue(value)).thenReturn(Optional.empty());

        TagService testee = new TagServiceImpl(tagRepositoryMock);
        testee.addTag(value);

        verify(tagRepositoryMock, times(1)).save(any());
    }

    @Test(expected = InvalidArgumentException.class)
    public void removeTag_tagDoesntExist_throwException() {
        TagRepository tagRepositoryMock = mock(TagRepository.class);
        when(tagRepositoryMock.findOne(anyLong())).thenReturn(null);

        TagService testee = new TagServiceImpl(tagRepositoryMock);
        testee.removeTag(1L);

        verify(tagRepositoryMock, times(0)).delete(anyLong());
    }

    @Test
    public void removeTag_tagDoesntExist_deleteIt() {
        String value = "tag";
        Tag tag = Tag.createTag(value);
        TagRepository tagRepositoryMock = mock(TagRepository.class);
        when(tagRepositoryMock.findOne(anyLong())).thenReturn(tag);

        TagService testee = new TagServiceImpl(tagRepositoryMock);
        testee.removeTag(1L);

        verify(tagRepositoryMock, times(1)).delete(1L);
    }
}