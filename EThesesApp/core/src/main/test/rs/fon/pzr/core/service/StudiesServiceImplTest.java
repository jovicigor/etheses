package rs.fon.pzr.core.service;

import org.junit.Test;
import rs.fon.pzr.core.domain.model.studies.Studies;
import rs.fon.pzr.core.domain.model.studies.StudiesBuilder;
import rs.fon.pzr.core.exception.InvalidArgumentException;
import rs.fon.pzr.core.service.repository.StudiesRepository;

import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;


public class StudiesServiceImplTest {
    private static final String studiesName = "osnovne studije";
    private static final String studiesShortName = "OSN";

    @Test
    public void getStudiesByName_studiesDoesntExist_returnsOptional() {
        StudiesRepository studiesRepositoryMock = mock(StudiesRepository.class);
        when(studiesRepositoryMock.findByName(anyString())).thenReturn(null);

        StudiesService testee = new StudiesServiceImpl(studiesRepositoryMock);

        assertFalse(testee.getStudiesByName("StudiesName").isPresent());
        verify(studiesRepositoryMock, times(1)).findByName(anyString());
    }

    @Test
    public void getStudiesByName_studiesExists_returnsOptional() {
        Studies studies = new StudiesBuilder()
                .withName(studiesName)
                .withNameShort(studiesShortName)
                .build();

        StudiesRepository studiesRepositoryMock = mock(StudiesRepository.class);
        when(studiesRepositoryMock.findByName(studiesName)).thenReturn(studies);

        StudiesService testee = new StudiesServiceImpl(studiesRepositoryMock);
        Optional<Studies> studiesByName = testee.getStudiesByName(studiesName);

        assertTrue(studiesByName.isPresent());
        assertEquals(studiesName, studiesByName.get().getName());
        assertEquals(studiesShortName, studiesByName.get().getNameShort());
        assertTrue(studiesByName.get().getCourses().isEmpty());
        verify(studiesRepositoryMock, times(1)).findByName(studiesName);
    }

    @Test(expected = InvalidArgumentException.class)
    public void addStudies_studiesWithSameNameExists_throwException() {
        Studies studies = new StudiesBuilder()
                .withName(studiesName)
                .withNameShort(studiesShortName)
                .build();

        StudiesRepository studiesRepositoryMock = mock(StudiesRepository.class);
        when(studiesRepositoryMock.findByName(studiesName)).thenReturn(studies);
        StudiesService testee = new StudiesServiceImpl(studiesRepositoryMock);

        testee.addStudies(studies);
        verify(studiesRepositoryMock, times(1)).findByName(studiesName);
        verify(studiesRepositoryMock, times(0)).save(studies);
    }

    @Test(expected = InvalidArgumentException.class)
    public void addStudies_studiesWithSameShortNameExists_throwException() {
        Studies studies = new StudiesBuilder()
                .withName(studiesName)
                .withNameShort(studiesShortName)
                .build();

        StudiesRepository studiesRepositoryMock = mock(StudiesRepository.class);
        when(studiesRepositoryMock.findByName(studiesName)).thenReturn(null);
        when(studiesRepositoryMock.findByNameShort(studiesShortName)).thenReturn(studies);
        StudiesService testee = new StudiesServiceImpl(studiesRepositoryMock);

        testee.addStudies(studies);
        verify(studiesRepositoryMock, times(1)).findByNameShort(studiesName);
        verify(studiesRepositoryMock, times(0)).save(studies);
    }

    @Test
    public void addStudies_studiesDoesntExist_saveStudies() {
        Studies studies = new StudiesBuilder()
                .withName(studiesName)
                .withNameShort(studiesShortName)
                .build();

        StudiesRepository studiesRepositoryMock = mock(StudiesRepository.class);
        when(studiesRepositoryMock.findByName(studiesName)).thenReturn(null);
        when(studiesRepositoryMock.findByNameShort(studiesShortName)).thenReturn(null);

        StudiesService testee = new StudiesServiceImpl(studiesRepositoryMock);
        testee.addStudies(studies);

        verify(studiesRepositoryMock, times(1)).save(studies);
    }

    @Test(expected = InvalidArgumentException.class)
    public void updateStudies_studiesDoesntExist_throwException() {
        Studies studies = new StudiesBuilder()
                .withName(studiesName)
                .withNameShort(studiesShortName)
                .build();
        StudiesRepository studiesRepositoryMock = mock(StudiesRepository.class);
        when(studiesRepositoryMock.findOne(anyLong())).thenReturn(null);

        StudiesService testee = new StudiesServiceImpl(studiesRepositoryMock);
        testee.updateStudies(studies);

        verify(studiesRepositoryMock, times(0)).save(studies);
    }

    @Test(expected = InvalidArgumentException.class)
    public void updateStudies_studiesWithSameNameExists_throwException() {
        Studies existingStudies = new StudiesBuilder()
                .withName(studiesName)
                .withNameShort(studiesShortName)
                .build();

        Studies studies = new StudiesBuilder()
                .withName("different name")
                .withNameShort(studiesShortName)
                .build();
        StudiesRepository studiesRepositoryMock = mock(StudiesRepository.class);
        when(studiesRepositoryMock.findOne(anyLong())).thenReturn(existingStudies);
        when(studiesRepositoryMock.findByName(studies.getName())).thenReturn(existingStudies);

        StudiesService testee = new StudiesServiceImpl(studiesRepositoryMock);
        testee.updateStudies(studies);

        verify(studiesRepositoryMock, times(0)).save(studies);
    }

    @Test(expected = InvalidArgumentException.class)
    public void updateStudies_studiesWithSameShortNameExists_throwException() {
        Studies existingStudies = new StudiesBuilder()
                .withName(studiesName)
                .withNameShort(studiesShortName)
                .build();

        Studies studies = new StudiesBuilder()
                .withName(studiesName)
                .withNameShort("different short name")
                .build();
        StudiesRepository studiesRepositoryMock = mock(StudiesRepository.class);
        when(studiesRepositoryMock.findOne(anyLong())).thenReturn(existingStudies);
        when(studiesRepositoryMock.findByNameShort(studies.getNameShort())).thenReturn(existingStudies);

        StudiesService testee = new StudiesServiceImpl(studiesRepositoryMock);
        testee.updateStudies(studies);

        verify(studiesRepositoryMock, times(0)).save(studies);
    }

    @Test
    public void updateStudies_studiesNoNamingConflicts_throwException() {
        Studies existingStudies = new StudiesBuilder()
                .withName(studiesName)
                .withNameShort(studiesShortName)
                .build();

        Studies studies = new StudiesBuilder()
                .withName("different name")
                .withNameShort("different short name")
                .build();
        StudiesRepository studiesRepositoryMock = mock(StudiesRepository.class);
        when(studiesRepositoryMock.findOne(anyLong())).thenReturn(existingStudies);
        when(studiesRepositoryMock.findByNameShort(studies.getNameShort())).thenReturn(null);
        when(studiesRepositoryMock.findByName(studies.getNameShort())).thenReturn(null);

        StudiesService testee = new StudiesServiceImpl(studiesRepositoryMock);
        testee.updateStudies(studies);

        verify(studiesRepositoryMock, times(1)).save(studies);
    }

    @Test(expected = InvalidArgumentException.class)
    public void removeStudies_studiesDoesntExist_throwException() {
        StudiesRepository studiesRepositoryMock = mock(StudiesRepository.class);
        when(studiesRepositoryMock.findOne(anyLong())).thenReturn(null);

        StudiesService testee = new StudiesServiceImpl(studiesRepositoryMock);
        testee.removeStudies(1L);
    }

    @Test
    public void removeStudies_studiesExist_throwException() {
        Studies existingStudies = new StudiesBuilder()
                .withName(studiesName)
                .withNameShort(studiesShortName)
                .build();
        StudiesRepository studiesRepositoryMock = mock(StudiesRepository.class);
        when(studiesRepositoryMock.findOne(anyLong())).thenReturn(existingStudies);

        StudiesService testee = new StudiesServiceImpl(studiesRepositoryMock);
        testee.removeStudies(1L);

        verify(studiesRepositoryMock, times(1)).delete(existingStudies);
    }
}