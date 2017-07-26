package rs.fon.pzr.rest.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rs.fon.pzr.core.exception.InvalidArgumentException;
import rs.fon.pzr.model.StudiesEntity;
import rs.fon.pzr.core.service.StudiesService;
import rs.fon.pzr.core.service.util.ParamaterCheck;
import rs.fon.pzr.rest.model.request.StudiesRequest;
import rs.fon.pzr.rest.model.response.level1.StudiesResponseLevel1;
import rs.fon.pzr.rest.model.util.RestFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        List<StudiesEntity> studiesList = studiesService.getAllStudies();
        List<StudiesResponseLevel1> studiesResponseLevel1 = new ArrayList<>();
        if (studiesName != null) {
            studiesService
                    .getStudiesByName(studiesName)
                    .map(RestFactory::createStudiesResponseLevel1)
                    .ifPresent(studiesResponseLevel1::add);
            return studiesResponseLevel1;
        }
        studiesResponseLevel1.addAll(studiesList
                .stream()
                .map(RestFactory::createStudiesResponseLevel1)
                .collect(Collectors.toList()));
        return studiesResponseLevel1;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{studiesID}")
    public
    @ResponseBody
    StudiesResponseLevel1 getStudiesById(@PathVariable("studiesID") Long id) {
        return studiesService.getStudies(id)
                .map(RestFactory::createStudiesResponseLevel1)
                .orElse(null);
    }

    @RequestMapping(method = RequestMethod.POST)
    public
    @ResponseBody
    StudiesResponseLevel1 addStudies(
            @RequestBody StudiesRequest studiesRequest) {
        String name = studiesRequest.getName()
                .orElseThrow(() -> new InvalidArgumentException("Studies name je obavezno polje!"));
        String nameShort = studiesRequest.getNameShort()
                .orElseThrow(() -> new InvalidArgumentException("Studies short name je obavezno polje!"));

        StudiesEntity newStudies = studiesService.addStudies(new StudiesEntity(name, nameShort, new HashSet<>()));

        return RestFactory.createStudiesResponseLevel1(newStudies);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{studiesID}")
    public
    @ResponseBody
    StudiesResponseLevel1 updateStudies(
            @RequestBody StudiesRequest studiesRequest,
            @PathVariable("studiesID") Long studiesID) {
        StudiesEntity studies = studiesService.getStudies(studiesID)
                .orElseThrow(() ->
                        new InvalidArgumentException("Nivo studija sa id-em " + studiesID
                                + " ne postoji u bazi!"));

        studiesRequest.getName()
                .ifPresent(studies::setName);
        studiesRequest.getNameShort()
                .ifPresent(studies::setNameShort);

        return RestFactory.createStudiesResponseLevel1(studiesService.updateStudies(studies));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{studiesID}")
    public void removeStudies(@PathVariable("studiesID") Long studiesID) {
        studiesService.removeStudies(studiesID);
    }
}
