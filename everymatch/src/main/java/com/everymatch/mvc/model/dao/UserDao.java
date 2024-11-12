package com.everymatch.mvc.model.dao;

import java.util.List;

import com.everymatch.mvc.model.dto.User;

public interface UserDao {
	
	public List<User> getAllUser();
	
	public User getUserById(String userId);
	
	public User getUserByEmail(String email);
	
	public void insertUser(User user);
    
	public void deleteUser(String userId);
    
	public void updateUser(User user);
}
