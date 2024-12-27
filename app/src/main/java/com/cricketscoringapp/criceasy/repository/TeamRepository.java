package com.cricketscoringapp.criceasy.repository;

import com.cricketscoringapp.criceasy.model.Player;
import com.cricketscoringapp.criceasy.model.PlayerTeam;
import com.cricketscoringapp.criceasy.Database.DatabaseHelper;

import java.util.List;

public class TeamRepository {

    private DatabaseHelper databaseHelper;

    public TeamRepository(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }
    public PlayerTeam getPlayersForMatch(int matchId) {
        // Get the players for both teams (this is assumed to be a List<List<Player>>)
        List<List<Player>> playersLists = databaseHelper.getPlayersForMatch(matchId);

        // Extract the two teams' players using indexing
        List<Player> team1Players = playersLists.get(0); // First list for team 1
        List<Player> team2Players = playersLists.get(1); // Second list for team 2

        // Return a TeamPlayers object containing both teams' players
        return new PlayerTeam(team1Players, team2Players);
    }
}
