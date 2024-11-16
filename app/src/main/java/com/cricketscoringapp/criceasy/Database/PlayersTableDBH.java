package com.cricketscoringapp.criceasy.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PlayersTableDBH extends SQLiteOpenHelper {

    // Database name and version
    private static final String DATABASE_NAME = "CricketScoring.db";
    private static final int DATABASE_VERSION = 1;

    // Table name
    public static final String TABLE_PLAYERS = "Players";

    // Column names
    public static final String COLUMN_PLAYER_ID = "player_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_ROLE = "role";
    //public static final String COLUMN_TEAM_ID = "team_id"; // Foreign Key reference to Teams table

    // Create table SQL statement
    private static final String CREATE_TABLE_PLAYERS = "CREATE TABLE " + TABLE_PLAYERS + " (" +
            COLUMN_PLAYER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_NAME + " TEXT NOT NULL, " +
            COLUMN_ROLE + " TEXT" +
            ");";

    public PlayersTableDBH(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_PLAYERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYERS);
        onCreate(db);
    }
}
