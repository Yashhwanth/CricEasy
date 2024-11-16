package com.cricketscoringapp.criceasy.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class OversTableDBH extends SQLiteOpenHelper {

    // Database version and name
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "CricketMatchDB";

    // Table and column names
    private static final String TABLE_OVERS = "overs";
    private static final String COLUMN_OVER_ID = "over_id";
    private static final String COLUMN_INNINGS_ID = "innings_id";
    private static final String COLUMN_MATCH_ID = "match_id";
    private static final String COLUMN_OVER = "over";
    private static final String COLUMN_BOWLER_ID = "bowler_id";
    private static final String COLUMN_IS_MAIDEN = "is_maiden";

    // SQL command to create the overs table
    private static final String CREATE_TABLE_OVERS = "CREATE TABLE " + TABLE_OVERS + " (" +
            COLUMN_OVER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_INNINGS_ID + " INTEGER, " +
            COLUMN_MATCH_ID + " INTEGER, " +
            COLUMN_OVER + " INTEGER, " +
            COLUMN_BOWLER_ID + " INTEGER, " +
            COLUMN_IS_MAIDEN + " BOOLEAN, " +
            "FOREIGN KEY(" + COLUMN_INNINGS_ID + ") REFERENCES innings(innings_id) ON DELETE CASCADE, " +
            "FOREIGN KEY(" + COLUMN_MATCH_ID + ") REFERENCES matches(match_id) ON DELETE CASCADE, " +
            "FOREIGN KEY(" + COLUMN_BOWLER_ID + ") REFERENCES bowlers(bowler_id) ON DELETE CASCADE" +
            ");";

    // Constructor
    public OversTableDBH(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the overs table
        db.execSQL(CREATE_TABLE_OVERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop the table if it exists and create a new one
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OVERS);
        onCreate(db);
    }
}
