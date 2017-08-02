package rs.fon.pzr.persistence.jpa;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import rs.fon.pzr.core.domain.model.user.UserEntity;

public interface UserJpaRepository extends CrudRepository<UserEntity, Long> {

    List<UserEntity> findAll();

    UserEntity findByUserCredentialsEmail(String email);
}
