package com.ssafy.mvc.model.dao;

import com.ssafy.mvc.model.dto.MatchSchedule;

import java.time.LocalDate;
import java.util.List;

public interface MatchScheduleDao {
    List<MatchSchedule> getMatchesByDate(LocalDate date);
    List<MatchSchedule> getMatchesByTeamId(int teamId);
    MatchSchedule getMatchById(int matchId);
}
