package rs.fon.pzr.core.service;

import java.util.List;

import rs.fon.pzr.model.UserEntity;

public interface UserService {

	UserEntity getUser(Long userId);
	
	UserEntity getUser(String email);
	
	List<UserEntity> getAllUsers();

	UserEntity addUser(UserEntity user);
	
	UserEntity updateUser(UserEntity user);
	
	void deleteUser(Long userId);

}
