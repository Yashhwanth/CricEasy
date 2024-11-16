package com.cricketscoringapp.criceasy.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BowlerTableDBH extends SQLiteOpenHelper {

    // Database name and version
    private static final String DATABASE_NAME = "CricketScoring.db";
    private static final int DATABASE_VERSION = 1;

    // Table name
    public static final String TABLE_BOWLERS = "Bowlers";

    // Column names
    public static final String COLUMN_BOWLER_ID = "bowler_id";
    public static final String COLUMN_BOWLER_NAME = "bowler_name";
    public static final String COLUMN_BOWLING_STYLE = "bowling_style";
    public static final String COLUMN_PLAYER_ID = "player_id"; // Foreign Key reference to Players table

    // Create table SQL statement
    private static final String CREATE_TABLE_BOWLERS = "CREATE TABLE " + TABLE_BOWLERS + " (" +
            COLUMN_BOWLER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_BOWLER_NAME + " TEXT NOT NULL, " +
            COLUMN_BOWLING_STYLE + " TEXT, " +
            COLUMN_PLAYER_ID + " INTEGER, " +
            "FOREIGN KEY(" + COLUMN_PLAYER_ID + ") REFERENCES Players(player_id) ON DELETE CASCADE" +
            ");";

    public BowlerTableDBH(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_BOWLERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOWLERS);
        onCreate(db);
    }
}
