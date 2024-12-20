package com.cricketscoringapp.criceasy.model;

public class Wicket {
    private int wicketId;
    private int ballId;
    private String wicketType;
    private int wicketBatsman;
    private int wicketRuns;
    private int wicketContributor;

    // Constructor
    public Wicket(int wicketId, int ballId, String wicketType, int wicketBatsman, int wicketRuns, int wicketContributor) {
        this.wicketId = wicketId;
        this.ballId = ballId;
        this.wicketType = wicketType;
        this.wicketBatsman = wicketBatsman;
        this.wicketRuns = wicketRuns;
        this.wicketContributor = wicketContributor;
    }

    // Getters and Setters
    public int getWicketId() {
        return wicketId;
    }

    public void setWicketId(int wicketId) {
        this.wicketId = wicketId;
    }

    public int getBallId() {
        return ballId;
    }

    public void setBallId(int ballId) {
        this.ballId = ballId;
    }

    public String getWicketType() {
        return wicketType;
    }

    public void setWicketType(String wicketType) {
        this.wicketType = wicketType;
    }

    public int getWicketBatsman() {
        return wicketBatsman;
    }

    public void setWicketBatsman(int wicketBatsman) {
        this.wicketBatsman = wicketBatsman;
    }

    public int getWicketRuns() {
        return wicketRuns;
    }

    public void setWicketRuns(int wicketRuns) {
        this.wicketRuns = wicketRuns;
    }

    public int getWicketContributor() {
        return wicketContributor;
    }

    public void setWicketContributor(int wicketContributor) {
        this.wicketContributor = wicketContributor;
    }
}

