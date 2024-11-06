package com.everymatch.mvc.model.service;

import com.everymatch.mvc.model.dto.User;

public class UserServiceImpl implements UserService {

	@Override
	public boolean registUser(User user) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean loginUser(String userId, String password) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public User detailUser(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean updateUser(User user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean findPassword(String userId, String email) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean changePassword(String userId, String oldPassword, String newPassword) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void deleteUser(String userId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean checkEmail(String email) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean checkId(String userId) {
		// TODO Auto-generated method stub
		return false;
	}

}
