package com.cricketscoringapp.criceasy.model;

import java.util.List;

public class PlayerTeam {
    private List<Player> team1Players;
    private List<Player> team2Players;

    public PlayerTeam(List<Player> team1Players, List<Player> team2Players) {
        this.team1Players = team1Players;
        this.team2Players = team2Players;
    }

    public List<Player> getTeam1Players() {
        return team1Players;
    }

    public void setTeam1Players(List<Player> team1Players) {
        this.team1Players = team1Players;
    }

    public List<Player> getTeam2Players() {
        return team2Players;
    }

    public void setTeam2Players(List<Player> team2Players) {
        this.team2Players = team2Players;
    }
}
