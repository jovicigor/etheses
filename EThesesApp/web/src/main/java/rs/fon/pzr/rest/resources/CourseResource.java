package rs.fon.pzr.rest.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rs.fon.pzr.core.exception.InvalidArgumentException;
import rs.fon.pzr.model.CourseEntity;
import rs.fon.pzr.model.StudiesEntity;
import rs.fon.pzr.core.service.CourseService;
import rs.fon.pzr.core.service.StudiesService;
import rs.fon.pzr.rest.model.request.CourseRequest;
import rs.fon.pzr.rest.model.response.level1.CourseResponseLevel1;
import rs.fon.pzr.rest.model.util.RestFactory;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Collections.singletonList;

@RestController
@RequestMapping(value = "/courses")
public class CourseResource {

    private final CourseService courseService;
    private final StudiesService studiesService;

    @Autowired
    public CourseResource(CourseService courseService, StudiesService studiesService) {
        this.courseService = courseService;
        this.studiesService = studiesService;
    }

    // READ
    @RequestMapping(method = RequestMethod.GET)
    public
    @ResponseBody
    List<CourseResponseLevel1> getCourses(
            @RequestParam(value = "courseName", required = false) String courseName) {
        List<CourseEntity> courseList = courseService.getAllCourses();
        List<CourseResponseLevel1> retval;
        if (courseName != null) {
            retval = courseService
                    .getCourseByName(courseName)
                    .map(RestFactory::createCourseResponseLevel1)
                    .map(Collections::singletonList)
                    .orElse(Collections.emptyList());
        } else {
            retval = courseList.stream()
                    .map(RestFactory::createCourseResponseLevel1)
                    .collect(Collectors.toList());
        }

        return retval;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{courseID}")
    public
    @ResponseBody
    CourseResponseLevel1 getCourseById(
            @PathVariable("courseID") Long id) {
        return courseService.getCourse(id)
                .map(RestFactory::createCourseResponseLevel1)
                .orElse(null);
    }


    // CREATE
    @RequestMapping(method = RequestMethod.POST)
    public
    @ResponseBody
    CourseResponseLevel1 addCourse(
            @RequestBody CourseRequest courseRequest) {
        String courseName = courseRequest.getName()
                .orElseThrow(() -> new InvalidArgumentException("Naziv smera je obavezno polje!"));
        String courseNameShort = courseRequest.getNameShort()
                .orElseThrow(() -> new InvalidArgumentException("Skraceni naziv smera je obavezno polje!"));


        Set<StudiesEntity> studiesList = new HashSet<>();
        if (courseRequest.getStudiesIDs() != null) {
            for (Long studiesId : courseRequest.getStudiesIDs()) {
                StudiesEntity studies = studiesService.getStudies(studiesId);
                if (studies != null) {
                    studiesList.add(studies);
                }
            }
        }
        CourseEntity course = new CourseEntity(courseName, courseNameShort, studiesList);

        return RestFactory.createCourseResponseLevel1(courseService
                .addCourse(course));
    }

    // UPDATE
    @RequestMapping(method = RequestMethod.PUT, value = "/{courseID}")
    public
    @ResponseBody
    CourseResponseLevel1 updateCourse(
            @RequestBody CourseRequest courseRequest,
            @PathVariable("courseID") Long courseID) {
        CourseEntity course = courseService.getCourse(courseID)
                .orElseThrow(() -> new InvalidArgumentException("Kurs sa id-em " + courseID
                        + " ne postoji u bazi!"));

        courseRequest.getName()
                .ifPresent(course::setName);

        courseRequest.getNameShort()
                .ifPresent(course::setNameShort);

        if (courseRequest.getStudiesIDs() != null) {
            Set<StudiesEntity> studiesList = new HashSet<>();
            for (Long studiesId : courseRequest.getStudiesIDs()) {
                StudiesEntity studies = studiesService.getStudies(studiesId);
                if (studies != null) {
                    studiesList.add(studies);
                }
            }
            course.updateStudies(studiesList);
        }

        return RestFactory.createCourseResponseLevel1(courseService
                .updateCourse(course));
    }

    // DELETE
    @RequestMapping(method = RequestMethod.DELETE, value = "/{courseID}")
    public void removeCourse(@PathVariable("courseID") Long courseID) {
        courseService.removeCourse(courseID);
    }
}
