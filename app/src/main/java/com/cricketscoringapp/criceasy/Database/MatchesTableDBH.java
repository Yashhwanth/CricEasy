package com.cricketscoringapp.criceasy.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class MatchesTableDBH extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "cricket_match.db";
    private static final int DATABASE_VERSION = 3;
    // Table schema
    public static final String TABLE_NAME = "Matches";
    public static final String COL_MATCH_ID = "match_id";
    public static final String COL_TEAM_A = "team_a";
    public static final String COL_TEAM_B = "team_b";
    public static final String COL_MATCH_TYPE = "match_type";
    public static final String COL_OVERS = "overs";
    public static final String COL_BALL_TYPE = "ball_type";
    public static final String COL_LOCATION = "location";
    public static final String COL_DATE_TIME = "date_time";
    public static final String COL_TOSS_WON_BY = "toss_won_by";
    public static final String COL_TOSS_WON_TEAM_CHOOSER = "toss_won_team_choose_to";
    public static final String COL_MATCH_WON_BY = "match_won_by";
    public static final String COL_MATCH_RESULT = "match_result";
    public static final String COL_IS_MATCH_COMPLETED = "is_match_completed";
    // Create table SQL query
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
            COL_MATCH_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_TEAM_A + " TEXT, " +
            COL_TEAM_B + " TEXT, " +
            COL_MATCH_TYPE + " TEXT, " +
            COL_OVERS + " INTEGER, " +
            COL_BALL_TYPE + " TEXT, " +
            COL_LOCATION + " TEXT, " +
            COL_DATE_TIME + " TEXT, " +
            COL_TOSS_WON_BY + " TEXT, " +
            COL_TOSS_WON_TEAM_CHOOSER + " TEXT, " +
            COL_MATCH_WON_BY + " TEXT, " +
            COL_MATCH_RESULT + " TEXT, " +
            COL_IS_MATCH_COMPLETED + " INTEGER)";
    // SQL to drop the old table
    private static final String DROP_OLD_TABLE_QUERY = "DROP TABLE IF EXISTS math_details";
    public MatchesTableDBH(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Check if the database version is being upgraded from version 1 to version 2
        if (oldVersion < 3) {
            db.execSQL(DROP_OLD_TABLE_QUERY);  // Drop the old table with the previous schema
            onCreate(db); // Create the new table with the updated schema
        }
    }
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }

    // Check if there is an ongoing match
    public Cursor getOngoingMatch() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(" SELECT " + COL_MATCH_ID + " FROM " + TABLE_NAME + " WHERE " + COL_IS_MATCH_COMPLETED + "=0", null);
    }

    // Insert a new match if no ongoing match exists
    public long insertNewMatch() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_IS_MATCH_COMPLETED, 0); // Match is not completed initially
        long matchId = db.insert(TABLE_NAME, null, values);
        db.close();
        return matchId;
    }

    public static String getColumnId() {
        return COL_MATCH_ID;
    }

    public boolean insertMatchBasicInfo1(long matchId, String matchType,
                                         String overs, String ballType, String place, String time,
                                         int isCompleted) {

        SQLiteDatabase db = this.getWritableDatabase();
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
