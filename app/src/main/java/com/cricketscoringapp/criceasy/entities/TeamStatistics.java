package com.cricketscoringapp.criceasy.entities;
import static androidx.room.ForeignKey.CASCADE;

import androidx.room.*;
@Entity(
        tableName = "Team_Statistics",
        foreignKeys = {
                @ForeignKey(entity = Innings.class, parentColumns = "innings_id", childColumns = "innings_id", onDelete = CASCADE)
        }
)
public class TeamStatistics {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "team_stats_id")
    public int teamStatsId;

    @ColumnInfo(name = "innings_id")
    public int inningsId;

    @ColumnInfo(name = "runs")
    public int runs = 0;

    @ColumnInfo(name = "wickets")
    public int wickets = 0;

    @ColumnInfo(name = "balls")
    public int balls = 0;

    @ColumnInfo(name = "extras")
    public int extras = 0;
}
