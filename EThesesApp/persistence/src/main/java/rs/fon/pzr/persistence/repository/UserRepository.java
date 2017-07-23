package rs.fon.pzr.persistence.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import rs.fon.pzr.persistence.model.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
	
	List<UserEntity> findAll();
	UserEntity findByEmail(String email);
}
