package com.everymatch.mvc.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.everymatch.mvc.model.dao.MatchScheduleDao;
import com.everymatch.mvc.model.dto.MatchSchedule;

@Service
public class MatchScheduleServiceImpl implements MatchScheduleService {

	private final MatchScheduleDao matchScheduleDao;
	
	public MatchScheduleServiceImpl(MatchScheduleDao matchScheduleDao) {
		this.matchScheduleDao = matchScheduleDao;
	}
	
	@Override
	public List<MatchSchedule> getMatchScheduleByUserId(String userId) {
		return matchScheduleDao.getMatcheScheduleByUserFavoriteTeamId(userId);
	}
}
