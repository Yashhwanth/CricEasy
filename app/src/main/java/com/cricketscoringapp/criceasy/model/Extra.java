package com.cricketscoringapp.criceasy.model;

public class Extra {
    private int extraId;
    private int ballId;
    private String extraType;
    private int extraRuns;

    // Constructor
    public Extra(int extraId, int ballId, String extraType, int extraRuns) {
        this.extraId = extraId;
        this.ballId = ballId;
        this.extraType = extraType;
        this.extraRuns = extraRuns;
    }

    // Getters and Setters
    public int getExtraId() {
        return extraId;
    }

    public void setExtraId(int extraId) {
        this.extraId = extraId;
    }

    public int getBallId() {
        return ballId;
    }

    public void setBallId(int ballId) {
        this.ballId = ballId;
    }

    public String getExtraType() {
        return extraType;
    }

    public void setExtraType(String extraType) {
        this.extraType = extraType;
    }

    public int getExtraRuns() {
        return extraRuns;
    }

    public void setExtraRuns(int extraRuns) {
        this.extraRuns = extraRuns;
    }
}

