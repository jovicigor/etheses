package rs.fon.pzr.rest.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rs.fon.pzr.core.exception.InvalidArgumentException;
import rs.fon.pzr.persistence.model.Studies;
import rs.fon.pzr.core.service.StudiesService;
import rs.fon.pzr.core.service.util.ParamaterCheck;
import rs.fon.pzr.rest.model.request.StudiesRequest;
import rs.fon.pzr.rest.model.response.level1.StudiesResponseLevel1;
import rs.fon.pzr.rest.model.util.RestFactory;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/studies")
public class StudiesResource {

    private final StudiesService studiesService;

    @Autowired
    public StudiesResource(StudiesService studiesService) {
        this.studiesService = studiesService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public
    @ResponseBody
    List<StudiesResponseLevel1> getStudies(
            @RequestParam(value = "studiesName", required = false) String studiesName) {
        List<Studies> studiesList = studiesService.getAllStudies();
        List<StudiesResponseLevel1> studiesResponseLevel1 = new ArrayList<>();
        if (studiesName != null) {
            studiesResponseLevel1.add(RestFactory
                    .createStudiesResponseLevel1(studiesService
                            .getStudiesByName(studiesName)));
            return studiesResponseLevel1;
        }
        for (Studies studies : studiesList) {
            studiesResponseLevel1.add(RestFactory.createStudiesResponseLevel1(studies));
        }
        return studiesResponseLevel1;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{studiesID}")
    public
    @ResponseBody
    StudiesResponseLevel1 getStudiesById(
            @PathVariable("studiesID") Long id) {
        return RestFactory.createStudiesResponseLevel1(studiesService.getStudies(id));
    }

	/*
     * @RequestMapping(method = RequestMethod.GET, value =
	 * "/{courseID}/subjects") public @ResponseBody List<SubjectResponse>
	 * getCourseSubjects(@PathVariable("courseID") Long courseID) {
	 * List<SubjectResponse> subjectResponseList = new
	 * ArrayList<SubjectResponse>(); Set<Subject> subjectList =
	 * courseService.getCourseSubjects(courseID); for(Subject
	 * subject:subjectList){
	 * subjectResponseList.add(RestFactory.createSubjectResponse(subject)); }
	 * return subjectResponseList; }
	 */

    // CREATE
    @RequestMapping(method = RequestMethod.POST)
    public
    @ResponseBody
    StudiesResponseLevel1 addStudies(
            @RequestBody StudiesRequest studiesRequest) {
        ParamaterCheck.mandatory("Naziv nivoa studija", studiesRequest.getName());
        ParamaterCheck.mandatory("Skraćeni naziv nivoa studija",
                studiesRequest.getNameShort());
        Studies studies = new Studies();
        studies.setName(studiesRequest.getName());
        studies.setNameShort(studiesRequest.getNameShort());
        return RestFactory.createStudiesResponseLevel1(studiesService.addStudies(studies));
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{studiesID}")
    public
    @ResponseBody
    StudiesResponseLevel1 updateStudies(
            @RequestBody StudiesRequest studiesRequest,
            @PathVariable("studiesID") Long studiesID) {
        Studies studies = studiesService.getStudies(studiesID);
        if (studies == null) {
            throw new InvalidArgumentException("Nivo studija sa id-em " + studiesID
                    + " ne postoji u bazi!");
        }
        if (studiesRequest.getName() != null) {
            studies.setName(studiesRequest.getName());
        }
        if (studiesRequest.getNameShort() != null) {
            studies.setNameShort(studiesRequest.getNameShort());
        }
        return RestFactory.createStudiesResponseLevel1(studiesService.updateStudies(studies));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{studiesID}")
    public void removeStudies(@PathVariable("studiesID") Long studiesID) {
        studiesService.removeStudies(studiesID);
    }
}
