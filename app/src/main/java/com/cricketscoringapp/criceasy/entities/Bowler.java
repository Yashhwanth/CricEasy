package com.cricketscoringapp.criceasy.entities;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

@Entity(
        tableName = "Bowler",
        primaryKeys = {"player", "inningsId"},
        foreignKeys = {
                @ForeignKey(entity = Player.class, parentColumns = "playerId", childColumns = "player", onDelete = CASCADE),
                @ForeignKey(entity = Innings.class, parentColumns = "inningsId", childColumns = "inningsId", onDelete = CASCADE)
        },
        indices = {
        @Index(value = "inningsId")
}
)
public class Bowler {
    @ColumnInfo(name = "player")
    public int player; // References Player

    @ColumnInfo(name = "inningsId")
    public int inningsId; // References Innings

    @ColumnInfo(name = "maidens")
    public Integer maidens = 0; // Default 0

    @ColumnInfo(name = "ballsPlayed")
    public Integer ballsPlayed = 0; // Default 0

    @ColumnInfo(name = "runs")
    public Integer runs = 0; // Default 0

    @ColumnInfo(name = "economy")
    public double economy = 0.0; // Default 0

    @ColumnInfo(name = "zeroes")
    public Integer zeroes = 0; // Default 0

    @ColumnInfo(name = "ones")
    public Integer ones = 0; // Default 0

    @ColumnInfo(name = "twos")
    public Integer twos = 0; // Default 0

    @ColumnInfo(name = "threes")
    public Integer threes = 0; // Default 0

    @ColumnInfo(name = "fours")
    public Integer fours = 0; // Default 0

    @ColumnInfo(name = "fives")
    public Integer fives = 0; // Default 0

    @ColumnInfo(name = "sixes")
    public Integer sixes = 0; // Default 0

    @ColumnInfo(name = "wickets")
    public Integer wickets = 0; // Wickets taken, Default 0

    @ColumnInfo(name = "byes")
    public Integer byes = 0; // Byes, Default 0

    @ColumnInfo(name = "legByes")
    public Integer legByes = 0; // Leg byes, Default 0

    @ColumnInfo(name = "wides")
    public Integer wides = 0; // Wides, Default 0

    @ColumnInfo(name = "noBalls")
    public Integer noBalls = 0; // No-balls, Default 0

    @ColumnInfo(name = "deadBalls")
    public Integer deadBalls = 0; // Dead balls, Default 0
}
