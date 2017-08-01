package rs.fon.pzr.rest.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import rs.fon.pzr.core.exception.InvalidArgumentException;
import rs.fon.pzr.core.exception.InvalidTicketException;
import rs.fon.pzr.core.service.CourseService;
import rs.fon.pzr.core.service.UserService;
import rs.fon.pzr.core.service.util.ParamaterCheck;
import rs.fon.pzr.model.user.UserCredentials;
import rs.fon.pzr.model.user.UserEntity;
import rs.fon.pzr.rest.model.LoginData;
import rs.fon.pzr.rest.model.request.AdminPrivilegeRequest;
import rs.fon.pzr.rest.model.request.UserRequest;
import rs.fon.pzr.rest.model.response.level1.UserResponseLevel1;
import rs.fon.pzr.rest.model.util.RestFactory;
import rs.fon.pzr.type.Email;
import rs.fon.pzr.type.Password;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/users")
public class UserResource {

    private final UserService userService;
    private final CourseService courseService;
    private static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public UserResource(UserService userService, CourseService courseService) {
        this.userService = userService;
        this.courseService = courseService;
    }

    @GetMapping
    @ResponseBody
    public List<UserResponseLevel1> getUsers() {
        List<UserEntity> userList = userService.getAllUsers();

        return userList.stream()
                .map(RestFactory::createUserResponseLevel1)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/{userID}")
    @ResponseBody
    public UserResponseLevel1 getUser(@PathVariable("userID") Long userID) {
        return userService.getUser(userID)
                .map(RestFactory::createUserResponseLevel1)
                .orElse(null);
    }

    @PostMapping
    @ResponseBody
    public UserResponseLevel1 addUser(@RequestBody LoginData loginData) {
        String userEmail = loginData.getEmail();

        if (!Email.isValid(userEmail)) {
            throw new InvalidArgumentException("Email koji ste uneli nije validan.");
        }
        String pass = loginData.getPassword();
        if (!Password.isValid(pass)) {
            throw new InvalidArgumentException(
                    "Šifra ne sme sadržati razmake, mora imati barem jedno malo slovo, jedno veliko slovo, jednu cifru i sadržati između 6 i 13 karaktera.");
        }

        Email email = Email.fromString(userEmail);
        Password password = Password.fromString(passwordEncoder.encode(pass));
        UserCredentials credentials = new UserCredentials(email, password);
        UserEntity user = UserEntity.createUserWithCredentials(credentials);

        user = userService.addUser(user);
        return new UserResponseLevel1(user);
    }

    @PutMapping(value = "/{userID}")
    @ResponseBody
    public UserResponseLevel1 updateUser(@RequestBody UserRequest userRequest,
                                         @PathVariable("userID") Long userID) {

        User springUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = springUser.getUsername();
        UserEntity loggedInUser = userService.getUser(email)
                .orElseThrow(() -> new InvalidTicketException("not logged in"));
        if (!loggedInUser.isAdmin() && !Objects.equals(loggedInUser.getId(), userID)) {
            throw new InvalidTicketException(
                    "Morate biti ulogovani kako bi menjali profil.");
        }

        UserEntity user = userService.getUser(userID)
                .orElseThrow(() -> new InvalidArgumentException("Korisnik sa id-em " + userID
                        + " ne postoji u bazi!"));

        if (userRequest.getBiography() != null) {
            user.setBiography(userRequest.getBiography());
        }
        String courseName = userRequest
                .getCourseName();
        if (courseName != null) {
            courseService.getCourseByName(courseName)
                    .ifPresent(user::setCourse);
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

    @PutMapping(value = "/{userID}/admin")
    @ResponseBody
    public UserResponseLevel1 changeAdminPrivilege(@PathVariable("userID") Long userID,
                                                   @RequestBody AdminPrivilegeRequest adminPrivilegeRequest) {
        ParamaterCheck.mandatory("admin", adminPrivilegeRequest.isAdmin());
        UserEntity user = userService.getUser(userID)
                .orElseThrow(() -> new InvalidArgumentException("Korisnik sa id-em " + userID
                        + " ne postoji u bazi!"));

        if (adminPrivilegeRequest.isAdmin())
            user.makeAdmin();
        else
            user.removeAdmin();
        return RestFactory.createUserResponseLevel1(
                userService.updateUser(user));
    }

    @DeleteMapping(value = "/{userID}")
    public void deleteUser(@PathVariable("userID") Long userID) {
        User springUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = springUser.getUsername();
        UserEntity loggedInUser = userService.getUser(email)
                .orElseThrow(() -> new InvalidTicketException("not logged in"));
        if (!loggedInUser.isAdmin() && !Objects.equals(loggedInUser.getId(), userID)) {
            throw new InvalidTicketException(
                    "Morate biti ulogovani kako bi menjali profil.");
        }
        userService.deleteUser(userID);
    }
}
