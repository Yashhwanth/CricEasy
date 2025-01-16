package com.cricketscoringapp.criceasy.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static android.content.ContentValues.TAG;

import com.cricketscoringapp.criceasy.model.BallDetails;
import com.cricketscoringapp.criceasy.model.Batsman;
import com.cricketscoringapp.criceasy.model.Bowler;
import com.cricketscoringapp.criceasy.model.Player;
import com.google.android.datatransport.cct.internal.LogEvent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 21; // Update version
    private static final String DATABASE_NAME = "CricketDB";
    private static final String NORMAL_BALL = "Normal";
    private static final String BYE_BALL = "Bye";
    private static final String LEG_BYE_BALL = "LegBye";
    private static final String WIDE_BALL = "Wide";
    private static final String NO_BALL = "NoBall";
    private static final String FROM_BAT = "Bat";
    private static final String BOWLED = "Bowled";
    private static final String CAUGHT = "Caught";
    private static final String LBW = "LBW";
    private static final String RUN_OUT = "Run-Out";
    private static final String STUMPED = "Stumped";

    // Table schema
    //Matches Table
    public static final String TABLE_MATCH = "Matches";
    public static final String COLUMN_MATCH_ID = "matchId";
    public static final String COLUMN_MATCH_TYPE = "matchType";
    public static final String COLUMN_NO_OF_OVERS = "numberOfOvers";
    public static final String COLUMN_BALL_TYPE = "ballType";
    public static final String COLUMN_PLACE_NAME = "placeName";
    public static final String COLUMN_DATE_TIME = "dateTime";
    public static final String COLUMN_TOSS = "toss";
    public static final String COLUMN_IS_MATCH_COMPLETED = "isMatchCompleted";
    public static final String COLUMN_MATCH_WON_BY = "matchWonBy";
    public static final String COLUMN_MATCH_RESULT = "matchResult";
    public static final String COLUMN_PLACE_ID = "placeId";
    public static final String COLUMN_TOSS_ID = "tossId";
    public static final String COLUMN_TEAM_ID = "teamId";

    // Create table SQL query
    public static final String CREATE_MATCHES_TABLE = "CREATE TABLE " + TABLE_MATCH + " ("
            + COLUMN_MATCH_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_MATCH_TYPE + " TEXT, "
            + COLUMN_NO_OF_OVERS + " INTEGER, "
            + COLUMN_BALL_TYPE + " TEXT, "
            + COLUMN_PLACE_NAME + " INTEGER, "
            + COLUMN_DATE_TIME + " DATETIME, "
            + COLUMN_TOSS + " INTEGER, "
            + COLUMN_IS_MATCH_COMPLETED + " INTEGER, "
            + COLUMN_MATCH_WON_BY + " INTEGER, "
            + COLUMN_MATCH_RESULT + " TEXT, "
            + "FOREIGN KEY(" + COLUMN_PLACE_NAME + ") REFERENCES Place(" + COLUMN_PLACE_ID + "), "
            + "FOREIGN KEY(" + COLUMN_TOSS + ") REFERENCES Toss(" + COLUMN_TOSS_ID + "), "
            + "FOREIGN KEY(" + COLUMN_MATCH_WON_BY + ") REFERENCES Team(" + COLUMN_TEAM_ID + ")"
            + ");";

    //Team Table
    public static final String TABLE_TEAMS = "Team";
    public static final String COLUMN_TEAM_NAME = "teamName";
    public static final String CREATE_TEAMS_TABLE = "CREATE TABLE " + TABLE_TEAMS + " ("
            + COLUMN_TEAM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_TEAM_NAME + " TEXT NOT NULL "
            + ");";


    // Matches-Teams Table
    public static final String TABLE_MATCHES_TEAMS = "MatchTeams";
    public static final String COLUMN_MATCHES_TEAMS_ID = "matchTeamsId";
    public static final String COLUMN_TEAM1_ID = "team1Id";
    public static final String COLUMN_TEAM2_ID = "team2Id";
    public static final String CREATE_MATCHES_TEAMS_TABLE =
            "CREATE TABLE " + TABLE_MATCHES_TEAMS + " (" +
                    COLUMN_MATCHES_TEAMS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_MATCH_ID + " INTEGER, " +
                    COLUMN_TEAM1_ID + " INTEGER, " +
                    COLUMN_TEAM2_ID + " INTEGER, " +
                    "FOREIGN KEY(" + COLUMN_MATCH_ID + ") REFERENCES Matches(" + COLUMN_MATCH_ID + "), " +
                    "FOREIGN KEY(" + COLUMN_TEAM1_ID + ") REFERENCES Team(" + COLUMN_TEAM_ID + "), " +
                    "FOREIGN KEY(" + COLUMN_TEAM2_ID + ") REFERENCES Team(" + COLUMN_TEAM_ID + ")" +
                    ");";

    public static final String TABLE_PLACES = "Place";
    public static final String CREATE_PLACES_TABLE = "CREATE TABLE " + TABLE_PLACES + " ("
            + COLUMN_PLACE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_PLACE_NAME + " TEXT NOT NULL UNIQUE"
            + ");";

    // Toss table and column names
    public static final String TABLE_TOSS = "Toss";
    public static final String COLUMN_TOSS_CALL_BY = "tossCallingTeam";
    public static final String COLUMN_TOSS_WON_BY = "tossWinningTeam";
    public static final String COLUMN_TOSS_WON_TEAM_CHOOSE_TO = "tossWonTeamChooseTo";
    String CREATE_TOSS_TABLE = "CREATE TABLE " + TABLE_TOSS + " ("
            + COLUMN_TOSS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_TOSS_CALL_BY + " INTEGER, "
            + COLUMN_TOSS_WON_BY + " INTEGER, "
            + COLUMN_TOSS_WON_TEAM_CHOOSE_TO + " TEXT, "
            + "FOREIGN KEY(" + COLUMN_TOSS_CALL_BY + ") REFERENCES Team(" + COLUMN_TEAM_ID + "), "
            + "FOREIGN KEY(" + COLUMN_TOSS_WON_BY + ") REFERENCES Team(" + COLUMN_TEAM_ID + ")"
            + ");";

    // Table Players
    public static final String TABLE_PLAYERS = "Player";
    public static final String COLUMN_PLAYER_ID = "playerId";
    public static final String COLUMN_PLAYER_NAME = "playerName";
    public static final String CREATE_PLAYERS_TABLE = "CREATE TABLE " + TABLE_PLAYERS + " (" +
            COLUMN_PLAYER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_PLAYER_NAME + " TEXT NOT NULL " + ");";

    // Table TeamPlayers
    public static final String TABLE_PLAYERS_TEAMS = "TeamPlayers";
    private static final String COLUMN_INNINGS_ID = "inningsId";
    public static final String CREATE_PLAYERS_TEAMS_TABLE = "CREATE TABLE " + TABLE_PLAYERS_TEAMS + " (" +
            COLUMN_TEAM_ID + " INTEGER, " +
            COLUMN_PLAYER_ID + " INTEGER, " +
            COLUMN_INNINGS_ID + " INTEGER, " +
            "FOREIGN KEY (" + COLUMN_TEAM_ID + ") REFERENCES Team(" + COLUMN_TEAM_ID + ") ON DELETE CASCADE, " +
            "FOREIGN KEY (" + COLUMN_PLAYER_ID + ") REFERENCES Player(" + COLUMN_PLAYER_ID + ") ON DELETE CASCADE, " +
            "FOREIGN KEY (" + COLUMN_INNINGS_ID + ") REFERENCES Innings(" + COLUMN_INNINGS_ID + ") ON DELETE CASCADE, " +
            "PRIMARY KEY (" + COLUMN_TEAM_ID + ", " + COLUMN_PLAYER_ID + ", " + COLUMN_INNINGS_ID + "))";

    // Partnerships Table
    private static final String TABLE_PARTNERSHIPS = "Partnership";
    private static final String COLUMN_PARTNERSHIP_ID = "partnershipId";
    private static final String COLUMN_BATSMAN1_ID = "batsman1Id";
    private static final String COLUMN_BATSMAN2_ID = "batsman2Id";
    private static final String COLUMN_RUNS = "runs";
    private static final String COLUMN_BALLS = "balls";
    private static final String CREATE_PARTNERSHIPS_TABLE = "CREATE TABLE " + TABLE_PARTNERSHIPS + " (" +
            COLUMN_PARTNERSHIP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_INNINGS_ID + " INTEGER, " +
            COLUMN_BATSMAN1_ID + " INTEGER, " +
            COLUMN_BATSMAN2_ID + " INTEGER, " +
            COLUMN_RUNS + " INTEGER, " +
            COLUMN_BALLS + " INTEGER, " +
            "FOREIGN KEY(" + COLUMN_INNINGS_ID + ") REFERENCES Innings(" + COLUMN_INNINGS_ID + ") ON DELETE CASCADE, " +
            "FOREIGN KEY(" + COLUMN_BATSMAN1_ID + ") REFERENCES Player(" + COLUMN_PLAYER_ID + ") ON DELETE CASCADE, " +
            "FOREIGN KEY(" + COLUMN_BATSMAN2_ID + ") REFERENCES Player(" + COLUMN_PLAYER_ID + ") ON DELETE CASCADE" +
            ");";

    // Innings Table
    private static final String TABLE_INNINGS = "Innings";
    private static final String COLUMN_INNINGS_NUMBER = "inningsNumber";
    private static final String COLUMN_TEAM_BATTING = "battingTeam";
    private static final String COLUMN_IS_COMPLETED = "isCompleted";
    private static final String CREATE_INNINGS_TABLE = "CREATE TABLE " + TABLE_INNINGS + " (" +
            COLUMN_INNINGS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_INNINGS_NUMBER + " INTEGER, " +
            COLUMN_MATCH_ID + " INTEGER, " +
            COLUMN_TEAM_BATTING + " INTEGER, " +
            COLUMN_IS_COMPLETED + " INTEGER, " +
            "FOREIGN KEY(" + COLUMN_MATCH_ID + ") REFERENCES Matches(" + COLUMN_MATCH_ID + ") ON DELETE CASCADE, " +
            "FOREIGN KEY(" + COLUMN_TEAM_BATTING + ") REFERENCES Team(" + COLUMN_TEAM_ID + ") ON DELETE CASCADE" +
            ");";

    // Over Table
    private static final String TABLE_OVERS = "Over";
    private static final String COLUMN_OVER_ID = "overId";
    private static final String COLUMN_OVER_NUMBER = "overNumber";
    private static final String COLUMN_IS_MAIDEN = "isMaiden";
    private static final String CREATE_OVERS_TABLE = "CREATE TABLE " + TABLE_OVERS + " (" +
            COLUMN_OVER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_INNINGS_ID + " INTEGER, " +
            COLUMN_OVER_NUMBER + " INTEGER, " +
            COLUMN_PLAYER_ID + " INTEGER, " +
            COLUMN_IS_MAIDEN + " INTEGER, " +
            "FOREIGN KEY(" + COLUMN_INNINGS_ID + ") REFERENCES Innings(" + COLUMN_INNINGS_ID + ") ON DELETE CASCADE, " +
            "FOREIGN KEY(" + COLUMN_PLAYER_ID + ") REFERENCES Player(" + COLUMN_PLAYER_ID + ") ON DELETE CASCADE" +
            ");";

    // Balls Table
    private static final String TABLE_BALLS = "Ball";
    private static final String COLUMN_BALL_ID = "ballId";
    private static final String COLUMN_BALL_NUMBER = "ballNumber";
    private static final String COLUMN_TYPE_OF_BALL = "ballType";
    private static final String COLUMN_IS_WICKET = "isWicket";
    private static final String COLUMN_STRIKER = "striker";
    private static final String COLUMN_NON_STRIKER = "nonStriker";
    private static final String COLUMN_IS_SYNCED = "isSynced";

    // SQL command to create the balls table
    private static final String CREATE_BALLS_TABLE = "CREATE TABLE " + TABLE_BALLS + " (" +
            COLUMN_BALL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_OVER_ID + " INTEGER, " +
            COLUMN_BALL_NUMBER + " INTEGER, " +
            COLUMN_TYPE_OF_BALL + " TEXT, " +
            COLUMN_RUNS + " INTEGER, " +
            COLUMN_IS_WICKET + " INTEGER, " + // 1 for true (wicket), 0 for false (no wicket)
            COLUMN_STRIKER + " INTEGER, " +
            COLUMN_NON_STRIKER + " INTEGER, " +
            COLUMN_IS_SYNCED + "INTEGER DEFAULT 0, " + // New column to track sync status (0 = not synced, 1 = synced)
            "FOREIGN KEY(" + COLUMN_OVER_ID + ") REFERENCES Over(" + COLUMN_OVER_ID + ") ON DELETE CASCADE, " +
            "FOREIGN KEY(" + COLUMN_STRIKER + ") REFERENCES Player(" + COLUMN_PLAYER_ID + ") ON DELETE CASCADE, " +
            "FOREIGN KEY(" + COLUMN_NON_STRIKER + ") REFERENCES Player(" + COLUMN_PLAYER_ID + ") ON DELETE CASCADE " +
            ");";


    // Wickets Table
    private static final String TABLE_WICKETS = "Wicket";
    private static final String COLUMN_WICKET_ID = "wicketId";
    private static final String COLUMN_WICKET_TYPE = "wicketType";
    private static final String COLUMN_WICKET_BATSMAN = "wicketBatsman";
    private static final String COLUMN_WICKET_RUNS = "runsInWicket";
    private static final String COLUMN_WICKET_CONTRIBUTOR = "wicketContributor";
    private static final String CREATE_WICKETS_TABLE = "CREATE TABLE " + TABLE_WICKETS + " (" +
            COLUMN_WICKET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_BALL_ID + " INTEGER, " +
            COLUMN_WICKET_TYPE + " TEXT, " +
            COLUMN_WICKET_BATSMAN + " INTEGER, " +
            COLUMN_WICKET_RUNS + " INTEGER, " +
            COLUMN_WICKET_CONTRIBUTOR + " INTEGER, " +
            "FOREIGN KEY(" + COLUMN_BALL_ID + ") REFERENCES Ball(" + COLUMN_BALL_ID + ") ON DELETE CASCADE, " +
            "FOREIGN KEY(" + COLUMN_WICKET_BATSMAN + ") REFERENCES Player(" + COLUMN_PLAYER_ID + ") ON DELETE CASCADE, " +
            "FOREIGN KEY(" + COLUMN_WICKET_CONTRIBUTOR + ") REFERENCES Player(" + COLUMN_PLAYER_ID + ") ON DELETE CASCADE" +
            ");";

    // Extras Table
    private static final String TABLE_EXTRAS = "Extra";
    private static final String COLUMN_EXTRA_ID = "extraId";
    private static final String COLUMN_EXTRA_TYPE = "extraType";
    private static final String COLUMN_EXTRA_RUNS = "runsInExtra";
    private static final String CREATE_EXTRAS_TABLE = "CREATE TABLE " + TABLE_EXTRAS + " (" +
            COLUMN_EXTRA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_BALL_ID + " INTEGER, " +
            COLUMN_EXTRA_TYPE + " TEXT, " +
            COLUMN_EXTRA_RUNS + " INTEGER, " +
            "FOREIGN KEY(" + COLUMN_BALL_ID + ") REFERENCES Ball(" + COLUMN_BALL_ID + ") ON DELETE CASCADE" +
            ");";

    //Batsman table
    private static final String TABLE_BATSMAN = "Batsman";
    private static final String COLUMN_PLAYER = "player";
    private static final String COLUMN_SCORE = "score";
    private static final String COLUMN_BALLS_PLAYED = "balls";
    private static final String COLUMN_ZEROES = "zeroes";
    private static final String COLUMN_ONES = "ones";
    private static final String COLUMN_TWOS = "twos";
    private static final String COLUMN_THREES = "threes";
    private static final String COLUMN_FOURS = "fours";
    private static final String COLUMN_FIVES = "fives";
    private static final String COLUMN_SIXES = "sixes";

    String CREATE_BATSMAN_TABLE = "CREATE TABLE " + TABLE_BATSMAN + " (" +
            COLUMN_PLAYER + " INTEGER, " +
            COLUMN_INNINGS_ID + " INTEGER, " +
            COLUMN_SCORE + " INTEGER DEFAULT 0, " +
            COLUMN_BALLS_PLAYED + " INTEGER DEFAULT 0, " +
            COLUMN_ZEROES + " INTEGER DEFAULT 0, " +
            COLUMN_ONES + " INTEGER DEFAULT 0, " +
            COLUMN_TWOS + " INTEGER DEFAULT 0, " +
            COLUMN_THREES + " INTEGER DEFAULT 0, " +
            COLUMN_FOURS + " INTEGER DEFAULT 0, " +
            COLUMN_FIVES + " INTEGER DEFAULT 0, " +
            COLUMN_SIXES + " INTEGER DEFAULT 0, " +
            "PRIMARY KEY(" + COLUMN_PLAYER + ", " + COLUMN_INNINGS_ID + "), " +
            "FOREIGN KEY (" + COLUMN_PLAYER + ") REFERENCES Player(player_id), " +
            "FOREIGN KEY (" + COLUMN_INNINGS_ID + ") REFERENCES Innings(innings_id)" +
            ")";

    // Bowler table name
    private static final String TABLE_BOWLER = "Bowler";
    private static final String COLUMN_MAIDENS = "maidens";
    private static final String COLUMN_ECONOMY = "economy";
    private static final String COLUMN_WK = "wickets";
    private static final String COLUMN_BY = "byes";
    private static final String COLUMN_LB = "legByes";
    private static final String COLUMN_WB = "wides";
    private static final String COLUMN_NB = "noBalls";
    private static final String COLUMN_DB = "deadBalls";
    private static final String CREATE_BOWLER_TABLE = "CREATE TABLE " + TABLE_BOWLER + " ("
            + COLUMN_INNINGS_ID + " INTEGER, "
            + COLUMN_PLAYER + " INTEGER, "
            + COLUMN_MAIDENS + " INTEGER DEFAULT 0, "
            + COLUMN_BALLS_PLAYED + " INTEGER DEFAULT 0, "
            + COLUMN_RUNS + " INTEGER DEFAULT 0, "
            + COLUMN_ECONOMY + " REAL DEFAULT 0, "
            + COLUMN_ZEROES + " INTEGER DEFAULT 0, "
            + COLUMN_ONES + " INTEGER DEFAULT 0, "
            + COLUMN_TWOS + " INTEGER DEFAULT 0, "
            + COLUMN_THREES + " INTEGER DEFAULT 0, "
            + COLUMN_FOURS + " INTEGER DEFAULT 0, "
            + COLUMN_FIVES + " INTEGER DEFAULT 0, "
            + COLUMN_SIXES + " INTEGER DEFAULT 0, "
            + COLUMN_WK + " INTEGER DEFAULT 0, "
            + COLUMN_BY + " INTEGER DEFAULT 0, "
            + COLUMN_LB + " INTEGER DEFAULT 0, "
            + COLUMN_WB + " INTEGER DEFAULT 0, "
            + COLUMN_NB + " INTEGER DEFAULT 0, "
            + COLUMN_DB + " INTEGER DEFAULT 0, "
            + "PRIMARY KEY(" + COLUMN_PLAYER + ", " + COLUMN_INNINGS_ID + "), "
            + "FOREIGN KEY (" + COLUMN_PLAYER + ") REFERENCES Player(player_id), "
            + "FOREIGN KEY (" + COLUMN_INNINGS_ID + ") REFERENCES Innings(innings_id));";

    // Team Statistics Table
    private static final String TABLE_TEAM_STATISTICS = "TeamStatistics";
    private static final String COLUMN_TEAM_STATS_ID = "TeamStatsId";
    private static final String COLUMN_EXTRAS = "extras";
    private static final String COLUMN_WICKETS = "wickets";
    private static final String CREATE_TEAM_STATISTICS_TABLE = "CREATE TABLE " + TABLE_TEAM_STATISTICS + " (" +
            COLUMN_TEAM_STATS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_INNINGS_ID + " INTEGER , " + // Links to Innings table
            COLUMN_RUNS + " INTEGER DEFAULT 0, " +         // Total runs scored
            COLUMN_WICKETS + " INTEGER DEFAULT 0, " +      // Total wickets lost
            COLUMN_BALLS + " INTEGER DEFAULT 0, " +
            COLUMN_EXTRAS + " INTEGER DEFAULT 0, " +
            "FOREIGN KEY(" + COLUMN_INNINGS_ID + ") REFERENCES " + TABLE_INNINGS + " (" + COLUMN_INNINGS_ID + ") ON DELETE CASCADE" +
            ");";

    // Constructor
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_MATCHES_TABLE);
        db.execSQL(CREATE_TEAMS_TABLE);
        db.execSQL(CREATE_PLACES_TABLE);
        db.execSQL(CREATE_TOSS_TABLE);
        db.execSQL(CREATE_MATCHES_TEAMS_TABLE);
        db.execSQL(CREATE_PLAYERS_TEAMS_TABLE);
        db.execSQL(CREATE_PLAYERS_TABLE);
        db.execSQL(CREATE_PARTNERSHIPS_TABLE);
        db.execSQL(CREATE_INNINGS_TABLE);
        db.execSQL(CREATE_OVERS_TABLE);
        db.execSQL(CREATE_BALLS_TABLE);
        db.execSQL(CREATE_WICKETS_TABLE);
        db.execSQL(CREATE_EXTRAS_TABLE);
        db.execSQL(CREATE_BATSMAN_TABLE);
        db.execSQL(CREATE_BOWLER_TABLE);
        db.execSQL(CREATE_TEAM_STATISTICS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Matches");
        db.execSQL("DROP TABLE IF EXISTS Team");
        db.execSQL("DROP TABLE IF EXISTS Place");
        db.execSQL("DROP TABLE IF EXISTS Toss");
        db.execSQL("DROP TABLE IF EXISTS MatchTeams");
        db.execSQL("DROP TABLE IF EXISTS TeamPlayers");
        db.execSQL("DROP TABLE IF EXISTS Player");
        db.execSQL("DROP TABLE IF EXISTS Partnership");
        db.execSQL("DROP TABLE IF EXISTS Innings");
        db.execSQL("DROP TABLE IF EXISTS Over");
        db.execSQL("DROP TABLE IF EXISTS Ball");
        db.execSQL("DROP TABLE IF EXISTS Wicket");
        db.execSQL("DROP TABLE IF EXISTS Extra");
        db.execSQL("DROP TABLE IF EXISTS Batsman");
        db.execSQL("DROP TABLE IF EXISTS Bowler");
        db.execSQL("DROP TABLE IF EXISTS TeamStatistics");
        onCreate(db);
    }




// -----------------------------------------------Main Activity------------------------------------------------------
    public Cursor getOngoingMatch() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(" SELECT " + COLUMN_MATCH_ID + " FROM " + TABLE_MATCH + " WHERE " + COLUMN_IS_MATCH_COMPLETED + "=0", null);
        Log.d(TAG, "getOngoingMatch: " + cursor);
        return cursor;
    }
    public long insertNewMatch() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_IS_MATCH_COMPLETED, 0); // Match is not completed initially
        return db.insert(TABLE_MATCH, null, values);
    }
    public static String getColumnId() {
        return COLUMN_MATCH_ID;
    }

// -----------------------------------------------MatchInfo Activity------------------------------------------------------
    public boolean insertMatchBasicInfo(long matchId, String matchType,
                                        String overs, String ballType, String place, String time,
                                        int isCompleted) {

        SQLiteDatabase db = this.getWritableDatabase();
        long placeId = getOrInsertPlaceId(place);
        ContentValues values = new ContentValues();
        values.put(COLUMN_MATCH_ID, matchId);
        values.put(COLUMN_MATCH_TYPE, matchType);
        values.put(COLUMN_NO_OF_OVERS, overs);
        values.put(COLUMN_BALL_TYPE, ballType);
        values.put(COLUMN_PLACE_NAME, placeId);
        values.put(COLUMN_DATE_TIME, time);
        values.put(COLUMN_IS_MATCH_COMPLETED, isCompleted);
        int rowsUpdated = db.update(TABLE_MATCH, values, COLUMN_MATCH_ID + "=?", new String[]{String.valueOf(matchId)});
        db.close();
        return rowsUpdated != 0;
    }

    public long getOrInsertPlaceId(String place) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_PLACES, new String[]{COLUMN_PLACE_ID}, COLUMN_PLACE_NAME + " = ?", new String[]{place}, null, null, null);
        if (cursor != null) {
            int placeIdColumnIndex = cursor.getColumnIndex(COLUMN_PLACE_ID);
            if (placeIdColumnIndex >= 0 && cursor.moveToFirst()) {
                long placeId = cursor.getLong(placeIdColumnIndex);
                cursor.close();  // Don't forget to close the cursor
                return placeId;
            }
            cursor.close();
        }
        // If the place is not found, insert a new place and return the new place_id
        ContentValues values = new ContentValues();
        values.put(COLUMN_PLACE_NAME, place);
        return db.insert(TABLE_PLACES, null, values);
    }

    //----------------------------------***TEAM CREATION PAGE ****---------------------------------
    public Pair<Long, Long> addTeamNames(long matchId, String teamAName, String teamBName) {
        Log.d(TAG, "addTeamNames: " + matchId);
        SQLiteDatabase db = this.getWritableDatabase();
        long teamAId = -1;
        long teamBId = -1;
        db.beginTransaction(); // Start a transaction for atomicity
        try {
            // Check if Team A exists, insert if not, and get its ID
            teamAId = getOrInsertTeam(db, teamAName);
            // Check if Team B exists, insert if not, and get its ID
            teamBId = getOrInsertTeam(db, teamBName);

            // Ensure Matches_Teams table is clean for the given match_id
            resetMatchTeams(db, matchId);
            // Insert into Matches_Teams table
            insertMatchTeamPair(db, matchId, teamAId, teamBId);

            db.setTransactionSuccessful(); // Mark transaction as successful
        } catch (Exception e) {
            Log.e(TAG, "Error in addTeamNames: ", e); // Log the exception with the TAG
        } finally {
            db.endTransaction(); // End the transaction
            db.close(); // Close the database
        }
        return new Pair<>(teamAId, teamBId);
    }
    private long getOrInsertTeam(SQLiteDatabase db, String teamName) {
        // Query to check if the team exists
        String query = "SELECT " + COLUMN_TEAM_ID + " FROM " + TABLE_TEAMS + " WHERE " + COLUMN_TEAM_NAME + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{teamName});
        long teamId = -1;
        try {
            if (cursor.moveToFirst()) {
                // Check if COLUMN_TEAM_ID index is valid
                int columnIndex = cursor.getColumnIndex(COLUMN_TEAM_ID);
                if (columnIndex != -1) {
                    teamId = cursor.getLong(columnIndex);
                } else {
                    throw new IllegalStateException("COLUMN_TEAM_ID does not exist in the result set.");
                }
            } else {
                // Team does not exist, insert it
                ContentValues values = new ContentValues();
                values.put(COLUMN_TEAM_NAME, teamName);
                teamId = db.insert(TABLE_TEAMS, null, values);
            }
        } finally {
            cursor.close(); // Always close the cursor to avoid memory leaks
        }
        if (teamId == -1) {
            throw new IllegalStateException("Failed to retrieve or insert team.");
        }
        return teamId;
    }
    // Helper method to reset Matches_Teams table for the given match ID
    private void resetMatchTeams(SQLiteDatabase db, long matchId) {
        // Delete all records for the current match ID in Matches_Teams
        String whereClause = COLUMN_MATCH_ID + " = ?";
        String[] whereArgs = {String.valueOf(matchId)};
        db.delete(TABLE_MATCHES_TEAMS, whereClause, whereArgs);
    }

    // Helper method to insert match and team ID pair into Matches_Teams table
    private void insertMatchTeamPair(SQLiteDatabase db, long matchId, long team1Id, long team2Id) {
        // Insert the pair into Matches_Teams
        ContentValues values = new ContentValues();
        values.put(COLUMN_MATCH_ID, matchId);
        values.put(COLUMN_TEAM1_ID, team1Id);
        values.put(COLUMN_TEAM2_ID, team2Id);
        long result = db.insert(TABLE_MATCHES_TEAMS, null, values);
        if (result == -1) {
            throw new IllegalStateException("Failed to insert match-team pair for match_id=" + matchId + " and team_id=" + team1Id);
        }
    }


    //-------------------------------------------*******TOSS PAGE METHODS********-------------------------------------------
    public long saveOrUpdateTossDetails(long currentMatchId, Long tossId, String teamCalling, String tossWinner, String tossDecision) {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d("DatabaseHelper", "Toss received with ID: " + tossId);
        // Start a database transaction
        db.beginTransaction();
        try {
            // Look up the team IDs based on the team names
            int callingTeamId = getTeamIdFromName(db, teamCalling);
            int winningTeamId = getTeamIdFromName(db, tossWinner);

            // Validate that both teams exist
            if (callingTeamId == -1 || winningTeamId == -1) {
                Log.e("DatabaseHelper", "Invalid team ID(s): callingTeamId=" + callingTeamId + ", winningTeamId=" + winningTeamId);
                return -1; // Exit early if validation fails
            }
            ContentValues tossValues = new ContentValues();
            tossValues.put(COLUMN_TOSS_CALL_BY, callingTeamId);
            tossValues.put(COLUMN_TOSS_WON_BY, winningTeamId);
            tossValues.put(COLUMN_TOSS_WON_TEAM_CHOOSE_TO, tossDecision);

            // Check if tossId is valid for updating or needs insertion
            if (tossId == -1) { // Includes -1 check explicitly
                tossId = db.insert(TABLE_TOSS, null, tossValues);
                if (tossId == -1) {
                    throw new Exception("Failed to insert toss details.");
                } else {
                    Log.d("DatabaseHelper", "Toss details inserted successfully with ID: " + tossId);
                }
            } else {
                // Otherwise, update the existing toss record
                int rowsUpdated = db.update(TABLE_TOSS, tossValues, "toss_id = ?", new String[]{String.valueOf(tossId)});
                if (rowsUpdated <= 0) {
                    throw new Exception("Failed to update toss details.");
                } else {
                    Log.d("DatabaseHelper", "Toss details updated successfully with ID: " + tossId);
                }
            }
            tossId = updateTossInMatch(currentMatchId, tossId);
            // Mark transaction as successful
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Transaction failed: " + e.getMessage());
        } finally {
            db.endTransaction();
        }return tossId;
    }
    private int getTeamIdFromName(SQLiteDatabase db, String teamName) {
        try (Cursor cursor = db.query(
                TABLE_TEAMS,  // Table name
                new String[]{COLUMN_TEAM_ID},  // Columns to retrieve
                COLUMN_TEAM_NAME + " = ?",  // WHERE clause
                new String[]{teamName},  // Selection args
                null,  // GROUP BY
                null,  // HAVING
                null   // ORDER BY
        )) {
            if (cursor != null && cursor.moveToFirst()) {
                int teamIdColumnIndex = cursor.getColumnIndex(COLUMN_TEAM_ID);
                if (teamIdColumnIndex != -1) {
                    return cursor.getInt(teamIdColumnIndex);
                } else {
                    Log.e("DatabaseHelper", "Column " + COLUMN_TEAM_ID + " not found in the cursor.");
                }
            } else {
                Log.e("DatabaseHelper", "Team not found with name: " + teamName);
            }
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error retrieving team ID for name: " + teamName + ", Error: " + e.getMessage());
        }
        return -1; // If no team found or error occurred, return -1
    }
    public long updateTossInMatch(long currentMatchId, long tossId) {
        SQLiteDatabase db = this.getWritableDatabase();
        long updatedTossId = -1;

        try {
            ContentValues matchValues = new ContentValues();
            matchValues.put(COLUMN_TOSS, tossId); // Update toss ID in the match
            // Update the MATCHES table with the new toss ID
            int rowsUpdatedInMatch = db.update(TABLE_MATCH, matchValues, "matchId = ?", new String[]{String.valueOf(currentMatchId)});
            if (rowsUpdatedInMatch > 0) {
                Log.d("DatabaseHelper", "Match updated successfully with toss ID: " + tossId);
                updatedTossId = tossId;
            } else {
                Log.e("DatabaseHelper", "Failed to update match with toss ID: " + tossId);
            }
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error updating toss in match: " + e.getMessage());
        }
        return updatedTossId;
    }

    //-------------------------------------------*******S,Ns,Bow PAGE METHODS *****----------------------------------------------
    public void initializeBatsmanStats(long playerId, long inningsId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        Log.d("batter id check", "initializeBatsmanStats: " + playerId);
        // Check if player already has stats for this innings
        String checkQuery = "SELECT " + COLUMN_PLAYER + " FROM " + TABLE_BATSMAN +
                " WHERE " + COLUMN_PLAYER + " = ? AND " + COLUMN_INNINGS_ID + " = ?";
        Cursor cursor = db.rawQuery(checkQuery, new String[]{String.valueOf(playerId), String.valueOf(inningsId)});
        if (cursor != null && cursor.moveToFirst()) {
            // Player already exists in the Batsman table, do nothing
            Log.d("DatabaseHelper", "Batsman stats already exist, no need to insert.");
        } else {
            // Player does not exist in the Batsman table, insert new stats
            values.put(COLUMN_PLAYER, playerId);
            values.put(COLUMN_INNINGS_ID, inningsId);
            values.put(COLUMN_SCORE, 0);
            values.put(COLUMN_BALLS_PLAYED, 0);
            values.put(COLUMN_ZEROES, 0);
            values.put(COLUMN_ONES, 0);
            values.put(COLUMN_TWOS, 0);
            values.put(COLUMN_THREES, 0);
            values.put(COLUMN_FOURS, 0);
            values.put(COLUMN_FIVES, 0);
            values.put(COLUMN_SIXES, 0);
            long rowId = db.insert(TABLE_BATSMAN, null, values);
            if (rowId == -1) {
                Log.e("DatabaseHelper", "Error inserting batsman stats into database");
            } else {
                Log.d("DatabaseHelper", "Batsman stats inserted successfully with row ID: " + rowId);
            }
        }
        if (cursor != null) {
            cursor.close();
        }
        db.close();
    }
    public void initializeBowlerStats(long playerId, long inningsId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        Log.d("bowler id check", "initializeBowlerStats: " + playerId);

        // Check if player already has stats for this innings
        String checkQuery = "SELECT " + COLUMN_PLAYER + " FROM " + TABLE_BOWLER +
                " WHERE " + COLUMN_PLAYER + " = ? AND " + COLUMN_INNINGS_ID + " = ?";
        Cursor cursor = db.rawQuery(checkQuery, new String[]{String.valueOf(playerId), String.valueOf(inningsId)});

        if (cursor != null && cursor.moveToFirst()) {
            // Player already exists in the Bowler table, do nothing
            Log.d("DatabaseHelper", "Bowler stats already exist, no need to insert.");
        } else {
            // Player does not exist in the Bowler table, insert new stats
            values.put(COLUMN_PLAYER, playerId);
            values.put(COLUMN_INNINGS_ID, inningsId);
            values.put(COLUMN_MAIDENS, 0);
            values.put(COLUMN_BALLS_PLAYED, 0);
            values.put(COLUMN_RUNS, 0);
            values.put(COLUMN_ECONOMY, 0.0);
            values.put(COLUMN_ZEROES, 0);
            values.put(COLUMN_ONES, 0);
            values.put(COLUMN_TWOS, 0);
            values.put(COLUMN_THREES, 0);
            values.put(COLUMN_FOURS, 0);
            values.put(COLUMN_FIVES, 0);
            values.put(COLUMN_SIXES, 0);
            values.put(COLUMN_WK, 0);
            values.put(COLUMN_BY, 0);
            values.put(COLUMN_LB, 0);
            values.put(COLUMN_WB, 0);
            values.put(COLUMN_NB, 0);
            values.put(COLUMN_DB, 0);

            long rowId = db.insert(TABLE_BOWLER, null, values);
            if (rowId == -1) {
                Log.e("DatabaseHelper", "Error inserting bowler stats into database");
            } else {
                Log.d("DatabaseHelper", "Bowler stats inserted successfully with row ID: " + rowId);
            }
        }

        if (cursor != null) {
            cursor.close();
        }
        db.close();
    }
    public long insertPlayer(String playerName, long teamId, long inningsId) {
        SQLiteDatabase db = this.getWritableDatabase();
        long playerId = -1;
        // Check if the player already exists
        String selectQuery = "SELECT " + COLUMN_PLAYER_ID + " FROM " + TABLE_PLAYERS + " WHERE " + COLUMN_PLAYER_NAME + " = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{playerName});
        if (cursor != null && cursor.moveToFirst()) {
            // Player exists, return the existing ID
            playerId = cursor.getLong(0);
            Log.d("DatabaseHelper", "Player already exists with ID: " + playerId);
        } else {
            // Player does not exist, insert a new record
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_PLAYER_NAME, playerName);
            playerId = db.insert(TABLE_PLAYERS, null, contentValues);
            if (playerId == -1) {
                Log.e("DatabaseHelper", "Error inserting player into database");
            } else {
                Log.d("DatabaseHelper", "Player inserted successfully with ID: " + playerId);
            }
        }
        // Now handle the player-team relation (insert or update)
        insertPlayerTeamRelation(db, playerId, teamId, inningsId);
        if (cursor != null) {
            cursor.close();
        }
        db.close();
        return playerId;
    }
    private void insertPlayerTeamRelation(SQLiteDatabase db, long playerId, long teamId, long inningsId) {
        // Check if the relation already exists
        String relationCheckQuery = "SELECT " + COLUMN_PLAYER_ID +
                " FROM " + TABLE_PLAYERS_TEAMS +
                " WHERE " + COLUMN_PLAYER_ID + " = ? AND " +
                COLUMN_TEAM_ID + " = ? AND " +
                COLUMN_INNINGS_ID + " = ?";
        Cursor cursor = db.rawQuery(relationCheckQuery, new String[]{
                String.valueOf(playerId),
                String.valueOf(teamId),
                String.valueOf(inningsId)
        });

        if (cursor != null && cursor.moveToFirst()) {
            // Relation already exists, no need to insert again
            Log.d("DatabaseHelper", "Player already linked to team for this innings.");
        } else {
            // Relation does not exist, insert new relation
            ContentValues playerTeamValues = new ContentValues();
            playerTeamValues.put(COLUMN_TEAM_ID, teamId);
            playerTeamValues.put(COLUMN_PLAYER_ID, playerId);
            playerTeamValues.put(COLUMN_INNINGS_ID, inningsId); // Add innings ID

            long teamResult = db.insert(TABLE_PLAYERS_TEAMS, null, playerTeamValues);
            if (teamResult == -1) {
                Log.e("DatabaseHelper", "Error inserting player-team-innings relation into database");
            } else {
                Log.d("DatabaseHelper", "Player-team-innings relation inserted successfully");
            }
        }

        if (cursor != null) {
            cursor.close();
        }
    }
    public long startFirstInnings(long matchId, long battingTeamId, String currentInningsNumber) {
        Log.d(TAG, "startFirstInnings: current innings number is" + currentInningsNumber);
        if (matchId == -1 || battingTeamId == -1) {
            Log.e("DatabaseHelper", "Missing data for match or team in SharedPreferences");
            return -1;
        }
        SQLiteDatabase db = this.getWritableDatabase();
        long inningsId = 0;
        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_INNINGS_NUMBER, currentInningsNumber == null ? 1 : 2);
            values.put(COLUMN_MATCH_ID, matchId);
            values.put(COLUMN_TEAM_BATTING, battingTeamId);
            values.put(COLUMN_IS_COMPLETED, 0);
            inningsId = db.insert(TABLE_INNINGS, null, values);
            if (inningsId == -1) {
                Log.e("DatabaseHelper", "Failed to start first innings.");
            }
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error inserting player-team into database");
        } finally {
            db.close();
        }
        return inningsId;
    }
    public long insertOver(long inningsId, int over, long bowlerId, int isMaiden) {
        SQLiteDatabase db = this.getWritableDatabase();
        long over_id = 0;
        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_INNINGS_ID, inningsId);
            values.put(COLUMN_OVER_NUMBER, over);
            values.put(COLUMN_PLAYER_ID, bowlerId);
            values.put(COLUMN_IS_MAIDEN, isMaiden);
            over_id = db.insert(TABLE_OVERS, null, values);
            if (over_id == -1) {
                Log.e("DatabaseHelper", "Failed to start over");
            }
        } catch (Exception e) {
            Log.e(TAG, "insertOver: , failed to insert over");
        } finally {
            db.close();
        }
        return over_id;
    }
    public void insertMaidenOver(long overId){
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_IS_MAIDEN, 1);
            db.update(TABLE_OVERS, values, COLUMN_OVER_ID + " = ?", new String[]{String.valueOf(overId)});
        } catch (Exception e) {
            Log.e(TAG, "insertMaidenOver: failed to update maiden over");
        }
    }
    public long insertPartnership(long inningsId, long batsman1Id, long batsman2Id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long partnership_id = 0;
        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_INNINGS_ID, inningsId);
            values.put(COLUMN_BATSMAN1_ID, batsman1Id);
            values.put(COLUMN_BATSMAN2_ID, batsman2Id);
            values.put(COLUMN_RUNS, 0);
            values.put(COLUMN_BALLS, 0);
            partnership_id = db.insert(TABLE_PARTNERSHIPS, null, values);
            if (partnership_id == -1) {
                Log.e("DatabaseHelper", "Failed to start over");
            }
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Failed to start over");
        } finally {
            db.close();
        }
        return partnership_id;
    }
    public long initializeTeamStats(long inningsId) {
        SQLiteDatabase db = this.getWritableDatabase();
        long teamStatsId = -1;
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_INNINGS_ID, inningsId);
            teamStatsId = db.insert(TABLE_TEAM_STATISTICS, null, contentValues);
            if (teamStatsId != -1) {
                Log.d("DatabaseHelper", "Team stats initialized for innings ID: " + inningsId);
            } else {
                Log.e("DatabaseHelper", "Failed to initialize team stats.");
            }
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error during team stats initialization.");
        } finally {
            db.close();
        }
        return teamStatsId;
    }


    //---------------------------------------------ball table------------------------------
    public long insertBallDataFor0To6(long overId, long ballNumber, int runs, long strikerId, long nonStrikerId) {
        String typeofBall = "Normal";
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_OVER_ID, overId);
        contentValues.put(COLUMN_BALL_NUMBER, ballNumber);
        contentValues.put(COLUMN_TYPE_OF_BALL, typeofBall);
        contentValues.put(COLUMN_RUNS, runs);
        contentValues.put(COLUMN_IS_WICKET, 0);
        contentValues.put(COLUMN_STRIKER, strikerId);
        contentValues.put(COLUMN_NON_STRIKER, nonStrikerId);
        long ballId = db.insert(TABLE_BALLS, null, contentValues);
        db.close();
        return ballId;
    }
    public long insertBallDataForByLByes(long overId, long ballNumber, String typeOfBall, int runs, long strikerId, long nonStrikerId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_OVER_ID, overId); // Get overId from SharedPreferences
        contentValues.put(COLUMN_BALL_NUMBER, ballNumber);
        contentValues.put(COLUMN_TYPE_OF_BALL, typeOfBall); // Set type of ball (all legal for now)
        contentValues.put(COLUMN_RUNS, runs); // Runs scored on the ball
        contentValues.put(COLUMN_IS_WICKET, 0);
        contentValues.put(COLUMN_STRIKER, strikerId); // Get striker ID from SharedPreferences
        contentValues.put(COLUMN_NON_STRIKER, nonStrikerId); // Get non-striker ID from SharedPreferences
        long ballId = db.insert(TABLE_BALLS, null, contentValues);
        db.close(); // Close the database connection
        return ballId;
    }
    public long insertBallDataForWide(long overId,  long ballNumber, int runs, long strikerId, long nonStrikerId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_OVER_ID, overId); // Get overId from SharedPreferences
        contentValues.put(COLUMN_BALL_NUMBER, ballNumber);
        contentValues.put(COLUMN_TYPE_OF_BALL, WIDE_BALL); // Set type of ball (all legal for now)
        contentValues.put(COLUMN_RUNS, runs + 1); // Runs scored on the ball
        contentValues.put(COLUMN_IS_WICKET, 0);
        contentValues.put(COLUMN_STRIKER, strikerId); // Get striker ID from SharedPreferences
        contentValues.put(COLUMN_NON_STRIKER, nonStrikerId); // Get non-striker ID from SharedPreferences
        long ballId = db.insert(TABLE_BALLS, null, contentValues);
        db.close(); // Close the database connection
        return ballId;
    }
    public long insertBallDataForNb(long overId,  long ballNumber, int extraRuns, long strikerId, long nonStrikerId) {
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            int totalRuns = extraRuns + 1;
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_OVER_ID, overId); // Over ID
            contentValues.put(COLUMN_BALL_NUMBER, ballNumber);
            contentValues.put(COLUMN_TYPE_OF_BALL, NO_BALL); // Type of ball set to "No Ball"
            contentValues.put(COLUMN_RUNS, totalRuns); // Total runs for the no-ball
            contentValues.put(COLUMN_IS_WICKET, 0); // No wicket for no-ball
            contentValues.put(COLUMN_STRIKER, strikerId); // Striker ID
            contentValues.put(COLUMN_NON_STRIKER, nonStrikerId); // Non-striker ID
            long ballId = db.insert(TABLE_BALLS, null, contentValues);
            Log.d("DatabaseHelper", "No Ball inserted successfully with ID: " + ballId);
            return ballId; // Return the ID of the inserted ball (auto-generated)
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Failed to insert No Ball data.");
            return -1; // Indicate failure
        }
        // Close the database connection
    }
    public long insertBallDataForWicket(long overId, long ballNumber, String typeOfBall, int runs, long strikerId, long nonStrikerId) {
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            int totalRuns = runs;
            if (typeOfBall.equals(WIDE_BALL) || typeOfBall.equals(NO_BALL)) {
                totalRuns += 1; // Add 1 extra run for wide/no ball
            }
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_OVER_ID, overId); // Over ID
            contentValues.put(COLUMN_BALL_NUMBER, ballNumber);
            contentValues.put(COLUMN_TYPE_OF_BALL, typeOfBall); // Type of ball (Wide/No Ball/Normal)
            contentValues.put(COLUMN_RUNS, totalRuns); // Total runs for the ball
            contentValues.put(COLUMN_IS_WICKET, 1); // No wicket for this ball type (unless it's a wicket)
            contentValues.put(COLUMN_STRIKER, strikerId); // Striker ID
            contentValues.put(COLUMN_NON_STRIKER, nonStrikerId); // Non-striker ID
            long ballId = db.insert(TABLE_BALLS, null, contentValues);
            Log.d("DatabaseHelper", "Ball data inserted successfully with ID: " + ballId);
            return ballId; // Return the ID of the inserted ball (auto-generated)
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Failed to insert ball data.");
            return -1; // Indicate failure
        }
    }



    //---------------------------------updating partnerships---------------------------------
    public void updatePartnershipFor0to6(int runsScored, long partnershipId) {
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            if (partnershipId == -1) {
                Log.e("DatabaseHelper", "Partnership ID not found in SharedPreferences.");
                return;
            }
            String query = "UPDATE " + TABLE_PARTNERSHIPS +
                    " SET " + COLUMN_RUNS + " = " + COLUMN_RUNS + " + ?, " +
                    COLUMN_BALLS + " = " + COLUMN_BALLS + " + 1 " +
                    "WHERE " + COLUMN_PARTNERSHIP_ID + " = ?";
            db.execSQL(query, new Object[]{runsScored, partnershipId});
            Log.d("DatabaseHelper", "Partnership updated successfully.");
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Failed to update partnership.");
        }
    }
    public void updatePartnershipForByLByes(long partnershipId) {
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            if (partnershipId == -1) {
                Log.e("DatabaseHelper", "Partnership ID not found in SharedPreferences.");
                return;
            }
            String updateQuery = "UPDATE " + TABLE_PARTNERSHIPS +
                    " SET " + COLUMN_BALLS + " = " + COLUMN_BALLS + " + ? " +
                    " WHERE " + COLUMN_PARTNERSHIP_ID + " = ?";
            SQLiteStatement statement = db.compileStatement(updateQuery);
            statement.bindLong(1, 1);    // Bind ballsFaced
            statement.bindLong(2, partnershipId); // Bind partnershipId
            statement.executeUpdateDelete(); // Execute the update query
            Log.d("DatabaseHelper", "Partnership updated for Byes/Leg Byes successfully.");
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Failed to update partnership for Byes/Leg Byes.");
        }
    }
    public void updatePartnershipForNb(long partnershipId, int runsScored, String runType) {
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            if (partnershipId == -1) {
                Log.e("DatabaseHelper", "Invalid Partnership ID.");
                return;
            }
            String query = "UPDATE " + TABLE_PARTNERSHIPS + " SET ";
            if (runType.equals(FROM_BAT)) {
                query += COLUMN_RUNS + " = " + COLUMN_RUNS + " + ? ";
            } else if (runType.equals(BYE_BALL) || runType.equalsIgnoreCase(LEG_BYE_BALL)) {
                // No runs added for byes or leg-byes in partnerships table
                Log.d("DatabaseHelper", "No runs added for run type: " + runType);
                return;
            }
            // Add WHERE condition to the query
            query += " WHERE " + COLUMN_PARTNERSHIP_ID + " = ?";
            // Execute the update query
            db.execSQL(query, new Object[]{runsScored, partnershipId});
            Log.d("DatabaseHelper", "Partnership updated successfully for no-ball run type: " + runType);
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Failed to update partnership for no-ball.");
        }
    }
    public void updatePartnershipForRunOut(long partnershipId, int runsScored, String ballType, String runsFrom) {
        String query = null;
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            Log.d(TAG, "updatePartnershipForRunOut: Ball Type: " + ballType + ", Runs From: " + runsFrom);
            if (runsFrom.equals("Bat") && ballType.equals("Normal")) {
                query = "UPDATE " + TABLE_PARTNERSHIPS + " SET " +
                        COLUMN_RUNS + " = " + COLUMN_RUNS + " + " + runsScored + ", " +
                        COLUMN_BALLS + " = " + COLUMN_BALLS + " + 1 " +
                        "WHERE " + COLUMN_PARTNERSHIP_ID + " = " + partnershipId;
            } else if (runsFrom.equals("Bat")) {
                query = "UPDATE " + TABLE_PARTNERSHIPS + " SET " +
                        COLUMN_RUNS + " = " + COLUMN_RUNS + " + " + runsScored + " " +
                        "WHERE " + COLUMN_PARTNERSHIP_ID + " = " + partnershipId;
            } else if (ballType.equals("Normal")) {
                query = "UPDATE " + TABLE_PARTNERSHIPS + " SET " +
                        COLUMN_BALLS + " = " + COLUMN_BALLS + " + 1 " +
                        "WHERE " + COLUMN_PARTNERSHIP_ID + " = " + partnershipId;
            }
            // Execute the query if it's not empty
            if (query != null) {
                db.execSQL(query);
                Log.d("DatabaseHelper", "Partnership updated successfully for run out.");
            } else {
                Log.d("DatabaseHelper", "No update query executed as conditions were not met.");
            }
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Failed to update partnership for run out.");
        }
    }



    //--------------------------------------updating batsman score---------------------------
    public void updateBatsmanStatsFor0To6(long inningsId, long playerId, int runs) {
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            String updateQuery = "UPDATE " + TABLE_BATSMAN +
                    " SET " +
                    COLUMN_SCORE + " = " + COLUMN_SCORE + " + ?, " +
                    COLUMN_BALLS_PLAYED + " = " + COLUMN_BALLS_PLAYED + " + 1, ";

            // Update the run type columns (0s, 1s, 2s, etc.)
            switch (runs) {
                case 0:
                    updateQuery += COLUMN_ZEROES + " = " + COLUMN_ZEROES + " + 1 ";
                    break;
                case 1:
                    updateQuery += COLUMN_ONES + " = " + COLUMN_ONES + " + 1 ";
                    break;
                case 2:
                    updateQuery += COLUMN_TWOS + " = " + COLUMN_TWOS + " + 1 ";
                    break;
                case 3:
                    updateQuery += COLUMN_THREES + " = " + COLUMN_THREES + " + 1 ";
                    break;
                case 4:
                    updateQuery += COLUMN_FOURS + " = " + COLUMN_FOURS + " + 1 ";
                    break;
                case 5:
                    updateQuery += COLUMN_FIVES + " = " + COLUMN_FIVES + " + 1 ";
                    break;
                case 6:
                    updateQuery += COLUMN_SIXES + " = " + COLUMN_SIXES + " + 1 ";
                    break;
                default:
                    return;  // Invalid run, do nothing
            }
            updateQuery += " WHERE " + COLUMN_PLAYER + " = ? AND " + COLUMN_INNINGS_ID + " = ?";
            SQLiteStatement statement = db.compileStatement(updateQuery);
            statement.bindLong(1, runs);  // Bind the runs value
            statement.bindLong(2, playerId);  // Bind player_id
            statement.bindLong(3, inningsId);  // Bind innings_id
            statement.executeUpdateDelete();  // Execute the update query
        } catch (Exception e) {
            Log.e(TAG, "updateBatsmanStatsFor0To6: failed to update stats of batsman with id" + playerId);
        }
    }
    public void updateBatsmanForByLByes(long inningsId, long playerId) {
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            String updateQuery = "UPDATE " + TABLE_BATSMAN +
                    " SET " + COLUMN_BALLS_PLAYED + " = " + COLUMN_BALLS_PLAYED + " + ? ," +
                    COLUMN_ZEROES + " = " + COLUMN_ZEROES + " + 1" +
                    " WHERE " + COLUMN_PLAYER + " = ? AND " + COLUMN_INNINGS_ID + " = ?";
            SQLiteStatement statement = db.compileStatement(updateQuery);
            statement.bindLong(1, 1);  // Bind player_id
            statement.bindLong(2, playerId); // Bind innings_id
            statement.bindLong(3, inningsId); // Bind innings_id
            statement.executeUpdateDelete();   // Execute the update query
        } catch (Exception e) {
            Log.e(TAG, "updateBatsmanForByLByes: failed to update");
        }
    }
    public void updateBatsmanStatsForNb(long inningsId, long playerId, int runs, String runsSourceInNoBall) {
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            String updateQuery = "UPDATE " + TABLE_BATSMAN +
                    " SET " + COLUMN_BALLS_PLAYED + " = " + COLUMN_BALLS_PLAYED + " + 0"; // No-ball does not increment balls played
            if (runsSourceInNoBall.equals(FROM_BAT)) {
                updateQuery += ", " + COLUMN_SCORE + " = " + COLUMN_SCORE + " + ?";
                switch (runs) {
                    case 0:
                        updateQuery += ", " + COLUMN_ZEROES + " = " + COLUMN_ZEROES + " + 1";
                        break;
                    case 1:
                        updateQuery += ", " + COLUMN_ONES + " = " + COLUMN_ONES + " + 1";
                        break;
                    case 2:
                        updateQuery += ", " + COLUMN_TWOS + " = " + COLUMN_TWOS + " + 1";
                        break;
                    case 3:
                        updateQuery += ", " + COLUMN_THREES + " = " + COLUMN_THREES + " + 1";
                        break;
                    case 4:
                        updateQuery += ", " + COLUMN_FOURS + " = " + COLUMN_FOURS + " + 1";
                        break;
                    case 5:
                        updateQuery += ", " + COLUMN_FIVES + " = " + COLUMN_FIVES + " + 1";
                        break;
                    case 6:
                        updateQuery += ", " + COLUMN_SIXES + " = " + COLUMN_SIXES + " + 1";
                        break;
                    default:
                        return; // Invalid runs, do nothing
                }
            } else {
                return; // Invalid run type, do nothing
            }
            updateQuery += " WHERE " + COLUMN_PLAYER + " = ? AND " + COLUMN_INNINGS_ID + " = ?";
            SQLiteStatement statement = db.compileStatement(updateQuery);
            statement.bindLong(1, runs); // Bind runs (actual or extras)
            statement.bindLong(2, playerId);  // Bind player_id
            statement.bindLong(3, inningsId);  // Bind innings_id
            statement.executeUpdateDelete();  // Execute the update query
        } catch (Exception e) {
            Log.e(TAG, "updateBatsmanStatsForNb:  failed to update batsman stats for no ball");
        }
    }
    public void updateBatsmanStatsForWicket(long inningsId, long playerId, int runs, String ballType, String runsFrom, String wicketType) {
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            String updateQuery = "UPDATE " + TABLE_BATSMAN + " SET ";
            SQLiteStatement statement = null;

            switch (wicketType) {
                case BOWLED:
                case CAUGHT:
                case LBW:
                    updateQuery += COLUMN_BALLS + " = " + COLUMN_BALLS + " + 1 ";
                    updateQuery += " WHERE " + COLUMN_PLAYER + " = ? AND " + COLUMN_INNINGS_ID + " = ?";
                    statement = db.compileStatement(updateQuery);
                    statement.bindLong(1, playerId);
                    statement.bindLong(2, inningsId);
                    break;

                case STUMPED:
                    if (ballType.equals(NORMAL_BALL)) {
                        updateQuery += COLUMN_BALLS + " = " + COLUMN_BALLS + " + 1 ";
                        updateQuery += " WHERE " + COLUMN_PLAYER + " = ? AND " + COLUMN_INNINGS_ID + " = ?";
                        statement = db.compileStatement(updateQuery);
                        statement.bindLong(1, playerId);
                        statement.bindLong(2, inningsId);
                    }
                    break;
                case RUN_OUT:
                    switch (ballType) {
                        case NORMAL_BALL:
                            if (runsFrom.equals(FROM_BAT)) {
                                updateQuery += COLUMN_SCORE + " = " + COLUMN_SCORE + " + ? ," +
                                        COLUMN_BALLS + " = " + COLUMN_BALLS + " + 1 ";
                                updateQuery += ", " + getRunColumn(runs) + " = " + getRunColumn(runs) + " + 1 ";
                                updateQuery += " WHERE " + COLUMN_PLAYER + " = ? AND " + COLUMN_INNINGS_ID + " = ?";
                                statement = db.compileStatement(updateQuery);
                                statement.bindLong(1, runs);
                                statement.bindLong(2, playerId);
                                statement.bindLong(3, inningsId);
                            } else if (runsFrom.equals(BYE_BALL) || runsFrom.equals(LEG_BYE_BALL)) {
                                updateQuery += COLUMN_BALLS + " = " + COLUMN_BALLS + " + 1 ";
                                updateQuery += " WHERE " + COLUMN_PLAYER + " = ? AND " + COLUMN_INNINGS_ID + " = ?";
                                statement = db.compileStatement(updateQuery);
                                statement.bindLong(1, playerId);
                                statement.bindLong(2, inningsId);
                            }
                            break;

                        case NO_BALL:
                            if (runsFrom.equals(FROM_BAT)) {
                                updateQuery += COLUMN_SCORE + " = " + COLUMN_SCORE + " + ? ";
                                updateQuery += ", " + getRunColumn(runs) + " = " + getRunColumn(runs) + " + 1 ";
                                updateQuery += " WHERE " + COLUMN_PLAYER + " = ? AND " + COLUMN_INNINGS_ID + " = ?";
                                statement = db.compileStatement(updateQuery);
                                statement.bindLong(1, runs);
                                statement.bindLong(2, playerId);
                                statement.bindLong(3, inningsId);
                            }
                            break;

                        case WIDE_BALL:
                            updateQuery = null;
                            break;
                    }
                    break;
            }

            if (updateQuery != null && statement != null) {
                statement.executeUpdateDelete();
            }
        } catch (Exception e) {
            Log.e(TAG, "updateBatsmanStatsForWicket: failed to update batter stats in runout", e);
        }
    }
    private String getRunColumn(int runs) {
        switch (runs) {
            case 0: return COLUMN_ZEROES;
            case 1: return COLUMN_ONES;
            case 2: return COLUMN_TWOS;
            case 3: return COLUMN_THREES;
            case 4: return COLUMN_FOURS;
            case 5: return COLUMN_FIVES;
            case 6: return COLUMN_SIXES;
            default: return ""; // No specific column for invalid runs
        }
    }


    //---------------------------------------- update bowlers stats-------------------------------
    public void updateBowlerStatsFor0to6(long innings_id, long player_id, int runs) {
        Log.d(TAG, "updateBowlerStatsFor0to6: "+ runs);
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            String updateQuery = "UPDATE " + TABLE_BOWLER +
                    " SET " +
                    COLUMN_RUNS + " = " + COLUMN_RUNS + " + ?, " +
                    COLUMN_BALLS_PLAYED + " = " + COLUMN_BALLS_PLAYED + " + 1, ";
            switch (runs) {
                case 0:
                    updateQuery += COLUMN_ZEROES + " = " + COLUMN_ZEROES + " + 1 ";
                    break;
                case 1:
                    updateQuery += COLUMN_ONES + " = " + COLUMN_ONES + " + 1 ";
                    break;
                case 2:
                    updateQuery += COLUMN_TWOS + " = " + COLUMN_TWOS + " + 1 ";
                    break;
                case 3:
                    updateQuery += COLUMN_THREES + " = " + COLUMN_THREES + " + 1 ";
                    break;
                case 4:
                    updateQuery += COLUMN_FOURS + " = " + COLUMN_FOURS + " + 1 ";
                    break;
                case 5:
                    updateQuery += COLUMN_FIVES + " = " + COLUMN_FIVES + " + 1 ";
                    break;
                case 6:
                    updateQuery += COLUMN_SIXES + " = " + COLUMN_SIXES + " + 1 ";
                    break;
                default:
                    return;
            }
            updateQuery += " WHERE " + COLUMN_PLAYER + " = ? AND " + COLUMN_INNINGS_ID + " = ?";
            SQLiteStatement statement = db.compileStatement(updateQuery);
            statement.bindLong(1, runs);
            statement.bindLong(2, player_id);
            statement.bindLong(3, innings_id);
            statement.executeUpdateDelete();
        } catch (Exception e) {
            Log.e(TAG, "updateBatsmanStatsFor0To6: failed to update stats of bowler with id" + player_id);
        }
    }
    public void updateBowlerForByLBes(long innings_id, long player_id, String ballType) {
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            String updateQuery = "UPDATE " + TABLE_BOWLER +
                    " SET " + COLUMN_BALLS_PLAYED + " = " + COLUMN_BALLS_PLAYED + " + 1, ";
            if (BYE_BALL.equalsIgnoreCase(ballType)) {
                updateQuery += COLUMN_BY + " = " + COLUMN_BY + " + 1 ";
            } else if (LEG_BYE_BALL.equalsIgnoreCase(ballType)) {
                updateQuery += COLUMN_LB + " = " + COLUMN_LB + " + 1 ";
            } else {
                return;
            }
            // Add WHERE condition to the query
            updateQuery += " WHERE " + COLUMN_PLAYER + " = ? AND " + COLUMN_INNINGS_ID + " = ?";
            // Execute the query
            SQLiteStatement statement = db.compileStatement(updateQuery);
            statement.bindLong(1, player_id);    // Bind player_id
            statement.bindLong(2, innings_id);   // Bind innings_id
            statement.executeUpdateDelete(); // Execute the update query
            Log.d("DatabaseHelper", "Bowler updated successfully for Byes/Leg Byes.");
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Failed to update bowler for Byes/Leg Byes.");
        }
    }
    public void updateBowlerForWide(long inningsId, long playerId) {
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            String updateQuery = "UPDATE " + TABLE_BOWLER +
                    " SET " + COLUMN_WB + " = " + COLUMN_WB + " + 1, " +
                    COLUMN_RUNS + " = " + COLUMN_RUNS + " + ? " +
                    " WHERE " + COLUMN_PLAYER + " = ? AND " + COLUMN_INNINGS_ID + " = ?";
            SQLiteStatement statement = db.compileStatement(updateQuery);
            statement.bindLong(1, 1);    // Bind the wide runs
            statement.bindLong(2, playerId);  // Bind the bowler ID
            statement.bindLong(3, inningsId); // Bind the innings ID
            statement.executeUpdateDelete();
            Log.d("DatabaseHelper", "Bowler updated successfully for Wide.");
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Failed to update bowler for Wide.");
        }
    }
    public void updateBowlerStatsForNb(long inningsId, long playerId, int runs, String runType) {
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            String updateQuery = "UPDATE " + TABLE_BOWLER +
                    " SET " +
                    COLUMN_RUNS + " = " + COLUMN_RUNS + " + ?, " +  // Increment runs based on the type
                    COLUMN_BALLS_PLAYED + " = " + COLUMN_BALLS_PLAYED + " + 0 ";  // No-ball does not increment balls played
            switch (runType) {
                case FROM_BAT:
                    switch (runs) {
                        case 0:
                            updateQuery += ", " + COLUMN_ZEROES + " = " + COLUMN_ZEROES + " + 1 ";
                            break;
                        case 1:
                            updateQuery += ", " + COLUMN_ONES + " = " + COLUMN_ONES + " + 1 ";
                            break;
                        case 2:
                            updateQuery += ", " + COLUMN_TWOS + " = " + COLUMN_TWOS + " + 1 ";
                            break;
                        case 3:
                            updateQuery += ", " + COLUMN_THREES + " = " + COLUMN_THREES + " + 1 ";
                            break;
                        case 4:
                            updateQuery += ", " + COLUMN_FOURS + " = " + COLUMN_FOURS + " + 1 ";
                            break;
                        case 5:
                            updateQuery += ", " + COLUMN_FIVES + " = " + COLUMN_FIVES + " + 1 ";
                            break;
                        case 6:
                            updateQuery += ", " + COLUMN_SIXES + " = " + COLUMN_SIXES + " + 1 ";
                            break;
                        default:
                            return; // Invalid run, do nothing
                    }
                    break;
                case BYE_BALL:
                case LEG_BYE_BALL:
                    // No need to update specific run type columns for byes or leg byes
                    // Increment runs by 1 for the no-ball
                    break;
                default:
                    return; // Invalid run type, do nothing
            }
            updateQuery += " WHERE " + COLUMN_PLAYER + " = ? AND " + COLUMN_INNINGS_ID + " = ?";
            SQLiteStatement statement = db.compileStatement(updateQuery);
            statement.bindLong(1, (runType.equals(FROM_BAT) ? runs + 1 : 1));  // Bind runs or 1 based on run type
            statement.bindLong(2, playerId);  // Bind player_id
            statement.bindLong(3, inningsId);  // Bind innings_id
            statement.executeUpdateDelete();  // Execute the update query
        } catch (Exception e) {
            Log.e(TAG, "updateBowlerStatsForNb: failed to update stats for bowler stats for no ball");
        }
    }
    public void updateBowlerStatsForWicket(long innings_id, long bowler_id, int runs, String ballType, String runsFrom, String wicketType) {
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            String updateQuery = "UPDATE " + TABLE_BOWLER + " SET ";
            SQLiteStatement statement = null;
            switch (wicketType) {
                case BOWLED:
                case CAUGHT:
                case LBW:
                    updateQuery += COLUMN_BALLS + " = " + COLUMN_BALLS + " + 1 ," +
                            COLUMN_WK + " = " + COLUMN_WK + " + 1 ";
                    updateQuery += " WHERE " + COLUMN_PLAYER + " = ? AND " + COLUMN_INNINGS_ID + " = ?";
                    statement = db.compileStatement(updateQuery);
                    statement.bindLong(1, bowler_id);
                    statement.bindLong(2, innings_id);
                    break;
                case STUMPED:
                    if (ballType.equals(WIDE_BALL)) {
                        updateQuery += COLUMN_WK + " = " + COLUMN_WK + " + 1 ," +
                                COLUMN_RUNS + " = " + COLUMN_RUNS + " + 1";
                        updateQuery += " WHERE " + COLUMN_PLAYER + " = ? AND " + COLUMN_INNINGS_ID + " = ?";
                        statement = db.compileStatement(updateQuery);
                        statement.bindLong(1, bowler_id);
                        statement.bindLong(2, innings_id);
                    } else if (ballType.equals(NORMAL_BALL)) {
                        updateQuery += COLUMN_BALLS + " = " + COLUMN_BALLS + " + 1 ," +
                                COLUMN_WK + " = " + COLUMN_WK + " + 1 ";
                        updateQuery += " WHERE " + COLUMN_PLAYER + " = ? AND " + COLUMN_INNINGS_ID + " = ?";
                        statement = db.compileStatement(updateQuery);
                        statement.bindLong(1, bowler_id);
                        statement.bindLong(2, innings_id);
                    }
                    break;
                case RUN_OUT:
                    switch (ballType) {
                        case NORMAL_BALL:
                            if (runsFrom.equals(FROM_BAT)) {
                                updateQuery += COLUMN_BALLS + " = " + COLUMN_BALLS + " + 1 ," +
                                        COLUMN_RUNS + " = " + COLUMN_RUNS + " + ?";
                                updateQuery += " WHERE " + COLUMN_PLAYER + " = ? AND " + COLUMN_INNINGS_ID + " = ?";
                                statement = db.compileStatement(updateQuery);
                                statement.bindLong(1, runs); // Bind runs
                                statement.bindLong(2, bowler_id); // Bind bowler_id
                                statement.bindLong(3, innings_id); // Bind innings_id
                            } else if (runsFrom.equals(BYE_BALL) || runsFrom.equals(LEG_BYE_BALL)) {
                                updateQuery += COLUMN_BALLS + " = " + COLUMN_BALLS + " + 1";
                                updateQuery += " WHERE " + COLUMN_PLAYER + " = ? AND " + COLUMN_INNINGS_ID + " = ?";
                                statement = db.compileStatement(updateQuery);
                                statement.bindLong(1, bowler_id); // Bind bowler_id
                                statement.bindLong(2, innings_id); // Bind innings_id
                            }
                            break;
                        case NO_BALL:
                            if (runsFrom.equals(FROM_BAT)) {
                                updateQuery += COLUMN_RUNS + " = " + COLUMN_RUNS + " + ?";
                                updateQuery += " WHERE " + COLUMN_PLAYER + " = ? AND " + COLUMN_INNINGS_ID + " = ?";
                                statement = db.compileStatement(updateQuery);
                                statement.bindLong(1, runs);
                                statement.bindLong(2, bowler_id);
                                statement.bindLong(3, innings_id);
                            }
                            break;
                        case WIDE_BALL:
                            updateQuery += COLUMN_RUNS + " = " + COLUMN_RUNS + " + 1";
                            updateQuery += " WHERE " + COLUMN_PLAYER + " = ? AND " + COLUMN_INNINGS_ID + " = ?";
                            statement = db.compileStatement(updateQuery);
                            statement.bindLong(1, bowler_id);
                            statement.bindLong(2, innings_id);
                            break;
                    }
                    break;
                default:
                    updateQuery = null;
                    break;
            }
            if (updateQuery != null && statement != null) {
                statement.executeUpdateDelete();
            }
        } catch (Exception e) {
            Log.e(TAG, "updateBowlerStatsForWicket: failed to update bowler stats in run out");
        }
    }

    //------------------------------------update extra table------------------------------
    public void updateExtrasTable(long ballId, String ballType, int runs) {
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_BALL_ID, ballId);
            values.put(COLUMN_EXTRA_TYPE, ballType);
            switch (ballType) {
                case BYE_BALL:
                case LEG_BYE_BALL: {
                    values.put(COLUMN_EXTRA_RUNS, runs);
                    long result = db.insertWithOnConflict(TABLE_EXTRAS, null, values, SQLiteDatabase.CONFLICT_REPLACE);
                    if (result == -1) {
                        Log.e("updateExtrasTable", "Failed to insert or update extra runs for Bye/Leg Bye.");
                    } else {
                        Log.d("updateExtrasTable", "Extra runs updated successfully for Bye/Leg Bye, ball_id: " + ballId);
                    }
                    break;
                }
                case WIDE_BALL: {
                    values.put(COLUMN_EXTRA_RUNS, runs + 1); // Add 1 run for the wide ball itself
                    long result = db.insertWithOnConflict(TABLE_EXTRAS, null, values, SQLiteDatabase.CONFLICT_REPLACE);
                    if (result == -1) {
                        Log.e("updateExtrasTable", "Failed to insert or update extra runs for Wide.");
                    } else {
                        Log.d("updateExtrasTable", "Extra runs updated successfully for Wide, ball_id: " + ballId);
                    }
                    break;
                }
                case NO_BALL: {
                    values.put(COLUMN_EXTRA_RUNS, runs + 1); // Add 1 run for the no-ball itself
                    long result = db.insertWithOnConflict(TABLE_EXTRAS, null, values, SQLiteDatabase.CONFLICT_REPLACE);
                    if (result == -1) {
                        Log.e("updateExtrasTable", "Failed to insert or update extra runs for No Ball.");
                    } else {
                        Log.d("updateExtrasTable", "Extra runs updated successfully for No Ball, ball_id: " + ballId);
                    }
                    break;
                }
                default:
                    Log.e("updateExtrasTable", "Invalid extra type: " + ballType);
                    break;
            }
        } catch (Exception e) {
            Log.e("updateExtrasTable", "Error while updating extras table: " + e.getMessage());
        }
    }
    public void updateWicketsTable(long ballId, String wicketType, long batsmanId, int runs) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.COLUMN_BALL_ID, ballId); // Associate the wicket with the ball
            values.put(DatabaseHelper.COLUMN_WICKET_TYPE, wicketType); // Type of wicket
            values.put(DatabaseHelper.COLUMN_WICKET_BATSMAN, batsmanId); // Dismissed batsman
            values.put(DatabaseHelper.COLUMN_WICKET_RUNS, runs); // Runs scored during dismissal
            values.put(DatabaseHelper.COLUMN_WICKET_CONTRIBUTOR, (Long) null); // Contributor is null for now
            long result = db.insert(DatabaseHelper.TABLE_WICKETS, null, values);
            if (result == -1) {
                Log.e("updateWicketsTable", "Failed to insert wicket details for ball ID: " + ballId);
            } else {
                Log.d("updateWicketsTable", "Wicket details updated successfully for ball ID: " + ballId);
            }
        } catch (Exception e) {
            Log.e("updateWicketsTable", "Error while updating wickets table: " + e.getMessage());
        }
    }


    //---------------------------------------------team stats---------------------------------------------------
    public void updateTeamStatsFor0to6(long teamStatsId, int runs) {
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            String selectQuery = "SELECT " + COLUMN_RUNS + ", " + COLUMN_BALLS + " FROM " + TABLE_TEAM_STATISTICS + " WHERE " + COLUMN_TEAM_STATS_ID + " = ?";
            Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(teamStatsId)});
            int currentRuns = 0;
            int currentBalls = 0;
            if (cursor != null && cursor.moveToFirst()) {
                // Ensure the columns exist by checking the index is not -1
                int runsIndex = cursor.getColumnIndex(COLUMN_RUNS);
                int ballsIndex = cursor.getColumnIndex(COLUMN_BALLS);
                if (runsIndex != -1 && ballsIndex != -1) {
                    currentRuns = cursor.getInt(runsIndex);
                    currentBalls = cursor.getInt(ballsIndex);
                } else {
                    Log.e("DatabaseHelper", "Column not found.");
                    return;
                }
                cursor.close();
            }
            // Increment runs and balls
            currentRuns += runs;
            currentBalls += 1;
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_RUNS, currentRuns);
            contentValues.put(COLUMN_BALLS, currentBalls);
            int rowsUpdated = db.update(TABLE_TEAM_STATISTICS, contentValues, COLUMN_TEAM_STATS_ID + " = ?", new String[]{String.valueOf(teamStatsId)});
            if (rowsUpdated > 0) {
                Log.d("DatabaseHelper", "Team stats updated for ID: " + teamStatsId);
            } else {
                Log.e("DatabaseHelper", "Failed to update team stats for ID: " + teamStatsId);
            }
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error during team stats update for 0 to 6 runs.");
        }
}
    public void updateTeamStatsForByesAndLegByes(long teamStatsId, int runs) {
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            String selectQuery = "SELECT " + COLUMN_RUNS + ", " + COLUMN_BALLS + ", " + COLUMN_EXTRAS +
                    " FROM " + TABLE_TEAM_STATISTICS +
                    " WHERE " + COLUMN_TEAM_STATS_ID + " = ?";
            Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(teamStatsId)});
            int currentRuns = 0;
            int currentBalls = 0;
            int currentExtras = 0;
            if (cursor != null && cursor.moveToFirst()) {
                int runsIndex = cursor.getColumnIndex(COLUMN_RUNS);
                int ballsIndex = cursor.getColumnIndex(COLUMN_BALLS);
                int extrasIndex = cursor.getColumnIndex(COLUMN_EXTRAS);
                if (runsIndex != -1 && ballsIndex != -1 && extrasIndex != -1) {
                    currentRuns = cursor.getInt(runsIndex);
                    currentBalls = cursor.getInt(ballsIndex);
                    currentExtras = cursor.getInt(extrasIndex);
                } else {
                    Log.e("DatabaseHelper", "Column not found.");
                    return; // Exit the method if column indices are invalid
                }
                cursor.close();
            }
            currentRuns += runs;  // Add runs to total runs
            currentBalls += 1;    // Increment the ball count
            currentExtras += runs; // Add runs to extras
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_RUNS, currentRuns);
            contentValues.put(COLUMN_BALLS, currentBalls);
            contentValues.put(COLUMN_EXTRAS, currentExtras);
            int rowsUpdated = db.update(TABLE_TEAM_STATISTICS, contentValues,
                    COLUMN_TEAM_STATS_ID + " = ?",
                    new String[]{String.valueOf(teamStatsId)});
            if (rowsUpdated > 0) {
                Log.d("DatabaseHelper", "Team stats updated for ID: " + teamStatsId + " (Byes/Leg Byes)");
            } else {
                Log.e("DatabaseHelper", "Failed to update team stats for ID: " + teamStatsId);
            }
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error during team stats update for Byes/Leg Byes.");
        }
    }
    public void updateTeamStatsForWide(long teamStatsId, int runs) {
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            String selectQuery = "SELECT " + COLUMN_RUNS + ", " + COLUMN_EXTRAS +
                    " FROM " + TABLE_TEAM_STATISTICS +
                    " WHERE " + COLUMN_TEAM_STATS_ID + " = ?";
            Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(teamStatsId)});
            int currentRuns = 0;
            int currentExtras = 0;
            if (cursor != null && cursor.moveToFirst()) {
                int runsIndex = cursor.getColumnIndex(COLUMN_RUNS);
                int extrasIndex = cursor.getColumnIndex(COLUMN_EXTRAS);
                if (runsIndex != -1 && extrasIndex != -1) {
                    currentRuns = cursor.getInt(runsIndex);
                    currentExtras = cursor.getInt(extrasIndex);
                } else {
                    Log.e("DatabaseHelper", "Column not found.");
                    return; // Exit the method if column indices are invalid
                }
                cursor.close();
            }
            int totalWideRuns = runs + 1; // Add the mandatory extra run for the wide
            currentRuns += totalWideRuns; // Update total runs
            currentExtras += totalWideRuns; // Update extras
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_RUNS, currentRuns);
            contentValues.put(COLUMN_EXTRAS, currentExtras);
            int rowsUpdated = db.update(TABLE_TEAM_STATISTICS, contentValues,
                    COLUMN_TEAM_STATS_ID + " = ?",
                    new String[]{String.valueOf(teamStatsId)});
            if (rowsUpdated > 0) {
                Log.d("DatabaseHelper", "Team stats updated for ID: " + teamStatsId + " (Wides)");
            } else {
                Log.e("DatabaseHelper", "Failed to update team stats for ID: " + teamStatsId);
            }
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error during team stats update for Wides.");
        }
    }
    public void updateTeamStatsForNoBall(long teamStatsId, int runs, String runsSource) {
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            // Get the current values of runs and extras
            String selectQuery = "SELECT " + COLUMN_RUNS + ", " + COLUMN_EXTRAS +
                    " FROM " + TABLE_TEAM_STATISTICS +
                    " WHERE " + COLUMN_TEAM_STATS_ID + " = ?";
            Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(teamStatsId)});
            int currentRuns = 0;
            int currentExtras = 0;
            if (cursor != null && cursor.moveToFirst()) {
                int runsIndex = cursor.getColumnIndex(COLUMN_RUNS);
                int extrasIndex = cursor.getColumnIndex(COLUMN_EXTRAS);
                if (runsIndex != -1 && extrasIndex != -1) {
                    currentRuns = cursor.getInt(runsIndex);
                    currentExtras = cursor.getInt(extrasIndex);
                } else {
                    Log.e("DatabaseHelper", "Column not found.");
                    return; // Exit the method if column indices are invalid
                }
                cursor.close();
            }
            // Update runs
            int totalNoBallRuns = runs + 1; // Add 1 run for no-ball
            currentRuns += totalNoBallRuns; // Update total runs
            // Update extras
            if (FROM_BAT.equalsIgnoreCase(runsSource)) {
                // Add only the no-ball extra
                currentExtras += 1;
            } else if (LEG_BYE_BALL.equalsIgnoreCase(runsSource) || BYE_BALL.equalsIgnoreCase(runsSource)) {
                // Add the total runs (runs + 1 for no-ball)
                currentExtras += totalNoBallRuns;
            } else {
                Log.e("DatabaseHelper", "Invalid runs source.");
                return; // Exit if the source is invalid
            }
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_RUNS, currentRuns);
            contentValues.put(COLUMN_EXTRAS, currentExtras);
            int rowsUpdated = db.update(TABLE_TEAM_STATISTICS, contentValues,
                    COLUMN_TEAM_STATS_ID + " = ?",
                    new String[]{String.valueOf(teamStatsId)});
            if (rowsUpdated > 0) {
                Log.d("DatabaseHelper", "Team stats updated for ID: " + teamStatsId + " (No-Ball)");
            } else {
                Log.e("DatabaseHelper", "Failed to update team stats for ID: " + teamStatsId);
            }
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error during team stats update for No-Ball.");
        }
    }
    public void updateTeamStatsForBowCauLbw(long teamStatsId) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_TEAM_STATISTICS +
                " SET " +
                COLUMN_BALLS + " = " + COLUMN_BALLS + " + 1, " +
                COLUMN_WICKETS + " = " + COLUMN_WICKETS + " + 1 " +
                "WHERE " + COLUMN_TEAM_STATS_ID + " = ?";
        SQLiteStatement statement = db.compileStatement(query);
        statement.bindLong(1, teamStatsId);
        statement.executeUpdateDelete();
        statement.close();
    }
    public void updateTeamStatsForStumping(long teamStatsId, String ballType) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query;

        if ("Wide".equalsIgnoreCase(ballType)) {
            // Case: Stumping on a Wide Ball
            query = "UPDATE " + TABLE_TEAM_STATISTICS +
                    " SET " +
                    COLUMN_EXTRAS + " = " + COLUMN_EXTRAS + " + 1, " + // Add wide to extras
                    COLUMN_RUNS + " = " + COLUMN_RUNS + " + 1, " +      // Add wide to total score
                    COLUMN_WICKETS + " = " + COLUMN_WICKETS + " + 1 " + // Increment wicket count
                    "WHERE " + COLUMN_TEAM_STATS_ID + " = ?";
        } else if ("Normal".equalsIgnoreCase(ballType)) {
            // Case: Stumping on a Normal Ball
            query = "UPDATE " + TABLE_TEAM_STATISTICS +
                    " SET " +
                    COLUMN_BALLS + " = " + COLUMN_BALLS + " + 1, " + // Increment ball count
                    COLUMN_WICKETS + " = " + COLUMN_WICKETS + " + 1 " + // Increment wicket count
                    "WHERE " + COLUMN_TEAM_STATS_ID + " = ?";
        } else {
            // Invalid ball type, log error or throw exception
            Log.e("DatabaseHelper", "Invalid ball type: " + ballType);
            return;
        }

        SQLiteStatement statement = db.compileStatement(query);
        statement.bindLong(1, teamStatsId); // Bind the TeamStatsId
        statement.executeUpdateDelete(); // Execute the update query
        statement.close();
    }
    public void updateTeamStatsForRunOut(long teamStatsId, int runs, String ballType, String runsFrom) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query;
        SQLiteStatement statement;
        if (WIDE_BALL.equalsIgnoreCase(ballType)) {
            // Wide case: Add runs + 1 for wide to both total runs and extras, increment wicket
            query = "UPDATE " + TABLE_TEAM_STATISTICS +
                    " SET " +
                    COLUMN_EXTRAS + " = " + COLUMN_EXTRAS + " + ?, " +
                    COLUMN_RUNS + " = " + COLUMN_RUNS + " + ?, " +
                    COLUMN_WICKETS + " = " + COLUMN_WICKETS + " + 1 " +
                    "WHERE " + COLUMN_TEAM_STATS_ID + " = ?";
            statement = db.compileStatement(query);
            statement.bindLong(1, runs + 1); // Extras: runs + 1 for wide
            statement.bindLong(2, runs + 1); // Total runs: runs + 1 for wide
            statement.bindLong(3, teamStatsId);
            statement.executeUpdateDelete();
            statement.close();
        } else if (NO_BALL.equalsIgnoreCase(ballType)) {
            if (FROM_BAT.equalsIgnoreCase(runsFrom)) {
                // No-ball from bat: Add runs + 1 for no-ball to total runs, increment wicket
                query = "UPDATE " + TABLE_TEAM_STATISTICS +
                        " SET " +
                        COLUMN_RUNS + " = " + COLUMN_RUNS + " + ?, " +
                        COLUMN_WICKETS + " = " + COLUMN_WICKETS + " + 1 " +
                        "WHERE " + COLUMN_TEAM_STATS_ID + " = ?";
                statement = db.compileStatement(query);
                statement.bindLong(1, runs + 1); // Total runs: runs + 1 for no-ball
                statement.bindLong(2, teamStatsId);
                statement.executeUpdateDelete();
                statement.close();
            } else if (BYE_BALL.equalsIgnoreCase(runsFrom) || LEG_BYE_BALL.equalsIgnoreCase(runsFrom)) {
                // No-ball from byes/leg-byes: Add runs + 1 for no-ball to both extras and total runs, increment wicket
                query = "UPDATE " + TABLE_TEAM_STATISTICS +
                        " SET " +
                        COLUMN_EXTRAS + " = " + COLUMN_EXTRAS + " + ?, " +
                        COLUMN_RUNS + " = " + COLUMN_RUNS + " + ?, " +
                        COLUMN_WICKETS + " = " + COLUMN_WICKETS + " + 1 " +
                        "WHERE " + COLUMN_TEAM_STATS_ID + " = ?";
                statement = db.compileStatement(query);
                statement.bindLong(1, runs + 1); // Extras: runs + 1 for no-ball
                statement.bindLong(2, runs + 1); // Total runs: runs + 1 for no-ball
                statement.bindLong(3, teamStatsId);
                statement.executeUpdateDelete();
                statement.close();
            }
        } else if (NORMAL_BALL.equalsIgnoreCase(ballType)) {
            if (FROM_BAT.equalsIgnoreCase(runsFrom)) {
                // Normal from bat: Add runs to total runs, increment ball and wicket
                query = "UPDATE " + TABLE_TEAM_STATISTICS +
                        " SET " +
                        COLUMN_RUNS + " = " + COLUMN_RUNS + " + ?, " +
                        COLUMN_BALLS + " = " + COLUMN_BALLS + " + 1, " +
                        COLUMN_WICKETS + " = " + COLUMN_WICKETS + " + 1 " +
                        "WHERE " + COLUMN_TEAM_STATS_ID + " = ?";
                statement = db.compileStatement(query);
                statement.bindLong(1, runs);
                statement.bindLong(2, teamStatsId);
                statement.executeUpdateDelete();
                statement.close();
            } else if (BYE_BALL.equalsIgnoreCase(runsFrom) || LEG_BYE_BALL.equalsIgnoreCase(runsFrom)) {
                // Normal from byes/leg-byes: Add runs to both extras and total runs, increment ball and wicket
                query = "UPDATE " + TABLE_TEAM_STATISTICS +
                        " SET " +
                        COLUMN_EXTRAS + " = " + COLUMN_EXTRAS + " + ?, " +
                        COLUMN_RUNS + " = " + COLUMN_RUNS + " + ?, " +
                        COLUMN_BALLS + " = " + COLUMN_BALLS + " + 1, " +
                        COLUMN_WICKETS + " = " + COLUMN_WICKETS + " + 1 " +
                        "WHERE " + COLUMN_TEAM_STATS_ID + " = ?";
                statement = db.compileStatement(query);
                statement.bindLong(1, runs); // Extras: runs from byes/lb
                statement.bindLong(2, runs); // Total runs: runs from byes/lb
                statement.bindLong(3, teamStatsId);
                statement.executeUpdateDelete();
                statement.close();
            }
        } else {
            Log.e("DatabaseHelper", "Invalid ball type: " + ballType);
        }
    }

// ------------------------------------  innings and match end ------------------------
    public void updateInningsCompletionStatus(long inningsId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_IS_COMPLETED, 1);
        String whereClause = COLUMN_INNINGS_ID + " = ?";
        String[] whereArgs = { String.valueOf(inningsId) };
        db.update(TABLE_INNINGS, values, whereClause, whereArgs);
        db.close();
    }
    public void updateMatchCompletionStatus(long matchId) {
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            // Step 1: Get the two innings IDs for the given match
            String inningsQuery = "SELECT " + COLUMN_INNINGS_ID + " FROM " + TABLE_INNINGS + " WHERE " + COLUMN_MATCH_ID + " = ?";
            Cursor inningsCursor = db.rawQuery(inningsQuery, new String[]{String.valueOf(matchId)});
            long innings1Id = -1, innings2Id = -1;
            if (inningsCursor.moveToNext()) {
                innings1Id = inningsCursor.getLong(0);
            }
            if (inningsCursor.moveToNext()) {
                innings2Id = inningsCursor.getLong(0);
            }
            inningsCursor.close();
            if (innings1Id == -1 || innings2Id == -1) {
                throw new Exception("Incomplete innings data for match ID: " + matchId);
            }
            // Step 2: Get scores and wickets from the teamStats table
            String statsQuery = "SELECT " + COLUMN_RUNS + ", " + COLUMN_WICKETS + " FROM " + TABLE_TEAM_STATISTICS + " WHERE " + COLUMN_INNINGS_ID + " IN (?, ?)";
            Cursor statsCursor = db.rawQuery(statsQuery, new String[]{String.valueOf(innings1Id), String.valueOf(innings2Id)});

            int team1Score = 0, team1Wickets = 0, team2Score = 0, team2Wickets = 0;
            if (statsCursor.moveToNext()) {
                team1Score = statsCursor.getInt(0);
                team1Wickets = statsCursor.getInt(1);
            }
            if (statsCursor.moveToNext()) {
                team2Score = statsCursor.getInt(0);
                team2Wickets = statsCursor.getInt(1);
            }
            statsCursor.close();

            // Step 3: Determine the winner and match result
            long winningTeamId = -1;
            String matchResult;
            if (team1Score > team2Score) {
                winningTeamId = getTeamBattingId(db, innings1Id); // Implement a helper to get batting team for an innings
                matchResult = "Team " + winningTeamId + " won by " + (team1Score - team2Score) + " runs";
            } else if (team2Score > team1Score) {
                winningTeamId = getTeamBattingId(db, innings2Id);
                matchResult = "Team " + winningTeamId + " won by " + (10 - team2Wickets) + " wickets";
            } else {
                // Draw or tie
                matchResult = "Match tied";
            }
            // Step 4: Update the Matches table
            ContentValues values = new ContentValues();
            values.put(COLUMN_IS_MATCH_COMPLETED, 1);
            values.put(COLUMN_MATCH_WON_BY, winningTeamId);
            values.put(COLUMN_MATCH_RESULT, matchResult);
            String whereClause = COLUMN_MATCH_ID + " = ?";
            String[] whereArgs = {String.valueOf(matchId)};
            db.update(TABLE_MATCH, values, whereClause, whereArgs);

        } catch (Exception e) {
            Log.e(TAG, "updateMatchCompletionStatus: failed to update match end stats");
        }
    }
    private long getTeamBattingId(SQLiteDatabase db, long inningsId) {
        long battingTeamId = -1;
        String query = "SELECT " + COLUMN_TEAM_BATTING + " FROM " + TABLE_INNINGS + " WHERE " + COLUMN_INNINGS_ID + " = ?";
        try (Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(inningsId)})) {
            if (cursor.moveToFirst()) {
                battingTeamId = cursor.getLong(0);
            }
        } catch (Exception e) {
            Log.e(TAG, "getTeamBattingId: Failed to retrieve batting team ID for inningsId " + inningsId, e);
        }
        return battingTeamId;
    }


    // --------------------------------Info Fragment ----------------------------------------
    public Map<String, String> getMatchDetails(long matchId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Map<String, String> matchDetails = new HashMap<>();
        // SQL query with JOIN to get the place name from the places table
        String query = "SELECT m.matchType, m.numberOfOvers, m.ballType, p.placeName AS venue, m.dateTime " +
                "FROM Matches m " +
                "INNER JOIN Place p ON m.placeName = p.placeId " +
                "WHERE m.matchId = ?";
        Log.d("SQLQuery", "Query: " + query);  // Log the query for debugging
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(matchId)});
        if (cursor.moveToFirst()) {
            matchDetails.put("matchType", cursor.getString(cursor.getColumnIndexOrThrow("matchType")));
            matchDetails.put("overs", cursor.getString(cursor.getColumnIndexOrThrow("numberOfOvers")));
            matchDetails.put("ballType", cursor.getString(cursor.getColumnIndexOrThrow("ballType")));
            matchDetails.put("venue", cursor.getString(cursor.getColumnIndexOrThrow("venue"))); // place name from places table
            matchDetails.put("dateTime", cursor.getString(cursor.getColumnIndexOrThrow("dateTime")));
        }else {
            Log.d("SQLQuery", "No data found for matchId: " + matchId);  // Log if no data is found
        }

        cursor.close();
        db.close();
        Log.d("MatchDetails", "Fetched Match Details: " + matchDetails);  // Log the match details map

        return matchDetails;
    }
    public List<List<Player>> getPlayersForMatch(int matchId) {
        Log.d("Database", "getPlayersForMatch started for matchId: " + matchId);
        List<Player> team1Players = new ArrayList<>();
        List<Player> team2Players = new ArrayList<>();
        List<List<Player>> playersList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Step 1: Get the innings ids for the given match id
        String inningsQuery = "SELECT " + COLUMN_INNINGS_ID +
                " FROM " + TABLE_INNINGS +
                " WHERE " + COLUMN_MATCH_ID + " = ?";
        Log.d("Database", "Executing inningsQuery: " + inningsQuery);
        Cursor inningsCursor = db.rawQuery(inningsQuery, new String[]{String.valueOf(matchId)});

        if (inningsCursor != null && inningsCursor.moveToFirst()) {
            // Step 2: Fetch innings ids for both innings (first and second innings)
            int inningsId1 = inningsCursor.getInt(0);
            Log.d(TAG, "getPlayersForMatch: fetched innings id is " + inningsId1);

            int inningsId2 = -1; // Initialize second innings ID as -1 (indicating no second innings)
            if (inningsCursor.moveToNext()) {
                inningsId2 = inningsCursor.getInt(0);  // Fetch second innings ID if available
                Log.d(TAG, "getPlayersForMatch: fetched second innings id is " + inningsId2);
            }

            // Step 3: Get players for the first innings (always exists)
            String playersQuery = "SELECT " + COLUMN_PLAYER_ID + ", " + COLUMN_TEAM_ID +
                    " FROM " + TABLE_PLAYERS_TEAMS +
                    " WHERE " + COLUMN_INNINGS_ID + " = ?";
            Log.d("Database", "Executing playersQuery for first innings: " + playersQuery);
            Cursor playersCursor = db.rawQuery(playersQuery, new String[]{String.valueOf(inningsId1)});

            // Fetch players for the first innings
            if (playersCursor != null && playersCursor.moveToFirst()) {
                do {
                    int playerId = playersCursor.getInt(playersCursor.getColumnIndex(COLUMN_PLAYER_ID));
                    int teamId = playersCursor.getInt(playersCursor.getColumnIndex(COLUMN_TEAM_ID));
                    Log.d("Database", "Fetched playerId=" + playerId + ", teamId=" + teamId);

                    // Query to get player name
                    String playerQuery = "SELECT " + COLUMN_PLAYER_NAME +
                            " FROM " + TABLE_PLAYERS +
                            " WHERE " + COLUMN_PLAYER_ID + " = ?";
                    Log.d("Database", "Executing playerQuery for playerId: " + playerId);
                    Cursor playerCursor = db.rawQuery(playerQuery, new String[]{String.valueOf(playerId)});
                    if (playerCursor != null && playerCursor.moveToFirst()) {
                        String playerName = playerCursor.getString(playerCursor.getColumnIndex(COLUMN_PLAYER_NAME));
                        Log.d("Database", "Fetched playerName: " + playerName + " for playerId: " + playerId);

                        Player player = new Player();
                        player.setPlayerId(playerId);
                        player.setName(playerName);

                        if (teamId == 1) {
                            team1Players.add(player);
                        } else if (teamId == 2) {
                            team2Players.add(player);
                        }
                    }

                    if (playerCursor != null) {
                        playerCursor.close();
                    }
                } while (playersCursor.moveToNext());
            }

            // Step 4: Get players for the second innings, if it exists
            if (inningsId2 != -1) {
                Log.d("Database", "Fetching players for second innings");
                Cursor playersCursor2 = db.rawQuery(playersQuery, new String[]{String.valueOf(inningsId2)});

                // Fetch players for second innings if available
                if (playersCursor2 != null && playersCursor2.moveToFirst()) {
                    do {
                        int playerId = playersCursor2.getInt(playersCursor2.getColumnIndex(COLUMN_PLAYER_ID));
                        int teamId = playersCursor2.getInt(playersCursor2.getColumnIndex(COLUMN_TEAM_ID));
                        Log.d("Database", "Fetched playerId=" + playerId + ", teamId=" + teamId);

                        String playerQuery = "SELECT " + COLUMN_PLAYER_NAME +
                                " FROM " + TABLE_PLAYERS +
                                " WHERE " + COLUMN_PLAYER_ID + " = ?";
                        Log.d("Database", "Executing playerQuery for playerId: " + playerId);
                        Cursor playerCursor = db.rawQuery(playerQuery, new String[]{String.valueOf(playerId)});
                        if (playerCursor != null && playerCursor.moveToFirst()) {
                            String playerName = playerCursor.getString(playerCursor.getColumnIndex(COLUMN_PLAYER_NAME));
                            Log.d("Database", "Fetched playerName: " + playerName + " for playerId: " + playerId);

                            Player player = new Player();
                            player.setPlayerId(playerId);
                            player.setName(playerName);

                            if (teamId == 1) {
                                team1Players.add(player);
                            } else if (teamId == 2) {
                                team2Players.add(player);
                            }
                        }

                        if (playerCursor != null) {
                            playerCursor.close();
                        }
                    } while (playersCursor2.moveToNext());
                }

                if (playersCursor2 != null) {
                    playersCursor2.close();
                }
            }

            if (playersCursor != null) {
                playersCursor.close();
            }

            // Step 5: Return players for both teams (empty list for second team if second innings is not available)
            playersList.add(team1Players);
            playersList.add(team2Players);
            Log.d("Database", "Returning combined playersList with size: " + playersList.size());
        } else {
            Log.e("Database", "No innings found for matchId: " + matchId);
        }

        if (inningsCursor != null) {
            inningsCursor.close();
        }

        return playersList;
    }

    //-----------------------------live fragment ----------------
    public HashMap<String, String> getTeamStats(long inningsId) {
        Log.d(TAG, "getTeamStats: inside get team stats");
        HashMap<String, String> teamStats = new HashMap<>();
        SQLiteDatabase db = this.getWritableDatabase(); // Ensure you get the database instance appropriately in your implementation
        Cursor cursor = null;
        try {
            String query = "SELECT " + COLUMN_RUNS + ", " + COLUMN_WICKETS + ", " + COLUMN_BALLS +
                    " FROM " + TABLE_TEAM_STATISTICS +
                    " WHERE " + COLUMN_INNINGS_ID + " = ?";

            // Open the database and execute the query
            cursor = db.rawQuery(query, new String[]{String.valueOf(inningsId)});
            if (cursor != null && cursor.moveToFirst()) {
                teamStats.put("runs", String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_RUNS))));
                teamStats.put("wickets", String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_WICKETS))));
                teamStats.put("balls", String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_BALLS))));
            }
            Log.d(TAG, "getTeamStats: maar saale" + teamStats);
        } catch (Exception e) {
            Log.e(TAG, "getTeamStats: error getting team stats");
        } finally {
            if (cursor != null) cursor.close();
        }
        return teamStats;
    }
    public String getTeamNameFromId(long teamId) {
        Log.d(TAG, "getTeamNameFromId: inside getetam from id");
        SQLiteDatabase db = this.getReadableDatabase(); // Use getReadableDatabase since we are only reading data.
        String teamName = null; // Variable to store the result
        String query = "SELECT " + COLUMN_TEAM_NAME + " FROM " + TABLE_TEAMS + " WHERE " + COLUMN_TEAM_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(teamId)});
        if (cursor.moveToFirst()) {
            teamName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TEAM_NAME));
        }
        cursor.close(); // Always close the cursor to avoid memory leaks
        Log.d(TAG, "getTeamNameFromId: team name is" + teamName);
        return teamName; // Return the fetched team name
    }
    public HashMap<String, String> getCurrentBattersStats(long inningsId, long playerId) {
        Log.d(TAG, "getCurrentBattersStats: inside get current batter");
        SQLiteDatabase db = this.getReadableDatabase();
        HashMap<String, String> batterStats = new HashMap<>();
        Cursor cursor = null;
        try {
            String query = "SELECT " +
                    COLUMN_SCORE + ", " +
                    COLUMN_BALLS_PLAYED + ", " +
                    COLUMN_FOURS + ", " +
                    COLUMN_SIXES +
                    " FROM " + TABLE_BATSMAN +
                    " WHERE " + COLUMN_PLAYER + " = ? AND " + COLUMN_INNINGS_ID + " = ?";
            cursor = db.rawQuery(query, new String[]{
                    String.valueOf(playerId),
                    String.valueOf(inningsId)
            });
            if (cursor != null && cursor.moveToFirst()) {
                int runs = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SCORE));
                int balls = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_BALLS_PLAYED));
                int fours = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_FOURS));
                int sixes = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SIXES));
                int overs = balls / 6;
                int partialBalls = balls % 6;
                String oversFormatted = overs + "." + partialBalls;
                batterStats.put("runs", String.valueOf(runs));
                batterStats.put("balls", String.valueOf(balls));
                batterStats.put("overs", oversFormatted);
                batterStats.put("fours", String.valueOf(fours));
                batterStats.put("sixes", String.valueOf(sixes));
                String playerName = getPlayerNameById(playerId);
                if (playerName != null) {
                    batterStats.put("name", playerName);
                }
            }
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error fetching batter stats", e);
        } finally {
            if (cursor != null) {
                cursor.close();  // Closing cursor to avoid memory leaks
            }
        }
        return batterStats;
    }
    public String getPlayerNameById(long playerId) {
        Log.d(TAG, "getPlayerNameById: inside get player from id");
        SQLiteDatabase db = this.getReadableDatabase();
        String playerName = null;
        String query = "SELECT " + COLUMN_PLAYER_NAME +
                " FROM " + TABLE_PLAYERS +
                " WHERE " + COLUMN_PLAYER_ID + " = ?";
        Cursor nameCursor = db.rawQuery(query, new String[]{String.valueOf(playerId)});
        if (nameCursor.moveToFirst()) {
            playerName = nameCursor.getString(nameCursor.getColumnIndexOrThrow(COLUMN_PLAYER_NAME));
        }
        nameCursor.close(); // Close the cursor to avoid memory leaks
        return playerName;
    }
    public HashMap<String, String> getPartnershipStats(long partnershipId) {
        Log.d(TAG, "getCurrentBowlerStats: inside getCurrentBowlerStats and arguments are " + partnershipId);
        HashMap<String, String> partnershipStats = new HashMap<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            String query = "SELECT " + COLUMN_RUNS + ", " + COLUMN_BALLS +
                    " FROM " + TABLE_PARTNERSHIPS +
                    " WHERE " + COLUMN_PARTNERSHIP_ID + " = ?";
            cursor = db.rawQuery(query, new String[]{String.valueOf(partnershipId)});
            if (cursor != null && cursor.moveToFirst()) {
                partnershipStats.put("runs", String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_RUNS))));
                partnershipStats.put("balls", String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_BALLS))));
            }
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error fetching partnership stats", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return partnershipStats;
    }
    public HashMap<String, String> getCurrentBowlerStats(long inningsId, long bowlerId) {
        Log.d(TAG, "getCurrentBowlerStats: inside getCurrentBowlerStats and arguments are " + inningsId + bowlerId);
        HashMap<String, String> bowlerStats = new HashMap<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            // Get the bowler's name using the previously defined method
            String bowlerName = getPlayerNameById(bowlerId);
            if (bowlerName != null) {
                bowlerStats.put("name", bowlerName);
            }
            String query = "SELECT " +
                    COLUMN_BALLS_PLAYED + ", " +
                    COLUMN_RUNS + ", " +
                    COLUMN_MAIDENS + ", " +
                    COLUMN_WK +
                    " FROM " + TABLE_BOWLER +
                    " WHERE " + COLUMN_INNINGS_ID + " = ? AND " + COLUMN_PLAYER + " = ?";
            cursor = db.rawQuery(query, new String[]{String.valueOf(inningsId), String.valueOf(bowlerId)});
            if (cursor != null && cursor.moveToFirst()) {
                int balls = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_BALLS_PLAYED));
                int runs = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_RUNS));
                int maidens = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MAIDENS));
                int wickets = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_WK));
                int overs = balls / 6;
                int partialBalls = balls % 6;
                String oversFormatted = overs + "." + partialBalls;
                bowlerStats.put("overs", oversFormatted);
                bowlerStats.put("runs", String.valueOf(runs));
                bowlerStats.put("maidens", String.valueOf(maidens));
                bowlerStats.put("wickets", String.valueOf(wickets));
            }
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error fetching bowler stats", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if(db.isOpen()) db.close();
        }
        return bowlerStats;
    }
    public List<BallDetails> getBallDetailsForInnings(long inningsId) {
        List<BallDetails> ballDetailsList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Query to get all overs for the given inningsId
        String overQuery = "SELECT " + COLUMN_OVER_ID + ", " + COLUMN_PLAYER_ID +
                " FROM " + TABLE_OVERS +
                " WHERE " + COLUMN_INNINGS_ID + " = ?";
        Cursor overCursor = db.rawQuery(overQuery, new String[]{String.valueOf(inningsId)});

        if (overCursor != null && overCursor.moveToFirst()) {
            do {
                // Fetch overId and bowlerId for each over
                int overIdIndex = overCursor.getColumnIndex(COLUMN_OVER_ID);
                int bowlerIdIndex = overCursor.getColumnIndex(COLUMN_PLAYER_ID);

                // Check if the column indices are valid
                if (overIdIndex != -1 && bowlerIdIndex != -1) {
                    long overId = overCursor.getLong(overIdIndex);
                    long bowlerId = overCursor.getLong(bowlerIdIndex);

                    // Query to get balls for the current over (fetch only required columns)
                    String ballQuery = "SELECT " + COLUMN_BALL_ID + ", " + COLUMN_BALL_NUMBER + ", " +
                            COLUMN_TYPE_OF_BALL + ", " + COLUMN_RUNS + ", " + COLUMN_IS_WICKET + ", " +
                            COLUMN_STRIKER + ", " + COLUMN_NON_STRIKER +
                            " FROM " + TABLE_BALLS +
                            " WHERE " + COLUMN_OVER_ID + " = ?";
                    Cursor ballCursor = db.rawQuery(ballQuery, new String[]{String.valueOf(overId)});

                    if (ballCursor != null && ballCursor.moveToFirst()) {
                        do {
                            // Fetch ball details
                            int ballIdIndex = ballCursor.getColumnIndex(COLUMN_BALL_ID);
                            int ballNumberIndex = ballCursor.getColumnIndex(COLUMN_BALL_NUMBER);
                            int ballTypeIndex = ballCursor.getColumnIndex(COLUMN_TYPE_OF_BALL);
                            int runsIndex = ballCursor.getColumnIndex(COLUMN_RUNS);
                            int isWicketIndex = ballCursor.getColumnIndex(COLUMN_IS_WICKET);
                            int strikerIndex = ballCursor.getColumnIndex(COLUMN_STRIKER);
                            int nonStrikerIndex = ballCursor.getColumnIndex(COLUMN_NON_STRIKER);

                            // Check if the indices are valid
                            if (ballIdIndex != -1 && ballTypeIndex != -1 && runsIndex != -1 &&
                                    isWicketIndex != -1 && strikerIndex != -1 && nonStrikerIndex != -1) {
                                long ballId = ballCursor.getLong(ballIdIndex);
                                long ballNumber = ballCursor.getLong(ballNumberIndex);
                                String ballType = ballCursor.getString(ballTypeIndex);
                                int runs = ballCursor.getInt(runsIndex);
                                int isWicket = ballCursor.getInt(isWicketIndex);
                                long strikerId = ballCursor.getLong(strikerIndex);
                                long nonStrikerId = ballCursor.getLong(nonStrikerIndex);

                                // Get player names for striker, non-striker, and bowler
                                String strikerName = getPlayerNameById(strikerId);
                                String nonStrikerName = getPlayerNameById(nonStrikerId);
                                String bowlerName = getPlayerNameById(bowlerId);

                                // Create BallDetails object with ballId and other info
                                BallDetails ballDetails = new BallDetails((int) ballId, (int) ballNumber, ballType,
                                        runs, isWicket == 1, strikerName,
                                        nonStrikerName, bowlerName);
                                ballDetailsList.add(ballDetails);
                            }
                        } while (ballCursor.moveToNext());
                        ballCursor.close();
                    }
                } else {
                    Log.e(TAG, "Invalid column indices in overs table");
                }
            } while (overCursor.moveToNext());
            overCursor.close();
        } else {
            Log.e(TAG, "No overs found for inningsId: " + inningsId);
        }

        db.close();
        Log.d(TAG, "getBallDetailsForInnings: " + ballDetailsList.size());
        return ballDetailsList;
    }

    //------------------------------ score card fragment--------------------------------------
    public List<Batsman> getAllBatterStats(long inningsId) {
        List<Batsman> batterList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Query to get all batter stats for the given inningsId
        String query = "SELECT * FROM " + TABLE_BATSMAN + " WHERE " + COLUMN_INNINGS_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(inningsId)});

        // Check if there are results
        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Create a new Batter object from the query result
                int playerId = cursor.getInt(cursor.getColumnIndex(COLUMN_PLAYER));
                int score = cursor.getInt(cursor.getColumnIndex(COLUMN_SCORE));
                int ballsPlayed = cursor.getInt(cursor.getColumnIndex(COLUMN_BALLS_PLAYED));
                int zeroes = cursor.getInt(cursor.getColumnIndex(COLUMN_ZEROES));
                int ones = cursor.getInt(cursor.getColumnIndex(COLUMN_ONES));
                int twos = cursor.getInt(cursor.getColumnIndex(COLUMN_TWOS));
                int threes = cursor.getInt(cursor.getColumnIndex(COLUMN_THREES));
                int fours = cursor.getInt(cursor.getColumnIndex(COLUMN_FOURS));
                int fives = cursor.getInt(cursor.getColumnIndex(COLUMN_FIVES));
                int sixes = cursor.getInt(cursor.getColumnIndex(COLUMN_SIXES));
                String playerName = getPlayerNameById(playerId);
                // Add the Batter object to the list
                Batsman batsman = new Batsman(playerId, playerName, (int) inningsId, score, ballsPlayed, zeroes, ones, twos,
                        threes, fours, fives, sixes);
                batterList.add(batsman);
            } while (cursor.moveToNext());
        }
        cursor.close();
        //db.close();
        return batterList;
    }
    public List<Bowler> getAllBowlerStats(long inningsId) {
        List<Bowler> bowlerList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Query to get all bowler stats for the given inningsId
        String query = "SELECT " + COLUMN_PLAYER + ", " + COLUMN_MAIDENS + ", "
                + COLUMN_BALLS_PLAYED + ", " + COLUMN_RUNS + ", " + COLUMN_WK
                + " FROM " + TABLE_BOWLER + " WHERE " + COLUMN_INNINGS_ID + " = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(inningsId)});

        // Check if there are results
        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Extract data for each bowler
                int playerId = cursor.getInt(cursor.getColumnIndex(COLUMN_PLAYER));
                String playerName = getPlayerNameById(playerId); // Helper method for player name
                int maidens = cursor.getInt(cursor.getColumnIndex(COLUMN_MAIDENS));
                int ballsPlayed = cursor.getInt(cursor.getColumnIndex(COLUMN_BALLS_PLAYED));
                int runs = cursor.getInt(cursor.getColumnIndex(COLUMN_RUNS));
                int wickets = cursor.getInt(cursor.getColumnIndex(COLUMN_WK));

                Bowler bowler = new Bowler(playerId, playerName, (int) inningsId, ballsPlayed, maidens, runs, wickets);
                bowlerList.add(bowler);
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }
        //db.close();
        return bowlerList;
    }


    //----------------------------sharing json data--------------------------------------
    public JSONObject getMatchDataById(int matchId) {
        SQLiteDatabase db = this.getReadableDatabase();
        JSONObject matchData = new JSONObject();
        Cursor cursor = null;
        try {
            String query = "SELECT * FROM " + TABLE_MATCH + " WHERE " + COLUMN_MATCH_ID + " = ?";
            cursor = db.rawQuery(query, new String[]{String.valueOf(matchId)});
            if (cursor != null && cursor.moveToFirst()) {
                int matchIdIndex = cursor.getColumnIndex(COLUMN_MATCH_ID);
                int matchTypeIndex = cursor.getColumnIndex(COLUMN_MATCH_TYPE);
                int noOfOversIndex = cursor.getColumnIndex(COLUMN_NO_OF_OVERS);
                int ballTypeIndex = cursor.getColumnIndex(COLUMN_BALL_TYPE);
                int placeNameIndex = cursor.getColumnIndex(COLUMN_PLACE_NAME);
                int dateTimeIndex = cursor.getColumnIndex(COLUMN_DATE_TIME);
                int tossIndex = cursor.getColumnIndex(COLUMN_TOSS);
                int isMatchCompletedIndex = cursor.getColumnIndex(COLUMN_IS_MATCH_COMPLETED);
                int matchWonByIndex = cursor.getColumnIndex(COLUMN_MATCH_WON_BY);
                int matchResultIndex = cursor.getColumnIndex(COLUMN_MATCH_RESULT);
                // Ensure each column index is valid (not -1)
                if (matchIdIndex >= 0) matchData.put(COLUMN_MATCH_ID, cursor.getInt(matchIdIndex));
                if (matchTypeIndex >= 0) matchData.put(COLUMN_MATCH_TYPE, cursor.getString(matchTypeIndex));
                if (noOfOversIndex >= 0) matchData.put(COLUMN_NO_OF_OVERS, cursor.getInt(noOfOversIndex));
                if (ballTypeIndex >= 0) matchData.put(COLUMN_BALL_TYPE, cursor.getString(ballTypeIndex));
                if (placeNameIndex >= 0) matchData.put(COLUMN_PLACE_NAME, cursor.getInt(placeNameIndex));
                if (dateTimeIndex >= 0) matchData.put(COLUMN_DATE_TIME, cursor.getString(dateTimeIndex));
                if (tossIndex >= 0) matchData.put(COLUMN_TOSS, cursor.getInt(tossIndex));
                if (isMatchCompletedIndex >= 0) matchData.put(COLUMN_IS_MATCH_COMPLETED, cursor.getInt(isMatchCompletedIndex));
                if (matchWonByIndex >= 0) matchData.put(COLUMN_MATCH_WON_BY, cursor.getInt(matchWonByIndex));
                if (matchResultIndex >= 0) matchData.put(COLUMN_MATCH_RESULT, cursor.getString(matchResultIndex));
            }
        } catch (Exception e) {
            Log.e(TAG, "getMatchDataById: error getting match data");
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return matchData;
    }
    public JSONArray getAllTeamsData() {
        SQLiteDatabase db = this.getReadableDatabase();
        JSONArray teamsDataArray = new JSONArray();
        Cursor cursor = null;
        try {
            String query = "SELECT * FROM " + TABLE_TEAMS;
            cursor = db.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    JSONObject teamData = new JSONObject();
                    int teamIdIndex = cursor.getColumnIndex(COLUMN_TEAM_ID);
                    int teamNameIndex = cursor.getColumnIndex(COLUMN_TEAM_NAME);
                    if (teamIdIndex >= 0) teamData.put(COLUMN_TEAM_ID, cursor.getInt(teamIdIndex));
                    if (teamNameIndex >= 0) teamData.put(COLUMN_TEAM_NAME, cursor.getString(teamNameIndex));
                    teamsDataArray.put(teamData);
                } while (cursor.moveToNext()); // Move to the next row
            }
        } catch (Exception e) {
            Log.e(TAG, "getAllTeamsData: error getting teams data");
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return teamsDataArray; // Return the JSON array containing all teams data
    }
    public JSONArray getAllPlaces() {
        SQLiteDatabase db = this.getReadableDatabase();
        JSONArray placesDataArray = new JSONArray();
        Cursor cursor = null;
        try {
            // Query to select all data from the PLACES table
            String query = "SELECT * FROM " + TABLE_PLACES;
            cursor = db.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    JSONObject placeData = new JSONObject();
                    int placeIdIndex = cursor.getColumnIndex(COLUMN_PLACE_ID);
                    int placeNameIndex = cursor.getColumnIndex(COLUMN_PLACE_NAME);
                    // Check if the columns exist and populate the JSON object
                    if (placeIdIndex >= 0) placeData.put(COLUMN_PLACE_ID, cursor.getInt(placeIdIndex));
                    if (placeNameIndex >= 0) placeData.put(COLUMN_PLACE_NAME, cursor.getString(placeNameIndex));
                    placesDataArray.put(placeData);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(TAG, "getAllPlaces: error getting all places");
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return placesDataArray;
    }
    public JSONObject getTossDataById(int tossId) {
        SQLiteDatabase db = this.getReadableDatabase();
        JSONObject tossData = new JSONObject();
        Cursor cursor = null;
        try {
            String query = "SELECT * FROM " + TABLE_TOSS + " WHERE " + COLUMN_TOSS_ID + " = ?";
            cursor = db.rawQuery(query, new String[]{String.valueOf(tossId)});
            if (cursor != null && cursor.moveToFirst()) {
                int tossIdIndex = cursor.getColumnIndex(COLUMN_TOSS_ID);
                int tossCallByIndex = cursor.getColumnIndex(COLUMN_TOSS_CALL_BY);
                int tossWonByIndex = cursor.getColumnIndex(COLUMN_TOSS_WON_BY);
                int tossWonTeamChooseToIndex = cursor.getColumnIndex(COLUMN_TOSS_WON_TEAM_CHOOSE_TO);
                // Check if the columns exist and populate the JSON object
                if (tossIdIndex >= 0) tossData.put(COLUMN_TOSS_ID, cursor.getInt(tossIdIndex));
                if (tossCallByIndex >= 0) tossData.put(COLUMN_TOSS_CALL_BY, cursor.getInt(tossCallByIndex));
                if (tossWonByIndex >= 0) tossData.put(COLUMN_TOSS_WON_BY, cursor.getInt(tossWonByIndex));
                if (tossWonTeamChooseToIndex >= 0) tossData.put(COLUMN_TOSS_WON_TEAM_CHOOSE_TO, cursor.getString(tossWonTeamChooseToIndex));
            }
        } catch (Exception e) {
            Log.e(TAG, "getTossDataById: error in getting toss");
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return tossData;
    }
    public JSONArray getAllPlayers() {
        SQLiteDatabase db = this.getReadableDatabase();
        JSONArray playersData = new JSONArray();
        Cursor cursor = null;
        try {
            // Query to select all players
            String query = "SELECT * FROM " + TABLE_PLAYERS;
            cursor = db.rawQuery(query, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    JSONObject player = new JSONObject();
                    int playerIdIndex = cursor.getColumnIndex(COLUMN_PLAYER_ID);
                    int playerNameIndex = cursor.getColumnIndex(COLUMN_PLAYER_NAME);
                    // Check if the columns exist and populate the JSON object
                    if (playerIdIndex >= 0) player.put(COLUMN_PLAYER_ID, cursor.getInt(playerIdIndex));
                    if (playerNameIndex >= 0) player.put(COLUMN_PLAYER_NAME, cursor.getString(playerNameIndex));
                    playersData.put(player);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            System.err.println("Error getting corresponding data: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return playersData;
    }
    public JSONArray getPlayersTeamsByInningsId(int inningsId) {
        SQLiteDatabase db = this.getReadableDatabase();
        JSONArray playersTeamsData = new JSONArray();
        Cursor cursor = null;
        try {
            // Query to select data based on Innings ID
            String query = "SELECT * FROM " + TABLE_PLAYERS_TEAMS + " WHERE " + COLUMN_INNINGS_ID + " = ?";
            cursor = db.rawQuery(query, new String[]{String.valueOf(inningsId)});
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    JSONObject playerTeam = new JSONObject();
                    int teamIdIndex = cursor.getColumnIndex(COLUMN_TEAM_ID);
                    int playerIdIndex = cursor.getColumnIndex(COLUMN_PLAYER_ID);
                    int inningsIdIndex = cursor.getColumnIndex(COLUMN_INNINGS_ID);
                    // Check if the columns exist and populate the JSON object
                    if (teamIdIndex >= 0) playerTeam.put(COLUMN_TEAM_ID, cursor.getInt(teamIdIndex));
                    if (playerIdIndex >= 0) playerTeam.put(COLUMN_PLAYER_ID, cursor.getInt(playerIdIndex));
                    if (inningsIdIndex >= 0) playerTeam.put(COLUMN_INNINGS_ID, cursor.getInt(inningsIdIndex));
                    playersTeamsData.put(playerTeam);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            System.err.println("Error getting corresponding data: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return playersTeamsData;
    }
    public JSONArray getPartnershipsByInningsId(int inningsId) {
        SQLiteDatabase db = this.getReadableDatabase();
        JSONArray partnershipsData = new JSONArray();
        Cursor cursor = null;
        try {
            String query = "SELECT * FROM " + TABLE_PARTNERSHIPS + " WHERE " + COLUMN_INNINGS_ID + " = ?";
            cursor = db.rawQuery(query, new String[]{String.valueOf(inningsId)});
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    JSONObject partnership = new JSONObject();
                    int partnershipIdIndex = cursor.getColumnIndex(COLUMN_PARTNERSHIP_ID);
                    int inningsIdIndex = cursor.getColumnIndex(COLUMN_INNINGS_ID);
                    int batsman1IdIndex = cursor.getColumnIndex(COLUMN_BATSMAN1_ID);
                    int batsman2IdIndex = cursor.getColumnIndex(COLUMN_BATSMAN2_ID);
                    int runsIndex = cursor.getColumnIndex(COLUMN_RUNS);
                    int ballsIndex = cursor.getColumnIndex(COLUMN_BALLS);
                    // Check if the columns exist and populate the JSON object
                    if (partnershipIdIndex >= 0) partnership.put(COLUMN_PARTNERSHIP_ID, cursor.getInt(partnershipIdIndex));
                    if (inningsIdIndex >= 0) partnership.put(COLUMN_INNINGS_ID, cursor.getInt(inningsIdIndex));
                    if (batsman1IdIndex >= 0) partnership.put(COLUMN_BATSMAN1_ID, cursor.getInt(batsman1IdIndex));
                    if (batsman2IdIndex >= 0) partnership.put(COLUMN_BATSMAN2_ID, cursor.getInt(batsman2IdIndex));
                    if (runsIndex >= 0) partnership.put(COLUMN_RUNS, cursor.getInt(runsIndex));
                    if (ballsIndex >= 0) partnership.put(COLUMN_BALLS, cursor.getInt(ballsIndex));
                    partnershipsData.put(partnership);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            System.err.println("Error getting corresponding data: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return partnershipsData;
    }
    public JSONArray getInningsByMatchId(int matchId) {
        SQLiteDatabase db = this.getReadableDatabase();
        JSONArray inningsData = new JSONArray();
        Cursor cursor = null;
        try {
            // Query to select data based on Match ID
            String query = "SELECT * FROM " + TABLE_INNINGS + " WHERE " + COLUMN_MATCH_ID + " = ?";
            cursor = db.rawQuery(query, new String[]{String.valueOf(matchId)});
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    JSONObject innings = new JSONObject();
                    int inningsIdIndex = cursor.getColumnIndex(COLUMN_INNINGS_ID);
                    int inningsNumberIndex = cursor.getColumnIndex(COLUMN_INNINGS_NUMBER);
                    int matchIdIndex = cursor.getColumnIndex(COLUMN_MATCH_ID);
                    int teamBattingIndex = cursor.getColumnIndex(COLUMN_TEAM_BATTING);
                    int isCompletedIndex = cursor.getColumnIndex(COLUMN_IS_COMPLETED);
                    // Check if the columns exist and populate the JSON object
                    if (inningsIdIndex >= 0) innings.put(COLUMN_INNINGS_ID, cursor.getInt(inningsIdIndex));
                    if (inningsNumberIndex >= 0) innings.put(COLUMN_INNINGS_NUMBER, cursor.getInt(inningsNumberIndex));
                    if (matchIdIndex >= 0) innings.put(COLUMN_MATCH_ID, cursor.getInt(matchIdIndex));
                    if (teamBattingIndex >= 0) innings.put(COLUMN_TEAM_BATTING, cursor.getInt(teamBattingIndex));
                    if (isCompletedIndex >= 0) innings.put(COLUMN_IS_COMPLETED, cursor.getInt(isCompletedIndex));
                    inningsData.put(innings);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            System.err.println("Error getting corresponding data: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return inningsData;
    }
    public JSONArray getOversByInningsId(int inningsId) {
        SQLiteDatabase db = this.getReadableDatabase();
        JSONArray oversData = new JSONArray();
        Cursor cursor = null;

        try {
            // Query to select data based on Innings ID
            String query = "SELECT * FROM " + TABLE_OVERS + " WHERE " + COLUMN_INNINGS_ID + " = ?";
            cursor = db.rawQuery(query, new String[]{String.valueOf(inningsId)});

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    JSONObject over = new JSONObject();

                    // Get the column indexes
                    int overIdIndex = cursor.getColumnIndex(COLUMN_OVER_ID);
                    int inningsIdIndex = cursor.getColumnIndex(COLUMN_INNINGS_ID);
                    int overNumberIndex = cursor.getColumnIndex(COLUMN_OVER_NUMBER);
                    int playerIdIndex = cursor.getColumnIndex(COLUMN_PLAYER_ID);
                    int isMaidenIndex = cursor.getColumnIndex(COLUMN_IS_MAIDEN);

                    // Check if the columns exist and populate the JSON object
                    if (overIdIndex >= 0) over.put(COLUMN_OVER_ID, cursor.getInt(overIdIndex));
                    if (inningsIdIndex >= 0) over.put(COLUMN_INNINGS_ID, cursor.getInt(inningsIdIndex));
                    if (overNumberIndex >= 0) over.put(COLUMN_OVER_NUMBER, cursor.getInt(overNumberIndex));
                    if (playerIdIndex >= 0) over.put(COLUMN_PLAYER_ID, cursor.getInt(playerIdIndex));
                    if (isMaidenIndex >= 0) over.put(COLUMN_IS_MAIDEN, cursor.getInt(isMaidenIndex));

                    // Add the over data to the JSON array
                    oversData.put(over);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            System.err.println("Error getting corresponding data: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return oversData;
    }
    public JSONArray getBallsByMatchId(int matchId) {
        SQLiteDatabase db = this.getReadableDatabase();
        JSONArray ballsData = new JSONArray();
        Cursor cursor = null;

        try {
            // First, get all INNINGS_IDs for the given MATCH_ID
            String inningsQuery = "SELECT " + COLUMN_INNINGS_ID + " FROM " + TABLE_INNINGS +
                    " WHERE " + COLUMN_MATCH_ID + " = ?";
            cursor = db.rawQuery(inningsQuery, new String[]{String.valueOf(matchId)});

            // Collect all INNINGS_IDs
            List<Integer> inningsIds = new ArrayList<>();
            while (cursor != null && cursor.moveToNext()) {
                int inningsIdIndex = cursor.getColumnIndex(COLUMN_INNINGS_ID);
                int inningsId = 0;
                if(inningsIdIndex >= 0) inningsId = cursor.getInt(inningsIdIndex);
                inningsIds.add(inningsId);
            }

            // Close the cursor for the INNINGS query
            if (cursor != null) cursor.close();

            // Now, get the OVER_IDs for both innings
            List<Integer> overIds = new ArrayList<>();
            if (!inningsIds.isEmpty()) {
                String oversQuery = "SELECT " + COLUMN_OVER_ID + " FROM " + TABLE_OVERS +
                        " WHERE " + COLUMN_INNINGS_ID + " IN (" + TextUtils.join(",", inningsIds) + ")";
                cursor = db.rawQuery(oversQuery, null);
                while (cursor != null && cursor.moveToNext()) {
                    int overIdIndex = cursor.getColumnIndex(COLUMN_OVER_ID);
                    int overId = 0;
                    if(overIdIndex >= 0) overId = cursor.getInt(overIdIndex);
                    //int overId = cursor.getInt(cursor.getColumnIndex(COLUMN_OVER_ID));
                    overIds.add(overId);
                }
            }

            // Close the cursor for the OVERS query
            if (cursor != null) cursor.close();

            // Now, get the BALLS data for those OVER_IDs
            if (!overIds.isEmpty()) {
                String ballsQuery = "SELECT * FROM " + TABLE_BALLS +
                        " WHERE " + COLUMN_OVER_ID + " IN (" + TextUtils.join(",", overIds) + ")";
                cursor = db.rawQuery(ballsQuery, null);

                if (cursor != null && cursor.moveToFirst()) {
                    do {
                        JSONObject ball = new JSONObject();

                        // Get the column indexes
                        int ballIdIndex = cursor.getColumnIndex(COLUMN_BALL_ID);
                        int overIdIndex = cursor.getColumnIndex(COLUMN_OVER_ID);
                        int ballNumberIndex = cursor.getColumnIndex(COLUMN_BALL_NUMBER);
                        int typeOfBallIndex = cursor.getColumnIndex(COLUMN_TYPE_OF_BALL);
                        int runsIndex = cursor.getColumnIndex(COLUMN_RUNS);
                        int isWicketIndex = cursor.getColumnIndex(COLUMN_IS_WICKET);
                        int strikerIndex = cursor.getColumnIndex(COLUMN_STRIKER);
                        int nonStrikerIndex = cursor.getColumnIndex(COLUMN_NON_STRIKER);
                        int isSyncedIndex = cursor.getColumnIndex(COLUMN_IS_SYNCED);

                        // Check if the columns exist and populate the JSON object
                        if (ballIdIndex >= 0) ball.put(COLUMN_BALL_ID, cursor.getInt(ballIdIndex));
                        if (overIdIndex >= 0) ball.put(COLUMN_OVER_ID, cursor.getInt(overIdIndex));
                        if (ballNumberIndex >= 0) ball.put(COLUMN_BALL_NUMBER, cursor.getInt(ballNumberIndex));
                        if (typeOfBallIndex >= 0) ball.put(COLUMN_TYPE_OF_BALL, cursor.getString(typeOfBallIndex));
                        if (runsIndex >= 0) ball.put(COLUMN_RUNS, cursor.getInt(runsIndex));
                        if (isWicketIndex >= 0) ball.put(COLUMN_IS_WICKET, cursor.getInt(isWicketIndex));
                        if (strikerIndex >= 0) ball.put(COLUMN_STRIKER, cursor.getInt(strikerIndex));
                        if (nonStrikerIndex >= 0) ball.put(COLUMN_NON_STRIKER, cursor.getInt(nonStrikerIndex));
                        if (isSyncedIndex >= 0) ball.put(COLUMN_IS_SYNCED, cursor.getInt(isSyncedIndex));

                        // Add the ball data to the JSON array
                        ballsData.put(ball);

                    } while (cursor.moveToNext());
                }
            }
        } catch (Exception e) {
            System.err.println("Error getting corresponding data: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return ballsData;
    }
    public JSONArray getBatsmanDataByInningsId(int inningsId) {
        SQLiteDatabase db = this.getReadableDatabase();
        JSONArray batsmanData = new JSONArray();
        try {
            // Define the query to retrieve batsman data for a particular inningsId
            String query = "SELECT * FROM " + TABLE_BATSMAN +
                    " WHERE " + COLUMN_INNINGS_ID + " = ?";

            // Execute the query
            Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(inningsId)});

            // Check if cursor is valid
            if (cursor != null && cursor.getCount() > 0) {
                // Get column indexes
                int playerIndex = cursor.getColumnIndex(COLUMN_PLAYER);
                int inningsIdIndex = cursor.getColumnIndex(COLUMN_INNINGS_ID);
                int scoreIndex = cursor.getColumnIndex(COLUMN_SCORE);
                int ballsPlayedIndex = cursor.getColumnIndex(COLUMN_BALLS_PLAYED);
                int zerosIndex = cursor.getColumnIndex(COLUMN_ZEROES);
                int onesIndex = cursor.getColumnIndex(COLUMN_ONES);
                int twosIndex = cursor.getColumnIndex(COLUMN_TWOS);
                int threesIndex = cursor.getColumnIndex(COLUMN_THREES);
                int foursIndex = cursor.getColumnIndex(COLUMN_FOURS);
                int fivesIndex = cursor.getColumnIndex(COLUMN_FIVES);
                int sixesIndex = cursor.getColumnIndex(COLUMN_SIXES);

                // Check if all indexes are valid (>= 0)
                if (playerIndex >= 0 && scoreIndex >= 0 && ballsPlayedIndex >= 0 &&
                        zerosIndex >= 0 && onesIndex >= 0 && twosIndex >= 0 &&
                        threesIndex >= 0 && foursIndex >= 0 && fivesIndex >= 0 &&
                        sixesIndex >= 0) {

                    // Move to the first row of results
                    while (cursor.moveToNext()) {
                        // Extract each column value
                        int playerId = cursor.getInt(playerIndex);
                        int inningsIdValue = cursor.getInt(inningsIdIndex);
                        int score = cursor.getInt(scoreIndex);
                        int ballsPlayed = cursor.getInt(ballsPlayedIndex);
                        int zeros = cursor.getInt(zerosIndex);
                        int ones = cursor.getInt(onesIndex);
                        int twos = cursor.getInt(twosIndex);
                        int threes = cursor.getInt(threesIndex);
                        int fours = cursor.getInt(foursIndex);
                        int fives = cursor.getInt(fivesIndex);
                        int sixes = cursor.getInt(sixesIndex);

                        // Create a JSONObject for the current row
                        JSONObject batsman = new JSONObject();
                        batsman.put("playerId", playerId);
                        batsman.put("inningsId", inningsIdValue);
                        batsman.put("score", score);
                        batsman.put("ballsPlayed", ballsPlayed);
                        batsman.put("zeros", zeros);
                        batsman.put("ones", ones);
                        batsman.put("twos", twos);
                        batsman.put("threes", threes);
                        batsman.put("fours", fours);
                        batsman.put("fives", fives);
                        batsman.put("sixes", sixes);

                        // Add the batsman data to the JSONArray
                        batsmanData.put(batsman);
                    }
                } else {
                    Log.e("Database Error", "Invalid column indexes.");
                }

                // Close the cursor after processing
                cursor.close();
            }
        } catch (Exception e) {
            // Log error
            Log.e("Error getting batsman data", "Error: " + e.getMessage());
        }

        return batsmanData;
    }
    public JSONArray getBowlerDataByInningsId(int inningsId) {
        SQLiteDatabase db = this.getReadableDatabase();
        JSONArray bowlerData = new JSONArray();
        try {
            // Define the query to retrieve bowler data for a particular inningsId
            String query = "SELECT * FROM " + TABLE_BOWLER +
                    " WHERE " + COLUMN_INNINGS_ID + " = ?";

            // Execute the query
            Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(inningsId)});

            // Check if cursor is valid and has data
            if (cursor != null && cursor.getCount() > 0) {
                // Get column indexes
                int playerIndex = cursor.getColumnIndex(COLUMN_PLAYER);
                int inningsIndex = cursor.getColumnIndex(COLUMN_INNINGS_ID);
                int maidensIndex = cursor.getColumnIndex(COLUMN_MAIDENS);
                int ballsPlayedIndex = cursor.getColumnIndex(COLUMN_BALLS_PLAYED);
                int runsIndex = cursor.getColumnIndex(COLUMN_RUNS);
                int economyIndex = cursor.getColumnIndex(COLUMN_ECONOMY);
                int zerosIndex = cursor.getColumnIndex(COLUMN_ZEROES);
                int onesIndex = cursor.getColumnIndex(COLUMN_ONES);
                int twosIndex = cursor.getColumnIndex(COLUMN_TWOS);
                int threesIndex = cursor.getColumnIndex(COLUMN_THREES);
                int foursIndex = cursor.getColumnIndex(COLUMN_FOURS);
                int fivesIndex = cursor.getColumnIndex(COLUMN_FIVES);
                int sixesIndex = cursor.getColumnIndex(COLUMN_SIXES);
                int wkIndex = cursor.getColumnIndex(COLUMN_WK);
                int byIndex = cursor.getColumnIndex(COLUMN_BY);
                int lbIndex = cursor.getColumnIndex(COLUMN_LB);
                int wbIndex = cursor.getColumnIndex(COLUMN_WB);
                int nbIndex = cursor.getColumnIndex(COLUMN_NB);
                int dbIndex = cursor.getColumnIndex(COLUMN_DB);

                // Check if all indexes are valid
                if (playerIndex >= 0 && maidensIndex >= 0 && ballsPlayedIndex >= 0 &&
                        runsIndex >= 0 && economyIndex >= 0 && zerosIndex >= 0 && onesIndex >= 0 &&
                        twosIndex >= 0 && threesIndex >= 0 && foursIndex >= 0 && fivesIndex >= 0 &&
                        sixesIndex >= 0 && wkIndex >= 0 && byIndex >= 0 && lbIndex >= 0 && wbIndex >= 0 &&
                        nbIndex >= 0 && dbIndex >= 0) {

                    // Move to the first row of results
                    while (cursor.moveToNext()) {
                        // Extract each column value
                        int playerId = cursor.getInt(playerIndex);
                        int inningsIdValue = cursor.getInt(inningsIndex);
                        int maidens = cursor.getInt(maidensIndex);
                        int ballsPlayed = cursor.getInt(ballsPlayedIndex);
                        int runs = cursor.getInt(runsIndex);
                        float economy = cursor.getFloat(economyIndex);
                        int zeros = cursor.getInt(zerosIndex);
                        int ones = cursor.getInt(onesIndex);
                        int twos = cursor.getInt(twosIndex);
                        int threes = cursor.getInt(threesIndex);
                        int fours = cursor.getInt(foursIndex);
                        int fives = cursor.getInt(fivesIndex);
                        int sixes = cursor.getInt(sixesIndex);
                        int wk = cursor.getInt(wkIndex);
                        int by = cursor.getInt(byIndex);
                        int lb = cursor.getInt(lbIndex);
                        int wb = cursor.getInt(wbIndex);
                        int nb = cursor.getInt(nbIndex);
                        int dBall = cursor.getInt(dbIndex);

                        // Create a JSONObject for the current row
                        JSONObject bowler = new JSONObject();
                        bowler.put("playerId", playerId);
                        bowler.put("inningsId", inningsIdValue);
                        bowler.put("maidens", maidens);
                        bowler.put("ballsPlayed", ballsPlayed);
                        bowler.put("runs", runs);
                        bowler.put("economy", economy);
                        bowler.put("zeros", zeros);
                        bowler.put("ones", ones);
                        bowler.put("twos", twos);
                        bowler.put("threes", threes);
                        bowler.put("fours", fours);
                        bowler.put("fives", fives);
                        bowler.put("sixes", sixes);
                        bowler.put("wk", wk);
                        bowler.put("by", by);
                        bowler.put("lb", lb);
                        bowler.put("wb", wb);
                        bowler.put("nb", nb);
                        bowler.put("db", dBall);

                        // Add the bowler data to the JSONArray
                        bowlerData.put(bowler);
                    }
                } else {
                    Log.e("Database Error", "Invalid column indexes.");
                }

                // Close the cursor after processing
                cursor.close();
            }
        } catch (Exception e) {
            // Log error if anything goes wrong
            Log.e("Error getting bowler data", "Error: " + e.getMessage());
        }

        return bowlerData;
    }
    public JSONObject getTeamStatisticsByInningsId(int inningsId) {
        SQLiteDatabase db = this.getReadableDatabase();
        JSONObject teamStats = new JSONObject();
        try {
            // Define the query to retrieve team statistics based on inningsId
            String query = "SELECT * FROM " + TABLE_TEAM_STATISTICS +
                    " WHERE " + COLUMN_INNINGS_ID + " = ?";

            // Execute the query
            Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(inningsId)});

            // Check if cursor is valid and has data
            if (cursor != null && cursor.moveToFirst()) {
                // Get column indexes
                int teamStatsIndex = cursor.getColumnIndex(COLUMN_TEAM_STATS_ID);
                int inningsIdIndex = cursor.getColumnIndex(COLUMN_INNINGS_ID);
                int runsIndex = cursor.getColumnIndex(COLUMN_RUNS);
                int wicketsIndex = cursor.getColumnIndex(COLUMN_WICKETS);
                int ballsIndex = cursor.getColumnIndex(COLUMN_BALLS);
                int extrasIndex = cursor.getColumnIndex(COLUMN_EXTRAS);

                // Check if the indexes are valid
                if (runsIndex >= 0 && wicketsIndex >= 0 && ballsIndex >= 0 && extrasIndex >= 0) {
                    // Extract data for the team statistics
                    int teamStatsId = cursor.getInt(teamStatsIndex);
                    int inningsIdValue = cursor.getInt(inningsIdIndex);
                    int runs = cursor.getInt(runsIndex);
                    int wickets = cursor.getInt(wicketsIndex);
                    int balls = cursor.getInt(ballsIndex);
                    int extras = cursor.getInt(extrasIndex);

                    // Add the extracted data into the JSONObject
                    teamStats.put("teamStatsId", teamStatsId);
                    teamStats.put("inningsId", inningsIdValue);
                    teamStats.put("runs", runs);
                    teamStats.put("wickets", wickets);
                    teamStats.put("balls", balls);
                    teamStats.put("extras", extras);
                } else {
                    Log.e("Database Error", "Invalid column indexes.");
                }

                // Close the cursor after processing
                cursor.close();
            }
        } catch (Exception e) {
            // Log any exceptions that occur
            Log.e("Error getting team stats", "Error: " + e.getMessage());
        }

        return teamStats;
    }
    public Map<String, Object> shareMatchData(int matchId, int inningsId) {
        // Initialize a structure to store all the data
        Map<String, Object> matchData = new HashMap<>();

        // Fetch Match Data
        matchData.put("match", getMatchDataById(matchId));

        // Fetch Team Data
        matchData.put("teams", getAllTeamsData());

        // Fetch Places Data
        matchData.put("places", getAllPlaces());

        // Fetch Toss Data
        matchData.put("toss", getTossDataById(matchId));

        // Fetch Players Data
        matchData.put("players", getAllPlayers());

        // Fetch Players-Teams Data
        matchData.put("playersTeams", getPlayersTeamsByInningsId(inningsId));

        // Fetch Partnerships Data
        matchData.put("partnerships", getPartnershipsByInningsId(inningsId));

        // Fetch Innings Data
        matchData.put("innings", getInningsByMatchId(matchId));

        // Fetch Overs Data
        matchData.put("overs", getOversByInningsId(inningsId));

        // Fetch Balls Data
        matchData.put("balls", getBallsByMatchId(matchId));

        // Fetch Batsman Data
        matchData.put("batsman", getBatsmanDataByInningsId(inningsId));

        // Fetch Bowler Data
        matchData.put("bowler", getBowlerDataByInningsId(inningsId));

        // Fetch Team Statistics
        matchData.put("teamStatistics", getTeamStatisticsByInningsId(inningsId));

        // Return all the collected data as an aggregated object
        return matchData;
    }















    public boolean restoreDatabaseData(JSONObject databaseJson) {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean isSuccessful = false; // Track transaction success
        db.beginTransaction(); // Start transaction
        try {
            restoreBowlerData(db, databaseJson.getJSONObject("bowler"));
            restoreBallsData(db, databaseJson.getJSONObject("balls"));
            restoreTeamsData(db, databaseJson.getJSONObject("teams"));
            restorePlayersData(db, databaseJson.getJSONObject("players"));
            restoreMatchData(db, databaseJson.getJSONObject("match"));
            restoreOversData(db, databaseJson.getJSONObject("overs"));
            restoreTossData(db, databaseJson.getJSONObject("toss"));
            restorePlayersTeamsData(db, databaseJson.getJSONObject("playersTeams"));
            restoreTeamStatisticsData(db, databaseJson.getJSONObject("teamStatistics"));
            restorePlacesData(db, databaseJson.getJSONObject("places"));
            restorePartnershipsData(db, databaseJson.getJSONObject("partnerships"));
            restoreInningsData(db, databaseJson.getJSONObject("innings"));
            restoreBatsmanData(db, databaseJson.getJSONObject("batsman"));
            db.setTransactionSuccessful(); // Commit transaction
            isSuccessful = true; // Indicate success
        } catch (Exception e) {
            Log.e(TAG, "restoreDatabaseData: error populating data to tables", e);
        } finally {
            db.endTransaction(); // End transaction
        }
        return isSuccessful;
    }
    public void restoreBowlerData(SQLiteDatabase db, JSONObject bowlerJson) throws JSONException {
        Log.d(TAG, "restoreBowlerData: bowler map is" + bowlerJson);
        JSONArray values = bowlerJson.getJSONArray("values");
        Log.d(TAG, "restoreBowlerData: bowler data is" + values);
        for (int i = 0; i < values.length(); i++) {
            JSONObject bowler = values.getJSONObject(i).getJSONObject("nameValuePairs");
            ContentValues contentValues = new ContentValues();

            // Add values dynamically only if they exist in the JSON
            if (bowler.has("playerId")) contentValues.put("player", bowler.getInt("playerId"));
            if (bowler.has("inningsId")) contentValues.put("inningsId", bowler.getInt("inningsId"));
            if (bowler.has("maidens")) contentValues.put("maidens", bowler.getInt("maidens"));
            if (bowler.has("ballsPlayed")) contentValues.put("balls", bowler.getInt("ballsPlayed"));
            if (bowler.has("runs")) contentValues.put("runs", bowler.getInt("runs"));
            if (bowler.has("economy")) contentValues.put("economy", bowler.getDouble("economy"));
            if (bowler.has("zeros")) contentValues.put("zeroes", bowler.getInt("zeros"));
            if (bowler.has("ones")) contentValues.put("ones", bowler.getInt("ones"));
            if (bowler.has("twos")) contentValues.put("twos", bowler.getInt("twos"));
            if (bowler.has("threes")) contentValues.put("threes", bowler.getInt("threes"));
            if (bowler.has("fours")) contentValues.put("fours", bowler.getInt("fours"));
            if (bowler.has("fives")) contentValues.put("fives", bowler.getInt("fives"));
            if (bowler.has("sixes")) contentValues.put("sixes", bowler.getInt("sixes"));
            if (bowler.has("wk")) contentValues.put("wickets", bowler.getInt("wk"));
            if (bowler.has("by")) contentValues.put("byes", bowler.getInt("by"));
            if (bowler.has("lb")) contentValues.put("legByes", bowler.getInt("lb"));
            if (bowler.has("wb")) contentValues.put("wides", bowler.getInt("wb"));
            if (bowler.has("nb")) contentValues.put("noBalls", bowler.getInt("nb"));
            if (bowler.has("db")) contentValues.put("deadBalls", bowler.getInt("db"));

            // Use INSERT OR REPLACE to handle duplicates
            db.insertWithOnConflict(TABLE_BOWLER, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
        }
    }
    public void restoreBallsData(SQLiteDatabase db, JSONObject ballsJson) throws JSONException {
        Log.d(TAG, "restoreBallsData: balls map is" + ballsJson);
        JSONArray values = ballsJson.getJSONArray("values");
        Log.d(TAG, "restoreBallsData: balls data is" + values);

        for (int i = 0; i < values.length(); i++) {
            JSONObject ball = values.getJSONObject(i).getJSONObject("nameValuePairs");
            ContentValues contentValues = new ContentValues();

            // Add values dynamically only if they exist in the JSON
            if (ball.has("ballId")) contentValues.put("ballId", ball.getInt("ballId"));
            if (ball.has("overId")) contentValues.put("overId", ball.getInt("overId"));
            if (ball.has("ballNumber")) contentValues.put("ballNumber", ball.getInt("ballNumber"));
            if (ball.has("ballType")) contentValues.put("ballType", ball.getString("ballType"));
            if (ball.has("runs")) contentValues.put("runs", ball.getInt("runs"));
            if (ball.has("isWicket")) contentValues.put("isWicket", ball.getInt("isWicket"));
            if (ball.has("striker")) contentValues.put("striker", ball.getInt("striker"));
            if (ball.has("nonStriker")) contentValues.put("nonStriker", ball.getInt("nonStriker"));

            // Use INSERT OR REPLACE to handle duplicates
            db.insertWithOnConflict(TABLE_BALLS, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
        }
    }
    public void restoreTeamsData(SQLiteDatabase db, JSONObject teamsJson) throws JSONException {
        JSONArray values = teamsJson.getJSONArray("values");
        for (int i = 0; i < values.length(); i++) {
            JSONObject team = values.getJSONObject(i).getJSONObject("nameValuePairs");
            ContentValues contentValues = new ContentValues();
            // Add values dynamically only if they exist in the JSON
            if (team.has("teamId")) contentValues.put("teamId", team.getInt("teamId"));
            if (team.has("teamName")) contentValues.put("teamName", team.getString("teamName"));
            // Use INSERT OR REPLACE to handle duplicates
            db.insertWithOnConflict(TABLE_TEAMS, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
        }
    }
    public void restorePlayersData(SQLiteDatabase db, JSONObject playersJson) throws JSONException {
        JSONArray values = playersJson.getJSONArray("values");
        for (int i = 0; i < values.length(); i++) {
            JSONObject player = values.getJSONObject(i).getJSONObject("nameValuePairs");
            ContentValues contentValues = new ContentValues();
            // Add values dynamically only if they exist in the JSON
            if (player.has("playerId")) contentValues.put("playerId", player.getInt("playerId"));
            if (player.has("playerName")) contentValues.put("playerName", player.getString("playerName"));
            // Use INSERT OR REPLACE to handle duplicates
            db.insertWithOnConflict(TABLE_PLAYERS, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
        }
    }
    public void restoreMatchData(SQLiteDatabase db, JSONObject matchJson) throws JSONException {
        // Extract the nameValuePairs object from the JSON
        JSONObject match = matchJson.getJSONObject("nameValuePairs");

        // Prepare ContentValues to insert into the database
        ContentValues contentValues = new ContentValues();

        // Add values dynamically only if they exist in the JSON and match the columns you need
        if (match.has("matchId")) contentValues.put("matchId", match.getInt("matchId"));
        if (match.has("matchType")) contentValues.put("matchType", match.getString("matchType"));
        if (match.has("numberOfOvers")) contentValues.put("numberOfOvers", match.getInt("numberOfOvers"));
        if (match.has("ballType")) contentValues.put("ballType", match.getString("ballType"));
        if (match.has("placeName")) contentValues.put("placeName", match.getInt("placeName"));
        if (match.has("dateTime")) contentValues.put("dateTime", match.getString("dateTime"));
        if (match.has("toss")) contentValues.put("toss", match.getInt("toss"));
        if (match.has("isMatchCompleted")) contentValues.put("isMatchCompleted", match.getInt("isMatchCompleted"));
        if (match.has("matchWonBy")) contentValues.put("matchWonBy", match.getInt("matchWonBy"));

        // Use INSERT OR REPLACE to handle duplicates in the database
        db.insertWithOnConflict(TABLE_MATCH, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
    }
    public void restoreOversData(SQLiteDatabase db, JSONObject oversJson) throws JSONException {
        // Extract the values array from the JSON
        JSONArray values = oversJson.getJSONArray("values");

        // Iterate through each value in the values array
        for (int i = 0; i < values.length(); i++) {
            // Extract nameValuePairs from each item
            JSONObject over = values.getJSONObject(i).getJSONObject("nameValuePairs");

            // Prepare ContentValues to insert into the database
            ContentValues contentValues = new ContentValues();

            // Add values dynamically only if they exist in the JSON and match the columns you need
            if (over.has("overId")) contentValues.put("overId", over.getInt("overId"));
            if (over.has("inningsId")) contentValues.put("inningsId", over.getInt("inningsId"));
            if (over.has("overNumber")) contentValues.put("overNumber", over.getInt("overNumber"));
            if (over.has("playerId")) contentValues.put("playerId", over.getInt("playerId"));
            if (over.has("isMaiden")) contentValues.put("isMaiden", over.getInt("isMaiden"));

            // Use INSERT OR REPLACE to handle duplicates in the database
            db.insertWithOnConflict(TABLE_OVERS, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
        }
    }
    public void restoreTossData(SQLiteDatabase db, JSONObject tossJson) throws JSONException {
        JSONObject toss = tossJson.getJSONObject("nameValuePairs");
        ContentValues contentValues = new ContentValues();
        // Add values dynamically only if they exist in the JSON
        if (toss.has("tossId")) contentValues.put("tossId", toss.getInt("tossId"));
        if (toss.has("tossCallingTeam")) contentValues.put("tossCallingTeam", toss.getInt("tossCallingTeam"));
        if (toss.has("tossWinningTeam")) contentValues.put("tossWinningTeam", toss.getInt("tossWinningTeam"));
        if (toss.has("tossWonTeamChooseTo")) contentValues.put("tossWonTeamChooseTo", toss.getString("tossWonTeamChooseTo"));
        // Use INSERT OR REPLACE to handle duplicates
        db.insertWithOnConflict(TABLE_TOSS, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
    }
    public void restorePlayersTeamsData(SQLiteDatabase db, JSONObject playersTeamsJson) throws JSONException {
        JSONArray values = playersTeamsJson.getJSONArray("values");
        for (int i = 0; i < values.length(); i++) {
            JSONObject playerTeam = values.getJSONObject(i).getJSONObject("nameValuePairs");
            ContentValues contentValues = new ContentValues();
            // Add values dynamically only if they exist in the JSON
            if (playerTeam.has("teamId")) contentValues.put("teamId", playerTeam.getInt("teamId"));
            if (playerTeam.has("playerId")) contentValues.put("playerId", playerTeam.getInt("playerId"));
            if (playerTeam.has("inningsId")) contentValues.put("inningsId", playerTeam.getInt("inningsId"));
            // Use INSERT OR REPLACE to handle duplicates
            db.insertWithOnConflict(TABLE_PLAYERS_TEAMS, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
        }
    }
    public void restoreTeamStatisticsData(SQLiteDatabase db, JSONObject teamStatisticsJson) throws JSONException {
        JSONObject teamStatistics = teamStatisticsJson.getJSONObject("nameValuePairs");
        ContentValues contentValues = new ContentValues();

        // Add values dynamically only if they exist in the JSON
        if (teamStatistics.has("teamStatsId")) contentValues.put("teamStatsId", teamStatistics.getInt("teamStatsId"));
        if (teamStatistics.has("inningsId")) contentValues.put("inningsId", teamStatistics.getInt("inningsId"));
        if (teamStatistics.has("runs")) contentValues.put("runs", teamStatistics.getInt("runs"));
        if (teamStatistics.has("wickets")) contentValues.put("wickets", teamStatistics.getInt("wickets"));
        if (teamStatistics.has("balls")) contentValues.put("balls", teamStatistics.getInt("balls"));
        if (teamStatistics.has("extras")) contentValues.put("extras", teamStatistics.getInt("extras"));

        // Use INSERT OR REPLACE to handle duplicates
        db.insertWithOnConflict(TABLE_TEAM_STATISTICS, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
    }
    public void restorePlacesData(SQLiteDatabase db, JSONObject placesJson) throws JSONException {
        JSONArray values = placesJson.getJSONArray("values");
        for (int i = 0; i < values.length(); i++) {
            JSONObject place = values.getJSONObject(i).getJSONObject("nameValuePairs");
            ContentValues contentValues = new ContentValues();

            // Add values dynamically only if they exist in the JSON
            if (place.has("placeId")) contentValues.put("placeId", place.getInt("placeId"));
            if (place.has("placeName")) contentValues.put("placeName", place.getString("placeName"));

            // Use INSERT OR REPLACE to handle duplicates
            db.insertWithOnConflict(TABLE_PLACES, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
        }
    }
    public void restorePartnershipsData(SQLiteDatabase db, JSONObject partnershipsJson) throws JSONException {
        JSONArray values = partnershipsJson.getJSONArray("values");
        for (int i = 0; i < values.length(); i++) {
            JSONObject partnership = values.getJSONObject(i).getJSONObject("nameValuePairs");
            ContentValues contentValues = new ContentValues();

            // Add values dynamically only if they exist in the JSON
            if (partnership.has("partnershipId")) contentValues.put("partnershipId", partnership.getInt("partnershipId"));
            if (partnership.has("inningsId")) contentValues.put("inningsId", partnership.getInt("inningsId"));
            if (partnership.has("batsman1Id")) contentValues.put("batsman1Id", partnership.getInt("batsman1Id"));
            if (partnership.has("batsman2Id")) contentValues.put("batsman2Id", partnership.getInt("batsman2Id"));
            if (partnership.has("runs")) contentValues.put("runs", partnership.getInt("runs"));
            if (partnership.has("balls")) contentValues.put("balls", partnership.getInt("balls"));

            // Use INSERT OR REPLACE to handle duplicates
            db.insertWithOnConflict(TABLE_PARTNERSHIPS, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
        }
    }
    public void restoreInningsData(SQLiteDatabase db, JSONObject inningsJson) throws JSONException {
        JSONArray values = inningsJson.getJSONArray("values");
        for (int i = 0; i < values.length(); i++) {
            JSONObject innings = values.getJSONObject(i).getJSONObject("nameValuePairs");
            ContentValues contentValues = new ContentValues();

            // Add values dynamically only if they exist in the JSON
            if (innings.has("inningsId")) contentValues.put("inningsId", innings.getInt("inningsId"));
            if (innings.has("inningsNumber")) contentValues.put("inningsNumber", innings.getInt("inningsNumber"));
            if (innings.has("matchId")) contentValues.put("matchId", innings.getInt("matchId"));
            if (innings.has("battingTeam")) contentValues.put("battingTeam", innings.getInt("battingTeam"));
            if (innings.has("isCompleted")) contentValues.put("isCompleted", innings.getInt("isCompleted"));

            // Use INSERT OR REPLACE to handle duplicates
            db.insertWithOnConflict(TABLE_INNINGS, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
        }
    }
    public void restoreBatsmanData(SQLiteDatabase db, JSONObject batsmanJson) throws JSONException {
        JSONArray values = batsmanJson.getJSONArray("values");
        for (int i = 0; i < values.length(); i++) {
            JSONObject batsman = values.getJSONObject(i).getJSONObject("nameValuePairs");
            ContentValues contentValues = new ContentValues();

            // Add values dynamically only if they exist in the JSON
            if (batsman.has("playerId")) contentValues.put("player", batsman.getInt("playerId"));
            if (batsman.has("inningsId")) contentValues.put("inningsId", batsman.getInt("inningsId"));
            if (batsman.has("score")) contentValues.put("score", batsman.getInt("score"));
            if (batsman.has("ballsPlayed")) contentValues.put("balls", batsman.getInt("ballsPlayed"));
            if (batsman.has("zeros")) contentValues.put("zeroes", batsman.getInt("zeros"));
            if (batsman.has("ones")) contentValues.put("ones", batsman.getInt("ones"));
            if (batsman.has("twos")) contentValues.put("twos", batsman.getInt("twos"));
            if (batsman.has("threes")) contentValues.put("threes", batsman.getInt("threes"));
            if (batsman.has("fours")) contentValues.put("fours", batsman.getInt("fours"));
            if (batsman.has("fives")) contentValues.put("fives", batsman.getInt("fives"));
            if (batsman.has("sixes")) contentValues.put("sixes", batsman.getInt("sixes"));

            // Use INSERT OR REPLACE to handle duplicates
            db.insertWithOnConflict(TABLE_BATSMAN, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
        }
    }

}





