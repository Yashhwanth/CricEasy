package com.cricketscoringapp.criceasy.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "Toss",
        foreignKeys = {
                @ForeignKey(entity = Team.class, parentColumns = "teamId", childColumns = "tossCallingTeam"),
                @ForeignKey(entity = Team.class, parentColumns = "teamId", childColumns = "tossWinningTeam")
        }
)
public class Toss {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "tossId")
    public int tossId;
    @ColumnInfo(name = "tossCallingTeam")
    public int tossCallingTeam; // References Teams
    @ColumnInfo(name = "tossWinningTeam")
    public int tossWinningTeam; // References Teams
    @ColumnInfo(name = "tossWonTeamChooseTo")
    public String tossWonTeamChooseTo;
}
