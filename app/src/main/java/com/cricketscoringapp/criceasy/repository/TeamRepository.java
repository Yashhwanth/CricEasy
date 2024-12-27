package com.cricketscoringapp.criceasy.repository;

import android.util.Log;

import com.cricketscoringapp.criceasy.model.Player;
import com.cricketscoringapp.criceasy.model.PlayerTeam;
import com.cricketscoringapp.criceasy.Database.DatabaseHelper;

import java.util.List;

public class TeamRepository {
    private static final String TAG = "TeamRepository";

    private DatabaseHelper databaseHelper;

    public TeamRepository(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }

    public PlayerTeam getPlayersForMatch(int matchId) {
        Log.d(TAG, "getPlayersForMatch started for matchId: " + matchId);

        try {
            // Get the players for both teams
            Log.d(TAG, "Fetching players for matchId: " + matchId);
            List<List<Player>> playersLists = databaseHelper.getPlayersForMatch(matchId);

            if (playersLists == null || playersLists.size() < 2) {
                Log.e(TAG, "Failed to fetch players. Players list is null or incomplete.");
                return null; // Or handle this case based on your app's logic
            }

            // Extract the two teams' players using indexing
            List<Player> team1Players = playersLists.get(0); // First list for team 1
            List<Player> team2Players = playersLists.get(1); // Second list for team 2

            Log.d(TAG, "Fetched players successfully for matchId: " + matchId);
            Log.d(TAG, "Team 1 Players Count: " + team1Players.size());
            for (Player player : team1Players) {
                Log.d(TAG, "Team 1 Player: " + player.toString());
            }

            Log.d(TAG, "Team 2 Players Count: " + team2Players.size());
            for (Player player : team2Players) {
                Log.d(TAG, "Team 2 Player: " + player.toString());
            }

            // Return a PlayerTeam object containing both teams' players
            PlayerTeam playerTeam = new PlayerTeam(team1Players, team2Players);
            Log.d(TAG, "Returning PlayerTeam object: " + playerTeam.toString());
            return playerTeam;

        } catch (Exception e) {
            Log.e(TAG, "Error occurred in getPlayersForMatch: ", e);
            return null; // Or handle this case based on your app's logic
        } finally {
            Log.d(TAG, "getPlayersForMatch ended for matchId: " + matchId);
        }
    }
}
