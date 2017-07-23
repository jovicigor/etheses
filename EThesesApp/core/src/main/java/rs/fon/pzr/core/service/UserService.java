package rs.fon.pzr.core.service;

import java.util.List;

import rs.fon.pzr.persistence.model.User;

public interface UserService {

	User getUser(Long userId);
	
	User getUser(String email);
	
	List<User> getAllUsers();

	User addUser(User user);
	
	User updateUser(User user);
	
	void deleteUser(Long userId);

}
