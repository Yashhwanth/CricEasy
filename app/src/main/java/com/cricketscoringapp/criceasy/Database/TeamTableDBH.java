package com.cricketscoringapp.criceasy.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class TeamTableDBH extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "cricket_match.db";
    private static final int DATABASE_VERSION = 3;
    // Teams table and column names
    public static final String TABLE_TEAMS = "Teams";
    public static final String COLUMN_TEAM_ID = "team_id";
    public static final String COLUMN_TEAM_NAME = "team_name";
    public static final String COLUMN_MATCH_ID = "match_id";
    public TeamTableDBH(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create the Teams table
        String CREATE_TEAMS_TABLE = "CREATE TABLE " + TABLE_TEAMS + " ("
                + COLUMN_TEAM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_TEAM_NAME + " TEXT NOT NULL, "
                + COLUMN_MATCH_ID + " INTEGER, "
                + "FOREIGN KEY(" + COLUMN_MATCH_ID + ") REFERENCES Matches(match_id)"
                + ")";

        // Create the table
        db.execSQL(CREATE_TEAMS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if it exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEAMS);

        // Create the table again
        onCreate(db);
    }
}
