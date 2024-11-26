package com.everymatch.mvc.model.dao;

import java.util.List;

import com.everymatch.mvc.model.dto.MatchSchedule;

public interface MatchScheduleDao {
	
	List<MatchSchedule> getMatchScheduleByUserFavoriteTeamId(String usrId);

	
}