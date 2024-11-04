package com.ssafy.mvc.model.dto;

public class MatchSchedule {
    private int matchId;
    private String sportName;
    private int date;
    private int time;
    private int homeTeamId;
    private int awayTeamId;
    private String location;

    public MatchSchedule() {
    }

    public MatchSchedule(int matchId, String sportName, int date, int time, int homeTeamId, int awayTeamId, String location) {
        this.matchId = matchId;
        this.sportName = sportName;
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

    public void setAwayTeamId(int awayTeamId) {
        this.awayTeamId = awayTeamId;
    }

    public int getHomeTeamId() {
        return homeTeamId;
    }

    public void setHomeTeamId(int homeTeamId) {
        this.homeTeamId = homeTeamId;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public String getSportName() {
        return sportName;
    }

    public void setSportName(String sportName) {
        this.sportName = sportName;
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
                ", sportName='" + sportName + '\'' +
                ", date=" + date +
                ", time=" + time +
                ", homeTeamId=" + homeTeamId +
                ", awayTeamId=" + awayTeamId +
                ", location='" + location + '\'' +
                '}';
    }
}
