package com.cricketscoringapp.criceasy.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "Toss",
        foreignKeys = {
                @ForeignKey(entity = Team.class, parentColumns = "teamId", childColumns = "tossCallingTeam"),
                @ForeignKey(entity = Team.class, parentColumns = "teamId", childColumns = "tossWinningTeam")
        },
        indices = {
                @Index(value = "tossCallingTeam"),  // Added index for placeName
                @Index(value = "tossWinningTeam")  // Added index for toss
        }

)
public class Toss {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "tossId")
    public Integer tossId;
    @ColumnInfo(name = "tossCallingTeam")
    public Integer tossCallingTeam; // References Teams
    @ColumnInfo(name = "tossWinningTeam")
    public Integer tossWinningTeam; // References Teams
    @ColumnInfo(name = "tossWonTeamChooseTo")
    public String tossWonTeamChooseTo;
}
