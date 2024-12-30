package com.cricketscoringapp.criceasy.entities;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.*;

@Entity(
        tableName = "Wicket",
        foreignKeys = {
                @ForeignKey(entity = Ball.class, parentColumns = "ballId", childColumns = "ballId", onDelete = CASCADE),
                @ForeignKey(entity = Player.class, parentColumns = "playerId", childColumns = "wicketBatsman", onDelete = CASCADE),
                @ForeignKey(entity = Player.class, parentColumns = "playerId", childColumns = "wicketContributor", onDelete = CASCADE)
        }
)
public class Wicket {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "wicketId")
    public int wicketId;

    @ColumnInfo(name = "ballId")
    public int ballId;

    @ColumnInfo(name = "wicketType")
    public String wicketType;

    @ColumnInfo(name = "wicket_batsman")
    public int wicketBatsman;

    @ColumnInfo(name = "runsInWicket")
    public int runsInWicket;

    @ColumnInfo(name = "wicketContributor")
    public int wicketContributor;
}
