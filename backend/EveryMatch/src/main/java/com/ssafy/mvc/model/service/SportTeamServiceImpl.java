package com.ssafy.mvc.model.service;

import com.ssafy.mvc.model.dao.SportTeamDao;
import com.ssafy.mvc.model.dto.SportTeam;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SportTeamServiceImpl implements SportTeamService {
    private SportTeamDao sportTeamDao;

    @Override
    public List<SportTeam> getAllTeams() {
        return sportTeamDao.getAllTeams();
    }

    @Override
    public List<SportTeam> getTeamsBySport(String sportName) {
        return sportTeamDao.getTeamsBySport(sportName);
    }

}
