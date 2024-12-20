package com.cricketscoringapp.criceasy.model;

public class Match {
    private int matchId;
    private String matchType;
    private int overs;
    private String ballType;
    private int location; // Foreign key to Places
    private String dateTime;
    private int toss; // Foreign key to Toss
    private boolean isMatchCompleted;
    private int matchWonBy; // Foreign key to Teams
    private String matchResult;

    // Getters and Setters
    public int getMatchId() {
        return matchId;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    public String getMatchType() {
        return matchType;
    }

    public void setMatchType(String matchType) {
        this.matchType = matchType;
    }

    public int getOvers() {
        return overs;
    }

    public void setOvers(int overs) {
        this.overs = overs;
    }

    public String getBallType() {
        return ballType;
    }

    public void setBallType(String ballType) {
        this.ballType = ballType;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public int getToss() {
        return toss;
    }

    public void setToss(int toss) {
        this.toss = toss;
    }

    public boolean isMatchCompleted() {
        return isMatchCompleted;
    }

    public void setMatchCompleted(boolean matchCompleted) {
        isMatchCompleted = matchCompleted;
    }

    public int getMatchWonBy() {
        return matchWonBy;
    }

    public void setMatchWonBy(int matchWonBy) {
        this.matchWonBy = matchWonBy;
    }

    public String getMatchResult() {
        return matchResult;
    }

    public void setMatchResult(String matchResult) {
        this.matchResult = matchResult;
    }
}


