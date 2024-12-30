package com.cricketscoringapp.criceasy.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "Over",
        foreignKeys = {
                @ForeignKey(entity = Inning.class, parentColumns = "inningsId", childColumns = "inningsId", onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Player.class, parentColumns = "playerId", childColumns = "playerId", onDelete = ForeignKey.CASCADE)
        }
)
public class Over {
    @PrimaryKey(autoGenerate = true)
    public int overId;
    public int inningsId; // References Innings
    public int over;
    public int playerId; // References Players
    public int isMaiden;
}
