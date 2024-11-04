package com.ssafy.mvc.model.dto;

public class SportTeam {
    private int teamId;
    private String sportName;
    private String teamName;
    private String teamLogo;

    public SportTeam() {
    }

    public SportTeam(int teamId, String sportName, String teamName, String teamLogo) {
        this.teamId = teamId;
        this.sportName = sportName;
        this.teamName = teamName;
        this.teamLogo = teamLogo;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public String getSportName() {
        return sportName;
    }

    public void setSportName(String sportName) {
        this.sportName = sportName;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamLogo() {
        return teamLogo;
    }

    public void setTeamLogo(String teamLogo) {
        this.teamLogo = teamLogo;
    }

    @Override
    public String toString() {
        return "SportTeam{" +
                "teamId='" + teamId + '\'' +
                ", sportName='" + sportName + '\'' +
                ", teamName='" + teamName + '\'' +
                ", teamLogo='" + teamLogo + '\'' +
                '}';
    }
}
