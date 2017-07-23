package rs.fon.pzr.core.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import rs.fon.pzr.core.model.User;

public interface UserRepository extends CrudRepository<User, Long> {
	
	List<User> findAll();
	User findByEmail(String email);
}
