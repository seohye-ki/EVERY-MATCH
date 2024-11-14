package com.everymatch.mvc.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.everymatch.mvc.model.dao.FavoriteTeamDao;
import com.everymatch.mvc.model.dao.UserDao;
import com.everymatch.mvc.model.dto.FavoriteTeam;

@Service
public class FavoriteTeamServiceImpl implements FavoriteTeamService {

	private final FavoriteTeamDao favoriteTeamDao;
	private final UserDao userDao;
	
	public FavoriteTeamServiceImpl(FavoriteTeamDao favoriteTeamDao, UserDao userDao) {
		this.favoriteTeamDao = favoriteTeamDao;
		this.userDao = userDao;
	}
	
	@Override
	public List<Integer> getFavoriteTeams(String userId) {
		if(userDao.getUserById(userId) == null)
			return null;
		return favoriteTeamDao.getFavoriteTeamsByUserId(userId);
	}
	
	@Override
	public void updateFavoriteTeam(String userId, List<Integer> teamIds) {
		favoriteTeamDao.deleteAllFavoriteTeamByUserId(userId);
		for(Integer teamId : teamIds) {
			favoriteTeamDao.insertFavoriteTeam(new FavoriteTeam(userId, teamId));
		}
	}
}
