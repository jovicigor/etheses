package rs.fon.pzr.persistence.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import rs.fon.pzr.model.user.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, Long> {

    List<UserEntity> findAll();

    UserEntity findByUserCredentialsEmail(String email);
}
