package com.cricketscoringapp.criceasy.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "Over",
        foreignKeys = {
                @ForeignKey(entity = Innings.class, parentColumns = "inningsId", childColumns = "inningsId", onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Player.class, parentColumns = "playerId", childColumns = "playerId", onDelete = ForeignKey.CASCADE)
        },
        indices = {
                @Index(value = "inningsId"),  // Added index for placeName
                @Index(value = "playerId"),  // Added index for toss
        }
)
public class Over {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "overId")
    public Integer overId;

    @ColumnInfo(name = "inningsId")
    public Integer inningsId; // References Innings
    @ColumnInfo(name = "overNumber")
    public Integer overNumber;
    @ColumnInfo(name = "playerId")
    public Integer playerId; // References Players
    @ColumnInfo(name = "isMaiden")
    public Integer isMaiden;
}
