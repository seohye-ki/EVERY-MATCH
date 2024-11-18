package com.everymatch.mvc.model.dao;

import java.util.List;

import com.everymatch.mvc.model.dto.FavoriteTeam;
import com.everymatch.mvc.model.dto.User;

public interface FavoriteTeamDao {
    
    //user가 좋아하는 팀 목록 
    public List<Integer> getFavoriteTeamsByUserId(String UserId);

    //favorite team으로 설정한 user 목록 
    public List<User> getUsersByTeamId(int teamId);
    
    //유저기준 모든 행 삭제  
    public void deleteAllFavoriteTeamByUserId(String userId);
    
    //팀기준 모든 행 삭제 
    public void deleteAllFavoriteTeamByTeamId(int teamId);
    
    //한 행 추가 
    public void insertFavoriteTeam(FavoriteTeam ft);

    //홈팀 또는 어웨이팀을 관심팀으로 설정한 유저 아이디 반환 
	public List<String> getUserIdsByTeamIds(int homeTeamId, int awayTeamId);
}