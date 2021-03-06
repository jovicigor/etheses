package rs.fon.pzr.core.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import rs.fon.pzr.core.exception.InvalidArgumentException;
import rs.fon.pzr.core.exception.PzrRuntimeException;
import rs.fon.pzr.core.service.page.ThesisPage;
import rs.fon.pzr.core.service.repository.CommentRepository;
import rs.fon.pzr.core.service.repository.FileRepository;
import rs.fon.pzr.core.service.repository.ThesisRepository;
import rs.fon.pzr.core.domain.model.thesis.TFile;
import rs.fon.pzr.core.domain.model.thesis.Thesis;
import rs.fon.pzr.core.domain.model.thesis.ThesisComment;
import rs.fon.pzr.core.domain.model.user.UserEntity;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ThesisServiceImpl implements ThesisService {

    private final Logger logger = Logger.getLogger(ThesisServiceImpl.class);
    private static final int DEFAULT_PAGE_SIZE = 10;

    private String thesisFilesFolder;

    private final UserService userService;
    private final ThesisRepository thesisRepository;
    private final FileRepository fileRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public ThesisServiceImpl(UserService userService, ThesisRepository thesisRepository,
                             FileRepository fileRepository, CommentRepository commentRepository) {
        this.userService = userService;
        this.thesisRepository = thesisRepository;
        this.fileRepository = fileRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public Optional<Thesis> getThesis(Long id) {
        Thesis thesis = thesisRepository.findOne(id);
        thesis.updateViewCount();
        thesis = thesisRepository.save(thesis);
        return Optional.ofNullable(thesis);
    }

    @Override
    public List<Thesis> getThesisByUserId(Long userId) {
        UserEntity user = userService.getUser(userId)
                .orElseThrow(() -> new InvalidArgumentException("Korisnik sa id-em " + userId
                        + " ne postoji u bazi!."));
        return thesisRepository.findByUser(user);
    }

    @Override
    public List<Thesis> getAllThesis() {
        return thesisRepository.findAll();
    }

    @Override
    public ThesisPage advancedSearch(Integer pageNumber, Integer pageSize,
                                     String thesisName, List<String> tagValues, Long tagsMatchLimit,
                                     String courseName, String studiesName, String sortField,
                                     List<String> fieldValues, Long fieldsMatchLimit,
                                     List<String> descriptionKeys, Long descriptionKeyLimit) {
        if (pageSize == null) {
            if (pageNumber == null) {
                pageSize = thesisRepository.findAll().size();
                if (pageSize < 1) {
                    pageSize = 1;
                }
            } else {
                pageSize = DEFAULT_PAGE_SIZE;
            }
        }
        if (pageNumber == null) {
            pageNumber = 0;
        } else {
            pageNumber = pageNumber - 1;
        }
        if (thesisName == null) {
            thesisName = "%";
        } else {
            thesisName = "%" + thesisName + "%";
        }
        if (tagValues == null || tagValues.isEmpty()) {
            tagsMatchLimit = 0L;
        } else {
            if (tagsMatchLimit == null) {
                tagsMatchLimit = (long) tagValues.size();
            }
        }
        if (fieldValues == null || fieldValues.isEmpty()) {
            fieldsMatchLimit = 0L;
        } else {
            if (fieldsMatchLimit == null) {
                fieldsMatchLimit = (long) fieldValues.size();
            }
        }

        try {
            if (courseName != null && !Objects.equals(courseName, "")) {
                if (descriptionKeys == null || descriptionKeys.isEmpty()) {
                    return thesisRepository
                            .findByNameTagsFieldsAndCourse(pageNumber, pageSize, sortField,
                                    thesisName, tagValues,
                                    tagsMatchLimit, courseName, fieldValues,
                                    fieldsMatchLimit);
                }
                if (descriptionKeyLimit == null) {
                    descriptionKeyLimit = (long) descriptionKeys.size();
                }
                return thesisRepository
                        .findByNameTagsFieldsCourseAndDescription(pageNumber, pageSize, sortField,
                                thesisName, tagValues,
                                tagsMatchLimit, courseName, fieldValues,
                                fieldsMatchLimit, descriptionKeys,
                                descriptionKeyLimit);
            } else if (studiesName != null && !Objects.equals(studiesName, "")) {
                if (descriptionKeys == null || descriptionKeys.isEmpty()) {
                    return thesisRepository
                            .findByNameTagsFieldsAndStudies(
                                    pageNumber, pageSize, sortField, thesisName, tagValues,
                                    tagsMatchLimit, studiesName, fieldValues,
                                    fieldsMatchLimit);
                }
                if (descriptionKeyLimit == null) {
                    descriptionKeyLimit = (long) descriptionKeys.size();
                }
                return thesisRepository
                        .findByNameTagsFieldsStudiesAndDescription(
                                pageNumber, pageSize, sortField, thesisName, tagValues,
                                tagsMatchLimit, studiesName, fieldValues,
                                fieldsMatchLimit, descriptionKeys,
                                descriptionKeyLimit);
            } else {
                if (descriptionKeys == null || descriptionKeys.isEmpty()) {
                    return thesisRepository.findByNameTagsAndFields(
                            pageNumber, pageSize, sortField, thesisName, tagValues, tagsMatchLimit,
                            fieldValues, fieldsMatchLimit);
                }
                if (descriptionKeyLimit == null) {
                    descriptionKeyLimit = (long) descriptionKeys.size();
                }
                return thesisRepository
                        .findByNameTagsFieldsAndDescription(
                                pageNumber, pageSize, sortField, thesisName, tagValues,
                                tagsMatchLimit, fieldValues, fieldsMatchLimit,
                                descriptionKeys, descriptionKeyLimit);

            }
        } catch (Exception e) {
            logger.error(e);
            throw new InvalidArgumentException(
                    "Prosledjeni parametri nisu ispravni.");
        }
    }

    @Override
    @Transactional
    public Thesis addThesis(Thesis thesis) {
        if (thesisRepository.findByName(thesis.getName()) != null) {
            throw new InvalidArgumentException("Rad " + thesis.getName()
                    + "već postoji u bazi!");
        }
        return thesisRepository.save(thesis);
    }

    @Transactional
    @Override
    public Thesis updateThesis(Thesis thesis) {
        Thesis oldThesis = thesisRepository.findOne(thesis.getId());
        if (oldThesis == null) {
            throw new InvalidArgumentException("Rad sa id-em " + thesis.getId()
                    + " ne postoji u bazi!");
        }
        if (!thesis.getName().equals(oldThesis.getName())) {
            if (thesisRepository.findByName(thesis.getName()) != null) {
                throw new InvalidArgumentException("Rad " + thesis.getName()
                        + "već postoji u bazi!");
            }
        }
        return thesisRepository.save(thesis);
    }

    @Transactional
    @Override
    public void removeThesis(Long thesisId) {
        Thesis thesis = thesisRepository.findOne(thesisId);
        if (thesis == null) {
            throw new InvalidArgumentException("Rad sa id-em " + thesisId
                    + " ne postoji u bazi!");
        }
        thesisRepository.delete(thesis);
    }

    @Override
    public TFile addFile(Long thesisId, MultipartFile file) {
        if (file.isEmpty()) {
            throw new InvalidArgumentException("Fajl je prazan!");
        }
        Thesis thesis = getThesis(thesisId)
                .orElseThrow(() -> new InvalidArgumentException("Rad sa id-em " + thesisId
                        + " ne postoji u bazi!"));

        TFile existingTFile = thesis.getFile();
        if (existingTFile != null) {
            throw new InvalidArgumentException("Rad " + thesis.getName()
                    + " već ima upload-ovan fajl "
                    + existingTFile.getFileName());
        }
        if (fileRepository.findByFileName(file.getOriginalFilename()) != null) {
            throw new InvalidArgumentException("Fajl sa imenom "
                    + file.getOriginalFilename()
                    + " već postoji. Molimo izaberite drugo ime fajla.");
        }
        TFile tFile = new TFile(file.getOriginalFilename());
        thesis.setFile(tFile);
        logger.debug(tFile.getFileName());
        try {
            byte[] bytes = file.getBytes();
            BufferedOutputStream stream = new BufferedOutputStream(
                    new FileOutputStream(thesisFilesFolder
                            + tFile.getFileName()));
            stream.write(bytes);
            stream.close();
        } catch (Exception e) {
            throw new PzrRuntimeException("Fajl nije uspešno postavljen: "
                    + e.getMessage());
        }
        fileRepository.save(tFile);
        thesisRepository.save(thesis);
        return tFile;

    }

    @Override
    public Set<TFile> getAllFileRecords() {
        return fileRepository.findAll();
    }

    @Override
    public void removeFile(Long fileId) {
        TFile existingFile = fileRepository.findOne(fileId);
        if (existingFile == null) {
            throw new InvalidArgumentException("Fajl sa id-em " + fileId
                    + " ne postoji u bazi!");
        }
        File file = new File(thesisFilesFolder + existingFile.getFileName());
        if (!file.exists()) {
            fileRepository.delete(existingFile);
            throw new PzrRuntimeException("Fajl " + existingFile.getFileName()
                    + " se ne nalazi na lokaciji " + thesisFilesFolder
                    + existingFile.getFileName() + "!");
        }
        if (!file.delete()) {
            throw new PzrRuntimeException("Fajl " + existingFile.getFileName()
                    + " nije uspešno izbrisan!");
        }
        fileRepository.delete(existingFile);

    }

    @Override
    public File getThesisFile(Long thesisId) {
        Thesis thesis = getThesis(thesisId)
                .orElseThrow(() ->
                        new InvalidArgumentException("Rad sa id-em " + thesisId + " ne postoji u bazi!"));

        TFile existingTFile = thesis.getFile();
        if (existingTFile == null) {
            throw new InvalidArgumentException("Rad " + thesis.getName()
                    + " nema upload-ovan fajl ");
        }
        File file = new File(thesisFilesFolder + existingTFile.getFileName());

        if (!file.exists()) {
            logger.error("Thesis " + thesis.getName() + " has attached file "
                    + existingTFile.getFileName()
                    + " but file doesnot exist on disc at location: "
                    + thesisFilesFolder + existingTFile.getFileName());
            throw new PzrRuntimeException("Fajl " + existingTFile.getFileName()
                    + " se ne nalazi na lokaciji " + thesisFilesFolder
                    + existingTFile.getFileName() + "!");
        }
        fileRepository.save(existingTFile);
        return file;
    }

    @Override
    public File getFileById(Long fileId) {
        TFile tFile = fileRepository.findOne(fileId);
        if (tFile == null) {
            throw new PzrRuntimeException("Fajl sa id-em " + fileId
                    + " ne postoji u bazi.");
        }
        File file = new File(thesisFilesFolder + tFile.getFileName());
        if (!file.exists()) {
            logger.error("There is a file record in database"
                    + " but file doesnot exist on disc at location: "
                    + thesisFilesFolder + tFile.getFileName());
            throw new PzrRuntimeException("Fajl " + tFile.getFileName()
                    + " se ne nalazi na lokaciji " + thesisFilesFolder
                    + tFile.getFileName() + "!");
        }
        fileRepository.save(tFile);
        return file;
    }

    @Override
    public Set<ThesisComment> getAllComments(Long thesisId) {
        Thesis thesis = thesisRepository.findOne(thesisId);
        if (thesis == null) {
            throw new InvalidArgumentException("Rad sa id-em " + thesisId
                    + " ne postoji u bazi!");
        }
        return thesis.getComments()
                .stream()
                .collect(Collectors.toSet());
    }

    @Transactional
    @Override
    public ThesisComment addComment(ThesisComment thesisComment) {
        return commentRepository.save(thesisComment);
    }

    @Transactional
    @Override
    public void removeComment(Long commentId) {
        if (commentRepository.findOne(commentId) == null) {
            throw new InvalidArgumentException("Komentar sa id-em " + commentId
                    + " ne postoji u bazi!");
        }
        commentRepository.delete(commentRepository.findOne(commentId));
    }

    @Override
    public ThesisComment getComment(long commentId) {
        return commentRepository.findOne(commentId);
    }
}
