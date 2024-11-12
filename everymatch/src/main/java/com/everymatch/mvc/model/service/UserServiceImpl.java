package com.everymatch.mvc.model.service;

import org.springframework.stereotype.Service;

import com.everymatch.mvc.model.dao.UserDao;
import com.everymatch.mvc.model.dto.User;

@Service
public class UserServiceImpl implements UserService {

	private final UserDao userDao;
	
	public UserServiceImpl(UserDao userDao) {
		this.userDao = userDao;
	}
	
	@Override
	public void registUser(User user) throws Exception {
		try {
			userDao.insertUser(user);
		} catch (Exception e) {
			throw new Exception("regist failed", e);
		}
	}

	@Override
	public boolean loginUser(String userId, String password) {
		User user = userDao.getUserById(userId);
		if(user == null || !user.getPassword().equals(password))
			return false;
		return true;
	}

	@Override
	public User detailUser(String userId) {
		return userDao.getUserById(userId);
	}

	@Override
	public void updateUser(String userId, String nickname, String email) {
        User user = userDao.getUserById(userId);
		user.setEmail(email);
        user.setNickname(nickname);
        userDao.updateUser(user);
	}

	@Override
	public boolean findPassword(String userId, String email) {
		User user = userDao.getUserById(userId);
		//아이디에 맞는 유저가 없거나 아이디로 찾은 사용자의 이메일과 일치하지 않는 경우
		if(user == null || !user.getEmail().equals(email))
			return false;
		return true;
	}

	@Override
	public boolean changePassword(String userId, String oldPassword, String newPassword) {
		User user = userDao.getUserById(userId);
		
		if(user.getPassword().equals(oldPassword)) {
			user.setPassword(newPassword);
			userDao.updateUser(user);
			return true;
		}
		return false;
	}

	@Override
	public boolean deleteUser(String userId) {
		User user = userDao.getUserById(userId);
		if(user != null) {
			userDao.deleteUser(userId);
			return true;
		}
		return false;
	}

	@Override
	public boolean checkEmail(String email) {
		return userDao.getUserByEmail(email) != null; //true 중복, false 중복x
	}

	@Override
	public boolean checkId(String userId) {
		return userDao.getUserById(userId) != null; //true 중복, false 중복x
	}

}
