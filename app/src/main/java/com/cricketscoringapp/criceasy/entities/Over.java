package com.cricketscoringapp.criceasy.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "Over",
        foreignKeys = {
                @ForeignKey(entity = Innings.class, parentColumns = "inningsId", childColumns = "inningsId", onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Player.class, parentColumns = "playerId", childColumns = "playerId", onDelete = ForeignKey.CASCADE)
        }
)
public class Over {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "overId")
    public int overId;

    @ColumnInfo(name = "inningsId")
    public int inningsId; // References Innings
    @ColumnInfo(name = "overNumber")
    public int overNumber;
    @ColumnInfo(name = "playerId")
    public int playerId; // References Players
    @ColumnInfo(name = "isMaiden")
    public int isMaiden;
}
