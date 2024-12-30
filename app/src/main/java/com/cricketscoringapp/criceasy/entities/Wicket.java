package com.cricketscoringapp.criceasy.entities;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.*;

@Entity(
        tableName = "Wickets",
        foreignKeys = {
                @ForeignKey(entity = Ball.class, parentColumns = "ball_id", childColumns = "ball_id", onDelete = CASCADE),
                @ForeignKey(entity = Player.class, parentColumns = "player_id", childColumns = "wicket_batsman", onDelete = CASCADE),
                @ForeignKey(entity = Player.class, parentColumns = "player_id", childColumns = "wicket_contributor", onDelete = CASCADE)
        }
)
public class Wicket {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "wicket_id")
    public int wicketId;

    @ColumnInfo(name = "ball_id")
    public int ballId;

    @ColumnInfo(name = "wicket_type")
    public String wicketType;

    @ColumnInfo(name = "wicket_batsman")
    public int wicketBatsman;

    @ColumnInfo(name = "wicket_runs")
    public int wicketRuns;

    @ColumnInfo(name = "wicket_contributor")
    public int wicketContributor;
}
