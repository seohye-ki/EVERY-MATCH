package com.everymatch.mvc.model.service;

import com.everymatch.mvc.model.dto.User;

public interface UserService {
	
	//회원가입
	public void registUser(User user) throws Exception;
	
	//로그인
	public boolean loginUser(String userId, String password);
	
	//회원 정보 상세
	public User detailUser(String userId);
	
	//회원 정보 수정
	public void updateUser(String userId, String nickname, String email);
	
	//비밀번호 찾기
	public boolean findPassword(String userId, String email);
	
	//비밀번호 변경
	public boolean changePassword(String userId, String oldPassword, String newPassword);
	
	//회원 탈퇴
	public boolean deleteUser(String userId);
	
	//이메일 중복확인
	public boolean checkEmail(String email);
	
	//아이디 중복확인
	public boolean checkId(String userId);
	
}
