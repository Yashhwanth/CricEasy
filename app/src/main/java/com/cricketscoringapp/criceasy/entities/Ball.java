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
                @ForeignKey(entity = Over.class, parentColumns = "overId", childColumns = "overId", onDelete = CASCADE),
                @ForeignKey(entity = Player.class, parentColumns = "playerId", childColumns = "striker", onDelete = CASCADE),
                @ForeignKey(entity = Player.class, parentColumns = "playerId", childColumns = "nonStriker", onDelete = CASCADE)
        }
)
public class Ball {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ballId")
    public int ballId;

    @ColumnInfo(name = "overId")
    public int overId;

    @ColumnInfo(name = "ballType")
    public String ballType;

    @ColumnInfo(name = "runs")
    public int runs;

    @ColumnInfo(name = "isWicket")
    public boolean isWicket;

    @ColumnInfo(name = "striker")
    public int striker;

    @ColumnInfo(name = "nonStriker")
    public int nonStriker;

    @ColumnInfo(name = "isSynced")
    public int isSynced = 0;
}
