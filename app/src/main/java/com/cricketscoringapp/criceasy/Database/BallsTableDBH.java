package com.cricketscoringapp.criceasy.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BallsTableDBH extends SQLiteOpenHelper {

    // Database version and name
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "CricketMatchDB";

    // Table and column names
    private static final String TABLE_BALLS = "balls";
    private static final String COLUMN_BALL_ID = "ball_id";
    private static final String COLUMN_OVER_ID = "over_id";
    private static final String COLUMN_BALL_TYPE = "ball_type";
    private static final String COLUMN_RUNS = "runs";
    private static final String COLUMN_IS_WICKET = "is_wicket";
    private static final String COLUMN_STRIKER = "striker";
    private static final String COLUMN_NON_STRIKER = "non_striker";
    private static final String COLUMN_BOWLER = "bowler";

    // SQL command to create the balls table
    private static final String CREATE_TABLE_BALLS = "CREATE TABLE " + TABLE_BALLS + " (" +
            COLUMN_BALL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_OVER_ID + " INTEGER, " +
            COLUMN_BALL_TYPE + " TEXT, " +
            COLUMN_RUNS + " INTEGER, " +
            COLUMN_IS_WICKET + " INTEGER, " + // 1 for true (wicket), 0 for false (no wicket)
            COLUMN_STRIKER + " INTEGER, " +
            COLUMN_NON_STRIKER + " INTEGER, " +
            COLUMN_BOWLER + " INTEGER, " +
            "FOREIGN KEY(" + COLUMN_OVER_ID + ") REFERENCES overs(over_id) ON DELETE CASCADE, " +
            "FOREIGN KEY(" + COLUMN_STRIKER + ") REFERENCES batsman(batsman_id) ON DELETE CASCADE, " +
            "FOREIGN KEY(" + COLUMN_NON_STRIKER + ") REFERENCES batsman(batsman_id) ON DELETE CASCADE, " +
            "FOREIGN KEY(" + COLUMN_BOWLER + ") REFERENCES bowlers(bowler_id) ON DELETE CASCADE" +
            ");";

    // Constructor
    public BallsTableDBH(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the balls table
        db.execSQL(CREATE_TABLE_BALLS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop the table if it exists and create a new one
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BALLS);
        onCreate(db);
    }
}
