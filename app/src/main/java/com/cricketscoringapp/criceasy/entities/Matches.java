package com.cricketscoringapp.criceasy.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "Matches",
        foreignKeys = {
                @ForeignKey(
                        entity = Place.class,
                        parentColumns = "placeId",  // Reference to 'placeId' column in Places table
                        childColumns = "placeName",  // 'location' in the Match table
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = Toss.class,
                        parentColumns = "tossId",  // Reference to 'tossId' column in Toss table
                        childColumns = "toss",  // 'toss' in the Match table
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = Team.class,
                        parentColumns = "teamId",  // Reference to 'teamId' column in Teams table
                        childColumns = "matchWonBy",  // 'matchWonBy' in the Match table
                        onDelete = ForeignKey.CASCADE
                )
        },
        indices = {
                @Index(value = "placeName"),  // Added index for placeName
                @Index(value = "toss"),  // Added index for toss
                @Index(value = "matchWonBy")  // Added index for matchWonBy
        }
)
public class Matches {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "matchId")  // Maps to COLUMN_MATCH_ID
    public Integer matchId;

    @ColumnInfo(name = "matchType")  // Maps to COLUMN_MATCH_TYPE
    public String matchType;

    @ColumnInfo(name = "numberOfOvers")  // Maps to COLUMN_NO_OF_OVERS
    public Integer numberOfOvers;

    @ColumnInfo(name = "ballType")  // Maps to COLUMN_BALL_TYPE
    public String ballType;

    @ColumnInfo(name = "placeName")  // Maps to COLUMN_PLACE_NAME
    public Integer placeName;  // References Places

    @ColumnInfo(name = "dateTime")  // Maps to COLUMN_DATE_TIME
    public String dateTime;

    @ColumnInfo(name = "toss")  // Maps to COLUMN_TOSS
    public Integer toss;  // References Toss

    @ColumnInfo(name = "isMatchCompleted")  // Maps to COLUMN_IS_MATCH_COMPLETED
    public Integer isMatchCompleted;

    @ColumnInfo(name = "matchWonBy")  // Maps to COLUMN_MATCH_WON_BY
    public Integer matchWonBy;  // References Teams

    @ColumnInfo(name = "matchResult")  // Maps to COLUMN_MATCH_RESULT
    public String matchResult;
}
