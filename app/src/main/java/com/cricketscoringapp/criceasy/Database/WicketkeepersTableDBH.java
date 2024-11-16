package com.cricketscoringapp.criceasy.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class WicketkeepersTableDBH extends SQLiteOpenHelper {

    // Database name and version
    private static final String DATABASE_NAME = "CricketScoring.db";
    private static final int DATABASE_VERSION = 1;

    // Table name
    public static final String TABLE_WICKETKEEPERS = "Wicketkeepers";

    // Column names
    public static final String COLUMN_WICKETKEEPER_ID = "wicketkeeper_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PLAYER_ID = "player_id"; // Foreign Key reference to Players table

    // Create table SQL statement
    private static final String CREATE_TABLE_WICKETKEEPERS = "CREATE TABLE " + TABLE_WICKETKEEPERS + " (" +
            COLUMN_WICKETKEEPER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_NAME + " TEXT NOT NULL, " +
            COLUMN_PLAYER_ID + " INTEGER, " +
            "FOREIGN KEY(" + COLUMN_PLAYER_ID + ") REFERENCES Players(player_id) ON DELETE CASCADE" +
            ");";

    public WicketkeepersTableDBH(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_WICKETKEEPERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WICKETKEEPERS);
        onCreate(db);
    }
}
