package rs.fon.pzr.rest.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rs.fon.pzr.core.exception.InvalidArgumentException;
import rs.fon.pzr.model.FieldOfStudyEntity;
import rs.fon.pzr.core.service.FieldOfStudyService;
import rs.fon.pzr.core.service.util.ParamaterCheck;
import rs.fon.pzr.rest.model.request.FieldOfStudyRequest;

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping(value = "/fields-of-study")
public class FieldOfStudyResource {

    private final FieldOfStudyService fieldOfStudyService;

    @Autowired
    public FieldOfStudyResource(FieldOfStudyService fieldOfStudyService) {
        this.fieldOfStudyService = fieldOfStudyService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public
    @ResponseBody
    Set<FieldOfStudyEntity> getFieldOfStudies() {
        return fieldOfStudyService.getAllFieldsOfStudy();
    }

    @RequestMapping(method = RequestMethod.POST)
    public
    @ResponseBody
    FieldOfStudyEntity addFieldOfStudy(
            @RequestBody FieldOfStudyRequest fieldOfStudyRequest) {
        String fieldName = fieldOfStudyRequest.getName()
                .orElseThrow(() -> new InvalidArgumentException("Naziv oblasti je obavezno polje!"));
        return fieldOfStudyService.addFieldOfStudy(fieldName);
    }

    // UPDATE
    @RequestMapping(method = RequestMethod.PUT, value = "/{fieldOfStudyID}")
    public
    @ResponseBody
    FieldOfStudyEntity updateFieldOfStudy(@PathVariable("fieldOfStudyID") Long fieldOfStudyID,
                                          @RequestBody FieldOfStudyRequest fieldOfStudyRequest) {
        FieldOfStudyEntity fieldOfStudy = fieldOfStudyService.getFieldOfStudy(fieldOfStudyID)
                .orElseThrow(() -> new InvalidArgumentException("Oblast sa id-em " + fieldOfStudyID
                        + " ne postoji u bazi!"));

        fieldOfStudyRequest.getName()
                .ifPresent(fieldOfStudy::setName);

        return fieldOfStudyService.updateFieldOfStudy(fieldOfStudy);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{fieldOfStudyID}")
    public void deleteTag(@PathVariable("fieldOfStudyID") Long fieldOfStudyID) {
        fieldOfStudyService.removeFieldOfStudy(fieldOfStudyID);
    }
}
