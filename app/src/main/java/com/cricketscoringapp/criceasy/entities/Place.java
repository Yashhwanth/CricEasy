package com.cricketscoringapp.criceasy.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Place")
public class Place {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "placeId")
    public int placeId;
    @ColumnInfo(name = "placeName")
    public String placeName;
}
