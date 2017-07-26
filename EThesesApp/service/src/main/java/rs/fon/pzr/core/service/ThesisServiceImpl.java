package rs.fon.pzr.core.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import rs.fon.pzr.core.exception.InvalidArgumentException;
import rs.fon.pzr.core.exception.InvalidTicketException;
import rs.fon.pzr.core.exception.PzrRuntimeException;
import rs.fon.pzr.model.TFileEntity;
import rs.fon.pzr.model.ThesisEntity;
import rs.fon.pzr.model.ThesisComment;
import rs.fon.pzr.model.UserEntity;
import rs.fon.pzr.persistence.repository.CommentRepository;
import rs.fon.pzr.persistence.repository.FileRepository;
import rs.fon.pzr.persistence.repository.ThesisRepository;
import rs.fon.pzr.persistence.repository.UserRepository;

@Service
public class ThesisServiceImpl implements ThesisService {

    private final Logger logger = Logger.getLogger(ThesisServiceImpl.class);
    private static final int DEFAULT_PAGE_SIZE = 10;

    private String thesisFilesFolder;

    private final ThesisRepository thesisRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final FileRepository fileRepository;

    @Autowired
    public ThesisServiceImpl(UserRepository userRepository, ThesisRepository thesisRepository,
                             FileRepository fileRepository, CommentRepository commentRepository,
                             UserService userService) {
        this.userRepository = userRepository;
        this.thesisRepository = thesisRepository;
        this.fileRepository = fileRepository;
        this.commentRepository = commentRepository;
        this.userService = userService;
    }

    @Override
    public Optional<ThesisEntity> getThesis(Long id) {
        ThesisEntity thesis = thesisRepository.findOne(id);
        thesis.updateViewCount();
        thesis = thesisRepository.save(thesis);
        return Optional.ofNullable(thesis);
    }

    @Override
    public List<ThesisEntity> getThesisByUserId(Long userId) {
        UserEntity user = userRepository.findOne(userId);
        if (user == null) {
            throw new InvalidArgumentException("Korisnik sa id-em " + userId
                    + " ne postoji u bazi!.");
        }
        return thesisRepository.findByUser(user);
    }

    @Override
    public List<ThesisEntity> getAllThesis() {
        return thesisRepository.findAll();
    }

    @Override
    public Page<ThesisEntity> advancedSearch(Integer pageNumber, Integer pageSize,
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

        PageRequest pageRequest;
        if (sortField == null || sortField.equals("")) {
            pageRequest = new PageRequest(pageNumber, pageSize, new Sort(
                    Sort.Direction.DESC, "datePosted"));
        } else {
            pageRequest = new PageRequest(pageNumber, pageSize, new Sort(
                    Sort.Direction.DESC, sortField).and(new Sort(
                    Sort.Direction.DESC, "datePosted")));
        }

        try {
            if (courseName != null && !Objects.equals(courseName, "")) {
                if (descriptionKeys == null || descriptionKeys.isEmpty()) {
                    return thesisRepository
                            .findByNameLikeTagsFieldsAndCoursePagable(
                                    pageRequest, thesisName, tagValues,
                                    tagsMatchLimit, courseName, fieldValues,
                                    fieldsMatchLimit);
                }
                if (descriptionKeyLimit == null) {
                    descriptionKeyLimit = (long) descriptionKeys.size();
                }
                return thesisRepository
                        .findByNameLikeTagsFieldsCourseAndDescriptioinPagable(
                                pageRequest, thesisName, tagValues,
                                tagsMatchLimit, courseName, fieldValues,
                                fieldsMatchLimit, descriptionKeys,
                                descriptionKeyLimit);
            } else if (studiesName != null && !Objects.equals(studiesName, "")) {
                if (descriptionKeys == null || descriptionKeys.isEmpty()) {
                    return thesisRepository
                            .findByNameLikeTagsFieldsAndStudiesPagable(
                                    pageRequest, thesisName, tagValues,
                                    tagsMatchLimit, studiesName, fieldValues,
                                    fieldsMatchLimit);
                }
                if (descriptionKeyLimit == null) {
                    descriptionKeyLimit = (long) descriptionKeys.size();
                }
                return thesisRepository
                        .findByNameLikeTagsFieldsStudiesAndDescriptionPagable(
                                pageRequest, thesisName, tagValues,
                                tagsMatchLimit, studiesName, fieldValues,
                                fieldsMatchLimit, descriptionKeys,
                                descriptionKeyLimit);
            } else {
                if (descriptionKeys == null || descriptionKeys.isEmpty()) {
                    return thesisRepository.findByNameLikeTagsAndFieldsPagable(
                            pageRequest, thesisName, tagValues, tagsMatchLimit,
                            fieldValues, fieldsMatchLimit);
                }
                if (descriptionKeyLimit == null) {
                    descriptionKeyLimit = (long) descriptionKeys.size();
                }
                return thesisRepository
                        .findByNameLikeTagsFieldsAndDescriptionPagable(
                                pageRequest, thesisName, tagValues,
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
    public ThesisEntity addThesis(ThesisEntity thesis) {
        if (thesisRepository.findByName(thesis.getName()) != null) {
            throw new InvalidArgumentException("Rad " + thesis.getName()
                    + "već postoji u bazi!");
        }
        return thesisRepository.save(thesis);
    }

    @Transactional
    @Override
    public ThesisEntity updateThesis(ThesisEntity thesis) {
        ThesisEntity oldThesis = thesisRepository.findOne(thesis.getId());
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
        ThesisEntity thesis = thesisRepository.findOne(thesisId);
        if (thesis == null) {
            throw new InvalidArgumentException("Rad sa id-em " + thesisId
                    + " ne postoji u bazi!");
        }
        thesisRepository.delete(thesis);
    }

    @Override
    public TFileEntity addFile(Long thesisId, MultipartFile file) {
        if (file.isEmpty()) {
            throw new InvalidArgumentException("Fajl je prazan!");
        }
        ThesisEntity thesis = getThesis(thesisId)
                .orElseThrow(() -> new InvalidArgumentException("Rad sa id-em " + thesisId
                        + " ne postoji u bazi!"));

        TFileEntity existingTFile = thesis.getFile();
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
        TFileEntity tFile = new TFileEntity(file.getOriginalFilename());
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
    public Set<TFileEntity> getAllFileRecords() {
        return fileRepository.findAll();
    }

    @Override
    public void removeFile(Long fileId) {
        TFileEntity existingFile = fileRepository.findOne(fileId);
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
        ThesisEntity thesis = getThesis(thesisId)
                .orElseThrow(() ->
                        new InvalidArgumentException("Rad sa id-em " + thesisId + " ne postoji u bazi!"));

        TFileEntity existingTFile = thesis.getFile();
        if (existingTFile == null) {
            throw new InvalidArgumentException("Rad " + thesis.getName()
                    + " nema upload-ovan fajl ");
        }
        File file = new File(thesisFilesFolder + existingTFile.getFileName());

        if (!file.exists()) {
            logger.error("ThesisEntity " + thesis.getName() + " has attached file "
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
        TFileEntity tFile = fileRepository.findOne(fileId);
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
        ThesisEntity thesis = thesisRepository.findOne(thesisId);
        if (thesis == null) {
            throw new InvalidArgumentException("Rad sa id-em " + thesisId
                    + " ne postoji u bazi!");
        }
        return thesis.getComments();
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
        try {
            org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder
                    .getContext().getAuthentication().getPrincipal();
            String email = user.getUsername();
            UserEntity loggedInUser = userService.getUser(email).orElseThrow(() -> new InvalidTicketException("not logged in"));
            ThesisComment thesisComment = commentRepository.findOne(commentId);
            if (!loggedInUser.isAdmin()
                    && !Objects.equals(thesisComment.getAuthor().getId(), loggedInUser
                    .getId())) {
                throw new Exception();
            }
        } catch (Exception e) {
            throw new InvalidArgumentException(
                    "Morate biti ulogovani da bi ste mogli obrisati komentar!");
        }
        commentRepository.delete(commentRepository.findOne(commentId));
    }
}
