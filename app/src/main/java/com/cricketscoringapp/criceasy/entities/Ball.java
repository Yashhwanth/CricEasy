package com.cricketscoringapp.criceasy.entities;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.*;

@Entity(
        tableName = "Balls",
        foreignKeys = {
                @ForeignKey(entity = Over.class, parentColumns = "over_id", childColumns = "over_id", onDelete = CASCADE),
                @ForeignKey(entity = Player.class, parentColumns = "player_id", childColumns = "striker", onDelete = CASCADE),
                @ForeignKey(entity = Player.class, parentColumns = "player_id", childColumns = "non_striker", onDelete = CASCADE)
        }
)
public class Ball {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ball_id")
    public int ballId;

    @ColumnInfo(name = "over_id")
    public int overId;

    @ColumnInfo(name = "type_of_ball")
    public String typeOfBall;

    @ColumnInfo(name = "runs")
    public int runs;

    @ColumnInfo(name = "is_wicket")
    public boolean isWicket;

    @ColumnInfo(name = "striker")
    public int striker;

    @ColumnInfo(name = "non_striker")
    public int nonStriker;

    @ColumnInfo(name = "is_synced")
    public int isSynced = 0;
}
