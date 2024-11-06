package com.everymatch.mvc.model.dao;

import java.util.List;

import com.everymatch.mvc.model.dto.User;

public interface UserDao {
	
	//유저 전체 가져오기
	List<User> getAllUser();
	
	//아이디로 찾기
	User getUserById(String userId);
	
	//유저 삽입
	void insertUser(User user);
    
	//유저 삭제
	void deleteUser(String userId);
    
	//유저 정보 수정
	void updateUser(User user);
    
}
