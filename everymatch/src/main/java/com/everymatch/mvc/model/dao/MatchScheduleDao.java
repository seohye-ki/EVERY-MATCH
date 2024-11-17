package com.everymatch.mvc.model.dao;

import java.util.List;

import com.everymatch.mvc.model.dto.MatchSchedule;

public interface MatchScheduleDao {
	
	List<MatchSchedule> getMatcheScheduleByUserFavoriteTeamId(String usrId);
	
	int insertMatchSchedule(MatchSchedule matchSchedule);
	
	int updateMatchSchedule(MatchSchedule matchSchedule);
	
	int deleteMatchSchedule(int matchId);
}