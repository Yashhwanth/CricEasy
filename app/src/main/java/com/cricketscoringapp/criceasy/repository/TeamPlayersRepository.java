package com.cricketscoringapp.criceasy.repository;

import android.util.Log;
import com.cricketscoringapp.criceasy.dao.TeamPlayersDao;
import com.cricketscoringapp.criceasy.entities.TeamPlayers;

public class TeamPlayersRepository {

    private final TeamPlayersDao teamPlayersDao;

    public TeamPlayersRepository(TeamPlayersDao teamPlayersDao) {
        this.teamPlayersDao = teamPlayersDao;
    }

    public void addPlayerToTeam(int teamId, int playerId, int inningsId) {
        if (teamPlayersDao.checkPlayerTeamRelation(teamId, playerId, inningsId) == 0) {
            TeamPlayers teamPlayer = new TeamPlayers();
            teamPlayer.teamId = teamId;
            teamPlayer.playerId = playerId;
            teamPlayer.inningsId = inningsId;
            long result = teamPlayersDao.insertTeamPlayer(teamPlayer);
            if (result == -1) {
                Log.e("TeamPlayers", "Failed to insert relation");
            } else {
                Log.d("TeamPlayers", "Relation inserted successfully");
            }
        } else {
            Log.d("TeamPlayers", "Relation already exists");
        }
    }
}
