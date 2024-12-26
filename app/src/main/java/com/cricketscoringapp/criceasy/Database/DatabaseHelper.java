package com.cricketscoringapp.criceasy.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;
import android.util.Pair;

import java.util.HashMap;
import java.util.Map;
import static android.content.ContentValues.TAG;


public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 15; // Update version
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
    public static final String TABLE_MATCHES = "Matches";
    public static final String COLUMN_MATCH_ID = "match_id";
    public static final String COLUMN_MATCH_TYPE = "match_type";
    public static final String COLUMN_OVERS = "overs";
    public static final String COLUMN_BALL_TYPE = "ball_type";
    public static final String COLUMN_LOCATION = "location";
    public static final String COLUMN_DATE_TIME = "date_time";
    public static final String COLUMN_TOSS = "toss";
    public static final String COLUMN_IS_MATCH_COMPLETED = "is_match_completed";
    public static final String COLUMN_MATCH_WON_BY = "match_won_by";
    public static final String COLUMN_MATCH_RESULT = "match_result";
    public static final String COLUMN_PLACE_ID = "place_id";
    public static final String COLUMN_TOSS_ID = "toss_id";
    public static final String COLUMN_TEAM_ID = "team_id";

    // Create table SQL query
    public static final String CREATE_MATCHES_TABLE = "CREATE TABLE " + TABLE_MATCHES + " ("
            + COLUMN_MATCH_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_MATCH_TYPE + " TEXT, "
            + COLUMN_OVERS + " INTEGER, "
            + COLUMN_BALL_TYPE + " TEXT, "
            + COLUMN_LOCATION + " INTEGER, "
            + COLUMN_DATE_TIME + " DATETIME, "
            + COLUMN_TOSS + " INTEGER, "
            + COLUMN_IS_MATCH_COMPLETED + " INTEGER, "
            + COLUMN_MATCH_WON_BY + " INTEGER, "
            + COLUMN_MATCH_RESULT + " TEXT, "
            + "FOREIGN KEY(" + COLUMN_LOCATION + ") REFERENCES Places(" + COLUMN_PLACE_ID + "), "
            + "FOREIGN KEY(" + COLUMN_TOSS + ") REFERENCES Toss(" + COLUMN_TOSS_ID + "), "
            + "FOREIGN KEY(" + COLUMN_MATCH_WON_BY + ") REFERENCES Teams(" + COLUMN_TEAM_ID + ")"
            + ");";


    //Team Table
    public static final String TABLE_TEAMS = "Teams";
    public static final String COLUMN_TEAM_NAME = "team_name";
    public static final String CREATE_TEAMS_TABLE = "CREATE TABLE " + TABLE_TEAMS + " ("
            + COLUMN_TEAM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_TEAM_NAME + " TEXT NOT NULL "
            + ");";


    // Matches-Teams Table
    public static final String TABLE_MATCHES_TEAMS = "Matches_Teams";
    public static final String COLUMN_MATCHES_TEAMS_ID = "Match_Team_id";
    public static final String COLUMN_TEAM1_ID = "team1_id";
    public static final String COLUMN_TEAM2_ID = "team2_id";


    public static final String CREATE_MATCHES_TEAMS_TABLE =
            "CREATE TABLE " + TABLE_MATCHES_TEAMS + " (" +
                    COLUMN_MATCHES_TEAMS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_MATCH_ID + " INTEGER, " +
                    COLUMN_TEAM1_ID + " INTEGER, " +
                    COLUMN_TEAM2_ID + " INTEGER, " +
                    "FOREIGN KEY(" + COLUMN_MATCH_ID + ") REFERENCES Matches(" + COLUMN_MATCH_ID + "), " +
                    "FOREIGN KEY(" + COLUMN_TEAM1_ID + ") REFERENCES Teams(" + COLUMN_TEAM_ID + "), " +
                    "FOREIGN KEY(" + COLUMN_TEAM2_ID + ") REFERENCES Teams(" + COLUMN_TEAM_ID + ")" +
                    ");";


    // Places Table
    // Places table and column names
    public static final String TABLE_PLACES = "Places";
    public static final String COLUMN_PLACE_NAME = "place";
    public static final String CREATE_PLACES_TABLE = "CREATE TABLE " + TABLE_PLACES + " ("
            + COLUMN_PLACE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_PLACE_NAME + " TEXT NOT NULL UNIQUE"
            + ");";


    // Toss table and column names
    public static final String TABLE_TOSS = "Toss";
    public static final String COLUMN_TOSS_CALL_BY = "toss_call_by";
    public static final String COLUMN_TOSS_WON_BY = "toss_won_by";
    public static final String COLUMN_TOSS_WON_TEAM_CHOOSE_TO = "toss_won_team_choose_to";
    // SQL statement to create the Toss table
    String CREATE_TOSS_TABLE = "CREATE TABLE " + TABLE_TOSS + " ("
            + COLUMN_TOSS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_TOSS_CALL_BY + " INTEGER, "
            + COLUMN_TOSS_WON_BY + " INTEGER, "
            + COLUMN_TOSS_WON_TEAM_CHOOSE_TO + " TEXT, "
            + "FOREIGN KEY(" + COLUMN_TOSS_CALL_BY + ") REFERENCES Teams(" + COLUMN_TEAM_ID + "), "
            + "FOREIGN KEY(" + COLUMN_TOSS_WON_BY + ") REFERENCES Teams(" + COLUMN_TEAM_ID + ")"
            + ");";


    // Table Players
    public static final String TABLE_PLAYERS = "Players";
    // Column names
    public static final String COLUMN_PLAYER_ID = "player_id";
    public static final String COLUMN_NAME = "name";
    public static final String CREATE_PLAYERS_TABLE = "CREATE TABLE " + TABLE_PLAYERS + " (" +
            COLUMN_PLAYER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_NAME + " TEXT NOT NULL " + ");";

    public static final String TABLE_PLAYERS_TEAMS = "Players_Teams";
    private static final String COLUMN_INNINGS_ID = "innings_id";
    public static final String CREATE_PLAYERS_TEAMS_TABLE = "CREATE TABLE " + TABLE_PLAYERS_TEAMS + " (" +
            COLUMN_TEAM_ID + " INTEGER, " +
            COLUMN_PLAYER_ID + " INTEGER, " +
            COLUMN_INNINGS_ID + " INTEGER, " +
            "FOREIGN KEY (" + COLUMN_TEAM_ID + ") REFERENCES Teams(" + COLUMN_TEAM_ID + ") ON DELETE CASCADE, " +
            "FOREIGN KEY (" + COLUMN_PLAYER_ID + ") REFERENCES Players(" + COLUMN_PLAYER_ID + ") ON DELETE CASCADE, " +
            "FOREIGN KEY (" + COLUMN_INNINGS_ID + ") REFERENCES Innings(" + COLUMN_INNINGS_ID + ") ON DELETE CASCADE, " +
            "PRIMARY KEY (" + COLUMN_TEAM_ID + ", " + COLUMN_PLAYER_ID + ", " + COLUMN_INNINGS_ID + "))";



    // Partnerships Table
    private static final String TABLE_PARTNERSHIPS = "Partnerships";
    private static final String COLUMN_PARTNERSHIP_ID = "partnership_id";
    private static final String COLUMN_BATSMAN1_ID = "batsman1_id";
    private static final String COLUMN_BATSMAN2_ID = "batsman2_id";
    private static final String COLUMN_RUNS = "runs";
    private static final String COLUMN_BALLS = "balls";

    // SQL command to create the partnerships table
    private static final String CREATE_PARTNERSHIPS_TABLE = "CREATE TABLE " + TABLE_PARTNERSHIPS + " (" +
            COLUMN_PARTNERSHIP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_INNINGS_ID + " INTEGER, " +
            COLUMN_BATSMAN1_ID + " INTEGER, " +
            COLUMN_BATSMAN2_ID + " INTEGER, " +
            COLUMN_RUNS + " INTEGER, " +
            COLUMN_BALLS + " INTEGER, " +
            "FOREIGN KEY(" + COLUMN_INNINGS_ID + ") REFERENCES Innings(" + COLUMN_INNINGS_ID + ") ON DELETE CASCADE, " +
            "FOREIGN KEY(" + COLUMN_BATSMAN1_ID + ") REFERENCES Players(" + COLUMN_PLAYER_ID + ") ON DELETE CASCADE, " +
            "FOREIGN KEY(" + COLUMN_BATSMAN2_ID + ") REFERENCES Players(" + COLUMN_PLAYER_ID + ") ON DELETE CASCADE" +
            ");";


    // Innings Table
    private static final String TABLE_INNINGS = "Innings";
    private static final String COLUMN_INNINGS_NUMBER = "innings_number";
    private static final String COLUMN_TEAM_BATTING = "batting_team";
    private static final String COLUMN_IS_COMPLETED = "is_completed";

    // SQL command to create the innings table
    private static final String CREATE_INNINGS_TABLE = "CREATE TABLE " + TABLE_INNINGS + " (" +
            COLUMN_INNINGS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_INNINGS_NUMBER + " INTEGER, " +
            COLUMN_MATCH_ID + " INTEGER, " +
            COLUMN_TEAM_BATTING + " INTEGER, " +
            COLUMN_IS_COMPLETED + " INTEGER, " +
            "FOREIGN KEY(" + COLUMN_MATCH_ID + ") REFERENCES Matches(" + COLUMN_MATCH_ID + ") ON DELETE CASCADE, " +
            "FOREIGN KEY(" + COLUMN_TEAM_BATTING + ") REFERENCES Teams(" + COLUMN_TEAM_ID + ") ON DELETE CASCADE" +
            ");";


    // Over Table
    private static final String TABLE_OVERS = "Overs";
    private static final String COLUMN_OVER_ID = "over_id";
    private static final String COLUMN_OVER = "over";
    private static final String COLUMN_IS_MAIDEN = "is_maiden";

    // SQL command to create the overs table
    private static final String CREATE_OVERS_TABLE = "CREATE TABLE " + TABLE_OVERS + " (" +
            COLUMN_OVER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_INNINGS_ID + " INTEGER, " +
            COLUMN_OVER + " INTEGER, " +
            COLUMN_PLAYER_ID + " INTEGER, " +
            COLUMN_IS_MAIDEN + " INTEGER, " +
            "FOREIGN KEY(" + COLUMN_INNINGS_ID + ") REFERENCES Innings(" + COLUMN_INNINGS_ID + ") ON DELETE CASCADE, " +
            "FOREIGN KEY(" + COLUMN_PLAYER_ID + ") REFERENCES Players(" + COLUMN_PLAYER_ID + ") ON DELETE CASCADE" +
            ");";


    // Balls Table
    private static final String TABLE_BALLS = "Balls";
    private static final String COLUMN_BALL_ID = "ball_id";
    private static final String COLUMN_TYPE_OF_BALL = "ball_type";
    private static final String COLUMN_IS_WICKET = "is_wicket";
    private static final String COLUMN_STRIKER = "striker";
    private static final String COLUMN_NON_STRIKER = "non_striker";
    private static final String COLUMN_IS_SYNCED = "bowler";

    // SQL command to create the balls table
    private static final String CREATE_BALLS_TABLE = "CREATE TABLE " + TABLE_BALLS + " (" +
            COLUMN_BALL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_OVER_ID + " INTEGER, " +
            COLUMN_TYPE_OF_BALL + " TEXT, " +
            COLUMN_RUNS + " INTEGER, " +
            COLUMN_IS_WICKET + " INTEGER, " + // 1 for true (wicket), 0 for false (no wicket)
            COLUMN_STRIKER + " INTEGER, " +
            COLUMN_NON_STRIKER + " INTEGER, " +
            COLUMN_IS_SYNCED + "INTEGER DEFAULT 0, " + // New column to track sync status (0 = not synced, 1 = synced)
            "FOREIGN KEY(" + COLUMN_OVER_ID + ") REFERENCES Overs(" + COLUMN_OVER_ID + ") ON DELETE CASCADE, " +
            "FOREIGN KEY(" + COLUMN_STRIKER + ") REFERENCES Players(" + COLUMN_PLAYER_ID + ") ON DELETE CASCADE, " +
            "FOREIGN KEY(" + COLUMN_NON_STRIKER + ") REFERENCES Players(" + COLUMN_PLAYER_ID + ") ON DELETE CASCADE " +
            ");";


    // Wickets Table
    private static final String TABLE_WICKETS = "Wickets";
    private static final String COLUMN_WICKET_ID = "wicket_id";
    private static final String COLUMN_WICKET_TYPE = "wicket_type";
    private static final String COLUMN_WICKET_BATSMAN = "wicket_batsman";
    private static final String COLUMN_WICKET_RUNS = "wicket_runs";
    private static final String COLUMN_WICKET_CONTRIBUTOR = "wicket_contributor";

    // SQL command to create the Wickets table
    private static final String CREATE_WICKETS_TABLE = "CREATE TABLE " + TABLE_WICKETS + " (" +
            COLUMN_WICKET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_BALL_ID + " INTEGER, " +
            COLUMN_WICKET_TYPE + " TEXT, " +
            COLUMN_WICKET_BATSMAN + " INTEGER, " +
            COLUMN_WICKET_RUNS + " INTEGER, " +
            COLUMN_WICKET_CONTRIBUTOR + " INTEGER, " +
            "FOREIGN KEY(" + COLUMN_BALL_ID + ") REFERENCES Balls(" + COLUMN_BALL_ID + ") ON DELETE CASCADE, " +
            "FOREIGN KEY(" + COLUMN_WICKET_BATSMAN + ") REFERENCES Players(" + COLUMN_PLAYER_ID + ") ON DELETE CASCADE, " +
            "FOREIGN KEY(" + COLUMN_WICKET_CONTRIBUTOR + ") REFERENCES Players(" + COLUMN_PLAYER_ID + ") ON DELETE CASCADE" +
            ");";


    // Extras Table
    private static final String TABLE_EXTRAS = "Extras";
    private static final String COLUMN_EXTRA_ID = "extra_id";
    private static final String COLUMN_EXTRA_TYPE = "extra_type";
    private static final String COLUMN_EXTRA_RUNS = "extra_runs";

    // SQL command to create the Extras table
    private static final String CREATE_EXTRAS_TABLE = "CREATE TABLE " + TABLE_EXTRAS + " (" +
            COLUMN_EXTRA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_BALL_ID + " INTEGER, " +
            COLUMN_EXTRA_TYPE + " TEXT, " +
            COLUMN_EXTRA_RUNS + " INTEGER, " +
            "FOREIGN KEY(" + COLUMN_BALL_ID + ") REFERENCES Balls(" + COLUMN_BALL_ID + ") ON DELETE CASCADE" +
            ");";



    //Batsmans table
    private static final String TABLE_BATSMAN = "Batsmans";
    // Batsman Table columns
    private static final String COLUMN_PLAYER = "player";
    private static final String COLUMN_SCORE = "score";
    private static final String COLUMN_BALLS_PLAYED = "balls";
    private static final String COLUMN_ZEROES = "zeroes";
    private static final String COLUMN_ONES = "ones";
    private static final String COLUMN_TWOS = "twos";
    private static final String COLUMN_THREES = "threes";
    private static final String COLUMN_FOURS = "bndry";
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
            "FOREIGN KEY (" + COLUMN_PLAYER + ") REFERENCES Players(player_id), " +
            "FOREIGN KEY (" + COLUMN_INNINGS_ID + ") REFERENCES Innings(innings_id)" +
            ")";


    // Bowler table name
    private static final String TABLE_BOWLER = "Bowlers";
    private static final String COLUMN_MAIDENS = "maidens";
    private static final String COLUMN_ECONOMY = "economy";
    private static final String COLUMN_WK = "wk";
    private static final String COLUMN_BY = "bye";
    private static final String COLUMN_LB = "lb";
    private static final String COLUMN_WB = "wb";
    private static final String COLUMN_NB = "nb";
    private static final String COLUMN_DB = "db";

    // SQL query to create the Bowler table
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
            + "FOREIGN KEY (" + COLUMN_PLAYER + ") REFERENCES Players(player_id), "
            + "FOREIGN KEY (" + COLUMN_INNINGS_ID + ") REFERENCES Innings(innings_id));";

    // Team Statistics Table
    private static final String TABLE_TEAM_STATISTICS = "TeamStatistics";
    private static final String COLUMN_TEAM_STATS_ID = "TeamStatsId";
    private static final String COLUMN_EXTRAS = "extras";
    private static final String COLUMN_WICKETS = "wickets";
    // SQL command to create the Team Statistics table
    private static final String CREATE_TEAM_STATISTICS_TABLE = "CREATE TABLE " + TABLE_TEAM_STATISTICS + " (" +
            COLUMN_TEAM_STATS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_INNINGS_ID + " INTEGER , " + // Links to Innings table
            COLUMN_RUNS + " INTEGER DEFAULT 0, " +         // Total runs scored
            COLUMN_WICKETS + " INTEGER DEFAULT 0, " +      // Total wickets lost
            COLUMN_BALLS + " INTEGER DEFAULT 0, " +
            COLUMN_EXTRAS + " INTEGER DEFAULT 0, " +
            "FOREIGN KEY(" + COLUMN_INNINGS_ID + ") REFERENCES " + TABLE_INNINGS + "(" + COLUMN_INNINGS_ID + ") ON DELETE CASCADE" +
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
        db.execSQL("DROP TABLE IF EXISTS Teams");
        db.execSQL("DROP TABLE IF EXISTS Places");
        db.execSQL("DROP TABLE IF EXISTS Toss");
        db.execSQL("DROP TABLE IF EXISTS Matches_Teams");
        db.execSQL("DROP TABLE IF EXISTS Players_Teams");
        db.execSQL("DROP TABLE IF EXISTS Players");
        db.execSQL("DROP TABLE IF EXISTS Partnerships");
        db.execSQL("DROP TABLE IF EXISTS Innings");
        db.execSQL("DROP TABLE IF EXISTS Overs");
        db.execSQL("DROP TABLE IF EXISTS Balls");
        db.execSQL("DROP TABLE IF EXISTS Wickets");
        db.execSQL("DROP TABLE IF EXISTS Extras");
        db.execSQL("DROP TABLE IF EXISTS Batsmans");
        db.execSQL("DROP TABLE IF EXISTS Bowlers");
        db.execSQL("DROP TABLE IF EXISTS TeamStatistics");
        onCreate(db);
    }




// -----------------------------------------------Main Activity------------------------------------------------------
    // Check if there is an ongoing match
    public Cursor getOngoingMatch() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(" SELECT " + COLUMN_MATCH_ID + " FROM " + TABLE_MATCHES + " WHERE " + COLUMN_IS_MATCH_COMPLETED + "=0", null);
        Log.d(TAG, "getOngoingMatch: " + cursor);
        return cursor;
    }

    // Insert a new match if no ongoing match exists
    public long insertNewMatch() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_IS_MATCH_COMPLETED, 0); // Match is not completed initially
        long matchId = db.insert(TABLE_MATCHES, null, values);
        db.close();
        return matchId;
    }

    public static String getColumnId() {
        return COLUMN_MATCH_ID;
    }

// -----------------------------------------------MatchInfo Activity------------------------------------------------------
    public boolean insertMatchBasicInfo1(long matchId, String matchType,
                                         String overs, String ballType, String place, String time,
                                         int isCompleted) {

        SQLiteDatabase db = this.getWritableDatabase();
        long placeId = getOrInsertPlaceId(place);
        ContentValues values = new ContentValues();
        values.put(COLUMN_MATCH_ID, matchId);
        values.put(COLUMN_MATCH_TYPE, matchType);
        values.put(COLUMN_OVERS, overs);
        values.put(COLUMN_BALL_TYPE, ballType);
        values.put(COLUMN_LOCATION, placeId);
        values.put(COLUMN_DATE_TIME, time);
        values.put(COLUMN_IS_MATCH_COMPLETED, isCompleted);
        // Check if the row exists
        int rowsUpdated = db.update(TABLE_MATCHES, values, COLUMN_MATCH_ID + "=?", new String[]{String.valueOf(matchId)});
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
        // Insert the new place and get the place_id
        // Return the place_id (newly inserted)
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

    // Helper method to check if a team exists or insert it and return the team ID
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
            int rowsUpdatedInMatch = db.update(TABLE_MATCHES, matchValues, "match_id = ?", new String[]{String.valueOf(currentMatchId)});
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
        String selectQuery = "SELECT " + COLUMN_PLAYER_ID + " FROM " + TABLE_PLAYERS + " WHERE " + COLUMN_NAME + " = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{playerName});
        if (cursor != null && cursor.moveToFirst()) {
            // Player exists, return the existing ID
            playerId = cursor.getLong(0);
            Log.d("DatabaseHelper", "Player already exists with ID: " + playerId);
        } else {
            // Player does not exist, insert a new record
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_NAME, playerName);
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
            values.put(COLUMN_OVER, over);
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
    public long insertBallDataFor0To6(long overId, int runs, long strikerId, long nonStrikerId) {
        String typeofBall = "Normal";
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_OVER_ID, overId);
        contentValues.put(COLUMN_TYPE_OF_BALL, typeofBall);
        contentValues.put(COLUMN_RUNS, runs);
        contentValues.put(COLUMN_IS_WICKET, 0);
        contentValues.put(COLUMN_STRIKER, strikerId);
        contentValues.put(COLUMN_NON_STRIKER, nonStrikerId);
        long ballId = db.insert(TABLE_BALLS, null, contentValues);
        db.close();
        return ballId;
    }
    public long insertBallDataForByLByes(long overId, String typeOfBall, int runs, long strikerId, long nonStrikerId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_OVER_ID, overId); // Get overId from SharedPreferences
        contentValues.put(COLUMN_TYPE_OF_BALL, typeOfBall); // Set type of ball (all legal for now)
        contentValues.put(COLUMN_RUNS, runs); // Runs scored on the ball
        contentValues.put(COLUMN_IS_WICKET, 0);
        contentValues.put(COLUMN_STRIKER, strikerId); // Get striker ID from SharedPreferences
        contentValues.put(COLUMN_NON_STRIKER, nonStrikerId); // Get non-striker ID from SharedPreferences
        long ballId = db.insert(TABLE_BALLS, null, contentValues);
        db.close(); // Close the database connection
        return ballId;
    }
    public long insertBallDataForWide(long overId, int runs, long strikerId, long nonStrikerId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_OVER_ID, overId); // Get overId from SharedPreferences
        contentValues.put(COLUMN_TYPE_OF_BALL, WIDE_BALL); // Set type of ball (all legal for now)
        contentValues.put(COLUMN_RUNS, runs + 1); // Runs scored on the ball
        contentValues.put(COLUMN_IS_WICKET, 0);
        contentValues.put(COLUMN_STRIKER, strikerId); // Get striker ID from SharedPreferences
        contentValues.put(COLUMN_NON_STRIKER, nonStrikerId); // Get non-striker ID from SharedPreferences
        long ballId = db.insert(TABLE_BALLS, null, contentValues);
        db.close(); // Close the database connection
        return ballId;
    }
    public long insertBallDataForNb(long overId, int extraRuns, long strikerId, long nonStrikerId) {
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            int totalRuns = extraRuns + 1;
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_OVER_ID, overId); // Over ID
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
    public long insertBallDataForWicket(long overId, String typeOfBall, int runs, long strikerId, long nonStrikerId) {
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            int totalRuns = runs;
            if (typeOfBall.equals(WIDE_BALL) || typeOfBall.equals(NO_BALL)) {
                totalRuns += 1; // Add 1 extra run for wide/no ball
            }
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_OVER_ID, overId); // Over ID
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
            db.update(TABLE_MATCHES, values, whereClause, whereArgs);

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
        String query = "SELECT m.match_type, m.overs, m.ball_type, p.place AS venue, m.date_time " +
                "FROM Matches m " +
                "INNER JOIN Places p ON m.location = p.place_id " +
                "WHERE m.match_id = ?";
        Log.d("SQLQuery", "Query: " + query);  // Log the query for debugging
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(matchId)});
        if (cursor.moveToFirst()) {
            matchDetails.put("matchType", cursor.getString(cursor.getColumnIndexOrThrow("matchType")));
            matchDetails.put("overs", cursor.getString(cursor.getColumnIndexOrThrow("overs")));
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


}





