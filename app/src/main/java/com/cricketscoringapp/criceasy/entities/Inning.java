package com.cricketscoringapp.criceasy.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "Inning",
        foreignKeys = {
                @ForeignKey(entity = Match.class, parentColumns = "matchId", childColumns = "matchId", onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Team.class, parentColumns = "teamId", childColumns = "teamBatting", onDelete = ForeignKey.CASCADE)
        }
)
public class Inning {
    @PrimaryKey(autoGenerate = true)
    public int inningsId;
    public int inningsNumber;
    public int matchId; // References Matches
    public int teamBatting; // References Teams
    public int isCompleted;
}
