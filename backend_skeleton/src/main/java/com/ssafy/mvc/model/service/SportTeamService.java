package com.ssafy.mvc.model.service;

import com.ssafy.mvc.model.dto.SportTeam;

import java.util.List;

public interface SportTeamService {
    List<SportTeam> getAllTeams();
    List<SportTeam> getTeamsBySport(String sportName);
}
