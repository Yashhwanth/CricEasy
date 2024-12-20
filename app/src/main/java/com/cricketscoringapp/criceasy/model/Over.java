package com.cricketscoringapp.criceasy.model;

public class Over {
    private int overId;
    private int inningsId;
    private int overNumber;
    private int playerId;
    private boolean isMaiden;

    // Constructor
    public Over(int overId, int inningsId, int overNumber, int playerId, boolean isMaiden) {
        this.overId = overId;
        this.inningsId = inningsId;
        this.overNumber = overNumber;
        this.playerId = playerId;
        this.isMaiden = isMaiden;
    }

    // Getters and Setters
    public int getOverId() {
        return overId;
    }

    public void setOverId(int overId) {
        this.overId = overId;
    }

    public int getInningsId() {
        return inningsId;
    }

    public void setInningsId(int inningsId) {
        this.inningsId = inningsId;
    }

    public int getOverNumber() {
        return overNumber;
    }

    public void setOverNumber(int overNumber) {
        this.overNumber = overNumber;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public boolean isMaiden() {
        return isMaiden;
    }

    public void setMaiden(boolean isMaiden) {
        this.isMaiden = isMaiden;
    }
}

