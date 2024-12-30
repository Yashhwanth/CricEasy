package com.cricketscoringapp.criceasy.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "Partnership",
        foreignKeys = {
                @ForeignKey(entity = Innings.class, parentColumns = "inningsId", childColumns = "inningsId", onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Player.class, parentColumns = "playerId", childColumns = "batsman1Id", onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Player.class, parentColumns = "playerId", childColumns = "batsman2Id", onDelete = ForeignKey.CASCADE)
        }
)
public class Partnership {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "matchId")
    public int partnershipId;
    @ColumnInfo(name = "matchId")
    public int inningsId; // References Innings
    @ColumnInfo(name = "matchId")
    public int batsman1Id; // References Players
    @ColumnInfo(name = "matchId")
    public int batsman2Id; // References Players

    @ColumnInfo(name = "matchId")
    public int runs;
    @ColumnInfo(name = "matchId")
    public int balls;
}
