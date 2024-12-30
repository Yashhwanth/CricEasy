package com.cricketscoringapp.criceasy.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Player")
public class Player {
    @PrimaryKey(autoGenerate = true)
    public int playerId;
    public String name; // Player name
}
