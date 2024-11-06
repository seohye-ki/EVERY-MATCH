package com.ssafy.mvc.model.dao;

import com.ssafy.mvc.model.dto.SportTeam;

import java.util.List;

public interface SportTeamDao {
    List<SportTeam> getAllTeams();
    List<SportTeam> getTeamsBySport(String sportName);
    SportTeam getTeamById(int teamId);
}
