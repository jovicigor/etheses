package rs.fon.pzr.rest.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rs.fon.pzr.core.exception.InvalidArgumentException;
import rs.fon.pzr.model.CourseEntity;
import rs.fon.pzr.model.UserEntity;
import rs.fon.pzr.core.service.CourseService;
import rs.fon.pzr.core.service.UserService;
import rs.fon.pzr.core.service.util.ParamaterCheck;
import rs.fon.pzr.rest.model.LoginData;
import rs.fon.pzr.rest.model.request.AdminPrivilegeRequest;
import rs.fon.pzr.rest.model.request.UserRequest;
import rs.fon.pzr.rest.model.response.level1.UserResponseLevel1;
import rs.fon.pzr.rest.model.util.RestFactory;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserResource {

    private final UserService userService;
    private final CourseService courseService;

    @Autowired
    public UserResource(UserService userService, CourseService courseService) {
        this.userService = userService;
        this.courseService = courseService;
    }

    // READ
    @RequestMapping(method = RequestMethod.GET)
    public
    @ResponseBody
    List<UserResponseLevel1> getUsers() {
        List<UserEntity> userList = userService.getAllUsers();
        List<UserResponseLevel1> userResponseList = new ArrayList<>();
        for (UserEntity user : userList) {
            userResponseList.add(RestFactory.createUserResponseLevel1(user));
        }
        return userResponseList;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{userID}")
    public
    @ResponseBody
    UserResponseLevel1 getUser(
            @PathVariable("userID") Long userID) {
        return RestFactory.createUserResponseLevel1(
                userService.getUser(userID));
    }

    // CREATE
    @RequestMapping(method = RequestMethod.POST)
    public
    @ResponseBody
    UserResponseLevel1 addUser(
            @RequestBody LoginData loginData) {
        ParamaterCheck.mandatory("Email ", loginData.getEmail());
        ParamaterCheck.mandatory("Password ", loginData.getPassword());

        UserEntity user = new UserEntity(loginData.getEmail(), loginData.getPassword());
        user = userService.addUser(user);
        UserResponseLevel1 userResponse = new UserResponseLevel1();
        userResponse.setId(user.getId());
        userResponse.setEmail(user.getEmail());
        return userResponse;
    }

    // UPDATE
    @RequestMapping(method = RequestMethod.PUT, value = "/{userID}")
    public
    @ResponseBody
    UserResponseLevel1 updateUser(
            @RequestBody UserRequest userRequest,
            @PathVariable("userID") Long userID) {

        UserEntity user = userService.getUser(userID);
        if (user == null) {
            throw new InvalidArgumentException("Korisnik sa id-em " + userID
                    + " ne postoji u bazi!");
        }

        if (userRequest.getBiography() != null) {
            user.setBiography(userRequest.getBiography());
        }
        if (userRequest.getCourseName() != null) {
            CourseEntity course = courseService.getCourseByName(userRequest
                    .getCourseName());
            user.setCourse(course);
        }
        if (userRequest.getFirstName() != null) {
            user.setFirstName(userRequest.getFirstName());
        }
        if (userRequest.getInterests() != null) {
            user.setInterests(userRequest.getInterests());
        }
        if (userRequest.getLastName() != null) {
            user.setLastName(userRequest.getLastName());
        }
        if (userRequest.getStudentsTranscript() != null) {
            user.setStudentsTranscript(userRequest.getStudentsTranscript());
        }
        return RestFactory.createUserResponseLevel1(
                userService.updateUser(user));

    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{userID}/admin")
    public
    @ResponseBody
    UserResponseLevel1 changeAdminPrivilege(
            @PathVariable("userID") Long userID,
            @RequestBody AdminPrivilegeRequest adminPrivilegeRequest) {
        ParamaterCheck.mandatory("admin", adminPrivilegeRequest.isAdmin());
        UserEntity user = userService.getUser(userID);
        if (user == null) {
            throw new InvalidArgumentException("Korisnik sa id-em " + userID
                    + " ne postoji u bazi!");
        }
        user.setAdmin(adminPrivilegeRequest.isAdmin());
        return RestFactory.createUserResponseLevel1(
                userService.updateUser(user));
    }

    // DELETE
    @RequestMapping(method = RequestMethod.DELETE, value = "/{userID}")
    public void deleteUser(@PathVariable("userID") Long userID) {
        userService.deleteUser(userID);
    }
}
