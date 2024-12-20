package com.cricketscoringapp.criceasy.model;

public class MatchTeam {
    private int matchTeamId;
    private int matchId; // Foreign key to Matches
    private int teamId;  // Foreign key to Teams

    // Getters and Setters
    public int getMatchTeamId() {
        return matchTeamId;
    }

    public void setMatchTeamId(int matchTeamId) {
        this.matchTeamId = matchTeamId;
    }

    public int getMatchId() {
        return matchId;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }
}
