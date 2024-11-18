package com.everymatch.mvc.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.everymatch.mvc.model.dao.SportTeamDao;
import com.everymatch.mvc.model.dto.SportTeam;

@Service
public class SportTeamServiceImpl implements SportTeamService {

	private final SportTeamDao sportTeamDao;
	
	public SportTeamServiceImpl(SportTeamDao sportTeamDao) {
		this.sportTeamDao = sportTeamDao;
	}
	
	@Override
	public List<SportTeam> getAllSportTeams() {
		List<SportTeam> allSportTeams = sportTeamDao.getAllSportTeam();
		return allSportTeams;
	}

	@Override
	public SportTeam getSportTeamByTeamId(Integer id) {
		SportTeam team = sportTeamDao.getSportTeamByTeamId(id);
		return team;
	}

	@Override
	public String getSportTeamNameByTeamId(int teamId) {
		String teamName = sportTeamDao.getSportTeamNameByTeamId(teamId);
		return teamName;
	}

}
