package com.cricketscoringapp.criceasy.entities;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(
        tableName = "Bowler",
        primaryKeys = {"player", "inningsId"},
        foreignKeys = {
                @ForeignKey(entity = Player.class, parentColumns = "playerId", childColumns = "player", onDelete = CASCADE),
                @ForeignKey(entity = Innings.class, parentColumns = "inningsId", childColumns = "inningsId", onDelete = CASCADE)
        }
)
public class Bowler {
    @ColumnInfo(name = "player")
    public int player; // References Player

    @ColumnInfo(name = "inningsId")
    public int inningsId; // References Innings

    @ColumnInfo(name = "maidens")
    public int maidens = 0; // Default 0

    @ColumnInfo(name = "ballsPlayed")
    public int ballsPlayed = 0; // Default 0

    @ColumnInfo(name = "runs")
    public int runs = 0; // Default 0

    @ColumnInfo(name = "economy")
    public double economy = 0.0; // Default 0

    @ColumnInfo(name = "zeroes")
    public int zeroes = 0; // Default 0

    @ColumnInfo(name = "ones")
    public int ones = 0; // Default 0

    @ColumnInfo(name = "twos")
    public int twos = 0; // Default 0

    @ColumnInfo(name = "threes")
    public int threes = 0; // Default 0

    @ColumnInfo(name = "fours")
    public int fours = 0; // Default 0

    @ColumnInfo(name = "fives")
    public int fives = 0; // Default 0

    @ColumnInfo(name = "sixes")
    public int sixes = 0; // Default 0

    @ColumnInfo(name = "wickets")
    public int wickets = 0; // Wickets taken, Default 0

    @ColumnInfo(name = "byes")
    public int byes = 0; // Byes, Default 0

    @ColumnInfo(name = "legByes")
    public int legByes = 0; // Leg byes, Default 0

    @ColumnInfo(name = "wides")
    public int wides = 0; // Wides, Default 0

    @ColumnInfo(name = "noBalls")
    public int noBalls = 0; // No-balls, Default 0

    @ColumnInfo(name = "deadBalls")
    public int deadBalls = 0; // Dead balls, Default 0
}
