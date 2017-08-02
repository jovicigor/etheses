package rs.fon.pzr.rest.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rs.fon.pzr.core.exception.InvalidArgumentException;
import rs.fon.pzr.core.service.CourseService;
import rs.fon.pzr.core.service.StudiesService;
import rs.fon.pzr.core.domain.model.studies.Course;
import rs.fon.pzr.core.domain.model.studies.CourseBuilder;
import rs.fon.pzr.core.domain.model.studies.Studies;
import rs.fon.pzr.rest.model.request.CourseRequest;
import rs.fon.pzr.rest.model.response.level1.CourseResponseLevel1;
import rs.fon.pzr.rest.model.util.RestFactory;

import java.util.*;
import java.util.stream.Collectors;

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

    @GetMapping
    @ResponseBody
    public List<CourseResponseLevel1> getCourses(@RequestParam(value = "courseName", required = false)
                                                         String courseName) {
        List<Course> courseList = courseService.getAllCourses();
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

    @GetMapping(value = "/{courseID}")
    @ResponseBody
    public CourseResponseLevel1 getCourseById(@PathVariable("courseID") Long id) {
        return courseService.getCourse(id)
                .map(RestFactory::createCourseResponseLevel1)
                .orElse(null);
    }

    @PostMapping
    @ResponseBody
    public CourseResponseLevel1 addCourse(@RequestBody CourseRequest courseRequest) {
        String courseName = courseRequest.getName()
                .orElseThrow(() -> new InvalidArgumentException("Naziv smera je obavezno polje!"));
        String courseNameShort = courseRequest.getNameShort()
                .orElseThrow(() -> new InvalidArgumentException("Skraceni naziv smera je obavezno polje!"));

        Set<Studies> studiesList = new HashSet<>();
        if (courseRequest.getStudiesIDs() != null) {
            studiesList = courseRequest.getStudiesIDs().stream()
                    .map(studiesService::getStudies)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toSet());
        }
        Course course = new CourseBuilder()
                .withName(courseName)
                .withNameShort(courseNameShort)
                .withStudies(studiesList)
                .build();
        Course newCourse = courseService.addCourse(course);

        return new CourseResponseLevel1(newCourse);
    }

    @PutMapping(value = "/{courseID}")
    @ResponseBody
    public CourseResponseLevel1 updateCourse(@RequestBody CourseRequest courseRequest,
                                             @PathVariable("courseID") Long courseID) {
        Course course = courseService.getCourse(courseID)
                .orElseThrow(() -> new InvalidArgumentException("Kurs sa id-em " + courseID
                        + " ne postoji u bazi!"));

        courseRequest.getName()
                .ifPresent(course::setName);

        courseRequest.getNameShort()
                .ifPresent(course::setNameShort);

        if (courseRequest.getStudiesIDs() != null) {
            Set<Studies> studies = courseRequest.getStudiesIDs().stream()
                    .map(studiesService::getStudies)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toSet());
            course.updateStudies(studies);
        }

        Course updatedCourse = courseService.updateCourse(course);
        return RestFactory.createCourseResponseLevel1(updatedCourse);
    }

    @DeleteMapping(value = "/{courseID}")
    public void removeCourse(@PathVariable("courseID") Long courseID) {
        courseService.removeCourse(courseID);
    }
}
