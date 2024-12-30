package com.cricketscoringapp.criceasy.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "Toss",
        foreignKeys = {
                @ForeignKey(entity = Team.class, parentColumns = "teamId", childColumns = "tossCallBy"),
                @ForeignKey(entity = Team.class, parentColumns = "teamId", childColumns = "tossWonBy")
        }
)
public class Toss {
    @PrimaryKey(autoGenerate = true)
    public int tossId;
    public int tossCallBy; // References Teams
    public int tossWonBy; // References Teams
    public String tossWonTeamChooseTo;
}
