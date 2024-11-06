package com.ssafy.mvc.model.dao;

import com.ssafy.mvc.model.dto.FavoriteTeam;

import java.util.List;

public interface FavoriteTeamDao {
    List<FavoriteTeam> getFavoriteTeamsByUserId(String userId);
    void insertFavoriteTeam(FavoriteTeam favoriteTeam);
    void deleteFavoriteTeam(String userId, int teamId);
    List<FavoriteTeam> getFavoriteTeamsByTeamId(int teamId);
}
