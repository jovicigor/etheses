package rs.fon.pzr.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.fon.pzr.core.exception.InvalidArgumentException;
import rs.fon.pzr.core.repository.UserRepository;
import rs.fon.pzr.core.domain.model.user.UserEntity;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<UserEntity> getUser(Long userId) {
        UserEntity user = userRepository.findOne(userId);
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<UserEntity> getUser(String email) {
        UserEntity user = userRepository.findByUserCredentialsEmail(email);
        return Optional.ofNullable(user);
    }

    @Override
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    @Override
    public UserEntity addUser(UserEntity user) {
        if (userRepository.findByUserCredentialsEmail(user.getEmail().asString()) != null) {
            throw new InvalidArgumentException("Korisnik sa email-om "
                    + user.getEmail() + " je već registrovan!");
        }
        return userRepository.save(user);
    }

    @Transactional
    @Override
    public UserEntity updateUser(UserEntity user) {
        if (userRepository.findOne(user.getId()) == null) {
            throw new InvalidArgumentException("Korisnik sa id-em "
                    + user.getId() + " ne postoji u bazi!");
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
        userRepository.delete(user);

    }
}
