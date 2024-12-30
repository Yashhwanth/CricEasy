package com.cricketscoringapp.criceasy.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "MatchTeams",
        foreignKeys = {
                @ForeignKey(entity = Match.class, parentColumns = "matchId", childColumns = "matchId"),
                @ForeignKey(entity = Team.class, parentColumns = "teamId", childColumns = "team1Id"),
                @ForeignKey(entity = Team.class, parentColumns = "teamId", childColumns = "team2Id")
        }
)
public class MatchTeams {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "matchTeamsId")
    public int matchTeamsId;
    @ColumnInfo(name = "matchId")
    public int matchId; // References Matches
    @ColumnInfo(name = "team1Id")
    public int team1Id; // References Teams
    @ColumnInfo(name = "team2Id")
    public int team2Id; // References Teams
}
