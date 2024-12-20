package com.cricketscoringapp.criceasy.model;

public class Toss {
    private int tossId;
    private int tossCallBy; // Foreign key to Teams
    private int tossWonBy;  // Foreign key to Teams
    private String tossWonTeamChooseTo;

    // Getters and Setters
    public int getTossId() {
        return tossId;
    }

    public void setTossId(int tossId) {
        this.tossId = tossId;
    }

    public int getTossCallBy() {
        return tossCallBy;
    }

    public void setTossCallBy(int tossCallBy) {
        this.tossCallBy = tossCallBy;
    }

    public int getTossWonBy() {
        return tossWonBy;
    }

    public void setTossWonBy(int tossWonBy) {
        this.tossWonBy = tossWonBy;
    }

    public String getTossWonTeamChooseTo() {
        return tossWonTeamChooseTo;
    }

    public void setTossWonTeamChooseTo(String tossWonTeamChooseTo) {
        this.tossWonTeamChooseTo = tossWonTeamChooseTo;
    }
}

