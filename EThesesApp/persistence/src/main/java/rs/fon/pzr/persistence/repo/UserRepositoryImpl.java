package rs.fon.pzr.persistence.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import rs.fon.pzr.core.repository.UserRepository;
import rs.fon.pzr.core.domain.model.user.UserEntity;
import rs.fon.pzr.persistence.jpa.UserJpaRepository;

import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository jpaRepository;

    @Autowired
    public UserRepositoryImpl(UserJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public UserEntity findOne(Long userId) {
        return jpaRepository.findOne(userId);
    }

    @Override
    public UserEntity findByUserCredentialsEmail(String email) {
        return jpaRepository.findByUserCredentialsEmail(email);
    }

    @Override
    public List<UserEntity> findAll() {
        return jpaRepository.findAll();
    }

    @Override
    public UserEntity save(UserEntity user) {
        return jpaRepository.save(user);
    }

    @Override
    public void delete(UserEntity user) {
        jpaRepository.delete(user);
    }
}
