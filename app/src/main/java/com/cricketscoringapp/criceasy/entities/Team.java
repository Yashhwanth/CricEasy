package com.cricketscoringapp.criceasy.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Team")
public class Team {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "teamId")
    public int teamId;
    @ColumnInfo(name = "teamName")
    public String teamName;
}
