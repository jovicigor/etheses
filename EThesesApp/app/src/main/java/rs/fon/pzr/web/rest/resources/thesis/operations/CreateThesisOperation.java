package rs.fon.pzr.web.rest.resources.thesis.operations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.fon.pzr.core.service.*;
import rs.fon.pzr.core.domain.model.thesis.*;
import rs.fon.pzr.web.rest.model.request.ThesisRequest;
import rs.fon.pzr.core.domain.type.Email;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;

import static rs.fon.pzr.core.domain.model.thesis.Keyword.createNotBannedKeyword;

@Service
public class CreateThesisOperation {

    private final ThesisService thesisService;
    private final UserService userService;
    private final CourseService courseService;
    private final TagService tagService;
    private final FieldOfStudyService fieldOfStudyService;
    private final KeywordService keywordService;

    @Autowired
    public CreateThesisOperation(ThesisService thesisService, UserService userService, CourseService courseService, TagService tagService, FieldOfStudyService fieldOfStudyService, KeywordService keywordService) {
        this.thesisService = thesisService;
        this.userService = userService;
        this.courseService = courseService;
        this.tagService = tagService;
        this.fieldOfStudyService = fieldOfStudyService;
        this.keywordService = keywordService;
    }

    public Thesis execute(ThesisRequest thesisRequest) {
        ThesisBuilder thesisBuilder = new ThesisBuilder();

        thesisBuilder.withName(thesisRequest.getName());
        thesisRequest.getGrade()
                .ifPresent(thesisBuilder::withGrade);
        thesisRequest.getDefenseDate()
                .ifPresent(thesisBuilder::withDefenseDate);
        thesisRequest.getCourseName()
                .flatMap(courseService::getCourseByName)
                .ifPresent(thesisBuilder::withCourse);
        thesisRequest.getUserId()
                .flatMap(userService::getUser)
                .ifPresent(thesisBuilder::withUser);
        thesisRequest.getDescription()
                .ifPresent(thesisBuilder::withDescription);
        thesisRequest.getUserEmail()
                .filter(Email::isValid)
                .map(Email::fromString)
                .ifPresent(thesisBuilder::withUserEmail);
        thesisRequest.getUserName()
                .ifPresent(thesisBuilder::withUserName);
        thesisRequest.getMentorEmail()
                .filter(Email::isValid)
                .map(Email::fromString)
                .ifPresent(thesisBuilder::withMentorEmail);
        thesisRequest.getMentorName()
                .ifPresent(thesisBuilder::withMentorName);
        thesisRequest.getMentorId()
                .flatMap(userService::getUser)
                .ifPresent(thesisBuilder::withMentor);

        if (thesisRequest.getTags() != null) {
            Set<Tag> tags = thesisRequest.getTags().stream()
                    .map(tagService::addTag)
                    .collect(Collectors.toSet());
            thesisBuilder.withTags(tags);
        }
        if (thesisRequest.getFieldsOfStudy() != null) {
            Collection<FieldOfStudy> fieldOfStudiesList = thesisRequest.getFieldsOfStudy().stream()
                    .map(fieldOfStudyService::addFieldOfStudy)
                    .collect(Collectors.toSet());
            thesisBuilder.withFieldOfStudies(fieldOfStudiesList);
        }
        Thesis thesis = thesisBuilder.build();
        thesisRequest.getDescription()
                .filter(description -> !description.isEmpty())
                .map(keywordService::extractWordsWithCount)
                .orElse(new HashMap<>())
                .forEach((word, keywordFrequency) -> {
                    Keyword keyword = createNotBannedKeyword(word);
                    keyword = keywordService.addKeyword(keyword);
                    thesis.addKeyword(keyword, keywordFrequency);
                });

        return thesisService.addThesis(thesis);
    }
}
