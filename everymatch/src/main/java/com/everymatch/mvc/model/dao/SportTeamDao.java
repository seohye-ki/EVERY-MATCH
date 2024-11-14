package com.everymatch.mvc.model.dao;

import java.util.List;

import com.everymatch.mvc.model.dto.SportTeam;

public interface SportTeamDao {

	//전체 조회
	public List<SportTeam> getAllSportTeam();
	
	//team id로 정보 조회
	public SportTeam getSportTeamByTeamId(int teamId);
	
	//스포츠팀 추가
	public void insertSportTeam(SportTeam team);
    
	//스포츠팀 삭제
	public void deleteSportTeam(int teamId);
    	
	//스포츠팀 정보 업데이트
	public void updateSportTeam(SportTeam team);
	
}
