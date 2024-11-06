package com.everymatch.mvc.model.service;

import com.everymatch.mvc.model.dto.User;

public interface UserService {
	
	//회원가입
	boolean registUser(User user) throws Exception;
	
	//로그인
	boolean loginUser(String userId, String password);
	
	//회원 정보 상세
	User detailUser(String userId);
	
	//회원 정보 수정
	boolean updateUser(User user);
	
	//비밀번호 찾기
	boolean findPassword(String userId, String email);
	
	//비밀번호 변경
	boolean changePassword(String userId, String oldPassword, String newPassword);
	
	//회원 탈퇴
	void deleteUser(String userId);
	
	//이메일 중복확인
	boolean checkEmail(String email);
	
	//아이디 중복확인
	boolean checkId(String userId);
	
}
