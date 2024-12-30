package com.cricketscoringapp.criceasy.entities;
import static androidx.room.ForeignKey.CASCADE;

import androidx.room.*;
@Entity(
        tableName = "Extra",
        foreignKeys = {
                @ForeignKey(entity = Ball.class, parentColumns = "ballId", childColumns = "ballId", onDelete = CASCADE)
        },
        indices = {
        @Index(value = "ballId")
        }
)
public class Extra {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "extraId")
    public Integer extraId;

    @ColumnInfo(name = "ballId")
    public Integer ballId;

    @ColumnInfo(name = "extraType")
    public String extraType;

    @ColumnInfo(name = "runsInExtra")
    public Integer runsInExtra;
}
