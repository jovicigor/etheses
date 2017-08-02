package rs.fon.pzr.rest.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rs.fon.pzr.core.exception.InvalidArgumentException;
import rs.fon.pzr.core.service.FieldOfStudyService;
import rs.fon.pzr.core.domain.model.thesis.FieldOfStudy;
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

    @GetMapping
    @ResponseBody
    public Set<FieldOfStudy> getFieldOfStudies() {
        return fieldOfStudyService.getAllFieldsOfStudy();
    }

    @PostMapping
    @ResponseBody
    public FieldOfStudy addFieldOfStudy(@RequestBody FieldOfStudyRequest fieldOfStudyRequest) {
        String fieldName = fieldOfStudyRequest.getName()
                .orElseThrow(() -> new InvalidArgumentException("Naziv oblasti je obavezno polje!"));

        return fieldOfStudyService.addFieldOfStudy(fieldName);
    }

    @PutMapping(value = "/{fieldOfStudyID}")
    @ResponseBody
    public FieldOfStudy updateFieldOfStudy(@PathVariable("fieldOfStudyID") Long fieldOfStudyID,
                                           @RequestBody FieldOfStudyRequest fieldOfStudyRequest) {
        FieldOfStudy fieldOfStudy = fieldOfStudyService.getFieldOfStudy(fieldOfStudyID)
                .orElseThrow(() -> new InvalidArgumentException("Oblast sa id-em " + fieldOfStudyID
                        + " ne postoji u bazi!"));

        fieldOfStudyRequest.getName()
                .ifPresent(fieldOfStudy::setName);

        return fieldOfStudyService.updateFieldOfStudy(fieldOfStudy);
    }

    @DeleteMapping(value = "/{fieldOfStudyID}")
    public void deleteTag(@PathVariable("fieldOfStudyID") Long fieldOfStudyID) {
        fieldOfStudyService.removeFieldOfStudy(fieldOfStudyID);
    }
}
