package com.cricketscoringapp.criceasy.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class MatchesTableDBH {
    private static final String DATABASE_NAME = "cricket_match.db";
    private static final int DATABASE_VERSION = 3;
    // Table schema
    public static final String TABLE_NAME = "Matches";
    public static final String COLUMN_MATCH_ID = "match_id";
    public static final String COLUMN_TEAM_A = "team_a";
    public static final String COLUMN_TEAM_B = "team_b";
    public static final String COLUMN_MATCH_TYPE = "match_type";
    public static final String COLUMN_OVERS = "overs";
    public static final String COLUMN_BALL_TYPE = "ball_type";
    public static final String COLUMN_LOCATION = "location";
    public static final String COLUMN_DATE_TIME = "date_time";
    public static final String COLUMN_TOSS = "toss";
    public static final String COLUMN_IS_MATCH_COMPLETED = "is_match_completed";
    public static final String COLUMN_MATCH_WON_BY = "match_won_by";
    public static final String COLUMN_MATCH_RESULT = "match_result";

    // Create table SQL query
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
            + COLUMN_MATCH_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_TEAM_A + " INTEGER, "
            + COLUMN_TEAM_B + " INTEGER, "
            + COLUMN_MATCH_TYPE + " TEXT, "
            + COLUMN_OVERS + " INTEGER, "
            + COLUMN_BALL_TYPE + " TEXT, "
            + COLUMN_LOCATION + " INTEGER, "
            + COLUMN_DATE_TIME + " DATETIME, "
            + COLUMN_TOSS + " INTEGER, "
            + COLUMN_IS_MATCH_COMPLETED + " BOOLEAN, "
            + COLUMN_MATCH_WON_BY + " INTEGER, "
            + COLUMN_MATCH_RESULT + " TEXT, "
            + "FOREIGN KEY(" + COLUMN_TEAM_A + ") REFERENCES Teams(id), "
            + "FOREIGN KEY(" + COLUMN_TEAM_B + ") REFERENCES Teams(id), "
            + "FOREIGN KEY(" + COLUMN_LOCATION + ") REFERENCES Places(id), "
            + "FOREIGN KEY(" + COLUMN_TOSS + ") REFERENCES Toss(id), "
            + "FOREIGN KEY(" + COLUMN_MATCH_WON_BY + ") REFERENCES Teams(id)"
            + ")";
    // SQL to drop the old table
    private static final String DROP_OLD_TABLE_QUERY = "DROP TABLE IF EXISTS math_details";


    //@Override
    public static void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    // Check if there is an ongoing match
    public Cursor getOngoingMatch(SQLiteDatabase db) {
        //SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(" SELECT " + COLUMN_MATCH_ID + " FROM " + TABLE_NAME + " WHERE " + COLUMN_IS_MATCH_COMPLETED + "=0", null);
    }

    // Insert a new match if no ongoing match exists
    public long insertNewMatch(SQLiteDatabase db) {
        //SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_IS_MATCH_COMPLETED, 0); // Match is not completed initially
        long matchId = db.insert(TABLE_NAME, null, values);
        db.close();
        return matchId;
    }

    public static String getColumnId() {
        return COLUMN_MATCH_ID;
    }

    public boolean insertMatchBasicInfo1(SQLiteDatabase db, long matchId, String matchType,
                                         String overs, String ballType, String place, String time,
                                         int isCompleted) {

        //SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        // Insert match_id (this is always passed)
        values.put("match_id", matchId);

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
            values.put("location", place);
        }
        if (time != null && !time.isEmpty()) {
            values.put("date_time", time);
        }

        // Insert the "is_completed" value, using the default value (0) if not provided
        values.put("is_match_completed", isCompleted);

        // Check if the row exists
        int rowsUpdated = db.update("Matches", values, "match_id=?", new String[]{String.valueOf(matchId)});

        // If no rows were updated, insert a new row
        if (rowsUpdated == 0) {
            values.put("match_id", matchId);
            long result = db.insert("Matches", null, values);
            return result != -1;
        }
        return true;
    }
}
