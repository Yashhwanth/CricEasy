package com.cricketscoringapp.criceasy.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "MatchTeams",
        foreignKeys = {
                @ForeignKey(entity = Matches.class, parentColumns = "matchId", childColumns = "matchId"),
                @ForeignKey(entity = Team.class, parentColumns = "teamId", childColumns = "team1Id"),
                @ForeignKey(entity = Team.class, parentColumns = "teamId", childColumns = "team2Id")
        },
        indices = {
                @Index(value = "matchId"),  // Added index for placeName
                @Index(value = "team1Id"),  // Added index for toss
                @Index(value = "team2Id")  // Added index for matchWonBy
        }
)
public class MatchTeams {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "matchTeamsId")
    public Integer matchTeamsId;
    @ColumnInfo(name = "matchId")
    public Integer matchId; // References Matches
    @ColumnInfo(name = "team1Id")
    public Integer team1Id; // References Teams
    @ColumnInfo(name = "team2Id")
    public Integer team2Id; // References Teams
}
