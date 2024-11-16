package com.cricketscoringapp.criceasy.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class InningsTableDBH extends SQLiteOpenHelper {

    // Database version and name
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "CricketMatchDB";

    // Table and column names
    private static final String TABLE_INNINGS = "innings";
    private static final String COLUMN_INNINGS_ID = "innings_id";
    private static final String COLUMN_INNINGS_NUMBER = "innings_number";
    private static final String COLUMN_MATCH_ID = "match_id";
    private static final String COLUMN_TEAM_ID = "team_id";
    private static final String COLUMN_IS_COMPLETED = "is_completed";

    // SQL command to create the innings table
    private static final String CREATE_TABLE_INNINGS = "CREATE TABLE " + TABLE_INNINGS + " (" +
            COLUMN_INNINGS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_INNINGS_NUMBER + " INTEGER, " +
            COLUMN_MATCH_ID + " INTEGER, " +
            COLUMN_TEAM_ID + " INTEGER, " +
            COLUMN_IS_COMPLETED + " BOOLEAN, " +
            "FOREIGN KEY(" + COLUMN_MATCH_ID + ") REFERENCES matches(match_id) ON DELETE CASCADE, " +
            "FOREIGN KEY(" + COLUMN_TEAM_ID + ") REFERENCES teams(team_id) ON DELETE CASCADE" +
            ");";

    // Constructor
    public InningsTableDBH(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the innings table
        db.execSQL(CREATE_TABLE_INNINGS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop the table if it exists and create a new one
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INNINGS);
        onCreate(db);
    }
}
