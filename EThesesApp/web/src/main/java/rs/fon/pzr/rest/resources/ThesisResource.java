package rs.fon.pzr.rest.resources;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import rs.fon.pzr.core.exception.InvalidArgumentException;
import rs.fon.pzr.core.exception.InvalidTicketException;
import rs.fon.pzr.core.service.*;
import rs.fon.pzr.core.service.util.ParamaterCheck;
import rs.fon.pzr.model.*;
import rs.fon.pzr.rest.model.request.ThesisCommentRequest;
import rs.fon.pzr.rest.model.request.ThesisRequest;
import rs.fon.pzr.rest.model.response.level1.ThesisCommentResponseLevel1;
import rs.fon.pzr.rest.model.response.level1.ThesisPageResponse;
import rs.fon.pzr.rest.model.response.level1.ThesisResponseLevel1;
import rs.fon.pzr.rest.model.util.RestFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLConnection;
import java.util.*;
import java.util.stream.Collectors;

import static rs.fon.pzr.model.KeywordEntity.createNotBannedKeyword;

@RestController
@RequestMapping(value = "/theses")
public class ThesisResource {

    private final Logger logger = Logger.getLogger(ThesisResource.class);
    private final ThesisService thesisService;
    private final UserService userService;
    private final CourseService courseService;
    private final TagService tagService;
    private final FieldOfStudyService fieldOfStudyService;
    private final KeywordService keywordService;

    @Autowired
    public ThesisResource(KeywordService keywordService, UserService userService, TagService tagService, ThesisService thesisService, CourseService courseService, FieldOfStudyService fieldOfStudyService) {
        this.keywordService = keywordService;
        this.userService = userService;
        this.tagService = tagService;
        this.thesisService = thesisService;
        this.courseService = courseService;
        this.fieldOfStudyService = fieldOfStudyService;
    }

    @GetMapping
    @ResponseBody
    public List<ThesisResponseLevel1> getThesises(@RequestParam(value = "userID", required = false) Long userID) {

        if (userID != null) {
            List<ThesisEntity> userThesis = thesisService.getThesisByUserId(userID);
            return userThesis.stream()
                    .map(RestFactory::createThesisResponseLevel1)
                    .collect(Collectors.toList());
        }
        List<ThesisEntity> thesisList = thesisService.getAllThesis();

        return thesisList.stream()
                .map(RestFactory::createThesisResponseLevel1)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/advanced_search")
    @ResponseBody
    public ThesisPageResponse advancedSearch(
            @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "thesisName", required = false) String thesisName,
            @RequestParam(value = "tagValues", required = false) List<String> tagValues,
            @RequestParam(value = "matchLimit", required = false) Long matchLimit,
            @RequestParam(value = "courseName", required = false) String courseName,
            @RequestParam(value = "studiesName", required = false) String studiesName,
            @RequestParam(value = "sortField", required = false) String sortField,
            @RequestParam(value = "fieldValues", required = false) List<String> fieldValues,
            @RequestParam(value = "fieldMatchLimit", required = false) Long fieldMatchLimit,
            @RequestParam(value = "descriptionKeys", required = false) List<String> descriptioinKeys,
            @RequestParam(value = "descriptionMatchLimit", required = false) Long descriptionMatchLimit) {
        Page<ThesisEntity> thesisPage = thesisService.advancedSearch(pageNumber,
                pageSize, thesisName, tagValues,
                matchLimit, courseName, studiesName,
                sortField, fieldValues, fieldMatchLimit, descriptioinKeys, descriptionMatchLimit);

        return RestFactory.CreateThesisPageResponse(thesisPage);
    }

    @GetMapping(value = "/{thesisID}")
    @ResponseBody
    public ThesisResponseLevel1 getThesis(@PathVariable("thesisID") Long thesisId) {
        Optional<ThesisEntity> thesis = thesisService.getThesis(thesisId);

        return thesis.map(RestFactory::createThesisResponseLevel1)
                .orElse(null);
    }

    @PostMapping
    @ResponseBody
    public ThesisResponseLevel1 addThesis(
            @RequestBody ThesisRequest thesisRequest) {
        ParamaterCheck.mandatory("Naziv rada", thesisRequest.getName());

        ThesisEntity thesis = new ThesisEntity();
        thesis.setName(thesisRequest.getName());
        thesis.setDatePosted(new Date());
        thesis.setDefenseDate(thesisRequest.getDefenseDate());
        String description = thesisRequest.getDescription();
        thesis.setDescription(description);
        if (description != null && !description.isEmpty()) {
            Map<String, Integer> keywords = keywordService
                    .extractWordsWithCount(thesisRequest.getDescription());

            for (Map.Entry<String, Integer> entry : keywords.entrySet()) {
                KeywordEntity keyword = createNotBannedKeyword(entry.getKey());
                // add or return existing
                keyword = keywordService.addKeyword(keyword);

                ThesisKeywordEntity thesisKeywod = new ThesisKeywordEntity();
                thesisKeywod.setCount(entry.getValue());
                thesisKeywod.setKeyword(keyword);
                thesisKeywod.setThesis(thesis);
                thesis.getThesisKeywords().add(thesisKeywod);
            }
        }
        thesis.setGrade(thesisRequest.getGrade());
        thesis.setUserEmail(thesisRequest.getUserEmail());
        thesis.setUserName(thesisRequest.getUserName());
        thesis.setMentorEmail(thesisRequest.getMentorEmail());
        thesis.setMentorName(thesisRequest.getMentorName());
        String courseName = thesisRequest.getCourseName();
        if (courseName != null) {
            courseService.getCourseByName(courseName)
                    .ifPresent(thesis::setCourse);
        }
        if (thesisRequest.getUserId() != null) {
            userService.getUser(thesisRequest.getUserId())
                    .ifPresent(thesis::setUser);
        }
        if (thesisRequest.getTags() != null) {
            Set<TagEntity> tagList = thesisRequest.getTags().stream()
                    .map(tagService::addTag)
                    .collect(Collectors.toSet());
            thesis.setTags(tagList);
        }
        if (thesisRequest.getFieldsOfStudy() != null) {
            Set<FieldOfStudyEntity> fieldOfStudiesList = thesisRequest.getFieldsOfStudy().stream()
                    .map(fieldOfStudyService::addFieldOfStudy)
                    .collect(Collectors.toSet());
            thesis.setFieldOfStudies(fieldOfStudiesList);
        }
        if (thesisRequest.getMentorId() != null) {
            userService.getUser(thesisRequest.getMentorId())
                    .ifPresent(thesis::setMentor);
        }
        return RestFactory
                .createThesisResponseLevel1(thesisService.addThesis(thesis));
    }

    @PutMapping(value = "/{thesisID}")
    @ResponseBody
    public ThesisResponseLevel1 updateThesis(
            @RequestBody ThesisRequest thesisRequest,
            @PathVariable("thesisID") Long thesisID) {

        ThesisEntity thesis = thesisService.getThesis(thesisID)
                .orElseThrow(() -> new InvalidArgumentException("Predmet sa id-em " + thesisID
                        + " ne postoji u bazi!"));

        if (thesisRequest.getDefenseDate() != null) {
            thesis.setDefenseDate(thesisRequest.getDefenseDate());
        }
        String description = thesisRequest.getDescription();
        if (description != null && !description.equals(thesis.getDescription())) {
            thesis.setDescription(description);
            thesis.getThesisKeywords().clear();
            if (!description.isEmpty()) {
                Map<String, Integer> keywords = keywordService
                        .extractWordsWithCount(description);

                for (Map.Entry<String, Integer> entry : keywords.entrySet()) {
                    KeywordEntity keyword = createNotBannedKeyword(entry.getKey());
                    // added or returned existing
                    keyword = keywordService.addKeyword(keyword);

                    ThesisKeywordEntity thesisKeywod = new ThesisKeywordEntity();
                    thesisKeywod.setCount(entry.getValue());
                    thesisKeywod.setKeyword(keyword);
                    thesisKeywod.setThesis(thesis);
                    thesis.getThesisKeywords().add(thesisKeywod);
                }
            }
        }
        if (thesisRequest.getGrade() != null) {
            thesis.setGrade(thesisRequest.getGrade());
        }
        if (thesisRequest.getUserEmail() != null) {
            thesis.setUserEmail(thesisRequest.getUserEmail());
        }
        if (thesisRequest.getUserName() != null) {
            thesis.setUserName(thesisRequest.getUserName());
        }
        if (thesisRequest.getMentorEmail() != null) {
            thesis.setMentorEmail(thesisRequest.getMentorEmail());
        }
        if (thesisRequest.getMentorName() != null) {
            thesis.setMentorName(thesisRequest.getMentorName());
        }
        if (thesisRequest.getMentorId() != null) {
            userService.getUser(thesisRequest.getMentorId())
                    .ifPresent(thesis::setMentor);
        }
        if (thesisRequest.getTags() != null) {
            Set<TagEntity> tagList = thesisRequest.getTags()
                    .stream()
                    .map(tagService::addTag)
                    .collect(Collectors.toSet());
            thesis.setTags(tagList);
        }
        if (thesisRequest.getFieldsOfStudy() != null) {
            Set<FieldOfStudyEntity> fieldOfStudiesList = thesisRequest.getFieldsOfStudy().stream()
                    .map(fieldOfStudyService::addFieldOfStudy)
                    .collect(Collectors.toSet());
            thesis.setFieldOfStudies(fieldOfStudiesList);
        }
        if (thesisRequest.getUserId() != null) {
            userService.getUser(thesisRequest.getUserId())
                    .ifPresent(thesis::setUser);
        }
        if (thesisRequest.getName() != null) {
            thesis.setName(thesisRequest.getName());
        }
        String courseName = thesisRequest.getCourseName();
        if (courseName != null) {
            courseService.getCourseByName(courseName)
                    .ifPresent(thesis::setCourse);
        }

        return RestFactory.createThesisResponseLevel1(thesisService
                .updateThesis(thesis));
    }

    @DeleteMapping(value = "/{thesisID}")
    public void removeThesis(@PathVariable("thesisID") Long thesisID) {
        thesisService.removeThesis(thesisID);
    }

    @GetMapping(value = "/{thesisID}/comments")
    @ResponseBody
    public Set<ThesisCommentResponseLevel1> getComments(
            @PathVariable("thesisID") Long thesisId) {
        Set<ThesisComment> thesisComments = thesisService
                .getAllComments(thesisId);
        return thesisComments.stream()
                .map(RestFactory::createThesisCommentResponseLevel1)
                .collect(Collectors.toSet());
    }

    @PostMapping(value = "/{thesisID}/comments")
    @ResponseBody
    public ThesisCommentResponseLevel1 createComment(@PathVariable("thesisID") Long thesisId,
                                                     @RequestBody ThesisCommentRequest thesisCommentRequest) {
        String message = thesisCommentRequest.getMessage()
                .orElseThrow(() -> new InvalidArgumentException("message je obavezno polje!"));

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = user.getUsername();
        UserEntity loggedInUser = userService.getUser(email)
                .orElseThrow(() -> new InvalidTicketException("Must provide a valid ticket!"));

        ThesisEntity thesis = thesisService.getThesis(thesisId)
                .orElseThrow(() -> new InvalidArgumentException("THesis doesn't exist"));
        ThesisComment thesisComment = new ThesisComment(message, new Date(), loggedInUser, thesis);

        ThesisComment newComment = thesisService.addComment(thesisComment);
        return RestFactory.createThesisCommentResponseLevel1(newComment);
    }

    @DeleteMapping(value = "/comments/{commentID}")
    public void removeComment(@PathVariable("commentID") Long commentID) {
        thesisService.removeComment(commentID);
    }

    @GetMapping(value = "/files")
    @ResponseBody
    public Set<TFileEntity> getAllFileRecords() {
        return thesisService.getAllFileRecords();
    }

    @GetMapping(value = "/files/{fileID}/download")
    public void downloadFileById(HttpServletResponse response,
                                 @PathVariable("fileID") Long fileID) throws IOException {
        File file = thesisService.getFileById(fileID);

        String mimeType = URLConnection
                .guessContentTypeFromName(file.getName());
        if (mimeType == null) {
            logger.debug("mimetype is not detectable, will take default");
            mimeType = "application/octet-stream";
        }
        logger.debug("mimetype : " + mimeType);
        response.setContentType(mimeType);

		/*
         * "Content-Disposition : inline" will show viewable types [like
		 * images/text/pdf/anything viewable by browser] right on browser while
		 * others(zip e.g) will be directly downloaded [may provide save as
		 * popup, based on your browser setting.]
		 */
        response.setHeader("Content-Disposition",
                "inline; filename=\"" + file.getName() + "\"");
        /*
         * "Content-Disposition : attachment" will be directly download, may
		 * provide save as popup, based on your browser setting
		 */
        // response.setHeader("Content-Disposition",
        // String.format("attachment; filename=\"%s\"", file.getName()));
        response.setContentLength((int) file.length());
        InputStream inputStream = new BufferedInputStream(new FileInputStream(
                file));
        // Copy bytes from source to destination(outputstream in this example),
        // closes both streams.
        FileCopyUtils.copy(inputStream, response.getOutputStream());
    }

    @DeleteMapping(value = "/files/{fileID}")
    public void removeFileById(@PathVariable("fileID") Long fileID) {
        thesisService.removeFile(fileID);
    }

    @PostMapping(value = "/{thesisID}/upload")
    public TFileEntity uploadThesisFile(@PathVariable("thesisID") Long thesisID,
                                        @RequestParam("file") MultipartFile file) {
        return thesisService.addFile(thesisID, file);
    }

    @GetMapping(value = "/{thesisID}/download")
    public void downloadThesisFile(HttpServletResponse response,
                                   @PathVariable("thesisID") Long thesisID) throws IOException {

        File file = thesisService.getThesisFile(thesisID);

        String mimeType = URLConnection
                .guessContentTypeFromName(file.getName());
        if (mimeType == null) {
            logger.debug("mimetype is not detectable, will take default");
            mimeType = "application/octet-stream";
        }
        logger.debug("mimetype : " + mimeType);
        response.setContentType(mimeType);

		/*
         * "Content-Disposition : inline" will show viewable types [like
		 * images/text/pdf/anything viewable by browser] right on browser while
		 * others(zip e.g) will be directly downloaded [may provide save as
		 * popup, based on your browser setting.]
		 */
        response.setHeader("Content-Disposition",
                "inline; filename=\"" + file.getName() + "\"");
        /*
         * "Content-Disposition : attachment" will be directly download, may
		 * provide save as popup, based on your browser setting
		 */
        // response.setHeader("Content-Disposition",
        // String.format("attachment; filename=\"%s\"", file.getName()));
        response.setContentLength((int) file.length());
        InputStream inputStream = new BufferedInputStream(new FileInputStream(
                file));
        // Copy bytes from source to destination(outputstream in this example),
        // closes both streams.
        FileCopyUtils.copy(inputStream, response.getOutputStream());
    }
}