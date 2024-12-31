package com.cricketscoringapp.criceasy.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.cricketscoringapp.criceasy.entities.Player;

@Dao
public interface PlayerDao {
    // Check if player exists by name
    @Query("SELECT playerId FROM Player WHERE playerName = :playerName LIMIT 1")
    Long getPlayerIdByName(String playerName);

    // Insert a new player
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insertPlayer(Player player);
}
