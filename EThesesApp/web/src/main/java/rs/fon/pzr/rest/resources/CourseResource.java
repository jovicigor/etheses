package rs.fon.pzr.rest.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rs.fon.pzr.core.exception.InvalidArgumentException;
import rs.fon.pzr.core.service.CourseService;
import rs.fon.pzr.core.service.StudiesService;
import rs.fon.pzr.model.CourseEntity;
import rs.fon.pzr.model.StudiesEntity;
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

    @GetMapping
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

        Set<StudiesEntity> studiesList = new HashSet<>();
        if (courseRequest.getStudiesIDs() != null) {
            studiesList = courseRequest.getStudiesIDs().stream()
                    .map(studiesService::getStudies)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toSet());
        }
        CourseEntity newCourse = courseService.addCourse(new CourseEntity(courseName, courseNameShort, studiesList));

        return RestFactory.createCourseResponseLevel1(newCourse);
    }

    @PutMapping(value = "/{courseID}")
    @ResponseBody
    public CourseResponseLevel1 updateCourse(@RequestBody CourseRequest courseRequest,
                                             @PathVariable("courseID") Long courseID) {
        CourseEntity course = courseService.getCourse(courseID)
                .orElseThrow(() -> new InvalidArgumentException("Kurs sa id-em " + courseID
                        + " ne postoji u bazi!"));

        courseRequest.getName()
                .ifPresent(course::setName);

        courseRequest.getNameShort()
                .ifPresent(course::setNameShort);

        if (courseRequest.getStudiesIDs() != null) {
            Set<StudiesEntity> studies = courseRequest.getStudiesIDs().stream()
                    .map(studiesService::getStudies)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toSet());
            course.updateStudies(studies);
        }

        CourseEntity updatedCourse = courseService.updateCourse(course);
        return RestFactory.createCourseResponseLevel1(updatedCourse);
    }

    @DeleteMapping(value = "/{courseID}")
    public void removeCourse(@PathVariable("courseID") Long courseID) {
        courseService.removeCourse(courseID);
    }
}
