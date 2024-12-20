package com.cricketscoringapp.criceasy.model;

public class Partnership {
    private int partnershipId;
    private int inningsId;
    private int batsman1Id;
    private int batsman2Id;
    private int runs;
    private int balls;

    // Constructor
    public Partnership(int partnershipId, int inningsId, int batsman1Id, int batsman2Id, int runs, int balls) {
        this.partnershipId = partnershipId;
        this.inningsId = inningsId;
        this.batsman1Id = batsman1Id;
        this.batsman2Id = batsman2Id;
        this.runs = runs;
        this.balls = balls;
    }

    // Getters and Setters
    public int getPartnershipId() {
        return partnershipId;
    }

    public void setPartnershipId(int partnershipId) {
        this.partnershipId = partnershipId;
    }

    public int getInningsId() {
        return inningsId;
    }

    public void setInningsId(int inningsId) {
        this.inningsId = inningsId;
    }

    public int getBatsman1Id() {
        return batsman1Id;
    }

    public void setBatsman1Id(int batsman1Id) {
        this.batsman1Id = batsman1Id;
    }

    public int getBatsman2Id() {
        return batsman2Id;
    }

    public void setBatsman2Id(int batsman2Id) {
        this.batsman2Id = batsman2Id;
    }

    public int getRuns() {
        return runs;
    }

    public void setRuns(int runs) {
        this.runs = runs;
    }

    public int getBalls() {
        return balls;
    }

    public void setBalls(int balls) {
        this.balls = balls;
    }
}

