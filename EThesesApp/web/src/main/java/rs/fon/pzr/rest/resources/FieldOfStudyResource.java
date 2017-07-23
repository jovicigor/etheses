package rs.fon.pzr.rest.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rs.fon.pzr.core.exception.InvalidArgumentException;
import rs.fon.pzr.persistence.model.FieldOfStudy;
import rs.fon.pzr.core.service.FieldOfStudyService;
import rs.fon.pzr.core.service.util.ParamaterCheck;
import rs.fon.pzr.rest.model.request.FieldOfStudyRequest;

import java.util.Set;

@RestController
@RequestMapping(value = "/fields-of-study")
public class FieldOfStudyResource {

    private final FieldOfStudyService fieldOfStudyService;

    @Autowired
    public FieldOfStudyResource(FieldOfStudyService fieldOfStudyService) {
        this.fieldOfStudyService = fieldOfStudyService;
    }

    // READ
    @RequestMapping(method = RequestMethod.GET)
    public
    @ResponseBody
    Set<FieldOfStudy> getFieldOfStudies() {
        return fieldOfStudyService.getAllFieldsOfStudy();
    }

    // CREATE
    @RequestMapping(method = RequestMethod.POST)
    public
    @ResponseBody
    FieldOfStudy addFieldOfStudy(
            @RequestBody FieldOfStudyRequest fieldOfStudyRequest) {
        ParamaterCheck.mandatory("Naziv oblasti", fieldOfStudyRequest.getName());
        return fieldOfStudyService.addFieldOfStudy(fieldOfStudyRequest.getName());
    }

    // UPDATE
    @RequestMapping(method = RequestMethod.PUT, value = "/{fieldOfStudyID}")
    public
    @ResponseBody
    FieldOfStudy updateFieldOfStudy(@PathVariable("fieldOfStudyID") Long fieldOfStudyID,
                                    @RequestBody FieldOfStudyRequest fieldOfStudyRequest) {
        FieldOfStudy fieldOfStudy = fieldOfStudyService.getFieldOfStudy(fieldOfStudyID);
        if (fieldOfStudy == null) {
            throw new InvalidArgumentException("Oblast sa id-em " + fieldOfStudyID
                    + " ne postoji u bazi!");
        }
        if (fieldOfStudyRequest.getName() != null) {
            fieldOfStudy.setName(fieldOfStudyRequest.getName());
        }
        return fieldOfStudyService.updateFieldOfStudy(fieldOfStudy);

    }

    // DELETE
    @RequestMapping(method = RequestMethod.DELETE, value = "/{fieldOfStudyID}")
    public void deleteTag(@PathVariable("fieldOfStudyID") Long fieldOfStudyID) {
        fieldOfStudyService.removeFieldOfStudy(fieldOfStudyID);
    }
}
