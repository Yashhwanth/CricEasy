package com.cricketscoringapp.criceasy.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "cricket_match.db";
    private static final int DATABASE_VERSION = 1;

    // Table name
    private static final String TABLE_MATCH = "match_details";

    // Column names
    private static final String COLUMN_ID = "match_id"; // Changed to match_id for unique identification
    private static final String COLUMN_TEAM_1 = "team_a"; // Changed to team_a
    private static final String COLUMN_TEAM_2 = "team_b"; // Changed to team_b
    private static final String COLUMN_MATCH_TYPE = "match_type"; // Added match_type
    private static final String COLUMN_OVERS = "overs"; // Added overs
    private static final String COLUMN_BALL_TYPE = "ball_type"; // Added ball_type
    private static final String COLUMN_PLACE = "place"; // Added place
    private static final String COLUMN_TIME = "time"; // Added time
    private static final String COLUMN_TOSS_BY = "toss_by"; // Added toss_by
    private static final String COLUMN_MATCH_RESULT = "match_result"; // Added match_result

    // SQL query to create table
    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_MATCH + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_TEAM_1 + " TEXT, "
            + COLUMN_TEAM_2 + " TEXT, "
            + COLUMN_MATCH_TYPE + " TEXT, "
            + COLUMN_OVERS + " INTEGER, "
            + COLUMN_BALL_TYPE + " TEXT, "
            + COLUMN_PLACE + " TEXT, "
            + COLUMN_TIME + " TEXT, "
            + COLUMN_TOSS_BY + " TEXT, "
            + COLUMN_MATCH_RESULT + " TEXT);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MATCH);
        onCreate(db);
    }

    // Insert method to insert match details
    public boolean insertMatchBasicInfo( String matchType, String overs,
                                   String ballType, String place, String time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_MATCH_TYPE, matchType);
        values.put(COLUMN_OVERS, overs);
        values.put(COLUMN_BALL_TYPE, ballType);
        values.put(COLUMN_PLACE, place);
        values.put(COLUMN_TIME, time);


        // Inserting values into the database
        long result = db.insert(TABLE_MATCH, null, values);
        db.close();
        return result != -1; // If the insertion fails, result will be -1
    }
}
