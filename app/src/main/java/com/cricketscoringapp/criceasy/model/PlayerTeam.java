package com.cricketscoringapp.criceasy.model;

public class PlayerTeam {
    private int teamId; // Foreign key to Teams
    private int playerId; // Foreign key to Players

    // Getters and Setters
    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }
}

