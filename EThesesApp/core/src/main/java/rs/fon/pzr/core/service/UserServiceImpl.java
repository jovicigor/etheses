package rs.fon.pzr.core.service;

import java.util.List;
import java.util.Objects;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import rs.fon.pzr.core.exception.InvalidArgumentException;
import rs.fon.pzr.core.exception.InvalidTicketException;
import rs.fon.pzr.persistence.model.User;
import rs.fon.pzr.persistence.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        passwordEncoder = new BCryptPasswordEncoder();

    }

    @Override
    public User getUser(Long userId) {
        return userRepository.findOne(userId);
    }

    @Override
    public User getUser(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    @Override
    public User addUser(User user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new InvalidArgumentException("Korisnik sa email-om "
                    + user.getEmail() + " je već registrovan!");
        }

        EmailValidator validator = EmailValidator.getInstance();
        if (!validator.isValid(user.getEmail())) {
            throw new InvalidArgumentException("Email koji ste uneli nije validan.");
        }

        if (!user.getPassword().matches(
                "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\\s).{6,13}$")) {
            throw new InvalidArgumentException(
                    "Šifra ne sme sadržati razmake, mora imati barem jedno malo slovo, jedno veliko slovo, jednu cifru i sadržati između 6 i 13 karaktera.");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Transactional
    @Override
    public User updateUser(User user) {
        if (userRepository.findOne(user.getId()) == null) {
            throw new InvalidArgumentException("Korisnik sa id-em "
                    + user.getId() + " ne postoji u bazi!");
        }
        try {
            org.springframework.security.core.userdetails.User springUser = (org.springframework.security.core.userdetails.User) SecurityContextHolder
                    .getContext().getAuthentication().getPrincipal();
            String email = springUser.getUsername();
            User loggedInUser = getUser(email);
            if (!loggedInUser.isAdmin() && !Objects.equals(loggedInUser.getId(), user.getId())) {
                throw new Exception();
            }
        } catch (Exception e) {
            throw new InvalidTicketException(
                    "Morate biti ulogovani kako bi menjali profil!");
        }
        return userRepository.save(user);
    }

    @Transactional
    @Override
    public void deleteUser(Long userId) {
        User user = userRepository.findOne(userId);
        if (user == null) {
            throw new InvalidArgumentException("Korisnik sa id-em " + userId
                    + " ne postoji u bazi!");
        }
        try {
            org.springframework.security.core.userdetails.User springUser = (org.springframework.security.core.userdetails.User) SecurityContextHolder
                    .getContext().getAuthentication().getPrincipal();
            String email = springUser.getUsername();
            User loggedInUser = getUser(email);
            if (!loggedInUser.isAdmin() && !Objects.equals(loggedInUser.getId(), user.getId())) {
                throw new Exception();
            }
        } catch (Exception e) {
            throw new InvalidTicketException(
                    "Morate biti ulogovani kako bi menjali profil.");
        }
        userRepository.delete(user);

    }
}
