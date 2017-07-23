package rs.fon.pzr.rest.resources;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import rs.fon.pzr.core.exception.InvalidArgumentException;
import rs.fon.pzr.core.model.*;
import rs.fon.pzr.core.service.*;
import rs.fon.pzr.core.service.util.ParamaterCheck;
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

    // READ
    @RequestMapping(method = RequestMethod.GET)
    public
    @ResponseBody
    List<ThesisResponseLevel1> getThesises(
            @RequestParam(value = "userID", required = false) Long userID) {
        List<ThesisResponseLevel1> thesisResponseList = new ArrayList<>();

        if (userID != null) {
            List<Thesis> userThesis = thesisService.getThesisByUserId(userID);
            for (Thesis thesis : userThesis) {
                thesisResponseList.add(RestFactory
                        .createThesisResponseLevel1(thesis));
            }
            return thesisResponseList;
        }

        List<Thesis> thesisList = thesisService.getAllThesis();

        for (Thesis thesis : thesisList) {
            thesisResponseList.add(RestFactory
                    .createThesisResponseLevel1(thesis));
        }
        return thesisResponseList;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/advanced_search")
    public
    @ResponseBody
    ThesisPageResponse advancedSearch(
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
        Page<Thesis> thesisPage = thesisService.advancedSearch(pageNumber,
                pageSize, thesisName, tagValues, matchLimit, courseName,
                studiesName, sortField, fieldValues, fieldMatchLimit, descriptioinKeys, descriptionMatchLimit);
        return RestFactory.CreateThesisPageResponse(thesisPage);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{thesisID}")
    public
    @ResponseBody
    ThesisResponseLevel1 getThesis(
            @PathVariable("thesisID") Long thesisId) {
        Thesis thesis = thesisService.getThesis(thesisId);
        thesis.setViewCount(thesis.getViewCount() + 1);
        thesis = thesisService.updateThesis(thesis);
        return RestFactory.createThesisResponseLevel1(thesis);
    }

    // CREATE
    @RequestMapping(method = RequestMethod.POST)
    public
    @ResponseBody
    ThesisResponseLevel1 addThesis(
            @RequestBody ThesisRequest thesisRequest) {
        ParamaterCheck.mandatory("Naziv rada", thesisRequest.getName());

        Thesis thesis = new Thesis();
        thesis.setName(thesisRequest.getName());
        thesis.setDatePosted(new Date());
        thesis.setDefenseDate(thesisRequest.getDefenseDate());
        String description = thesisRequest.getDescription();
        thesis.setDescription(description);
        if (description != null && !description.isEmpty()) {
            Map<String, Integer> keywords = keywordService
                    .extractWordsWithCount(thesisRequest.getDescription());

            for (Map.Entry<String, Integer> entry : keywords.entrySet()) {
                Keyword keyword = new Keyword();
                keyword.setValue(entry.getKey());
                // add or return existing
                keyword = keywordService.addKeyword(keyword);

                ThesisKeyword thesisKeywod = new ThesisKeyword();
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
        if (thesisRequest.getCourseName() != null) {
            Course course = courseService.getCourseByName(thesisRequest
                    .getCourseName());
            thesis.setCourse(course);
        }
        if (thesisRequest.getUserId() != null) {
            User user1 = userService.getUser(thesisRequest.getUserId());
            thesis.setUser(user1);
        }
        if (thesisRequest.getTags() != null) {
            Set<Tag> tagList = new HashSet<>();
            for (String tagValue : thesisRequest.getTags()) {
                tagList.add(tagService.addTag(tagValue));
            }
            thesis.setTags(tagList);
        }
        if (thesisRequest.getFieldsOfStudy() != null) {
            Set<FieldOfStudy> fieldOfStudiesList = new HashSet<>();
            for (String fieldOfStudyName : thesisRequest.getFieldsOfStudy()) {
                fieldOfStudiesList.add(fieldOfStudyService.addFieldOfStudy(fieldOfStudyName));
            }
            thesis.setFieldOfStudies(fieldOfStudiesList);
        }
        if (thesisRequest.getMentorId() != null) {
            User mentor = userService.getUser(thesisRequest.getMentorId());
            thesis.setMentor(mentor);
        }
        return RestFactory
                .createThesisResponseLevel1(thesisService.addThesis(thesis));
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{thesisID}")
    public
    @ResponseBody
    ThesisResponseLevel1 updateThesis(
            @RequestBody ThesisRequest thesisRequest,
            @PathVariable("thesisID") Long thesisID) {

        Thesis thesis = thesisService.getThesis(thesisID);
        if (thesis == null) {
            throw new InvalidArgumentException("Predmet sa id-em " + thesisID
                    + " ne postoji u bazi!");
        }

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
                    Keyword keyword = new Keyword();
                    keyword.setValue(entry.getKey());
                    // added or returned existing
                    keyword = keywordService.addKeyword(keyword);

                    ThesisKeyword thesisKeywod = new ThesisKeyword();
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
            User mentor = userService.getUser(thesisRequest.getMentorId());
            thesis.setMentor(mentor);
        }
        if (thesisRequest.getTags() != null) {
            Set<Tag> tagList = new HashSet<>();
            for (String tagValue : thesisRequest.getTags()) {
                tagList.add(tagService.addTag(tagValue));
            }
            thesis.setTags(tagList);
        }
        if (thesisRequest.getFieldsOfStudy() != null) {
            Set<FieldOfStudy> fieldOfStudiesList = new HashSet<>();
            for (String fieldOfStudyName : thesisRequest.getFieldsOfStudy()) {
                fieldOfStudiesList.add(fieldOfStudyService.addFieldOfStudy(fieldOfStudyName));
            }
            thesis.setFieldOfStudies(fieldOfStudiesList);
        }
        if (thesisRequest.getUserId() != null) {
            User user = userService.getUser(thesisRequest.getUserId());
            thesis.setUser(user);
        }
        if (thesisRequest.getName() != null) {
            thesis.setName(thesisRequest.getName());
            if (thesis.getFile() != null) {
                thesis.getFile().setThesisName(thesisRequest.getName());
            }
        }
        if (thesisRequest.getCourseName() != null) {
            Course course = courseService.getCourseByName(thesisRequest
                    .getCourseName());
            thesis.setCourse(course);
        }

        return RestFactory.createThesisResponseLevel1(thesisService
                .updateThesis(thesis));
    }

    // DELETE
    @RequestMapping(method = RequestMethod.DELETE, value = "/{thesisID}")
    public void removeThesis(@PathVariable("thesisID") Long thesisID) {
        thesisService.removeThesis(thesisID);
    }

    // COMMENT
    // READ
    @RequestMapping(method = RequestMethod.GET, value = "/{thesisID}/comments")
    public
    @ResponseBody
    Set<ThesisCommentResponseLevel1> getComments(
            @PathVariable("thesisID") Long thesisId) {
        Set<ThesisComment> thesisComments = thesisService
                .getAllComments(thesisId);
        Set<ThesisCommentResponseLevel1> thesisCommentResponses = new HashSet<>();
        for (ThesisComment thesisComment : thesisComments) {
            thesisCommentResponses.add(RestFactory
                    .createThesisCommentResponseLevel1(thesisComment));
        }
        return thesisCommentResponses;
    }

    // CREATE
    @RequestMapping(method = RequestMethod.POST, value = "/{thesisID}/comments")
    public
    @ResponseBody
    ThesisCommentResponseLevel1 createComment(
            @PathVariable("thesisID") Long thesisId,
            @RequestBody ThesisCommentRequest thesisCommentRequest) {
        ParamaterCheck.mandatory("Sadržaj komentara",
                thesisCommentRequest.getMessage());

        ThesisComment thesisComment = new ThesisComment();

        Thesis thesis = thesisService.getThesis(thesisId);
        thesisComment.setThesis(thesis);
        thesisComment.setMessage(thesisCommentRequest.getMessage());
        thesisComment.setDatePosted(new Date());
        return RestFactory.createThesisCommentResponseLevel1(thesisService
                .addComment(thesisComment));
    }

    // DELETE
    @RequestMapping(method = RequestMethod.DELETE, value = "/comments/{commentID}")
    public void removeComment(@PathVariable("commentID") Long commentID) {
        thesisService.removeComment(commentID);
    }

    // FILE
    @RequestMapping(value = "/files", method = RequestMethod.GET)
    public
    @ResponseBody
    Set<TFile> getAllFileRecords() {
        return thesisService.getAllFileRecords();
    }

    // FILE
    @RequestMapping(value = "/files/{fileID}/download", method = RequestMethod.GET)
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

    @RequestMapping(value = "/files/{fileID}", method = RequestMethod.DELETE)
    public void removeFileById(@PathVariable("fileID") Long fileID) {
        thesisService.removeFile(fileID);
    }

    @RequestMapping(value = "/{thesisID}/upload", method = RequestMethod.POST)
    public TFile uploadThesisFile(@PathVariable("thesisID") Long thesisID,
                                  @RequestParam("file") MultipartFile file) {
        return thesisService.addFile(thesisID, file);
    }

    // TODO: Consider deleting endpoint(same purpose as downloadFileById)
    @RequestMapping(value = "/{thesisID}/download", method = RequestMethod.GET)
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