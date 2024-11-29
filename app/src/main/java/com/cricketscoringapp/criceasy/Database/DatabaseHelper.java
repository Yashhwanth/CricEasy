package com.cricketscoringapp.criceasy.Database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 8; // Update version
    private static final String DATABASE_NAME = "CricketDB";
    private Context context;
    private SharedPreferences sharedPreferences;


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

    // Create statement for the Matches-Teams junction table
    public static final String CREATE_MATCHES_TEAMS_TABLE =
            "CREATE TABLE " + TABLE_MATCHES_TEAMS + " (" +
                    COLUMN_MATCHES_TEAMS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_MATCH_ID + " INTEGER, " +
                    COLUMN_TEAM_ID + " INTEGER, " +
                    "FOREIGN KEY(" + COLUMN_MATCH_ID + ") REFERENCES Matches(" + COLUMN_MATCH_ID + "), " +
                    "FOREIGN KEY(" + COLUMN_TEAM_ID + ") REFERENCES Teams(" + COLUMN_TEAM_ID + ")" +
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
    public static final String COLUMN_PLAYER_BATTING_STYLE = "batting_style";
    public static final String COLUMN_PLAYER_BOWLING_STYLE = "bowling_style";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_ROLE = "role";
    public static final String CREATE_PLAYERS_TABLE = "CREATE TABLE " + TABLE_PLAYERS + " (" +
            COLUMN_PLAYER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_NAME + " TEXT NOT NULL, " +
            COLUMN_ROLE + " TEXT ," +
            COLUMN_PLAYER_BATTING_STYLE + " TEXT ," +
            COLUMN_PLAYER_BOWLING_STYLE + " TEXT " +
            ");";


    //
    public static final String TABLE_PLAYERS_TEAMS = "Players_Teams";
    public static final String CREATE_PLAYERS_TEAMS_TABLE = "CREATE TABLE " + TABLE_PLAYERS_TEAMS + " (" +
            COLUMN_TEAM_ID + " INTEGER, " +
            COLUMN_PLAYER_ID + " INTEGER, " +
            "FOREIGN KEY (" + COLUMN_TEAM_ID + ") REFERENCES Teams(" + COLUMN_TEAM_ID + ") ON DELETE CASCADE," +
            "FOREIGN KEY (" + COLUMN_PLAYER_ID + ") REFERENCES Players(" + COLUMN_PLAYER_ID + ") ON DELETE CASCADE," +
            "PRIMARY KEY (" + COLUMN_TEAM_ID + "," + COLUMN_PLAYER_ID + "))";


    // Partnerships Table
    private static final String TABLE_PARTNERSHIPS = "Partnerships";
    private static final String COLUMN_PARTNERSHIP_ID = "partnership_id";
    private static final String COLUMN_INNINGS_ID = "innings_id";
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

    // Bowler table columns
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


    // Constructor
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
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
        onCreate(db);
    }


    // Check if there is an ongoing match
    public Cursor getOngoingMatch() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(" SELECT " + COLUMN_MATCH_ID + " FROM " + TABLE_MATCHES + " WHERE " + COLUMN_IS_MATCH_COMPLETED + "=0", null);
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
            // Get or insert the place and retrieve the placeId
            long placeId = getOrInsertPlaceId(place);
            values.put("location", placeId); // Insert the placeId into Matches table
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


    public long getOrInsertPlaceId(String place) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Query to check if the place already exists in the Places table
        Cursor cursor = db.query(TABLE_PLACES, new String[]{COLUMN_PLACE_ID}, COLUMN_PLACE_NAME + " = ?", new String[]{place}, null, null, null);

        // If the cursor is not null and it contains data
        if (cursor != null) {
            // Get the column index
            int placeIdColumnIndex = cursor.getColumnIndex(COLUMN_PLACE_ID);

            // If the column index is valid (greater than or equal to 0)
            if (placeIdColumnIndex >= 0 && cursor.moveToFirst()) {
                long placeId = cursor.getLong(placeIdColumnIndex);
                cursor.close();  // Don't forget to close the cursor
                return placeId;
            }
            cursor.close();  // Close the cursor if no valid data was found
        }

        // If the place is not found, insert a new place and return the new place_id
        ContentValues values = new ContentValues();
        values.put(COLUMN_PLACE_NAME, place);

        // Insert the new place and get the place_id
        long placeId = db.insert(TABLE_PLACES, null, values);

        // Return the place_id (newly inserted)
        return placeId;
    }


    //                           ***TEAM CREATION PAGE ****
    public void addTeamNames(long match_id, String teamAName, String teamBName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction(); // Start a transaction for atomicity
        try {
            // Check if Team A exists, insert if not, and get its ID
            long teamAId = getOrInsertTeam(db, teamAName);

            // Check if Team B exists, insert if not, and get its ID
            long teamBId = getOrInsertTeam(db, teamBName);

            // Ensure Matches_Teams table is clean for the given match_id
            resetMatchTeams(db, match_id);

            // Insert into Matches_Teams table
            insertMatchTeamPair(db, match_id, teamAId);
            insertMatchTeamPair(db, match_id, teamBId);

            SharedPreferences sharedPreferences = context.getSharedPreferences("match_prefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putLong("teamA_id", teamAId);
            editor.putLong("teamB_id", teamBId);
            editor.apply(); // Save team IDs for use in TossActivity


            db.setTransactionSuccessful(); // Mark transaction as successful
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction(); // End the transaction
            db.close(); // Close the database
        }
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
    private void insertMatchTeamPair(SQLiteDatabase db, long matchId, long teamId) {
        // Insert the pair into Matches_Teams
        ContentValues values = new ContentValues();
        values.put(COLUMN_MATCH_ID, matchId);
        values.put(COLUMN_TEAM_ID, teamId);
        long result = db.insert(TABLE_MATCHES_TEAMS, null, values);
        if (result == -1) {
            throw new IllegalStateException("Failed to insert match-team pair for match_id=" + matchId + " and team_id=" + teamId);
        }
    }


    //                         *******TOSS PAGE METHODS********
    public void saveOrUpdateTossDetails(Context context, Long tossId, String teamCalling, String tossWinner, String tossDecision) {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d("DatabaseHelper", "Toss received with ID: " + tossId);

        // Retrieve the ongoing match ID from SharedPreferences
        SharedPreferences prefs = context.getSharedPreferences("match_prefs", Context.MODE_PRIVATE);
        long matchId = prefs.getLong("match_id", -1); // Default to -1 if not found
        Log.d("match_id check", "saveOrUpdateTossDetails: " + matchId);

        if (matchId == -1) {
            Log.e("DatabaseHelper", "Match ID not found in SharedPreferences.");
            return; // Exit early if match ID is not available
        }

        // Start a database transaction
        db.beginTransaction();
        try {
            // Look up the team IDs based on the team names
            int callingTeamId = getTeamIdFromName(db, teamCalling);
            int winningTeamId = getTeamIdFromName(db, tossWinner);

            // Validate that both teams exist
            if (callingTeamId == -1 || winningTeamId == -1) {
                Log.e("DatabaseHelper", "Invalid team ID(s): callingTeamId=" + callingTeamId + ", winningTeamId=" + winningTeamId);
                return; // Exit early if validation fails
            }

            // Create ContentValues for the toss details
            ContentValues tossValues = new ContentValues();
            tossValues.put(COLUMN_TOSS_CALL_BY, callingTeamId);
            tossValues.put(COLUMN_TOSS_WON_BY, winningTeamId);
            tossValues.put(COLUMN_TOSS_WON_TEAM_CHOOSE_TO, tossDecision);

            // Check if tossId is valid for updating or needs insertion
            if (tossId == null || tossId <= 0) { // Includes -1 check explicitly
                tossId = db.insert(TABLE_TOSS, null, tossValues);
                if (tossId == -1) {
                    throw new Exception("Failed to insert toss details.");
                } else {
                    Log.d("DatabaseHelper", "Toss details inserted successfully with ID: " + tossId);

                    // Save tossId in SharedPreferences for future reference (used when updating toss)
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putLong("toss_id", tossId);
                    editor.apply();
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

            // Update the toss ID in the MATCHES table for the current match
            ContentValues matchValues = new ContentValues();
            matchValues.put(COLUMN_TOSS, tossId);
            int rowsUpdatedInMatch = db.update(TABLE_MATCHES, matchValues, "match_id = ?", new String[]{String.valueOf(matchId)});
            if (rowsUpdatedInMatch <= 0) {
                throw new Exception("Failed to update match details with toss ID.");
            } else {
                Log.d("DatabaseHelper", "Match updated with toss ID: " + tossId);
            }

            // Mark transaction as successful
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Transaction failed: " + e.getMessage());
        } finally {
            // End the transaction
            db.endTransaction();
        }
    }


    private int getTeamIdFromName(SQLiteDatabase db, String teamName) {
        Cursor cursor = null;
        try {
            cursor = db.query(
                    "Teams",  // Table name
                    new String[]{COLUMN_TEAM_ID},  // Columns to retrieve
                    COLUMN_TEAM_NAME + " = ?",  // WHERE clause
                    new String[]{teamName},  // Selection args
                    null,  // GROUP BY
                    null,  // HAVING
                    null   // ORDER BY
            );

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
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return -1; // If no team found or error occurred, return -1
    }

    public void initializeBatsmanStats(long playerId, long inningsId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
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

        db.insert(TABLE_BATSMAN, null, values);
    }

    public void initializeBowlerStats(long playerId, long inningsId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
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

        db.insert(TABLE_BOWLER, null, values);
    }


    //                                           ****S,Ns,Bow PAGE METHODS *****
    public void insertPlayer(String playerName, String playerRole, String battingStyle, String bowlingStyle, String playerType) {
        // Get writable database instance
        SQLiteDatabase db = this.getWritableDatabase();

        // Create a ContentValues object to store the player data
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, playerName);
        contentValues.put(COLUMN_ROLE, playerRole);
        contentValues.put(COLUMN_PLAYER_BATTING_STYLE, battingStyle);
        contentValues.put(COLUMN_PLAYER_BOWLING_STYLE, bowlingStyle);

        // Insert the data into the players table
        long result = db.insert(TABLE_PLAYERS, null, contentValues);

        // Check if the insertion was successful
        if (result == -1) {
            Log.e("DatabaseHelper", "Error inserting player into database");
        } else {
            // Player inserted successfully
            Log.d("DatabaseHelper", "Player inserted successfully");

            SharedPreferences sharedPreferences = context.getSharedPreferences("match_prefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            // Update the specific role
            switch (playerType) {
                case "striker":
                    editor.putLong("striker_id", result);
                    break;
                case "non_striker":
                    editor.putLong("non_striker_id", result);
                    break;
                case "bowler":
                    editor.putLong("bowler_id", result);
                    break;
            }
            editor.apply();
        }
        // Close the database connection
        db.close();
    }

    //start first innings
    public void startFirstInnings(long matchId, long battingTeamId) {

        // Check if the necessary data is available
        if (matchId == -1 || battingTeamId == -1) {
            // Handle error: return or show a message that necessary data is missing
            Log.e("DatabaseHelper", "Missing data for match or team in SharedPreferences");
            return;
        }

        // Create a writable database
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            // Insert a new row to start the first innings
            ContentValues values = new ContentValues();
            values.put(COLUMN_INNINGS_NUMBER, 1); // First innings
            values.put(COLUMN_MATCH_ID, matchId); // Match ID
            values.put(COLUMN_TEAM_BATTING, battingTeamId); // Batting team ID
            values.put(COLUMN_IS_COMPLETED, 0); // Incomplete innings

            // Insert into the INNINGS table
            long inningsId = db.insert(TABLE_INNINGS, null, values);

            if (inningsId == -1) {
                // Handle failure to insert
                Log.e("DatabaseHelper", "Failed to start first innings.");
            } else {
                // Successfully inserted, store inningsId in SharedPreferences
                SharedPreferences sharedPreferences = context.getSharedPreferences("match_prefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putLong("Innings_id", inningsId); // Save inningsId in SharedPreferences
                editor.apply(); // Commit changes
                Log.d("DatabaseHelper", "First innings started with inningsId: " + inningsId);
            }
        } catch (Exception e) {
            // Handle any exceptions
            e.printStackTrace();
        } finally {
            // Close the database
            db.close();
        }
    }

    public void insertOver(long inningsId, int over, long playerId, int isMaiden) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_INNINGS_ID, inningsId);
            values.put(COLUMN_OVER, over);
            values.put(COLUMN_PLAYER_ID, playerId);
            values.put(COLUMN_IS_MAIDEN, isMaiden);

            // Insert into the overs table
            long over_id = db.insert(TABLE_OVERS, null, values);
            if (over_id == -1) {
                // Handle failure to insert
                Log.e("DatabaseHelper", "Failed to start over");
            } else {
                SharedPreferences sharedPreferences = context.getSharedPreferences("match_prefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putLong("over_id", over_id);
                editor.apply();
            }
        } catch (Exception e) {
            // Handle any exceptions
            e.printStackTrace();
        } finally {
            // Close the database
            db.close();
        }
    }

    public void insertPartnership(long innings_id, long bat1_id, long bat2_id, int runs,int balls){
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            ContentValues values = new ContentValues();
            values.put(COLUMN_INNINGS_ID,innings_id);
            values.put(COLUMN_BATSMAN1_ID,bat1_id);
            values.put(COLUMN_BATSMAN2_ID,bat2_id);
            values.put(COLUMN_RUNS,runs);
            values.put(COLUMN_BALLS,balls);

            long partnership_id = db.insert(TABLE_PARTNERSHIPS, null, values);
            if (partnership_id == -1) {
                // Handle failure to insert
                Log.e("DatabaseHelper", "Failed to start over");
            } else {
                SharedPreferences sharedPreferences = context.getSharedPreferences("match_prefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putLong("partnership_id", partnership_id);
                editor.apply();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            db.close();
        }
    }



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
            matchDetails.put("match_type", cursor.getString(cursor.getColumnIndexOrThrow("match_type")));
            matchDetails.put("overs", cursor.getString(cursor.getColumnIndexOrThrow("overs")));
            matchDetails.put("ball_type", cursor.getString(cursor.getColumnIndexOrThrow("ball_type")));
            matchDetails.put("venue", cursor.getString(cursor.getColumnIndexOrThrow("venue"))); // place name from places table
            matchDetails.put("datetime", cursor.getString(cursor.getColumnIndexOrThrow("date_time")));
        }else {
            Log.d("SQLQuery", "No data found for matchId: " + matchId);  // Log if no data is found
        }


        cursor.close();
        db.close();
        Log.d("MatchDetails", "Fetched Match Details: " + matchDetails);  // Log the match details map

        return matchDetails;
    }

}





