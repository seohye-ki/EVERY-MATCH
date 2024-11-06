package com.ssafy.mvc.model.service;

import com.ssafy.mvc.model.dto.FavoriteTeam;

import java.util.List;

public interface FavoriteTeamService {
    List<FavoriteTeam> getFavoriteTeams(String userId);
    void updateFavoriteTeams(String userId, List<Integer> teamIds);
}
