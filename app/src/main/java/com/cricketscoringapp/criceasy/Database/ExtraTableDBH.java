package com.cricketscoringapp.criceasy.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ExtraTableDBH extends SQLiteOpenHelper {
    // Database version and name
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "CricketMatchDB";

    // Table and column names
    private static final String TABLE_EXTRAS = "Extras";
    private static final String COLUMN_EXTRA_ID = "extra_id";
    private static final String COLUMN_BALL_ID = "ball_id";
    private static final String COLUMN_EXTRA_TYPE = "extra_type";
    private static final String COLUMN_EXTRA_RUNS = "extra_runs";

    // SQL command to create the Extras table
    private static final String CREATE_TABLE_EXTRAS = "CREATE TABLE " + TABLE_EXTRAS + " (" +
            COLUMN_EXTRA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_BALL_ID + " INTEGER, " +
            COLUMN_EXTRA_TYPE + " TEXT, " +
            COLUMN_EXTRA_RUNS + " INTEGER, " +
            "FOREIGN KEY(" + COLUMN_BALL_ID + ") REFERENCES Balls(ball_id) ON DELETE CASCADE" +
            ");";

    // Constructor
    public ExtraTableDBH(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the Extras table
        db.execSQL(CREATE_TABLE_EXTRAS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop the table if it exists and create a new one
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXTRAS);
        onCreate(db);
    }
}
