package rs.fon.pzr.core.repository;

import rs.fon.pzr.core.domain.model.user.UserEntity;

import java.util.List;

public interface UserRepository {

    UserEntity findOne(Long userId);

    UserEntity findByUserCredentialsEmail(String email);

    List<UserEntity> findAll();

    UserEntity save(UserEntity user);

    void delete(UserEntity user);
}
