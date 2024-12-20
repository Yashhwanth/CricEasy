package com.cricketscoringapp.criceasy.model;

public class TeamStatistics {
    private int teamStatsId;
    private int inningsId;
    private int runs;
    private int wickets;
    private int balls;
    private int extras;

    // Getters and Setters
    public int getTeamStatsId() {
        return teamStatsId;
    }

    public void setTeamStatsId(int teamStatsId) {
        this.teamStatsId = teamStatsId;
    }

    public int getInningsId() {
        return inningsId;
    }

    public void setInningsId(int inningsId) {
        this.inningsId = inningsId;
    }

    public int getRuns() {
        return runs;
    }

    public void setRuns(int runs) {
        this.runs = runs;
    }

    public int getWickets() {
        return wickets;
    }

    public void setWickets(int wickets) {
        this.wickets = wickets;
    }

    public int getBalls() {
        return balls;
    }

    public void setBalls(int balls) {
        this.balls = balls;
    }

    public int getExtras() {
        return extras;
    }

    public void setExtras(int extras) {
        this.extras = extras;
    }
}

