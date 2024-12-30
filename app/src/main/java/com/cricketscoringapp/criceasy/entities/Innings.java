package com.cricketscoringapp.criceasy.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "Innings",
        foreignKeys = {
                @ForeignKey(entity = Matches.class, parentColumns = "matchId", childColumns = "matchId", onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Team.class, parentColumns = "teamId", childColumns = "battingTeam", onDelete = ForeignKey.CASCADE) // Fixed the column reference here
        },
        indices = {
                @Index(value = "matchId"),
                @Index(value = "battingTeam") // Added index for matchId column
        }
)
public class Innings {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "inningsId")
    public Integer inningsId;

    @ColumnInfo(name = "inningsNumber")
    public Integer inningsNumber;

    @ColumnInfo(name = "matchId")
    public Integer matchId; // References Matches

    @ColumnInfo(name = "battingTeam") // This corresponds to the foreign key, renamed to match your foreign key reference
    public Integer battingTeam; // References Teams

    @ColumnInfo(name = "isCompleted")
    public Integer isCompleted;
}
