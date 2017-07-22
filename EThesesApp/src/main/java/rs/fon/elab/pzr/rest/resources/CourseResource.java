package rs.fon.elab.pzr.rest.resources;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import rs.fon.elab.pzr.core.exception.InvalidArgumentException;
import rs.fon.elab.pzr.core.model.Course;
import rs.fon.elab.pzr.core.model.Studies;
import rs.fon.elab.pzr.core.service.CourseService;
import rs.fon.elab.pzr.core.service.StudiesService;
import rs.fon.elab.pzr.core.service.util.ParamaterCheck;
import rs.fon.elab.pzr.rest.model.request.CourseRequest;
import rs.fon.elab.pzr.rest.model.response.level1.CourseResponseLevel1;
import rs.fon.elab.pzr.rest.model.util.RestFactory;

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
        List<Course> courseList = courseService.getAllCourses();
        List<CourseResponseLevel1> courseResponseList = new ArrayList<>();
        if (courseName != null) {
            courseResponseList.add(RestFactory
                    .createCourseResponseLevel1(courseService
                            .getCourseByName(courseName)));
            return courseResponseList;
        }
        for (Course course : courseList) {
            courseResponseList.add(RestFactory
                    .createCourseResponseLevel1(course));
        }
        return courseResponseList;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{courseID}")
    public
    @ResponseBody
    CourseResponseLevel1 getCourseById(
            @PathVariable("courseID") Long id) {
        return RestFactory.createCourseResponseLevel1(courseService
                .getCourse(id));
    }


    // CREATE
    @RequestMapping(method = RequestMethod.POST)
    public
    @ResponseBody
    CourseResponseLevel1 addCourse(
            @RequestBody CourseRequest courseRequest) {
        ParamaterCheck.mandatory("Naziv smera", courseRequest.getName());
        ParamaterCheck.mandatory("Skraćeni naziv smera",
                courseRequest.getNameShort());

        Course course = new Course();
        course.setName(courseRequest.getName());
        course.setNameShort(courseRequest.getNameShort());
        if (courseRequest.getStudiesIDs() != null) {
            Set<Studies> studiesList = new HashSet<>();
            for (Long studiesId : courseRequest.getStudiesIDs()) {
                Studies studies = studiesService.getStudies(studiesId);
                if (studies != null) {
                    studiesList.add(studies);
                }
            }
            course.setStudies(studiesList);
        }
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
        Course course = courseService.getCourse(courseID);
        if (course == null) {
            throw new InvalidArgumentException("Kurs sa id-em " + courseID
                    + " ne postoji u bazi!");
        }
        if (courseRequest.getName() != null) {
            course.setName(courseRequest.getName());
        }
        if (courseRequest.getNameShort() != null) {
            course.setNameShort(courseRequest.getNameShort());
        }

        if (courseRequest.getStudiesIDs() != null) {
            Set<Studies> studiesList = new HashSet<>();
            for (Long studiesId : courseRequest.getStudiesIDs()) {
                Studies studies = studiesService.getStudies(studiesId);
                if (studies != null) {
                    studiesList.add(studies);
                }
            }
            course.setStudies(studiesList);
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
