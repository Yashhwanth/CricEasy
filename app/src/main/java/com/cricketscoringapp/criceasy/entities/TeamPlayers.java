package com.cricketscoringapp.criceasy.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(
        tableName = "TeamPlayers",
        primaryKeys = {"teamId", "playerId", "inningsId"},
        foreignKeys = {
                @ForeignKey(entity = Team.class, parentColumns = "teamId", childColumns = "teamId", onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Player.class, parentColumns = "playerId", childColumns = "playerId", onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Inning.class, parentColumns = "inningsId", childColumns = "inningsId", onDelete = ForeignKey.CASCADE)
        }
)
public class TeamPlayers {
    public int teamId; // References Teams
    public int playerId; // References Players
    public int inningsId; // References Innings
}
