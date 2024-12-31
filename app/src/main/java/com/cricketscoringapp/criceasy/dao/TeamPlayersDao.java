package com.cricketscoringapp.criceasy.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.cricketscoringapp.criceasy.entities.TeamPlayers;

@Dao
public interface TeamPlayersDao {

    // Check if the relation already exists
    @Query("SELECT COUNT(*) FROM TeamPlayers WHERE teamId = :teamId AND playerId = :playerId AND inningsId = :inningsId")
    int checkPlayerTeamRelation(int teamId, int playerId, int inningsId);

    // Insert a new relation
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insertTeamPlayer(TeamPlayers teamPlayer);
}
