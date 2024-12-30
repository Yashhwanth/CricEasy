package com.cricketscoringapp.criceasy.model;

import java.util.List;

public class Team {
    private final int teamId;
    private final String teamName;
    public Team(int teamId, String teamName, List<Player> players) {
        this.teamId = teamId;
        this.teamName = teamName;
    }
    public int getTeamId() {
        return teamId;
    }

    public String getTeamName() {
        return teamName;
    }

}


