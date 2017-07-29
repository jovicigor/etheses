package rs.fon.pzr.rest.resources.thesis.operations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.fon.pzr.core.exception.InvalidArgumentException;
import rs.fon.pzr.core.service.*;
import rs.fon.pzr.model.thesis.*;
import rs.fon.pzr.rest.model.request.ThesisRequest;
import rs.fon.pzr.rest.model.util.RestFactory;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;

import static rs.fon.pzr.model.thesis.Keyword.createNotBannedKeyword;

@Service
public class UpdateThesisOperation {

    private final ThesisService thesisService;
    private final UserService userService;
    private final CourseService courseService;
    private final TagService tagService;
    private final FieldOfStudyService fieldOfStudyService;
    private final KeywordService keywordService;

    @Autowired
    public UpdateThesisOperation(ThesisService thesisService, UserService userService, CourseService courseService, TagService tagService, FieldOfStudyService fieldOfStudyService, KeywordService keywordService) {
        this.thesisService = thesisService;
        this.userService = userService;
        this.courseService = courseService;
        this.tagService = tagService;
        this.fieldOfStudyService = fieldOfStudyService;
        this.keywordService = keywordService;
    }

    public Thesis execute(ThesisRequest thesisRequest, Long thesisID) {
        Thesis thesis = thesisService.getThesis(thesisID)
                .orElseThrow(() -> new InvalidArgumentException("Predmet sa id-em " + thesisID
                        + " ne postoji u bazi!"));

        thesisRequest.getGrade()
                .ifPresent(thesis::setGrade);
        thesisRequest.getDefenseDate()
                .ifPresent(thesis::setDefenseDate);
        thesisRequest.getCourseName()
                .flatMap(courseService::getCourseByName)
                .ifPresent(thesis::setCourse);
        thesisRequest.getUserId()
                .flatMap(userService::getUser)
                .ifPresent(thesis::setUser);
        thesisRequest.getDescription()
                .ifPresent(thesis::setDescription);
        thesisRequest.getUserEmail()
                .ifPresent(thesis::setUserEmail);
        thesisRequest.getUserName()
                .ifPresent(thesis::setUserName);
        thesisRequest.getMentorEmail()
                .ifPresent(thesis::setMentorEmail);
        thesisRequest.getMentorName()
                .ifPresent(thesis::setMentorName);
        thesisRequest.getMentorId()
                .flatMap(userService::getUser)
                .ifPresent(thesis::setMentor);

        thesisRequest.getDescription()
                .filter(description -> !description.isEmpty())
                .map(keywordService::extractWordsWithCount)
                .orElse(new HashMap<>())
                .forEach((word, keywordFrequency) -> {
                    Keyword keyword = createNotBannedKeyword(word);
                    keyword = keywordService.addKeyword(keyword);
                    thesis.addKeyword(keyword, keywordFrequency);
                });

        if (thesisRequest.getTags() != null) {
            Set<Tag> tagList = thesisRequest.getTags()
                    .stream()
                    .map(tagService::addTag)
                    .collect(Collectors.toSet());
            thesis.setTags(tagList);
        }
        if (thesisRequest.getFieldsOfStudy() != null) {
            Set<FieldOfStudy> fieldOfStudiesList = thesisRequest.getFieldsOfStudy().stream()
                    .map(fieldOfStudyService::addFieldOfStudy)
                    .collect(Collectors.toSet());
            thesis.setFieldOfStudies(fieldOfStudiesList);
        }

        return thesis;
    }
}
