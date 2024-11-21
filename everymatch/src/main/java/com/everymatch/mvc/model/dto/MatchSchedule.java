package com.everymatch.mvc.model.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class MatchSchedule {
    private int matchId;
    private LocalDate date;
    private LocalTime time;
    private String homeTeamName;
    private String awayTeamName;
    private String location;

    public MatchSchedule() {
    }

    public MatchSchedule(int matchId, LocalDate date, LocalTime time, String homeTeamName, String awayTeamName, String location) {
        this.matchId = matchId;
        this.date = date;
        this.time = time;
        this.homeTeamName = homeTeamName;
        this.awayTeamName = awayTeamName;
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAwayTeamName() {
        return awayTeamName;
    }

    public void setAwayTeamName(String awayTeamName) {
        this.awayTeamName = awayTeamName;
    }

    public String getHomeTeamName() {
        return homeTeamName;
    }

    public void setHomeTeamId(String homeTeamName) {
        this.homeTeamName = homeTeamName;
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
                ", homeTeamId=" + homeTeamName +
                ", awayTeamId=" + awayTeamName +
                ", location='" + location + '\'' +
                '}';
    }
}
