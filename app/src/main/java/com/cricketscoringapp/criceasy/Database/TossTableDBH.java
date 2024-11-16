package com.cricketscoringapp.criceasy.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class TossTableDBH extends SQLiteOpenHelper {
    // Database version and name
    private static final String DATABASE_NAME = "cricket_matches.db";
    private static final int DATABASE_VERSION = 1;

    // Toss table and column names
    public static final String TABLE_TOSS = "Toss";
    public static final String COLUMN_TOSS_ID = "toss_id";
    public static final String COLUMN_TOSS_CALL_BY = "toss_call_by";
    public static final String COLUMN_TOSS_WON_BY = "toss_won_by";
    public static final String COLUMN_TOSS_WON_TEAM_CHOOSE_TO = "toss_won_team_choose_to";
    public TossTableDBH(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create the Toss table
        String CREATE_TOSS_TABLE = "CREATE TABLE " + TABLE_TOSS + " ("
                + COLUMN_TOSS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_TOSS_CALL_BY + " INTEGER, "
                + COLUMN_TOSS_WON_BY + " INTEGER, "
                + COLUMN_TOSS_WON_TEAM_CHOOSE_TO + " TEXT, "
                + "FOREIGN KEY(" + COLUMN_TOSS_CALL_BY + ") REFERENCES Teams(team_id), "
                + "FOREIGN KEY(" + COLUMN_TOSS_WON_BY + ") REFERENCES Teams(team_id)"
                + ")";

        // Execute the statement
        db.execSQL(CREATE_TOSS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if it exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TOSS);

        // Recreate the table
        onCreate(db);
    }
}
