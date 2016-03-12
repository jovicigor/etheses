package rs.fon.elab.pzr.rest.resources;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import rs.fon.elab.pzr.core.exception.InvalidArgumentException;
import rs.fon.elab.pzr.core.model.Course;
import rs.fon.elab.pzr.core.model.Subject;
import rs.fon.elab.pzr.core.service.CourseService;
import rs.fon.elab.pzr.core.service.SubjectService;
import rs.fon.elab.pzr.core.service.util.ParamaterCheck;
import rs.fon.elab.pzr.rest.model.request.SubjectRequest;
import rs.fon.elab.pzr.rest.model.response.level1.SubjectResponseLevel1;
import rs.fon.elab.pzr.rest.model.response.old.SubjectResponse;
import rs.fon.elab.pzr.rest.model.util.RestFactory;

@RestController
@RequestMapping(value = "/subjects")
public class SubjectResource {

	private SubjectService subjectService;
	private CourseService courseService;

	// READ
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody List<SubjectResponseLevel1> getSubjects(
			@RequestParam(value = "subjectName", required = false) String subjectName) {
		List<SubjectResponseLevel1> subjectResponseList = new ArrayList<SubjectResponseLevel1>();
		if (subjectName != null) {
			subjectResponseList.add(RestFactory
					.createSubjectResponseLevel1(subjectService
							.getSubjectByName(subjectName)));
			return subjectResponseList;
		}

		List<Subject> subjectList = subjectService.getAllSubjects();
		for (Subject subject : subjectList) {
			subjectResponseList.add(RestFactory
					.createSubjectResponseLevel1(subject));
		}
		return subjectResponseList;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{subjectID}")
	public @ResponseBody SubjectResponseLevel1 getSubject(
			@PathVariable("subjectID") Long subjectID) {
		return RestFactory.createSubjectResponseLevel1(subjectService
				.getSubject(subjectID));
	}

	// CREATE
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody SubjectResponseLevel1 addSubject(
			@RequestBody SubjectRequest subjectRequest) {
		ParamaterCheck.mandatory("Naziv predmeta", subjectRequest.getName());

		Subject subject = new Subject();
		subject.setName(subjectRequest.getName());
		if (subjectRequest.getCourseIDs() != null) {
			Set<Course> courseList = new HashSet<Course>();
			for (Long courseId : subjectRequest.getCourseIDs()) {
				Course course = courseService.getCourse(courseId);
				if (course != null) {
					courseList.add(course);
				}
			}
			subject.setCourses(courseList);
		}		
		return RestFactory.createSubjectResponseLevel1(subjectService
				.addSubject(subject));
	}

	// UPDATE
	@RequestMapping(method = RequestMethod.PUT, value = "/{subjectID}")
	public @ResponseBody SubjectResponseLevel1 updateSubject(
			@RequestBody SubjectRequest subjectRequest,
			@PathVariable("subjectID") Long subjectID) {

		Subject subject = subjectService.getSubject(subjectID);
		if (subject == null) {
			throw new InvalidArgumentException("Predmet sa id-em " + subjectID
					+ " ne postoji u bazi!");
		}

		if (subjectRequest.getName() != null) {
			subject.setName(subjectRequest.getName());
		}
		if (subjectRequest.getCourseIDs() != null) {
			Set<Course> courseList = new HashSet<Course>();
			for (Long courseId : subjectRequest.getCourseIDs()) {
				Course course = courseService.getCourse(courseId);
				if (course != null) {
					courseList.add(course);
				}
			}
			subject.setCourses(courseList);
		}
		return RestFactory.createSubjectResponseLevel1(subjectService
				.updateSubject(subject));
	}

	// DELETE
	@RequestMapping(method = RequestMethod.DELETE, value = "/{subjectID}")
	public void removeSubject(@PathVariable("subjectID") Long subjectID) {
		subjectService.removeSubject(subjectID);
	}

	// GETTERS AND SETTERS
	public SubjectService getSubjectService() {
		return subjectService;
	}

	public void setSubjectService(SubjectService subjectService) {
		this.subjectService = subjectService;
	}

	public CourseService getCourseService() {
		return courseService;
	}

	public void setCourseService(CourseService courseService) {
		this.courseService = courseService;
	}

}
