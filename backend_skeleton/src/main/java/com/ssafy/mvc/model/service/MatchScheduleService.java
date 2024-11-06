package com.ssafy.mvc.model.service;

import com.ssafy.mvc.model.dto.MatchSchedule;

import java.time.LocalDate;
import java.util.List;

public interface MatchScheduleService {
    List<MatchSchedule> getDailyMatches(LocalDate date);
    void notifyUsersOneHourBeforeMatch(int matchId);
}
