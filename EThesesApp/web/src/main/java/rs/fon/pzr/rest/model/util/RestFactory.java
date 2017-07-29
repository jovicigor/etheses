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

        return new ThesisResponseLevel1(thesis);
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

    public static UserResponseLevel1 createUserResponseLevel1(UserEntity user) {
        if (user == null) {
            return null;
        }
        UserResponseLevel1 userResponseLevel1 = new UserResponseLevel1();
        userResponseLevel1.setAdmin(user.isAdmin());
        userResponseLevel1.setBiography(user.getBiography());
        userResponseLevel1.setCourse(createCourseResponseLevel2(user
                .getCourse()));
        userResponseLevel1.setEmail(user.getEmail().asString());
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
