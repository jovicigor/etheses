package rs.fon.pzr.web.rest.resources.thesis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import rs.fon.pzr.core.domain.model.thesis.TFile;
import rs.fon.pzr.core.domain.model.thesis.Thesis;
import rs.fon.pzr.core.domain.model.thesis.ThesisComment;
import rs.fon.pzr.core.domain.model.thesis.ThesisCommentBuilder;
import rs.fon.pzr.core.domain.model.user.UserEntity;
import rs.fon.pzr.core.exception.InvalidArgumentException;
import rs.fon.pzr.core.service.ThesisService;
import rs.fon.pzr.core.service.UserService;
import rs.fon.pzr.core.service.page.ThesisPage;
import rs.fon.pzr.web.rest.exception.InvalidTicketException;
import rs.fon.pzr.web.rest.model.request.ThesisCommentRequest;
import rs.fon.pzr.web.rest.model.request.ThesisRequest;
import rs.fon.pzr.web.rest.model.response.level1.ThesisCommentResponseLevel1;
import rs.fon.pzr.web.rest.model.response.level1.ThesisPageResponse;
import rs.fon.pzr.web.rest.model.response.level1.ThesisResponseLevel1;
import rs.fon.pzr.web.rest.model.util.RestFactory;
import rs.fon.pzr.web.rest.resources.thesis.operations.CreateThesisOperation;
import rs.fon.pzr.web.rest.resources.thesis.operations.UpdateThesisOperation;
import rs.fon.pzr.web.rest.util.ParamaterCheck;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.security.Principal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/theses")
public class ThesisResource {

    private final ThesisService thesisService;
    private final UserService userService;

    private final CreateThesisOperation createThesisOperation;
    private final UpdateThesisOperation updateThesisOperation;

    @Autowired
    public ThesisResource(UserService userService, ThesisService thesisService,
                          CreateThesisOperation createThesisOperation, UpdateThesisOperation updateThesisOperation) {
        this.userService = userService;
        this.thesisService = thesisService;
        this.createThesisOperation = createThesisOperation;
        this.updateThesisOperation = updateThesisOperation;
    }

    @GetMapping
    @ResponseBody
    public List<ThesisResponseLevel1> getThesises(@RequestParam(value = "userID", required = false) Long userID) {
        if (userID != null) {
            List<Thesis> userThesis = thesisService.getThesisByUserId(userID);
            return userThesis.stream()
                    .map(RestFactory::createThesisResponseLevel1)
                    .collect(Collectors.toList());
        }
        List<Thesis> thesisList = thesisService.getAllThesis();

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
        ThesisPage thesisPage = thesisService.advancedSearch(pageNumber,
                pageSize, thesisName, tagValues,
                matchLimit, courseName, studiesName,
                sortField, fieldValues, fieldMatchLimit, descriptioinKeys, descriptionMatchLimit);

        return new ThesisPageResponse(thesisPage);
    }

    @GetMapping(value = "/{thesisID}")
    @ResponseBody
    public ThesisResponseLevel1 getThesis(@PathVariable("thesisID") Long thesisId) {
        Optional<Thesis> thesis = thesisService.getThesis(thesisId);

        return thesis.map(RestFactory::createThesisResponseLevel1).orElse(null);
    }

    @PostMapping
    @ResponseBody
    public ThesisResponseLevel1 addThesis(@RequestBody ThesisRequest thesisRequest) {
        ParamaterCheck.mandatory("Naziv rada", thesisRequest.getName());

        Thesis thesis = createThesisOperation.execute(thesisRequest);

        return RestFactory.createThesisResponseLevel1(thesis);
    }


    @PutMapping(value = "/{thesisID}")
    @ResponseBody
    public ThesisResponseLevel1 updateThesis(@RequestBody ThesisRequest thesisRequest,
                                             @PathVariable("thesisID") Long thesisID) {

        Thesis thesis = updateThesisOperation.execute(thesisRequest, thesisID);
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
                                                     @RequestBody ThesisCommentRequest thesisCommentRequest,
                                                     Principal principal) {
        String message = thesisCommentRequest.getMessage()
                .orElseThrow(() -> new InvalidArgumentException("message je obavezno polje!"));

        String email = principal.getName();
        UserEntity loggedInUser = userService.getUser(email)
                .orElseThrow(() -> new InvalidTicketException("Must provide a valid ticket!"));

        Thesis thesis = thesisService.getThesis(thesisId)
                .orElseThrow(() -> new InvalidArgumentException("THesis doesn't exist"));

        ThesisComment thesisComment = new ThesisCommentBuilder()
                .withMessage(message)
                .withAuthor(loggedInUser)
                .withThesis(thesis)
                .build();

        ThesisComment newComment = thesisService.addComment(thesisComment);
        return RestFactory.createThesisCommentResponseLevel1(newComment);
    }

    @DeleteMapping(value = "/comments/{commentID}")
    public void removeComment(@PathVariable("commentID") Long commentID,
                              Principal principal) {
        String email = principal.getName();
        UserEntity loggedInUser = userService.getUser(email)
                .orElseThrow(() -> new InvalidTicketException("not logged in"));
        ThesisComment thesisComment = thesisService.getComment(commentID);
        if (!loggedInUser.isAdmin() && !Objects.equals(thesisComment.getAuthor().getId(), loggedInUser.getId())) {
            throw new InvalidArgumentException(
                    "Morate biti ulogovani da bi ste mogli obrisati komentar!");

        }
        thesisService.removeComment(commentID);
    }

    @GetMapping(value = "/files")
    @ResponseBody
    public Set<TFile> getAllFileRecords() {
        return thesisService.getAllFileRecords();
    }

    @GetMapping(value = "/files/{fileID}/download")
    public void downloadFileById(HttpServletResponse response, @PathVariable("fileID") Long fileID) {
        try {
            File file = thesisService.getFileById(fileID);

            addFileToResponse(response, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping(value = "/{thesisID}/download")
    public void downloadThesisFile(HttpServletResponse response, @PathVariable("thesisID") Long thesisID) {
        try {
            File file = thesisService.getThesisFile(thesisID);
            addFileToResponse(response, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @DeleteMapping(value = "/files/{fileID}")
    public void removeFileById(@PathVariable("fileID") Long fileID) {
        thesisService.removeFile(fileID);
    }

    @PostMapping(value = "/{thesisID}/upload")
    public TFile uploadThesisFile(@PathVariable("thesisID") Long thesisID,
                                  @RequestParam("file") MultipartFile file) {
        return thesisService.addFile(thesisID, file);
    }


    private void addFileToResponse(HttpServletResponse response, File file) throws IOException {
        String mimeType = URLConnection.guessContentTypeFromName(file.getName());
        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }
        response.setContentType(mimeType);
        response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
        response.setContentLength((int) file.length());

        BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file));
        FileCopyUtils.copy(inputStream, response.getOutputStream());
    }
}