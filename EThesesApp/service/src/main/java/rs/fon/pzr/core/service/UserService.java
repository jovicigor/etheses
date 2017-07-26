package rs.fon.pzr.core.service;

import java.util.List;
import java.util.Optional;

import rs.fon.pzr.model.UserEntity;

public interface UserService {

    Optional<UserEntity> getUser(Long userId);

    Optional<UserEntity> getUser(String email);

    List<UserEntity> getAllUsers();

    UserEntity addUser(UserEntity user);

    UserEntity updateUser(UserEntity user);

    void deleteUser(Long userId);

}
