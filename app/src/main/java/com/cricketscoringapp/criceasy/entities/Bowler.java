package com.cricketscoringapp.criceasy.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "Bowler",
        foreignKeys = {
                @ForeignKey(entity = Player.class, parentColumns = "player_id", childColumns = "player", onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Innings.class, parentColumns = "innings_id", childColumns = "inningsId", onDelete = ForeignKey.CASCADE)
        }
)
public class Bowler {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public int inningsId; // References Innings
    public int player; // References Player

    public int maidens; // Default 0
    public int ballsPlayed; // Default 0
    public int runs; // Default 0
    public double economy; // Default 0
    public int zeroes; // Default 0
    public int ones; // Default 0
    public int twos; // Default 0
    public int threes; // Default 0
    public int fours; // Default 0
    public int fives; // Default 0
    public int sixes; // Default 0
    public int wk; // Wickets taken
    public int by; // Byes
    public int lb; // Leg byes
    public int wb; // Wides
    public int nb; // No-balls
    public int db; // Dead balls
}
