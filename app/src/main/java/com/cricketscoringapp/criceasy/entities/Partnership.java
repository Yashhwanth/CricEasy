package com.cricketscoringapp.criceasy.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "Partnership",
        foreignKeys = {
                @ForeignKey(entity = Inning.class, parentColumns = "inningsId", childColumns = "inningsId", onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Player.class, parentColumns = "playerId", childColumns = "batsman1Id", onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Player.class, parentColumns = "playerId", childColumns = "batsman2Id", onDelete = ForeignKey.CASCADE)
        }
)
public class Partnership {
    @PrimaryKey(autoGenerate = true)
    public int partnershipId;
    public int inningsId; // References Innings
    public int batsman1Id; // References Players
    public int batsman2Id; // References Players
    public int runs;
    public int balls;
}
