package com.cricketscoringapp.criceasy.entities;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

@Entity(
        tableName = "Batsman",
        primaryKeys = {"player", "inningsId"},
        foreignKeys = {
                @ForeignKey(entity = Player.class, parentColumns = "playerId", childColumns = "player", onDelete = CASCADE),
                @ForeignKey(entity = Innings.class, parentColumns = "inningsId", childColumns = "inningsId", onDelete = CASCADE)
        },
        indices = {
                @Index(value = "inningsId")
        }
)
public class Batsman {
    @ColumnInfo(name = "player")
    public int player;

    @ColumnInfo(name = "inningsId")
    public int inningsId;

    @ColumnInfo(name = "score")
    public Integer score = 0;

    @ColumnInfo(name = "ballsPlayed")
    public Integer ballsPlayed = 0;

    @ColumnInfo(name = "zeroes")
    public Integer zeroes = 0;

    @ColumnInfo(name = "ones")
    public Integer ones = 0;

    @ColumnInfo(name = "twos")
    public Integer twos = 0;

    @ColumnInfo(name = "threes")
    public Integer threes = 0;

    @ColumnInfo(name = "fours")
    public Integer fours = 0;

    @ColumnInfo(name = "fives")
    public Integer fives = 0;

    @ColumnInfo(name = "sixes")
    public Integer sixes = 0;
}
