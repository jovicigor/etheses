package rs.fon.pzr.core.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import rs.fon.pzr.core.exception.InvalidArgumentException;
import rs.fon.pzr.core.exception.InvalidTicketException;
import rs.fon.pzr.model.UserEntity;
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
    public Optional<UserEntity> getUser(Long userId) {
        UserEntity user = userRepository.findOne(userId);
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<UserEntity> getUser(String email) {
        UserEntity user = userRepository.findByUserLoginEmail(email);
        return Optional.ofNullable(user);
    }

    @Override
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    @Override
    public UserEntity addUser(UserEntity user) {
        if (userRepository.findByUserLoginEmail(user.getEmail()) != null) {
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
    public UserEntity updateUser(UserEntity user) {
        if (userRepository.findOne(user.getId()) == null) {
            throw new InvalidArgumentException("Korisnik sa id-em "
                    + user.getId() + " ne postoji u bazi!");
        }
        try {
            org.springframework.security.core.userdetails.User springUser = (org.springframework.security.core.userdetails.User) SecurityContextHolder
                    .getContext().getAuthentication().getPrincipal();
            String email = springUser.getUsername();
            UserEntity loggedInUser = getUser(email).orElseThrow(() -> new InvalidTicketException("not logged in"));
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
        UserEntity user = userRepository.findOne(userId);
        if (user == null) {
            throw new InvalidArgumentException("Korisnik sa id-em " + userId
                    + " ne postoji u bazi!");
        }
        try {
            org.springframework.security.core.userdetails.User springUser = (org.springframework.security.core.userdetails.User) SecurityContextHolder
                    .getContext().getAuthentication().getPrincipal();
            String email = springUser.getUsername();
            UserEntity loggedInUser = getUser(email).orElseThrow(() -> new InvalidTicketException("not logged in"));
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
