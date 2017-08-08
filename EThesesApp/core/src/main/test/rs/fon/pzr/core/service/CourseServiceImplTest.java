package rs.fon.pzr.core.service;

import org.junit.Test;
import rs.fon.pzr.core.domain.model.studies.Course;
import rs.fon.pzr.core.domain.model.studies.CourseBuilder;
import rs.fon.pzr.core.exception.InvalidArgumentException;
import rs.fon.pzr.core.service.repository.CourseRepository;

import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class CourseServiceImplTest {

    private static final String courseName = "Softversko inzenjerstvo";
    private static final String courseShortName = "SI";

    @Test
    public void getCourseByName_courseDoesntExist_returnsOptional() {
        CourseRepository courseRepositoryMock = mock(CourseRepository.class);
        when(courseRepositoryMock.findByName(anyString())).thenReturn(null);

        CourseService testee = new CourseServiceImpl(courseRepositoryMock);

        assertFalse(testee.getCourseByName("CourseName").isPresent());
        verify(courseRepositoryMock, times(1)).findByName(anyString());
    }

    @Test
    public void getCourseByName_courseExists_returnsOptional() {
        Course course = new CourseBuilder()
                .withName(courseName)
                .withNameShort(courseShortName)
                .withStudies(Collections.emptyList())
                .build();

        CourseRepository courseRepositoryMock = mock(CourseRepository.class);
        when(courseRepositoryMock.findByName(courseName)).thenReturn(course);

        CourseService testee = new CourseServiceImpl(courseRepositoryMock);
        Optional<Course> courseByName = testee.getCourseByName(courseName);

        assertTrue(courseByName.isPresent());
        assertEquals(courseName, courseByName.get().getName());
        assertEquals(courseShortName, courseByName.get().getNameShort());
        assertTrue(courseByName.get().getStudies().isEmpty());
        verify(courseRepositoryMock, times(1)).findByName(courseName);
    }

    @Test(expected = InvalidArgumentException.class)
    public void addCourse_courseWithSameNameExists_throwException() {
        Course course = new CourseBuilder()
                .withName(courseName)
                .withNameShort(courseShortName)
                .withStudies(Collections.emptyList())
                .build();

        CourseRepository courseRepositoryMock = mock(CourseRepository.class);
        when(courseRepositoryMock.existsByName(courseName)).thenReturn(true);
        CourseService testee = new CourseServiceImpl(courseRepositoryMock);

        testee.addCourse(course);
        verify(courseRepositoryMock, times(1)).existsByName(courseName);
        verify(courseRepositoryMock, times(0)).save(course);
    }

    @Test(expected = InvalidArgumentException.class)
    public void addCourse_courseWithSameShortNameExists_throwException() {
        Course course = new CourseBuilder()
                .withName(courseName)
                .withNameShort(courseShortName)
                .withStudies(Collections.emptyList())
                .build();

        CourseRepository courseRepositoryMock = mock(CourseRepository.class);
        when(courseRepositoryMock.existsByName(courseName)).thenReturn(false);
        when(courseRepositoryMock.existsByNameShort(courseShortName)).thenReturn(true);
        CourseService testee = new CourseServiceImpl(courseRepositoryMock);

        testee.addCourse(course);
        verify(courseRepositoryMock, times(1)).existsByNameShort(courseName);
        verify(courseRepositoryMock, times(0)).save(course);
    }

    @Test
    public void addCourse_courseDoesntExist_saveCourse() {
        Course course = new CourseBuilder()
                .withName(courseName)
                .withNameShort(courseShortName)
                .withStudies(Collections.emptyList())
                .build();

        CourseRepository courseRepositoryMock = mock(CourseRepository.class);
        when(courseRepositoryMock.existsByName(courseName)).thenReturn(false);
        when(courseRepositoryMock.existsByNameShort(courseShortName)).thenReturn(false);

        CourseService testee = new CourseServiceImpl(courseRepositoryMock);
        testee.addCourse(course);

        verify(courseRepositoryMock, times(1)).save(course);
    }

    @Test(expected = InvalidArgumentException.class)
    public void updateCourse_courseDoesntExist_throwException() {
        Course course = new CourseBuilder()
                .withName(courseName)
                .withNameShort(courseShortName)
                .withStudies(Collections.emptyList())
                .build();
        CourseRepository courseRepositoryMock = mock(CourseRepository.class);
        when(courseRepositoryMock.findOne(anyLong())).thenReturn(null);

        CourseService testee = new CourseServiceImpl(courseRepositoryMock);
        testee.updateCourse(course);

        verify(courseRepositoryMock, times(0)).save(course);
    }

    @Test(expected = InvalidArgumentException.class)
    public void updateCourse_courseWithSameNameExists_throwException() {
        Course existingCourse = new CourseBuilder()
                .withName(courseName)
                .withNameShort(courseShortName)
                .withStudies(Collections.emptyList())
                .build();

        Course course = new CourseBuilder()
                .withName("different name")
                .withNameShort(courseShortName)
                .withStudies(Collections.emptyList())
                .build();
        CourseRepository courseRepositoryMock = mock(CourseRepository.class);
        when(courseRepositoryMock.findOne(anyLong())).thenReturn(existingCourse);
        when(courseRepositoryMock.existsByName(course.getName())).thenReturn(true);

        CourseService testee = new CourseServiceImpl(courseRepositoryMock);
        testee.updateCourse(course);

        verify(courseRepositoryMock, times(0)).save(course);
    }

    @Test(expected = InvalidArgumentException.class)
    public void updateCourse_courseWithSameShortNameExists_throwException() {
        Course existingCourse = new CourseBuilder()
                .withName(courseName)
                .withNameShort(courseShortName)
                .withStudies(Collections.emptyList())
                .build();

        Course course = new CourseBuilder()
                .withName(courseName)
                .withNameShort("different short name")
                .withStudies(Collections.emptyList())
                .build();
        CourseRepository courseRepositoryMock = mock(CourseRepository.class);
        when(courseRepositoryMock.findOne(anyLong())).thenReturn(existingCourse);
        when(courseRepositoryMock.existsByNameShort(course.getNameShort())).thenReturn(true);

        CourseService testee = new CourseServiceImpl(courseRepositoryMock);
        testee.updateCourse(course);

        verify(courseRepositoryMock, times(0)).save(course);
    }

    @Test
    public void updateCourse_courseNoNamingConflicts_throwException() {
        Course existingCourse = new CourseBuilder()
                .withName(courseName)
                .withNameShort(courseShortName)
                .withStudies(Collections.emptyList())
                .build();

        Course course = new CourseBuilder()
                .withName("different name")
                .withNameShort("different short name")
                .withStudies(Collections.emptyList())
                .build();
        CourseRepository courseRepositoryMock = mock(CourseRepository.class);
        when(courseRepositoryMock.findOne(anyLong())).thenReturn(existingCourse);
        when(courseRepositoryMock.existsByNameShort(course.getNameShort())).thenReturn(false);
        when(courseRepositoryMock.existsByName(course.getNameShort())).thenReturn(false);

        CourseService testee = new CourseServiceImpl(courseRepositoryMock);
        testee.updateCourse(course);

        verify(courseRepositoryMock, times(1)).save(course);
    }

    @Test(expected = InvalidArgumentException.class)
    public void removeCourse_courseDoesntExist_throwException() {
        CourseRepository courseRepositoryMock = mock(CourseRepository.class);
        when(courseRepositoryMock.findOne(anyLong())).thenReturn(null);

        CourseService testee = new CourseServiceImpl(courseRepositoryMock);
        testee.removeCourse(1L);
    }

    @Test
    public void removeCourse_courseExist_throwException() {
        Course existingCourse = new CourseBuilder()
                .withName(courseName)
                .withNameShort(courseShortName)
                .withStudies(Collections.emptyList())
                .build();
        CourseRepository courseRepositoryMock = mock(CourseRepository.class);
        when(courseRepositoryMock.findOne(anyLong())).thenReturn(existingCourse);

        CourseService testee = new CourseServiceImpl(courseRepositoryMock);
        testee.removeCourse(1L);

        verify(courseRepositoryMock, times(1)).delete(existingCourse);
    }
}