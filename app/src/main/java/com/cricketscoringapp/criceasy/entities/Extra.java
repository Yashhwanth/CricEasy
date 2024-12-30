package com.cricketscoringapp.criceasy.entities;
import static androidx.room.ForeignKey.CASCADE;

import androidx.room.*;
@Entity(
        tableName = "Extra",
        foreignKeys = {
                @ForeignKey(entity = Ball.class, parentColumns = "ballId", childColumns = "ballId", onDelete = CASCADE)
        }
)
public class Extra {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "extraId")
    public int extraId;

    @ColumnInfo(name = "ballId")
    public int ballId;

    @ColumnInfo(name = "extraType")
    public String extraType;

    @ColumnInfo(name = "runsInExtra")
    public int runsInExtra;
}
