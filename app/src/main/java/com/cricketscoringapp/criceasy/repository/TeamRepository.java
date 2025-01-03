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
            Log.d(TAG, "Fetching players for matchId: " + matchId);
            List<List<Player>> playersLists = databaseHelper.getPlayersForMatch(matchId);
            if (playersLists == null || playersLists.size() < 2) {
                Log.e(TAG, "Failed to fetch players. Players list is null or incomplete.");
                return null;
            }
            List<Player> team1Players = playersLists.get(0);
            List<Player> team2Players = playersLists.get(1);
            PlayerTeam playerTeam = new PlayerTeam(team1Players, team2Players);
            Log.d(TAG, "Returning PlayerTeam object: " + playerTeam.toString());
            return playerTeam;
        } catch (Exception e) {
            Log.e(TAG, "Error occurred in getPlayersForMatch: ", e);
            return null;
        } finally {
            Log.d(TAG, "getPlayersForMatch ended for matchId: " + matchId);
        }
    }
}
