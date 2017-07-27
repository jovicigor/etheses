package rs.fon.pzr.rest.model.util;

import org.springframework.data.domain.Page;
import rs.fon.pzr.model.*;
import rs.fon.pzr.rest.model.response.level1.*;
import rs.fon.pzr.rest.model.response.level2.*;

import java.util.*;
import java.util.stream.Collectors;

public class RestFactory {

    public static ThesisPageResponse CreateThesisPageResponse(Page<ThesisEntity> thesisPage) {
        ThesisPageResponse thesisPageResponse = new ThesisPageResponse();
        List<ThesisResponseLevel1> thesisResponseLevel1List = thesisPage.getContent().stream().map(RestFactory::createThesisResponseLevel1).collect(Collectors.toList());
        thesisPageResponse.setNumber(thesisPage.getNumber() + 1);
        thesisPageResponse.setNumberOfElements(thesisPage.getNumberOfElements());
        thesisPageResponse.setSize(thesisPage.getSize());
        thesisPageResponse.setTotalElements(thesisPage.getTotalElements());
        thesisPageResponse.setTotalPages(thesisPage.getTotalPages());
        thesisPageResponse.setContent(thesisResponseLevel1List);

        return thesisPageResponse;
    }

    public static ThesisResponseLevel1 createThesisResponseLevel1(ThesisEntity thesis) {
        if (thesis == null) {
            return null;
        }
        ThesisResponseLevel1 thesisResponseLevel1 = new ThesisResponseLevel1();

        Set<ThesisCommentResponseLevel2> thesisCommentResponseLevel2List = thesis.getComments().stream().map(RestFactory::createThesisCommentResponseLevel2).collect(Collectors.toSet());
        thesisResponseLevel1.setComments(thesisCommentResponseLevel2List);

        thesisResponseLevel1.setDatePosted(thesis.getDatePosted());
        thesisResponseLevel1.setDefenseDate(thesis.getDefenseDate());
        thesisResponseLevel1.setDescription(thesis.getDescription());
        thesisResponseLevel1.setFile(thesis.getFile());
        thesisResponseLevel1.setGrade(thesis.getGrade());
        thesisResponseLevel1.setId(thesis.getId());
        thesisResponseLevel1.setMentor(createUserResponseLevel2(
                thesis.getMentor()));
        thesisResponseLevel1.setMentorName(thesis.getMentorName());
        thesisResponseLevel1.setMentorEmail(thesis.getMentorEmail());
        thesisResponseLevel1.setName(thesis.getName());
        thesisResponseLevel1.setCourse(createCourseResponseLevel2(thesis
                .getCourse()));
        thesisResponseLevel1.setTags(thesis.getTags());
        thesisResponseLevel1.setFieldsOfStudy(thesis.getFieldOfStudies());
        thesisResponseLevel1.setUser(createUserResponseLevel2(thesis.getUser()));
        thesisResponseLevel1.setUserEmail(thesis.getUserEmail());
        thesisResponseLevel1.setUserName(thesis.getUserName());
        thesisResponseLevel1.setViewCount(thesis.getViewCount());

        return thesisResponseLevel1;
    }

    private static ThesisResponseLevel2 createThesisResponseLevel2(ThesisEntity thesis) {
        if (thesis == null) {
            return null;
        }
        return new ThesisResponseLevel2(thesis);
    }

    public static ThesisCommentResponseLevel1 createThesisCommentResponseLevel1(ThesisComment comment) {
        if (comment == null) {
            return null;
        }
        return new ThesisCommentResponseLevel1(comment);
    }

    private static ThesisCommentResponseLevel2 createThesisCommentResponseLevel2(ThesisComment comment) {
        if (comment == null) {
            return null;
        }
        ThesisCommentResponseLevel2 thesisCommentResponseLevel2 = new ThesisCommentResponseLevel2();
        UserEntity author = comment.getAuthor();
        if (author != null) {
            thesisCommentResponseLevel2.setAuthorId(author.getId());
        }
        thesisCommentResponseLevel2.setDatePosted(comment.getDatePosted());
        thesisCommentResponseLevel2.setId(comment.getId());
        thesisCommentResponseLevel2.setMessage(comment.getMessage());
        ThesisEntity thesis = comment.getThesis();
        if (thesis != null) {
            thesisCommentResponseLevel2.setThesisId(thesis.getId());
        }

        return thesisCommentResponseLevel2;

    }

    public static UserResponseLevel1 createUserResponseLevel1(UserEntity user) {
        if (user == null) {
            return null;
        }
        UserResponseLevel1 userResponseLevel1 = new UserResponseLevel1();
        userResponseLevel1.setAdmin(user.isAdmin());
        userResponseLevel1.setBiography(user.getBiography());
        userResponseLevel1.setCourse(createCourseResponseLevel2(user
                .getCourse()));
        userResponseLevel1.setEmail(user.getEmail());
        userResponseLevel1.setFirstName(user.getFirstName());
        userResponseLevel1.setId(user.getId());
        userResponseLevel1.setInterests(user.getInterests());
        userResponseLevel1.setLastName(user.getLastName());
        userResponseLevel1.setStudentsTranscript(user.getStudentsTranscript());
        Set<ThesisResponseLevel2> thesisResponseLevel2 = user.getTheses().
                stream()
                .map(RestFactory::createThesisResponseLevel2)
                .collect(Collectors.toSet());
        userResponseLevel1.setTheses(thesisResponseLevel2);

        return userResponseLevel1;

    }

    private static UserResponseLevel2 createUserResponseLevel2(UserEntity user) {
        if (user == null) {
            return null;
        }
        return new UserResponseLevel2(user);
    }


    public static CourseResponseLevel1 createCourseResponseLevel1(CourseEntity course) {
        if (course == null) {
            return null;
        }
        return new CourseResponseLevel1(course);
    }

    private static CourseResponseLevel2 createCourseResponseLevel2(CourseEntity course) {
        if (course == null) {
            return null;
        }
        return new CourseResponseLevel2(course);

    }

    public static StudiesResponseLevel1 createStudiesResponseLevel1(StudiesEntity studies) {
        if (studies == null) {
            return null;
        }
        return new StudiesResponseLevel1(studies);
    }
}
