package com.cricketscoringapp.criceasy.entities;
import static androidx.room.ForeignKey.CASCADE;

import androidx.room.*;
@Entity(
        tableName = "Extras",
        foreignKeys = {
                @ForeignKey(entity = Ball.class, parentColumns = "ball_id", childColumns = "ball_id", onDelete = CASCADE)
        }
)
public class Extra {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "extra_id")
    public int extraId;

    @ColumnInfo(name = "ball_id")
    public int ballId;

    @ColumnInfo(name = "extra_type")
    public String extraType;

    @ColumnInfo(name = "extra_runs")
    public int extraRuns;
}
