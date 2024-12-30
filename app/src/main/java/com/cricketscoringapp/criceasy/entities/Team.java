package com.cricketscoringapp.criceasy.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Team")
public class Team {
    @PrimaryKey(autoGenerate = true)
    public int teamId;
    public String teamName;
}
