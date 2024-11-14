package com.cricketscoringapp.criceasy.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "cricket_match.db";
    private static final int DATABASE_VERSION = 2;

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
    private static final String COLUMN_IS_COMPLETED = "is_completed"; // Added column to track match status

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
            + COLUMN_MATCH_RESULT + " TEXT, "
            + COLUMN_IS_COMPLETED + "INTEGER DEFAULT 0)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Check if the database version is being upgraded from version 1 to version 2
        if (oldVersion < 2) {
            // Add the new column if not already present
            String ALTER_TABLE = "ALTER TABLE match_details ADD COLUMN is_completed INTEGER DEFAULT 0";
            db.execSQL(ALTER_TABLE);
        }
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }

    // Check if there is an ongoing match
    public Cursor getOngoingMatch() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(" SELECT " + COLUMN_ID + " FROM " + TABLE_MATCH + " WHERE " + COLUMN_IS_COMPLETED + "=0", null);
    }

    // Insert method to insert match details
    // Insert a new match if no ongoing match exists
    public long insertNewMatch() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_IS_COMPLETED, 0); // Match is not completed initially
        long matchId = db.insert(TABLE_MATCH, null, values);
        db.close();
        return matchId;
    }
    public static String getColumnId() {
        return COLUMN_ID;
    }

    public boolean insertMatchBasicInfo(long matchId, String teamA, String teamB, String matchType,
                                        String overs, String ballType, String place, String time,
                                        String tossBy, String matchResult, int isCompleted) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        // Insert match_id (this is always passed)
        values.put("match_id", matchId);

        // Add other columns conditionally (if values are provided)
        if (teamA != null && !teamA.isEmpty()) {
            values.put("team_a", teamA);
        }
        if (teamB != null && !teamB.isEmpty()) {
            values.put("team_b", teamB);
        }
        if (matchType != null && !matchType.isEmpty()) {
            values.put("match_type", matchType);
        }
        if (overs != null && !overs.isEmpty()) {
            values.put("overs", overs);
        }
        if (ballType != null && !ballType.isEmpty()) {
            values.put("ball_type", ballType);
        }
        if (place != null && !place.isEmpty()) {
            values.put("place", place);
        }
        if (time != null && !time.isEmpty()) {
            values.put("time", time);
        }
        if (tossBy != null && !tossBy.isEmpty()) {
            values.put("toss_by", tossBy);
        }
        if (matchResult != null && !matchResult.isEmpty()) {
            values.put("match_result", matchResult);
        }

        // Insert the "is_completed" value, using the default value (0) if not provided
        values.put("is_completed", isCompleted);

        // Insert into the database (use CONFLICT_REPLACE to update if the match_id already exists)
        long result = db.insertWithOnConflict("match_details", null, values, SQLiteDatabase.CONFLICT_REPLACE);

        return result != -1;
    }


}
