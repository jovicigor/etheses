package rs.fon.pzr.rest.model.util;

import rs.fon.pzr.model.studies.Course;
import rs.fon.pzr.model.studies.Studies;
import rs.fon.pzr.model.thesis.Thesis;
import rs.fon.pzr.model.thesis.ThesisComment;
import rs.fon.pzr.model.user.UserEntity;
import rs.fon.pzr.rest.model.response.level1.*;

public class RestFactory {

    public static ThesisResponseLevel1 createThesisResponseLevel1(Thesis thesis) {
        if (thesis == null) {
            return null;
        }

        return new ThesisResponseLevel1(thesis);
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
        return new UserResponseLevel1(user);

    }

    public static CourseResponseLevel1 createCourseResponseLevel1(Course course) {
        if (course == null) {
            return null;
        }
        return new CourseResponseLevel1(course);
    }

    public static StudiesResponseLevel1 createStudiesResponseLevel1(Studies studies) {
        if (studies == null) {
            return null;
        }
        return new StudiesResponseLevel1(studies);
    }
}
