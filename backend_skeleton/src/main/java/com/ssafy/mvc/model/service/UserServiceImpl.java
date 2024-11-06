package com.ssafy.mvc.model.service;

import com.ssafy.mvc.model.dao.FavoriteTeamDao;
import com.ssafy.mvc.model.dao.UserDao;
import com.ssafy.mvc.model.dto.FavoriteTeam;
import com.ssafy.mvc.model.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private FavoriteTeamDao favoriteTeamDao;

    @Override
    public User getUserById(String userId) {
        return userDao.getUserById(userId);
    }

    @Override
    public void registerUser(User user) throws Exception {
        if (userDao.existsByEmail(user.getEmail())) {
            throw new Exception("Email already exists");
        }
        userDao.insertUser(user);
    }

    @Override
    public void updateUser(User user) {
        userDao.updateUser(user);
    }

    @Override
    public void deleteUser(String userId) {
        userDao.deleteUser(userId);
    }

    @Override
    public void resetPassword(String email) {
        // Generate new password and send email
    }

    @Override
    public void changePassword(String userId, String oldPassword, String newPassword) throws Exception {
        if (!userDao.validateUser(userId, oldPassword)) {
            throw new Exception("Current password is incorrect");
        }
        userDao.updatePassword(userId, newPassword);
    }

    @Override
    public List<User> getUsersForTeam(int teamId) {
        List<FavoriteTeam> favorites = favoriteTeamDao.getFavoriteTeamsByTeamId(teamId);
        return favorites.stream()
                .map(fav -> userDao.getUserById(fav.getUserId()))
                .collect(Collectors.toList());
    }
}
