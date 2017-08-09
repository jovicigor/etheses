package rs.fon.pzr.core.service;

import org.junit.Test;
import rs.fon.pzr.core.domain.model.thesis.FieldOfStudy;
import rs.fon.pzr.core.exception.InvalidArgumentException;
import rs.fon.pzr.core.service.repository.FieldOfStudyRepository;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;

public class FieldOfStudyServiceImplTest {

    @Test
    public void getFieldOfStudy_courseExists_returnsIt() {
        FieldOfStudy fieldOfStudy = FieldOfStudy.createFieldOfStudy("ISIT");
        FieldOfStudyRepository fieldOfStudyRepositoryMock = mock(FieldOfStudyRepository.class);
        when(fieldOfStudyRepositoryMock.findOne(anyLong())).thenReturn(fieldOfStudy);

        FieldOfStudyServiceImpl testee = new FieldOfStudyServiceImpl(fieldOfStudyRepositoryMock);
        Optional<FieldOfStudy> result = testee.getFieldOfStudy(1L);

        assertTrue(result.isPresent());
    }

    @Test
    public void getFieldOfStudy_courseDoesntExists_empty() {
        FieldOfStudyRepository fieldOfStudyRepositoryMock = mock(FieldOfStudyRepository.class);
        when(fieldOfStudyRepositoryMock.findOne(anyLong())).thenReturn(null);

        FieldOfStudyServiceImpl testee = new FieldOfStudyServiceImpl(fieldOfStudyRepositoryMock);
        Optional<FieldOfStudy> result = testee.getFieldOfStudy(1L);

        assertFalse(result.isPresent());
    }

    @Test
    public void addFieldOfStudy_fieldOfStudyExists_returnsIt() {
        String name = "ISIT";
        FieldOfStudy fieldOfStudy = FieldOfStudy.createFieldOfStudy(name);
        FieldOfStudyRepository fieldOfStudyRepositoryMock = mock(FieldOfStudyRepository.class);
        when(fieldOfStudyRepositoryMock.findByName(name)).thenReturn(fieldOfStudy);

        FieldOfStudyServiceImpl testee = new FieldOfStudyServiceImpl(fieldOfStudyRepositoryMock);
        FieldOfStudy same = testee.addFieldOfStudy(name);

        assertSame(fieldOfStudy, same);
    }

    @Test
    public void addFieldOfStudy_fieldOfStudyDoesntExists_savesIt() {
        FieldOfStudyRepository fieldOfStudyRepositoryMock = mock(FieldOfStudyRepository.class);
        when(fieldOfStudyRepositoryMock.findByName(any())).thenReturn(null);

        FieldOfStudyServiceImpl testee = new FieldOfStudyServiceImpl(fieldOfStudyRepositoryMock);
        testee.addFieldOfStudy("not existing");

        verify(fieldOfStudyRepositoryMock, times(1)).save(any());
    }

    @Test(expected = InvalidArgumentException.class)
    public void updateFieldOfStudy_fieldDoesntExist_throwsException() {
        String name = "ISIT";
        FieldOfStudy fieldOfStudy = FieldOfStudy.createFieldOfStudy(name);
        FieldOfStudyRepository fieldOfStudyRepositoryMock = mock(FieldOfStudyRepository.class);
        when(fieldOfStudyRepositoryMock.findOne(any())).thenReturn(null);

        FieldOfStudyServiceImpl testee = new FieldOfStudyServiceImpl(fieldOfStudyRepositoryMock);
        testee.updateFieldOfStudy(fieldOfStudy);

        verify(fieldOfStudyRepositoryMock, times(0)).save(any());
    }

    @Test(expected = InvalidArgumentException.class)
    public void updateFieldOfStudy_updatedFieldNameExists_throwsException() {
        String name = "ISIT";
        FieldOfStudy fieldOfStudy = FieldOfStudy.createFieldOfStudy(name);
        FieldOfStudy existingFieldOfStudy = FieldOfStudy.createFieldOfStudy("existing field");
        FieldOfStudyRepository fieldOfStudyRepositoryMock = mock(FieldOfStudyRepository.class);
        when(fieldOfStudyRepositoryMock.findOne(any())).thenReturn(existingFieldOfStudy);
        when(fieldOfStudyRepositoryMock.findByName(fieldOfStudy.getName())).thenReturn(fieldOfStudy);

        FieldOfStudyServiceImpl testee = new FieldOfStudyServiceImpl(fieldOfStudyRepositoryMock);
        testee.updateFieldOfStudy(fieldOfStudy);

        verify(fieldOfStudyRepositoryMock, times(0)).save(any());
    }

    @Test
    public void updateFieldOfStudy_updatedFieldCanBeSaved_save() {
        String name = "ISIT";
        FieldOfStudy fieldOfStudy = FieldOfStudy.createFieldOfStudy(name);
        FieldOfStudy existingFieldOfStudy = FieldOfStudy.createFieldOfStudy("existing field");
        FieldOfStudyRepository fieldOfStudyRepositoryMock = mock(FieldOfStudyRepository.class);
        when(fieldOfStudyRepositoryMock.findOne(any())).thenReturn(existingFieldOfStudy);
        when(fieldOfStudyRepositoryMock.findByName(fieldOfStudy.getName())).thenReturn(null);

        FieldOfStudyServiceImpl testee = new FieldOfStudyServiceImpl(fieldOfStudyRepositoryMock);
        testee.updateFieldOfStudy(fieldOfStudy);
        verify(fieldOfStudyRepositoryMock, times(1)).save(fieldOfStudy);
    }

    @Test(expected = InvalidArgumentException.class)
    public void removeFieldOfStudy_fieldDoesntExist_throwException() {
        FieldOfStudyRepository repositoryMock = mock(FieldOfStudyRepository.class);
        when(repositoryMock.findOne(anyLong())).thenReturn(null);

        FieldOfStudyServiceImpl testee = new FieldOfStudyServiceImpl(repositoryMock);
        testee.removeFieldOfStudy(1L);

        verify(repositoryMock, times(0)).delete(anyLong());
    }

    @Test
    public void removeFieldOfStudy_fieldOfStudyExist_delete() {
        String name = "ISIT";
        FieldOfStudy existingFieldOfStudy = FieldOfStudy.createFieldOfStudy(name);
        FieldOfStudyRepository repositoryMock = mock(FieldOfStudyRepository.class);
        when(repositoryMock.findOne(anyLong())).thenReturn(existingFieldOfStudy);

        FieldOfStudyServiceImpl testee = new FieldOfStudyServiceImpl(repositoryMock);
        testee.removeFieldOfStudy(1L);

        verify(repositoryMock, times(1)).delete(1L);
    }

}