package com.cricketscoringapp.criceasy.model;

public class BallDetails {
    private String ballType;
    private int runs;
    private boolean isWicket;
    private String strikerName;
    private String nonStrikerName;
    private String bowlerName;
    public BallDetails(String ballType, int runs, boolean b, String strikerName, String nonStrikerName, String bowlerName) {
        this.ballType = ballType;
        this.runs = runs;
        this.isWicket = b;
        this.strikerName = strikerName;
        this.nonStrikerName = nonStrikerName;
        this.bowlerName = bowlerName;
    }
}
