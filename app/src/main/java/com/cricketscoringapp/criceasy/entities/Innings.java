package com.cricketscoringapp.criceasy.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "Innings",
        foreignKeys = {
                @ForeignKey(entity = Match.class, parentColumns = "matchId", childColumns = "matchId", onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Team.class, parentColumns = "teamId", childColumns = "teamBatting", onDelete = ForeignKey.CASCADE)
        }
)
public class Innings {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "inningsId")
    public int inningsId;
    @ColumnInfo(name = "inningsNumber")
    public int inningsNumber;

    @ColumnInfo(name = "matchId")
    public int matchId; // References Matches
    @ColumnInfo(name = "battingTeam")
    public int battingTeam; // References Teams
    @ColumnInfo(name = "isCompleted")
    public int isCompleted;
}
