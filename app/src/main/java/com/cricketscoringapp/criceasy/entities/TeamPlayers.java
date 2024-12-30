package com.cricketscoringapp.criceasy.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(
        tableName = "TeamPlayers",
        primaryKeys = {"teamId", "playerId", "inningsId"},
        foreignKeys = {
                @ForeignKey(entity = Team.class, parentColumns = "teamId", childColumns = "teamId", onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Player.class, parentColumns = "playerId", childColumns = "playerId", onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Innings.class, parentColumns = "inningsId", childColumns = "inningsId", onDelete = ForeignKey.CASCADE)
        }
)
public class TeamPlayers {
    @ColumnInfo(name = "teamId")
    public int teamId; // References Teams
    @ColumnInfo(name = "playerId")
    public int playerId; // References Players

    @ColumnInfo(name = "inningsId")
    public int inningsId; // References Innings
}
