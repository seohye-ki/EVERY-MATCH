package com.ssafy.mvc.model.service;

import com.ssafy.mvc.model.dao.FavoriteTeamDao;
import com.ssafy.mvc.model.dto.FavoriteTeam;
import com.ssafy.mvc.model.dto.SportTeam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteTeamServiceImpl implements FavoriteTeamService {

    @Autowired
    private FavoriteTeamDao favoriteTeamDao;

    @Override
    public List<FavoriteTeam> getFavoriteTeams(String userId) {
        return favoriteTeamDao.getFavoriteTeamsByUserId(userId);
    }

    @Override
    public void updateFavoriteTeams(String userId, List<Integer> teamIds) {
        // Delete existing favorite teams
        favoriteTeamDao.deleteFavoriteTeam(userId, 0);  // assuming a method to delete all for a user
        // Insert new favorite teams
        for (Integer teamId : teamIds) {
            FavoriteTeam newFavorite = new FavoriteTeam(userId, teamId);
            favoriteTeamDao.insertFavoriteTeam(newFavorite);
        }
    }
}
