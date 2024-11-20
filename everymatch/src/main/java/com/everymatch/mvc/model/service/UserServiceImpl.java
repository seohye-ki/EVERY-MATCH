package com.everymatch.mvc.model.service;

import org.springframework.stereotype.Service;

import com.everymatch.mvc.model.dao.UserDao;
import com.everymatch.mvc.model.dto.User;
import com.everymatch.mvc.util.EmailUtil;
import com.everymatch.mvc.util.PasswordUtil;

@Service
public class UserServiceImpl implements UserService {

	private final UserDao userDao;
	private final EmailUtil emailUtil;
	
	public UserServiceImpl(UserDao userDao, EmailUtil emailUtil) {
		this.userDao = userDao;
		this.emailUtil = emailUtil;
	}
	
	@Override
	public void registerUser(User user) throws Exception {
		try {
			String hashedPassword = PasswordUtil.hashPassword(user.getPassword());
			user.setPassword(hashedPassword);
			userDao.insertUser(user);
		} catch (Exception e) {
			throw new Exception("등록 실패", e);
		}
	}

	@Override
	public boolean loginUser(String userId, String password) {
		User user = userDao.getUserById(userId);
		if(user == null)
			return false;
		return PasswordUtil.verifyPassword(password, user.getPassword());
	}

	@Override
	public User getUserDetails(String userId) {
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
	public boolean resetPassword(String userId, String email) {
		User user = userDao.getUserById(userId);
		//아이디에 맞는 유저가 없거나 아이디로 찾은 사용자의 이메일과 일치하지 않는 경우
		if(user == null || !user.getEmail().equals(email))
			return false;
		
		//임시 비밀번호 생성
		String temporaryPassword = PasswordUtil.generateRandomPassword();
		//암호화해서 DB업데이트
		String hashedPassword = PasswordUtil.hashPassword(temporaryPassword);
		user.setPassword(hashedPassword);
		userDao.updateUser(user);
		
		//이메일 전송
		String content = "<div style='text-align: center;'>"
	            + "<h1>임시 비밀번호 발급</h1>"
	            + "<p>안녕하세요, " + user.getNickname() + "님!</p>"
	            + "<p>요청하신 임시 비밀번호는 다음과 같습니다.</p>"
	            + "<h3>임시 비밀번호: " + temporaryPassword + "</h3>"
	            + "<p>로그인 후 비밀번호를 꼭 변경해 주세요.</p>"
	            + "</div>";
		emailUtil.sendEmail(user.getEmail(), "[EVERYMATCH]임시 비밀번호 발급 안내", content);
		return true;
	}

	@Override
	public boolean updatePassword(String userId, String oldPassword, String newPassword) {
		User user = userDao.getUserById(userId);
		if(user == null)
			return false;
		
		if(PasswordUtil.verifyPassword(oldPassword, user.getPassword())) {
			String hashedNewPassword = PasswordUtil.hashPassword(newPassword);
			user.setPassword(hashedNewPassword);
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
	public boolean isDuplicateEmail(String email) {
		return userDao.getUserByEmail(email) != null; //true 중복, false 중복x
	}

	@Override
	public boolean isDuplicateId(String userId) {
		return userDao.getUserById(userId) != null; //true 중복, false 중복x
	}

}
