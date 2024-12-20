package com.cricketscoringapp.criceasy.model;

public class Innings {
    private int inningsId;
    private int inningsNumber;
    private int matchId;
    private int teamBatting;
    private boolean isCompleted;

    // Constructor
    public Innings(int inningsId, int inningsNumber, int matchId, int teamBatting, boolean isCompleted) {
        this.inningsId = inningsId;
        this.inningsNumber = inningsNumber;
        this.matchId = matchId;
        this.teamBatting = teamBatting;
        this.isCompleted = isCompleted;
    }

    // Getters and Setters
    public int getInningsId() {
        return inningsId;
    }

    public void setInningsId(int inningsId) {
        this.inningsId = inningsId;
    }

    public int getInningsNumber() {
        return inningsNumber;
    }

    public void setInningsNumber(int inningsNumber) {
        this.inningsNumber = inningsNumber;
    }

    public int getMatchId() {
        return matchId;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    public int getTeamBatting() {
        return teamBatting;
    }

    public void setTeamBatting(int teamBatting) {
        this.teamBatting = teamBatting;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }
}

