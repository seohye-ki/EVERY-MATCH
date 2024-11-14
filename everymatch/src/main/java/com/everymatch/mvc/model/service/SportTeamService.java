package com.everymatch.mvc.model.service;

import java.util.List;

import com.everymatch.mvc.model.dto.SportTeam;

public interface SportTeamService {

	List<SportTeam> getAllSportTeams();

	SportTeam getSportTeamByTeamId(Integer id);
	
}
