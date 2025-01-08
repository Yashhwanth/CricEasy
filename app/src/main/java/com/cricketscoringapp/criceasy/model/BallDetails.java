package com.cricketscoringapp.criceasy.model;

public class BallDetails {
    private int ballId;
    private int ballNumber;
    private String ballType;
    private int runs;
    private boolean isWicket;
    private String strikerName;
    private String nonStrikerName;
    private String bowlerName;

    public BallDetails(int ballId, int ballNumber, String ballType, int runs, boolean isWicket, String strikerName, String nonStrikerName, String bowlerName) {
        this.ballId = ballId;
        this.ballNumber = ballNumber;
        this.ballType = ballType;
        this.runs = runs;
        this.isWicket = isWicket;
        this.strikerName = strikerName;
        this.nonStrikerName = nonStrikerName;
        this.bowlerName = bowlerName;
    }

    // Getters
    public int getBallId() {
        return ballId;
    }

    public void setBallId(int ballId) {
        this.ballId = ballId;
    }
    public int getBallNumber() {
        return ballNumber;
    }

    public void setBallNumber(int ballNumber) {
        this.ballNumber = ballNumber;
    }
    public String getBallType() {
        return ballType;
    }

    public int getRuns() {
        return runs;
    }

    public boolean isWicket() {
        return isWicket;
    }

    public String getStrikerName() {
        return strikerName;
    }

    public String getNonStrikerName() {
        return nonStrikerName;
    }

    public String getBowlerName() {
        return bowlerName;
    }

    // Setters
    public void setBallType(String ballType) {
        this.ballType = ballType;
    }

    public void setRuns(int runs) {
        this.runs = runs;
    }

    public void setWicket(boolean wicket) {
        isWicket = wicket;
    }

    public void setStrikerName(String strikerName) {
        this.strikerName = strikerName;
    }

    public void setNonStrikerName(String nonStrikerName) {
        this.nonStrikerName = nonStrikerName;
    }

    public void setBowlerName(String bowlerName) {
        this.bowlerName = bowlerName;
    }
}
