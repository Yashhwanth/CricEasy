package com.cricketscoringapp.criceasy.entities;
import static androidx.room.ForeignKey.CASCADE;

import androidx.room.*;
@Entity(
        tableName = "TeamStatistics",
        foreignKeys = {
                @ForeignKey(entity = Innings.class, parentColumns = "inningsId", childColumns = "inningsId", onDelete = CASCADE)
        },
        indices = {
                @Index(value = "inningsId"),  // Added index for placeName
        }
)
public class TeamStatistics {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "teamStatsId")
    public Integer teamStatsId;

    @ColumnInfo(name = "inningsId")
    public Integer inningsId;

    @ColumnInfo(name = "runs")
    public Integer runs = 0;

    @ColumnInfo(name = "wickets")
    public Integer wickets = 0;

    @ColumnInfo(name = "balls")
    public Integer balls = 0;

    @ColumnInfo(name = "extras")
    public Integer extras = 0;
}
