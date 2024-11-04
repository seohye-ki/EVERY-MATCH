package com.ssafy.mvc.model.dao;

import com.ssafy.mvc.model.dto.User;

public interface UserDao {
    User getUserById(String userId);
    void insertUser(User user);
    void updateUser(User user);
    void deleteUser(String userId);
    boolean existsByEmail(String email);
    boolean validateUser(String userId, String password);
    void updatePassword(String userId, String newPassword);
}
