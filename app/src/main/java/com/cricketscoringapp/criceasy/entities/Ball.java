package com.cricketscoringapp.criceasy.entities;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "Ball",
        foreignKeys = {
                @ForeignKey(entity = Over.class, parentColumns = "overId", childColumns = "overId", onDelete = CASCADE),
                @ForeignKey(entity = Player.class, parentColumns = "playerId", childColumns = "striker", onDelete = CASCADE),
                @ForeignKey(entity = Player.class, parentColumns = "playerId", childColumns = "nonStriker", onDelete = CASCADE)
        },
        indices = {
                @Index(value = "overId"),
                @Index(value = "striker"),
                @Index(value = "nonStriker")
        }
)
public class Ball {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ballId")
    public Integer ballId;

    @ColumnInfo(name = "overId")
    public Integer overId;

    @ColumnInfo(name = "ballType")
    public String ballType;

    @ColumnInfo(name = "runs")
    public Integer runs;

    @ColumnInfo(name = "isWicket")
    public boolean isWicket;

    @ColumnInfo(name = "striker")
    public Integer striker;

    @ColumnInfo(name = "nonStriker")
    public Integer nonStriker;

    @ColumnInfo(name = "isSynced")
    public Integer isSynced = 0;
}
