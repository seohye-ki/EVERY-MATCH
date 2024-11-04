package com.ssafy.mvc.model.service;

import com.ssafy.mvc.model.dto.User;

import java.util.List;

public interface UserService {
    User getUserById(String userId);
    void registerUser(User user) throws Exception;
    void updateUser(User user);
    void deleteUser(String userId);
    void resetPassword(String email);
    void changePassword(String userId, String oldPassword, String newPassword) throws Exception;
    List<User> getUsersForTeam(int teamId);
}
