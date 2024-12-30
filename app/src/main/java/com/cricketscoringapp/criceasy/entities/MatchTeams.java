package com.cricketscoringapp.criceasy.entities;

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
    public int matchesTeamsId;
    public int matchId; // References Matches
    public int team1Id; // References Teams
    public int team2Id; // References Teams
}
