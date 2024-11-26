package com.everymatch.mvc.model.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class Match {
	private int matchId;
	private LocalDate date;
	private LocalTime time;
	private int homeTeamId;
	private int awayTeamId;
	private String location;

	public Match() {
	}

	public Match(int matchId, LocalDate date, LocalTime time, int homeTeamId, int awayTeamId, String location) {
		this.matchId = matchId;
		this.date = date;
		this.time = time;
		this.homeTeamId = homeTeamId;
		this.awayTeamId = awayTeamId;
		this.location = location;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getAwayTeamId() {
		return awayTeamId;
	}

	public void setAwayTeamName(int awayTeamId) {
		this.awayTeamId = awayTeamId;
	}

	public int getHomeTeamId() {
		return homeTeamId;
	}

	public void setHomeTeamId(int homeTeamId) {
		this.homeTeamId = homeTeamId;
	}

	public LocalTime getTime() {
		return time;
	}

	public void setTime(LocalTime time) {
		this.time = time;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public int getMatchId() {
		return matchId;
	}

	public void setMatchId(int matchId) {
		this.matchId = matchId;
	}

	@Override
	public String toString() {
		return "MatchSchedule{" +
				"matchId=" + matchId +
				", date=" + date +
				", time=" + time +
				", homeTeamId=" + homeTeamId +
				", awayTeamId=" + awayTeamId +
				", location='" + location + '\'' +
				'}';
	}
}
