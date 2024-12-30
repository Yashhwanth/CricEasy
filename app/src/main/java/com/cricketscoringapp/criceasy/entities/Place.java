package com.cricketscoringapp.criceasy.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Place")
public class Place {
    @PrimaryKey(autoGenerate = true)
    public int placeId;
    public String placeName;
}
