package com.cricketscoringapp.criceasy.model;

public class Batsman {
    private int playerId;
    private String playerName;
    private int inningsId;
    private int score;
    private int ballsPlayed;
    private int zeroes;
    private int ones;
    private int twos;
    private int threes;
    private int fours;
    private int fives;
    private int sixes;

    // Constructor
    public Batsman(int playerId, String playerName, int inningsId, int score, int ballsPlayed, int zeroes, int ones,
                  int twos, int threes, int fours, int fives, int sixes) {
        this.playerId = playerId;
        this.playerName = playerName;
        this.inningsId = inningsId;
        this.score = score;
        this.ballsPlayed = ballsPlayed;
        this.zeroes = zeroes;
        this.ones = ones;
        this.twos = twos;
        this.threes = threes;
        this.fours = fours;
        this.fives = fives;
        this.sixes = sixes;
    }

    // Getters and Setters for all fields
    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getInningsId() {
        return inningsId;
    }

    public void setInningsId(int inningsId) {
        this.inningsId = inningsId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getBallsPlayed() {
        return ballsPlayed;
    }

    public void setBallsPlayed(int ballsPlayed) {
        this.ballsPlayed = ballsPlayed;
    }

    public int getZeroes() {
        return zeroes;
    }

    public void setZeroes(int zeroes) {
        this.zeroes = zeroes;
    }

    public int getOnes() {
        return ones;
    }

    public void setOnes(int ones) {
        this.ones = ones;
    }

    public int getTwos() {
        return twos;
    }

    public void setTwos(int twos) {
        this.twos = twos;
    }

    public int getThrees() {
        return threes;
    }

    public void setThrees(int threes) {
        this.threes = threes;
    }

    public int getFours() {
        return fours;
    }

    public void setFours(int fours) {
        this.fours = fours;
    }

    public int getFives() {
        return fives;
    }

    public void setFives(int fives) {
        this.fives = fives;
    }

    public int getSixes() {
        return sixes;
    }

    public void setSixes(int sixes) {
        this.sixes = sixes;
    }
}
