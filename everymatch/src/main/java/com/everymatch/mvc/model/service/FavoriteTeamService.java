package com.everymatch.mvc.model.service;

import java.util.List;

public interface FavoriteTeamService {
	
	//관심팀 설정
	public void updateFavoriteTeam(String userId, List<Integer> teamIds);
	
	//현재 관심팀 정보
	public List<Integer> getFavoriteTeams(String userId);
}
