package com.cricketscoringapp.criceasy.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "Partnership",
        foreignKeys = {
                @ForeignKey(entity = Innings.class, parentColumns = "inningsId", childColumns = "inningsId", onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Player.class, parentColumns = "playerId", childColumns = "batsman1Id", onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Player.class, parentColumns = "playerId", childColumns = "batsman2Id", onDelete = ForeignKey.CASCADE)
        },
        indices = {
                @Index(value = "inningsId"),  // Added index for placeName
                @Index(value = "batsman1Id"),  // Added index for toss
                @Index(value = "batsman2Id")  // Added index for matchWonBy}
        }
)
public class Partnership {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "partnershipId")
    public Integer partnershipId;
    @ColumnInfo(name = "inningsId")
    public Integer inningsId; // References Innings
    @ColumnInfo(name = "batsman1Id")
    public Integer batsman1Id; // References Players
    @ColumnInfo(name = "batsman2Id")
    public Integer batsman2Id; // References Players

    @ColumnInfo(name = "runs")
    public Integer runs;
    @ColumnInfo(name = "balls")
    public Integer balls;
}
