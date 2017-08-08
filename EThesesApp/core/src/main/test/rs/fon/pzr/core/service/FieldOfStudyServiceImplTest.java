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

        FieldOfStudyServiceImpl fieldOfStudyService = new FieldOfStudyServiceImpl(fieldOfStudyRepositoryMock);
        Optional<FieldOfStudy> result = fieldOfStudyService.getFieldOfStudy(1L);

        assertTrue(result.isPresent());
    }

    @Test
    public void getFieldOfStudy_courseDoesntExists_empty() {
        FieldOfStudyRepository fieldOfStudyRepositoryMock = mock(FieldOfStudyRepository.class);
        when(fieldOfStudyRepositoryMock.findOne(anyLong())).thenReturn(null);

        FieldOfStudyServiceImpl fieldOfStudyService = new FieldOfStudyServiceImpl(fieldOfStudyRepositoryMock);
        Optional<FieldOfStudy> result = fieldOfStudyService.getFieldOfStudy(1L);

        assertFalse(result.isPresent());
    }

    @Test
    public void addFieldOfStudy_fieldOfStudyExists_returnsIt() {
        String name = "ISIT";
        FieldOfStudy fieldOfStudy = FieldOfStudy.createFieldOfStudy(name);
        FieldOfStudyRepository fieldOfStudyRepositoryMock = mock(FieldOfStudyRepository.class);
        when(fieldOfStudyRepositoryMock.findByName(name)).thenReturn(fieldOfStudy);

        FieldOfStudyServiceImpl fieldOfStudyService = new FieldOfStudyServiceImpl(fieldOfStudyRepositoryMock);
        FieldOfStudy same = fieldOfStudyService.addFieldOfStudy(name);

        assertSame(fieldOfStudy, same);
    }

    @Test
    public void addFieldOfStudy_fieldOfStudyDoesntExists_savesIt() {
        FieldOfStudyRepository fieldOfStudyRepositoryMock = mock(FieldOfStudyRepository.class);
        when(fieldOfStudyRepositoryMock.findByName(any())).thenReturn(null);

        FieldOfStudyServiceImpl fieldOfStudyService = new FieldOfStudyServiceImpl(fieldOfStudyRepositoryMock);
        fieldOfStudyService.addFieldOfStudy("not existing");

        verify(fieldOfStudyRepositoryMock, times(1)).save(any());
    }

    @Test(expected = InvalidArgumentException.class)
    public void updateFieldOfStudy_fieldDoesntExist_throwsException() {
        String name = "ISIT";
        FieldOfStudy fieldOfStudy = FieldOfStudy.createFieldOfStudy(name);
        FieldOfStudyRepository fieldOfStudyRepositoryMock = mock(FieldOfStudyRepository.class);
        when(fieldOfStudyRepositoryMock.findOne(any())).thenReturn(null);

        FieldOfStudyServiceImpl fieldOfStudyService = new FieldOfStudyServiceImpl(fieldOfStudyRepositoryMock);
        fieldOfStudyService.updateFieldOfStudy(fieldOfStudy);
    }

//    TODO: more tests
}