package com.cricketscoringapp.criceasy.entities;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.*;

@Entity(
        tableName = "Wicket",
        foreignKeys = {
                @ForeignKey(entity = Ball.class, parentColumns = "ballId", childColumns = "ballId", onDelete = CASCADE),
                @ForeignKey(entity = Player.class, parentColumns = "playerId", childColumns = "wicketBatsman", onDelete = CASCADE),
                @ForeignKey(entity = Player.class, parentColumns = "playerId", childColumns = "wicketContributor", onDelete = CASCADE)
        },
        indices = {
                @Index(value = "ballId"),  // Added index for placeName
                @Index(value = "wicketBatsman"),  // Added index for toss
                @Index(value = "wicketContributor")  // Added index for matchWonBy
        }
)
public class Wicket {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "wicketId")
    public Integer wicketId;

    @ColumnInfo(name = "ballId")
    public Integer ballId;

    @ColumnInfo(name = "wicketType")
    public String wicketType;

    @ColumnInfo(name = "wicketBatsman")
    public Integer wicketBatsman;

    @ColumnInfo(name = "runsInWicket")
    public Integer runsInWicket;

    @ColumnInfo(name = "wicketContributor")
    public Integer wicketContributor;
}
