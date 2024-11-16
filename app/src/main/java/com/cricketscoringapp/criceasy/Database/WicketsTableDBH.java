package com.cricketscoringapp.criceasy.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class WicketsTableDBH extends SQLiteOpenHelper {
    // Database version and name
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "CricketMatchDB";

    // Table and column names
    private static final String TABLE_WICKETS = "Wickets";
    private static final String COLUMN_WICKET_ID = "wicket_id";
    private static final String COLUMN_BALL_ID = "ball_id";
    private static final String COLUMN_WICKET_TYPE = "wicket_type";
    private static final String COLUMN_WICKET_BATSMAN = "wicket_batsman";
    private static final String COLUMN_WICKET_RUNS = "wicket_runs";
    private static final String COLUMN_WICKET_CONTRIBUTOR = "wicket_contributor";

    // SQL command to create the Wickets table
    private static final String CREATE_TABLE_WICKETS = "CREATE TABLE " + TABLE_WICKETS + " (" +
            COLUMN_WICKET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_BALL_ID + " INTEGER, " +
            COLUMN_WICKET_TYPE + " TEXT, " +
            COLUMN_WICKET_BATSMAN + " INTEGER, " +
            COLUMN_WICKET_RUNS + " INTEGER, " +
            COLUMN_WICKET_CONTRIBUTOR + " INTEGER, " +
            "FOREIGN KEY(" + COLUMN_BALL_ID + ") REFERENCES Balls(ball_id) ON DELETE CASCADE, " +
            "FOREIGN KEY(" + COLUMN_WICKET_BATSMAN + ") REFERENCES Batsman(batsman_id) ON DELETE CASCADE, " +
            "FOREIGN KEY(" + COLUMN_WICKET_CONTRIBUTOR + ") REFERENCES Players(player_id) ON DELETE CASCADE" +
            ");";

    // Constructor
    public WicketsTableDBH(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the Wickets table
        db.execSQL(CREATE_TABLE_WICKETS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop the table if it exists and create a new one
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WICKETS);
        onCreate(db);
    }
}
