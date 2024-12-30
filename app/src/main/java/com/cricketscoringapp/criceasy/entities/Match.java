package com.cricketscoringapp.criceasy.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "Match",
        foreignKeys = {
                @ForeignKey(entity = Place.class, parentColumns = "placeId", childColumns = "location"),
                @ForeignKey(entity = Toss.class, parentColumns = "tossId", childColumns = "toss"),
                @ForeignKey(entity = Team.class, parentColumns = "teamId", childColumns = "matchWonBy")
        }
)
public class Match {
    @PrimaryKey(autoGenerate = true)
    public int matchId;
    public String matchType;
    public int overs;
    public String ballType;
    public int location; // References Places
    public String dateTime;
    public int toss; // References Toss
    public int isMatchCompleted;
    public int matchWonBy; // References Teams
    public String matchResult;
}
