package com.everymatch.mvc.model.dao;

import java.util.List;

import com.everymatch.mvc.model.dto.Match;

public interface MatchDao {
	
	int insertMatch(Match match);
	
	int updateMatch(Match match);
	
	int deleteMatch(int matchId);
	
	List<Match> getMatchesByTime(String start, String end);
}
