package com.cricketscoringapp.criceasy.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Player")
public class Player {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "playerId")
    public int playerId;
    @ColumnInfo(name = "playerName")
    public String playerName; // Player name
}
