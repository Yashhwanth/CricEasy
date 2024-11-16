package com.cricketscoringapp.criceasy.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PartnershipsTableDBH extends SQLiteOpenHelper {

    // Database version and name
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "CricketMatchDB";

    // Table and column names
    private static final String TABLE_PARTNERSHIPS = "partnerships";
    private static final String COLUMN_PARTNERSHIP_ID = "partnership_id";
    private static final String COLUMN_MATCH_ID = "match_id";
    private static final String COLUMN_INNINGS_ID = "innings_id";
    private static final String COLUMN_BATSMAN1_ID = "batsman1_id";
    private static final String COLUMN_BATSMAN2_ID = "batsman2_id";
    private static final String COLUMN_RUNS = "runs";
    private static final String COLUMN_BALLS = "balls";

    // SQL command to create the partnerships table
    private static final String CREATE_TABLE_PARTNERSHIPS = "CREATE TABLE " + TABLE_PARTNERSHIPS + " (" +
            COLUMN_PARTNERSHIP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_MATCH_ID + " INTEGER, " +
            COLUMN_INNINGS_ID + " INTEGER, " +
            COLUMN_BATSMAN1_ID + " INTEGER, " +
            COLUMN_BATSMAN2_ID + " INTEGER, " +
            COLUMN_RUNS + " INTEGER, " +
            COLUMN_BALLS + " INTEGER, " +
            "FOREIGN KEY(" + COLUMN_MATCH_ID + ") REFERENCES matches(match_id) ON DELETE CASCADE, " +
            "FOREIGN KEY(" + COLUMN_INNINGS_ID + ") REFERENCES innings(innings_id) ON DELETE CASCADE, " +
            "FOREIGN KEY(" + COLUMN_BATSMAN1_ID + ") REFERENCES batsman(batsman_id) ON DELETE CASCADE, " +
            "FOREIGN KEY(" + COLUMN_BATSMAN2_ID + ") REFERENCES batsman(batsman_id) ON DELETE CASCADE" +
            ");";

    // Constructor
    public PartnershipsTableDBH(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the partnerships table
        db.execSQL(CREATE_TABLE_PARTNERSHIPS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop the table if it exists and create a new one
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PARTNERSHIPS);
        onCreate(db);
    }
}
