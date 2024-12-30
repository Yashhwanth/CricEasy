package com.cricketscoringapp.criceasy.entities;
import static androidx.room.ForeignKey.CASCADE;

import androidx.room.*;
@Entity(
        tableName = "Batsman",
        primaryKeys = {"player", "inningsId"},
        foreignKeys = {
                @ForeignKey(entity = Player.class, parentColumns = "playerId", childColumns = "player", onDelete = CASCADE),
                @ForeignKey(entity = Innings.class, parentColumns = "inningsId", childColumns = "inningsId", onDelete = CASCADE)
        }
)
public class Batsman {
    @ColumnInfo(name = "player")
    public int player;

    @ColumnInfo(name = "inningsId")
    public int inningsId;

    @ColumnInfo(name = "score")
    public int score = 0;

    @ColumnInfo(name = "ballsPlayed")
    public int ballsPlayed = 0;

    @ColumnInfo(name = "zeroes")
    public int zeroes = 0;

    @ColumnInfo(name = "ones")
    public int ones = 0;

    @ColumnInfo(name = "twos")
    public int twos = 0;

    @ColumnInfo(name = "threes")
    public int threes = 0;

    @ColumnInfo(name = "fours")
    public int fours = 0;

    @ColumnInfo(name = "fives")
    public int fives = 0;

    @ColumnInfo(name = "sixes")
    public int sixes = 0;
}
