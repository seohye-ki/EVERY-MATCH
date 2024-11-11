package com.everymatch.mvc.model.dto;

public class FavoriteTeam {
    private String userId;
    private int teamId;

    public FavoriteTeam() {
    }

    public FavoriteTeam(String userId, int teamId) {
        this.userId = userId;
        this.teamId = teamId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    @Override
    public String toString() {
        return "FavoriteTeam{" +
                "userId='" + userId + '\'' +
                ", teamId='" + teamId + '\'' +
                '}';
    }
}
