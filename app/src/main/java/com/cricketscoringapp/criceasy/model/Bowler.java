package com.cricketscoringapp.criceasy.model;

public class Bowler {
    private int playerId;
    private int inningsId;
    private String playerName;
    private int maidens;
    private int ballsPlayed;
    private int runs;
    private double economy;
    private int zeroes;
    private int ones;
    private int twos;
    private int threes;
    private int fours;
    private int fives;
    private int sixes;
    private int wk;
    private int bye;
    private int lb;
    private int wb;
    private int nb;
    private int db;

    public Bowler(int playerId, String playerName, int inningsId, int ballsPlayed, int maidens, int runs, int wickets) {
        this.playerId = playerId;
        this.playerName = playerName;
        this.inningsId = inningsId;
        this.ballsPlayed = ballsPlayed;
        this.maidens = maidens;
        this.runs = runs;
        this.wk = wickets;
    }
    // Getters and Setters

    public int getPlayerId() {
        return playerId;
    }

    public int getInningsId() {
        return inningsId;
    }

    public void setInningsId(int inningsId) {
        this.inningsId = inningsId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getMaidens() {
        return maidens;
    }

    public void setMaidens(int maidens) {
        this.maidens = maidens;
    }

    public int getBallsPlayed() {
        return ballsPlayed;
    }

    public void setBallsPlayed(int ballsPlayed) {
        this.ballsPlayed = ballsPlayed;
    }

    public int getRuns() {
        return runs;
    }

    public void setRuns(int runs) {
        this.runs = runs;
    }

    public double getEconomy() {
        return economy;
    }

    public void setEconomy(double economy) {
        this.economy = economy;
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

    public int getWk() {
        return wk;
    }

    public void setWk(int wk) {
        this.wk = wk;
    }

    public int getBye() {
        return bye;
    }

    public void setBye(int bye) {
        this.bye = bye;
    }

    public int getLb() {
        return lb;
    }

    public void setLb(int lb) {
        this.lb = lb;
    }

    public int getWb() {
        return wb;
    }

    public void setWb(int wb) {
        this.wb = wb;
    }

    public int getNb() {
        return nb;
    }

    public void setNb(int nb) {
        this.nb = nb;
    }

    public int getDb() {
        return db;
    }

    public void setDb(int db) {
        this.db = db;
    }
}

