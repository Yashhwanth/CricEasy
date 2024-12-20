package com.cricketscoringapp.criceasy.model;

public class Ball {
    private int ballId;
    private int overId;
    private String ballType;
    private int runs;
    private boolean isWicket;
    private int striker;
    private int nonStriker;
    private int isSynced;

    // Constructor
    public Ball(int ballId, int overId, String ballType, int runs, boolean isWicket, int striker, int nonStriker, int isSynced) {
        this.ballId = ballId;
        this.overId = overId;
        this.ballType = ballType;
        this.runs = runs;
        this.isWicket = isWicket;
        this.striker = striker;
        this.nonStriker = nonStriker;
        this.isSynced = isSynced;
    }

    // Getters and Setters
    public int getBallId() {
        return ballId;
    }

    public void setBallId(int ballId) {
        this.ballId = ballId;
    }

    public int getOverId() {
        return overId;
    }

    public void setOverId(int overId) {
        this.overId = overId;
    }

    public String getBallType() {
        return ballType;
    }

    public void setBallType(String ballType) {
        this.ballType = ballType;
    }

    public int getRuns() {
        return runs;
    }

    public void setRuns(int runs) {
        this.runs = runs;
    }

    public boolean isWicket() {
        return isWicket;
    }

    public void setWicket(boolean isWicket) {
        this.isWicket = isWicket;
    }

    public int getStriker() {
        return striker;
    }

    public void setStriker(int striker) {
        this.striker = striker;
    }

    public int getNonStriker() {
        return nonStriker;
    }

    public void setNonStriker(int nonStriker) {
        this.nonStriker = nonStriker;
    }

    public int getIsSynced() {
        return isSynced;
    }

    public void setIsSynced(int isSynced) {
        this.isSynced = isSynced;
    }
}

