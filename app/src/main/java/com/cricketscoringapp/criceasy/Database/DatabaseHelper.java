package com.cricketscoringapp.criceasy.Database;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;
import static android.content.ContentValues.TAG;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 11; // Update version
    private static final String DATABASE_NAME = "CricketDB";
    private final Context context;
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




// ---------------------------------------------------------------------------------------------------------------------------------
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


    //                                           ****S,Ns,Bow PAGE METHODS *****
    public void initializeBatsmanStats(long playerId, long inningsId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        Log.d("batter id check", "initializeBatsmanStats: " + playerId);
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
        Log.d("bowler id check", "initializeBowlerStats: " + playerId);
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
    public long initializeTeamStats(long inningsId) {
        SQLiteDatabase db = this.getWritableDatabase();
        long teamStatsId = -1; // Default value if insertion fails
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("innings_id", inningsId);
            // Insert and get the row ID
            teamStatsId = db.insert(TABLE_TEAM_STATISTICS, null, contentValues);

            if (teamStatsId != -1) {
                Log.d("DatabaseHelper", "Team stats initialized for innings ID: " + inningsId);
            } else {
                Log.e("DatabaseHelper", "Failed to initialize team stats.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("DatabaseHelper", "Error during team stats initialization.");
        } finally {
            db.close();
        }
        return teamStatsId;
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

    //---------------------------------------------ball table------------------------------
    public long insertBallDataFor0To6(long overId, String typeOfBall, int runs, long strikerId, long nonStrikerId) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Create a ContentValues object to hold the values to be inserted
        ContentValues contentValues = new ContentValues();

        // Add the values to the ContentValues object
        contentValues.put(COLUMN_OVER_ID, overId); // Get overId from SharedPreferences
        contentValues.put(COLUMN_TYPE_OF_BALL, typeOfBall); // Set type of ball (all legal for now)
        contentValues.put(COLUMN_RUNS, runs); // Runs scored on the ball
        contentValues.put(COLUMN_IS_WICKET, 0);
        contentValues.put(COLUMN_STRIKER, strikerId); // Get striker ID from SharedPreferences
        contentValues.put(COLUMN_NON_STRIKER, nonStrikerId); // Get non-striker ID from SharedPreferences

        // Insert the data into the balls table
        long ballId = db.insert(TABLE_BALLS, null, contentValues);

        db.close(); // Close the database connection

        // Return the ID of the inserted ball (auto-generated)
        return ballId;
    }
    public long insertBallDataForByLByes(long overId, String typeOfBall, int runs, long strikerId, long nonStrikerId) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Create a ContentValues object to hold the values to be inserted
        ContentValues contentValues = new ContentValues();

        // Add the values to the ContentValues object
        contentValues.put(COLUMN_OVER_ID, overId); // Get overId from SharedPreferences
        contentValues.put(COLUMN_TYPE_OF_BALL, typeOfBall); // Set type of ball (all legal for now)
        contentValues.put(COLUMN_RUNS, runs); // Runs scored on the ball
        contentValues.put(COLUMN_IS_WICKET, 0);
        contentValues.put(COLUMN_STRIKER, strikerId); // Get striker ID from SharedPreferences
        contentValues.put(COLUMN_NON_STRIKER, nonStrikerId); // Get non-striker ID from SharedPreferences

        // Insert the data into the balls table
        long ballId = db.insert(TABLE_BALLS, null, contentValues);

        db.close(); // Close the database connection

        // Return the ID of the inserted ball (auto-generated)
        return ballId;
    }
    public long insertBallDataForWide(long overId, int runs, long strikerId, long nonStrikerId) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Create a ContentValues object to hold the values to be inserted
        ContentValues contentValues = new ContentValues();

        // Add the values to the ContentValues object
        contentValues.put(COLUMN_OVER_ID, overId); // Get overId from SharedPreferences
        contentValues.put(COLUMN_TYPE_OF_BALL, "Wide"); // Set type of ball (all legal for now)
        contentValues.put(COLUMN_RUNS, runs + 1); // Runs scored on the ball
        contentValues.put(COLUMN_IS_WICKET, 0);
        contentValues.put(COLUMN_STRIKER, strikerId); // Get striker ID from SharedPreferences
        contentValues.put(COLUMN_NON_STRIKER, nonStrikerId); // Get non-striker ID from SharedPreferences

        // Insert the data into the balls table
        long ballId = db.insert(TABLE_BALLS, null, contentValues);

        db.close(); // Close the database connection

        // Return the ID of the inserted ball (auto-generated)
        return ballId;
    }
    public long insertBallDataForNb(long overId, int extraRuns, long strikerId, long nonStrikerId) {
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            // Calculate the total runs for the no-ball
            int totalRuns = extraRuns + 1; // 1 run for the no-ball itself + additional runs

            // Create a ContentValues object to hold the values to be inserted
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_OVER_ID, overId); // Over ID
            contentValues.put(COLUMN_TYPE_OF_BALL, "No Ball"); // Type of ball set to "No Ball"
            contentValues.put(COLUMN_RUNS, totalRuns); // Total runs for the no-ball
            contentValues.put(COLUMN_IS_WICKET, 0); // No wicket for no-ball
            contentValues.put(COLUMN_STRIKER, strikerId); // Striker ID
            contentValues.put(COLUMN_NON_STRIKER, nonStrikerId); // Non-striker ID

            // Insert the data into the balls table
            long ballId = db.insert(TABLE_BALLS, null, contentValues);

            Log.d("DatabaseHelper", "No Ball inserted successfully with ID: " + ballId);
            return ballId; // Return the ID of the inserted ball (auto-generated)
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("DatabaseHelper", "Failed to insert No Ball data.");
            return -1; // Indicate failure
        } finally {
            db.close(); // Close the database connection
        }
    }
    public long insertBallDataForWicket(long overId, String typeOfBall, int runs, long strikerId, long nonStrikerId) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            // Calculate the total runs for the ball (add extra runs for wide/no ball)
            int totalRuns = runs;
            if (typeOfBall.equals("Wide") || typeOfBall.equals("No-ball")) {
                totalRuns += 1; // Add 1 extra run for wide/no ball
            }

            // Create a ContentValues object to hold the values to be inserted
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_OVER_ID, overId); // Over ID
            contentValues.put(COLUMN_TYPE_OF_BALL, typeOfBall); // Type of ball (Wide/No Ball/Normal)
            contentValues.put(COLUMN_RUNS, totalRuns); // Total runs for the ball
            contentValues.put(COLUMN_IS_WICKET, 1); // No wicket for this ball type (unless it's a wicket)
            contentValues.put(COLUMN_STRIKER, strikerId); // Striker ID
            contentValues.put(COLUMN_NON_STRIKER, nonStrikerId); // Non-striker ID

            // Insert the data into the balls table
            long ballId = db.insert(TABLE_BALLS, null, contentValues);
            Log.d("DatabaseHelper", "Ball data inserted successfully with ID: " + ballId);
            return ballId; // Return the ID of the inserted ball (auto-generated)
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("DatabaseHelper", "Failed to insert ball data.");
            return -1; // Indicate failure
        } finally {
            db.close(); // Close the database connection
        }
    }



    //---------------------------------updating partnerships---------------------------------
    public void updatePartnershipFor0to6(int runsScored, int ballsFaced) {
        SQLiteDatabase db = this.getWritableDatabase();
        SharedPreferences sharedPreferences = context.getSharedPreferences("match_prefs", Context.MODE_PRIVATE);

        // Retrieve the partnership ID from shared preferences
        long partnershipId = sharedPreferences.getLong("partnership_id", -1);

        if (partnershipId == -1) {
            Log.e("DatabaseHelper", "Partnership ID not found in SharedPreferences.");
            return;
        }

        try {
            // Prepare SQL update query
            String query = "UPDATE " + TABLE_PARTNERSHIPS +
                    " SET " + COLUMN_RUNS + " = " + COLUMN_RUNS + " + ?, " +
                    COLUMN_BALLS + " = " + COLUMN_BALLS + " + ? " +
                    "WHERE " + COLUMN_PARTNERSHIP_ID + " = ?";

            // Execute update
            db.execSQL(query, new Object[]{runsScored, ballsFaced, partnershipId});
            Log.d("DatabaseHelper", "Partnership updated successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("DatabaseHelper", "Failed to update partnership.");
        } finally {
            db.close();
        }
    }
    public void updatePartnershipForByLByes(int ballsFaced) {
        SQLiteDatabase db = this.getWritableDatabase();
        SharedPreferences sharedPreferences = context.getSharedPreferences("match_prefs", Context.MODE_PRIVATE);

        // Retrieve the partnership ID from SharedPreferences
        long partnershipId = sharedPreferences.getLong("partnership_id", -1);

        if (partnershipId == -1) {
            Log.e("DatabaseHelper", "Partnership ID not found in SharedPreferences.");
            return;
        }

        try {
            // Prepare SQL update query for updating balls faced in partnership
            String updateQuery = "UPDATE " + TABLE_PARTNERSHIPS +
                    " SET " + COLUMN_BALLS + " = " + COLUMN_BALLS + " + ? " +
                    " WHERE " + COLUMN_PARTNERSHIP_ID + " = ?";

            // Execute the update query with the appropriate arguments (ballsFaced, partnershipId)
            SQLiteStatement statement = db.compileStatement(updateQuery);
            statement.bindLong(1, ballsFaced);    // Bind ballsFaced
            statement.bindLong(2, partnershipId); // Bind partnershipId

            statement.executeUpdateDelete(); // Execute the update query

            Log.d("DatabaseHelper", "Partnership updated for Byes/Leg Byes successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("DatabaseHelper", "Failed to update partnership for Byes/Leg Byes.");
        } finally {
            db.close();
        }
    }
    public void updatePartnershipForNb(long partnershipId, int runsScored, String runType) {
        SQLiteDatabase db = this.getWritableDatabase();

        if (partnershipId == -1) {
            Log.e("DatabaseHelper", "Invalid Partnership ID.");
            return;
        }

        try {
            // Base SQL update query
            String query = "UPDATE " + TABLE_PARTNERSHIPS + " SET ";

            // Determine how to update based on the run type
            if (runType.equals("Bat")) {
                // Runs from the bat during a no-ball
                query += COLUMN_RUNS + " = " + COLUMN_RUNS + " + ? ";
            } else if (runType.equals("Bye") || runType.equalsIgnoreCase("legbye")) {
                // No runs added for byes or leg-byes in partnerships table
                Log.d("DatabaseHelper", "No runs added for run type: " + runType);
                return;
            } else if(runType.equals("Leg Bye")){
                //Log.e("DatabaseHelper", "Invalid run type: " + runType);
                return;
            }

            // Add WHERE condition to the query
            query += " WHERE " + COLUMN_PARTNERSHIP_ID + " = ?";

            // Execute the update query
            db.execSQL(query, new Object[]{runsScored, partnershipId});
            Log.d("DatabaseHelper", "Partnership updated successfully for no-ball run type: " + runType);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("DatabaseHelper", "Failed to update partnership for no-ball.");
        } finally {
            db.close();
        }
    }



    //--------------------------------------updating batsman score---------------------------
    public void updateBatsmanStatsFor0To6(long innings_id, long player_id, int runs) {
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            // Prepare SQL update query for batsman stats
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



            // Add WHERE condition to the query
            updateQuery += " WHERE " + COLUMN_PLAYER + " = ? AND " + COLUMN_INNINGS_ID + " = ?";

            // Execute the query
            SQLiteStatement statement = db.compileStatement(updateQuery);
            statement.bindLong(1, runs);  // Bind the runs value
            statement.bindLong(2, player_id);  // Bind player_id
            statement.bindLong(3, innings_id);  // Bind innings_id

            statement.executeUpdateDelete();  // Execute the update query

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
    }
    public void updateBatsmanForByLByes(long innings_id, long player_id, int balls) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            // Prepare SQL update query for batsman balls played
            String updateQuery = "UPDATE " + TABLE_BATSMAN +
                    " SET " + COLUMN_BALLS_PLAYED + " = " + COLUMN_BALLS_PLAYED + " + ? ," +
                    COLUMN_ZEROES + " = " + COLUMN_ZEROES + " + 1" +
                    " WHERE " + COLUMN_PLAYER + " = ? AND " + COLUMN_INNINGS_ID + " = ?";

            // Execute the query
            SQLiteStatement statement = db.compileStatement(updateQuery);
            statement.bindLong(1, balls);  // Bind player_id
            statement.bindLong(2, player_id); // Bind innings_id
            statement.bindLong(3, innings_id); // Bind innings_id
            statement.executeUpdateDelete();   // Execute the update query
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
    }
    public void updateBatsmanStatsForNb(long innings_id, long player_id, int runs, String runType) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            // Base SQL update query for batsman stats
            String updateQuery = "UPDATE " + TABLE_BATSMAN +
                    " SET " + COLUMN_BALLS_PLAYED + " = " + COLUMN_BALLS_PLAYED + " + 0"; // No-ball does not increment balls played

            // Check run type
            switch (runType) {
                case "Bat":
                    // Add runs to score column
                    updateQuery += ", " + COLUMN_SCORE + " = " + COLUMN_SCORE + " + ?";
                    // Update the specific run type columns (0s, 1s, etc.)
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
                    break;

                case "bye":
                case "legbye":
                    // Add 1 run for the no-ball (extras) but do not update specific run type columns
                    updateQuery += ", " + COLUMN_SCORE + " = " + COLUMN_SCORE + " + 0";
                    break;

                default:
                    return; // Invalid run type, do nothing
            }

            // Add WHERE condition to the query
            updateQuery += " WHERE " + COLUMN_PLAYER + " = ? AND " + COLUMN_INNINGS_ID + " = ?";

            // Prepare the statement and bind values
            SQLiteStatement statement = db.compileStatement(updateQuery);
            statement.bindLong(1, (runType.equals("Bat") ? runs : 0)); // Bind runs (actual or extras)
            statement.bindLong(2, player_id);  // Bind player_id
            statement.bindLong(3, innings_id);  // Bind innings_id
            statement.executeUpdateDelete();  // Execute the update query
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
    }
    public void updateBatsmanStatsForWicket(long innings_id, long player_id, int runs, String ballType, String runsFrom, String wicketType) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            String updateQuery = "UPDATE " + TABLE_BATSMAN + " SET ";
            SQLiteStatement statement = null;

            if (wicketType.equals("BOWLED") || wicketType.equals("CAUGHT") || wicketType.equals("LBW")) {
                updateQuery += COLUMN_SCORE + " = " + COLUMN_SCORE + " + ? ," +
                        COLUMN_BALLS + " = " + COLUMN_BALLS + " + 1 ";
                updateQuery += " WHERE " + COLUMN_PLAYER + " = ? AND " + COLUMN_INNINGS_ID + " = ?";
                statement = db.compileStatement(updateQuery);
                statement.bindLong(1, runs); // Bind runs
                statement.bindLong(2, player_id); // Bind player_id
                statement.bindLong(3, innings_id); // Bind innings_id

            } else if (wicketType.equals("STUMPED")) {
                if (ballType.equals("Wide")) {
                    updateQuery += COLUMN_SCORE + " = " + COLUMN_SCORE + " + ? ";
                    updateQuery += " WHERE " + COLUMN_PLAYER + " = ? AND " + COLUMN_INNINGS_ID + " = ?";
                    statement = db.compileStatement(updateQuery);
                    statement.bindLong(1, runs); // Bind runs
                    statement.bindLong(2, player_id); // Bind player_id
                    statement.bindLong(3, innings_id); // Bind innings_id
                } else if (ballType.equals("Normal")) {
                    updateQuery += COLUMN_SCORE + " = " + COLUMN_SCORE + " + ? ," +
                            COLUMN_BALLS + " = " + COLUMN_BALLS + " + 1 ";
                    updateQuery += " WHERE " + COLUMN_PLAYER + " = ? AND " + COLUMN_INNINGS_ID + " = ?";
                    statement = db.compileStatement(updateQuery);
                    statement.bindLong(1, runs); // Bind runs
                    statement.bindLong(2, player_id); // Bind player_id
                    statement.bindLong(3, innings_id); // Bind innings_id
                }
            } else if (wicketType.equals("RUN-OUT")) {
                if (ballType.equals("Normal")) {
                    if (runsFrom.equals("From Bat")) {
                        updateQuery += COLUMN_SCORE + " = " + COLUMN_SCORE + " + ? ," +
                                COLUMN_BALLS + " = " + COLUMN_BALLS + " + 1 ";
                        updateQuery += " WHERE " + COLUMN_PLAYER + " = ? AND " + COLUMN_INNINGS_ID + " = ?";
                        statement = db.compileStatement(updateQuery);
                        statement.bindLong(1, runs); // Bind runs
                        statement.bindLong(2, player_id); // Bind player_id
                        statement.bindLong(3, innings_id); // Bind innings_id
                    } else if (runsFrom.equals("From by/lb")) {
                        updateQuery += COLUMN_BALLS + " = " + COLUMN_BALLS + " + 1 ";
                        updateQuery += " WHERE " + COLUMN_PLAYER + " = ? AND " + COLUMN_INNINGS_ID + " = ?";
                        statement = db.compileStatement(updateQuery);
                        statement.bindLong(1, player_id); // Bind player_id
                        statement.bindLong(2, innings_id); // Bind innings_id
                    }
                } else if (ballType.equals("No-ball")) {
                    if (runsFrom.equals("From Bat")) {
                        updateQuery += COLUMN_SCORE + " = " + COLUMN_SCORE + " + ? ";
                        updateQuery += " WHERE " + COLUMN_PLAYER + " = ? AND " + COLUMN_INNINGS_ID + " = ?";
                        statement = db.compileStatement(updateQuery);
                        statement.bindLong(1, runs); // Bind runs
                        statement.bindLong(2, player_id); // Bind player_id
                        statement.bindLong(3, innings_id); // Bind innings_id
                    }
                } else if (ballType.equals("Wide")) {
                    // Wide deliveries do not affect batter stats
                    updateQuery = null;
                }
            }

            // If the query is valid (not null), execute it
            if (updateQuery != null && statement != null) {
                statement.executeUpdateDelete();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
    }




    //---------------------------------------- update bowlers stats-------------------------------
    public void updateBowlerStatsFor0to6(long innings_id, long player_id, int runs) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            // Prepare SQL update query for bowler stats
            String updateQuery = "UPDATE " + TABLE_BOWLER +
                    " SET " +
                    COLUMN_RUNS + " = " + COLUMN_RUNS + " + ?, " +
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
                    return; // Invalid run, do nothing
            }

            // Add WHERE condition to the query
            updateQuery += " WHERE " + COLUMN_PLAYER + " = ? AND " + COLUMN_INNINGS_ID + " = ?";

            // Execute the query
            SQLiteStatement statement = db.compileStatement(updateQuery);
            statement.bindLong(1, runs);  // Bind the runs value
            statement.bindLong(2, player_id);  // Bind player_id
            statement.bindLong(3, innings_id);  // Bind innings_id
            statement.executeUpdateDelete();  // Execute the update query
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
    }
    public void updateBowlerForByLBes(long innings_id, long player_id, String ballType) {
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            // Prepare SQL update query for updating byes or leg byes
            String updateQuery = "UPDATE " + TABLE_BOWLER +
                    " SET " + COLUMN_BALLS_PLAYED + " = " + COLUMN_BALLS_PLAYED + " + 1, ";

            // Depending on the ball type, increment the respective column
            if ("Bye".equalsIgnoreCase(ballType)) {
                updateQuery += COLUMN_BY + " = " + COLUMN_BY + " + 1 ";
            } else if ("Leg Bye".equalsIgnoreCase(ballType)) {
                updateQuery += COLUMN_LB + " = " + COLUMN_LB + " + 1 ";
            } else {
                // If ballType is neither "Bye" nor "Leg Bye", return early.
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
            e.printStackTrace();
            Log.e("DatabaseHelper", "Failed to update bowler for Byes/Leg Byes.");
        } finally {
            db.close();
        }
    }
    public void updateBowlerForWide(long innings_id, long player_id, int wideRuns) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            // Prepare SQL update query for updating wide balls
            String updateQuery = "UPDATE " + TABLE_BOWLER +
                    " SET " + COLUMN_WB + " = " + COLUMN_WB + " + 1, " +
                    COLUMN_RUNS + " = " + COLUMN_RUNS + " + ? " +
                    " WHERE " + COLUMN_PLAYER + " = ? AND " + COLUMN_INNINGS_ID + " = ?";

            // Compile the statement
            SQLiteStatement statement = db.compileStatement(updateQuery);
            statement.bindLong(1, 1);    // Bind the wide runs
            statement.bindLong(2, player_id);  // Bind the bowler ID
            statement.bindLong(3, innings_id); // Bind the innings ID

            // Execute the update
            statement.executeUpdateDelete();
            Log.d("DatabaseHelper", "Bowler updated successfully for Wide.");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("DatabaseHelper", "Failed to update bowler for Wide.");
        } finally {
            db.close();
        }
    }
    public void updateBowlerStatsForNb(long innings_id, long player_id, int runs, String runType) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            // Base SQL update query for bowler stats
            String updateQuery = "UPDATE " + TABLE_BOWLER +
                    " SET " +
                    COLUMN_RUNS + " = " + COLUMN_RUNS + " + ?, " +  // Increment runs based on the type
                    COLUMN_BALLS_PLAYED + " = " + COLUMN_BALLS_PLAYED + " + 0 ";  // No-ball does not increment balls played

            // Check run type
            switch (runType) {
                case "Bat":
                    // Add additional logic for runs from the bat
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

                case "Bye":
                case "Leg Bye":
                    // No need to update specific run type columns for byes or leg byes
                    // Increment runs by 1 for the no-ball
                    break;

                default:
                    return; // Invalid run type, do nothing
            }

            // Add WHERE condition to the query
            updateQuery += " WHERE " + COLUMN_PLAYER + " = ? AND " + COLUMN_INNINGS_ID + " = ?";

            // Prepare the statement and bind values
            SQLiteStatement statement = db.compileStatement(updateQuery);
            statement.bindLong(1, (runType.equals("Bat") ? runs + 1 : 1));  // Bind runs or 1 based on run type
            statement.bindLong(2, player_id);  // Bind player_id
            statement.bindLong(3, innings_id);  // Bind innings_id
            statement.executeUpdateDelete();  // Execute the update query
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
    }
    public void updateBowlerStatsForWicket(long innings_id, long bowler_id, int runs, String ballType, String runsFrom, String wicketType) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            String updateQuery = "UPDATE " + TABLE_BOWLER + " SET ";
            SQLiteStatement statement = null;

            switch (wicketType) {
                case "BOWLED":
                case "CAUGHT":
                case "LBW":
                    Log.d(TAG, "updateBowlerStatsForWicket: hiiiiiiiiiiiii");
                    updateQuery += COLUMN_BALLS + " = " + COLUMN_BALLS + " + 1 ," +
                            COLUMN_WK + " = " + COLUMN_WK + " + 1 ";
                    updateQuery += " WHERE " + COLUMN_PLAYER + " = ? AND " + COLUMN_INNINGS_ID + " = ?";
                    statement = db.compileStatement(updateQuery);
                    statement.bindLong(1, bowler_id); // Bind bowler_id
                    statement.bindLong(2, innings_id); // Bind innings_id
                    break;
                case "STUMPED":
                    if (ballType.equals("Wide")) {
                        updateQuery += COLUMN_WK + " = " + COLUMN_WK + " + 1 ," +
                                COLUMN_RUNS + " = " + COLUMN_RUNS + " + 1";
                        updateQuery += " WHERE " + COLUMN_PLAYER + " = ? AND " + COLUMN_INNINGS_ID + " = ?";
                        statement = db.compileStatement(updateQuery);
                        statement.bindLong(1, bowler_id); // Bind bowler_id
                        statement.bindLong(2, innings_id); // Bind innings_id
                    } else if (ballType.equals("Normal")) {
                        updateQuery += COLUMN_BALLS + " = " + COLUMN_BALLS + " + 1 ," +
                                COLUMN_WK + " = " + COLUMN_WK + " + 1 ";
                        updateQuery += " WHERE " + COLUMN_PLAYER + " = ? AND " + COLUMN_INNINGS_ID + " = ?";
                        statement = db.compileStatement(updateQuery);
                        statement.bindLong(1, bowler_id); // Bind bowler_id
                        statement.bindLong(2, innings_id); // Bind innings_id
                    }
                    break;
                case "RUN-OUT":
                    switch (ballType) {
                        case "Normal":
                            if (ballType.equals("Normal")) {
                                if (runsFrom.equals("From Bat")) {
                                    // Construct query and bind together
                                    updateQuery += COLUMN_BALLS + " = " + COLUMN_BALLS + " + 1 ," +
                                            COLUMN_RUNS + " = " + COLUMN_RUNS + " + ?";
                                    updateQuery += " WHERE " + COLUMN_PLAYER + " = ? AND " + COLUMN_INNINGS_ID + " = ?";
                                    statement = db.compileStatement(updateQuery);
                                    statement.bindLong(1, runs); // Bind runs
                                    statement.bindLong(2, bowler_id); // Bind bowler_id
                                    statement.bindLong(3, innings_id); // Bind innings_id
                                } else if (runsFrom.equals("From by/lb")) {
                                    // Construct query and bind together
                                    updateQuery += COLUMN_BALLS + " = " + COLUMN_BALLS + " + 1";
                                    updateQuery += " WHERE " + COLUMN_PLAYER + " = ? AND " + COLUMN_INNINGS_ID + " = ?";
                                    statement = db.compileStatement(updateQuery);
                                    statement.bindLong(1, bowler_id); // Bind bowler_id
                                    statement.bindLong(2, innings_id); // Bind innings_id
                                }
                            }
                            break;
                        case "No-ball":
                            if (runsFrom.equals("From Bat")) {
                                updateQuery += COLUMN_RUNS + " = " + COLUMN_RUNS + " + ?";
                                updateQuery += " WHERE " + COLUMN_PLAYER + " = ? AND " + COLUMN_INNINGS_ID + " = ?";
                                statement = db.compileStatement(updateQuery);
                                statement.bindLong(1, runs); // Bind runs
                                statement.bindLong(2, bowler_id); // Bind bowler_id
                                statement.bindLong(3, innings_id); // Bind innings_id
                            }
                            break;
                        case "Wide":
                            updateQuery += COLUMN_RUNS + " = " + COLUMN_RUNS + " + 1";
                            updateQuery += " WHERE " + COLUMN_PLAYER + " = ? AND " + COLUMN_INNINGS_ID + " = ?";
                            statement = db.compileStatement(updateQuery);
                            statement.bindLong(1, bowler_id); // Bind bowler_id

                            statement.bindLong(2, innings_id); // Bind innings_id

                            break;
                    }
                    break;
            }

            // If the query is valid (not null), execute it
            if (updateQuery != null && statement != null) {
                statement.executeUpdateDelete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
    }




    //------------------------------------update extra table------------------------------
    public void updateExtrasTable(long ball_id, String ball_type, int runs) {
        // Get writable database instance
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            // Prepare the content values to insert or update
            ContentValues values = new ContentValues();
            values.put(COLUMN_BALL_ID, ball_id);
            values.put(COLUMN_EXTRA_TYPE, ball_type);

            // Handle Bye and Leg Bye
            if (ball_type.equals("Bye") || ball_type.equals("Leg Bye")) {
                values.put(COLUMN_EXTRA_RUNS, runs);

                // Insert the extra data into the EXTRAS table
                long result = db.insertWithOnConflict(TABLE_EXTRAS, null, values, SQLiteDatabase.CONFLICT_REPLACE);

                if (result == -1) {
                    Log.e("updateExtrasTable", "Failed to insert or update extra runs for Bye/Leg Bye.");
                } else {
                    Log.d("updateExtrasTable", "Extra runs updated successfully for Bye/Leg Bye, ball_id: " + ball_id);
                }

            }
            // Handle Wide
            else if (ball_type.equals("Wide")) {
                values.put(COLUMN_EXTRA_RUNS, runs + 1); // Add 1 run for the wide ball itself

                long result = db.insertWithOnConflict(TABLE_EXTRAS, null, values, SQLiteDatabase.CONFLICT_REPLACE);

                if (result == -1) {
                    Log.e("updateExtrasTable", "Failed to insert or update extra runs for Wide.");
                } else {
                    Log.d("updateExtrasTable", "Extra runs updated successfully for Wide, ball_id: " + ball_id);
                }

            }
            // Handle No Ball
            else if (ball_type.equals("No Ball")) {
                values.put(COLUMN_EXTRA_RUNS, runs + 1); // Add 1 run for the no-ball itself

                long result = db.insertWithOnConflict(TABLE_EXTRAS, null, values, SQLiteDatabase.CONFLICT_REPLACE);

                if (result == -1) {
                    Log.e("updateExtrasTable", "Failed to insert or update extra runs for No Ball.");
                } else {
                    Log.d("updateExtrasTable", "Extra runs updated successfully for No Ball, ball_id: " + ball_id);
                }

            }
            // Handle invalid extra type
            else {
                Log.e("updateExtrasTable", "Invalid extra type: " + ball_type);
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("updateExtrasTable", "Error while updating extras table: " + e.getMessage());
        } finally {
            // Close the database connection
            db.close();
        }
    }

//---------------------------------------------team stats---------------------------------------------------
    public void updateTeamStatsFor0to6(long teamStatsId, int runs) {
    SQLiteDatabase db = this.getWritableDatabase();
    try {
        // Get the current values of runs and balls
        String selectQuery = "SELECT " + COLUMN_RUNS + ", " + "balls" + " FROM " + TABLE_TEAM_STATISTICS + " WHERE " + COLUMN_TEAM_STATS_ID + " = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(teamStatsId)});

        int currentRuns = 0;
        int currentBalls = 0;

        if (cursor != null && cursor.moveToFirst()) {
            // Ensure the columns exist by checking the index is not -1
            int runsIndex = cursor.getColumnIndex(COLUMN_RUNS);
            int ballsIndex = cursor.getColumnIndex("balls");

            if (runsIndex != -1 && ballsIndex != -1) {
                // Only proceed if the column indices are valid
                currentRuns = cursor.getInt(runsIndex);
                currentBalls = cursor.getInt(ballsIndex);
            } else {
                Log.e("DatabaseHelper", "Column not found.");
                return; // Exit the method if column indices are invalid
            }

            cursor.close();
        }

        // Increment runs and balls
        currentRuns += runs;
        currentBalls += 1;

        // Update the stats in the database
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
        e.printStackTrace();
        Log.e("DatabaseHelper", "Error during team stats update for 0 to 6 runs.");
    } finally {
        db.close();
    }
}
    public void updateTeamStatsForByesAndLegByes(long teamStatsId, int runs) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            // Get the current values of runs, balls, and extras
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

            // Increment runs, balls, and extras for Byes or Leg Byes
            currentRuns += runs;  // Add runs to total runs
            currentBalls += 1;    // Increment the ball count
            currentExtras += runs; // Add runs to extras

            // Update the stats in the database
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
            e.printStackTrace();
            Log.e("DatabaseHelper", "Error during team stats update for Byes/Leg Byes.");
        } finally {
            db.close();
        }
    }
    public void updateTeamStatsForWide(long teamStatsId, int runs) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            // Get the current values of runs, extras
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

            // Increment runs and extras for Wides
            int totalWideRuns = runs + 1; // Add the mandatory extra run for the wide
            currentRuns += totalWideRuns; // Update total runs
            currentExtras += totalWideRuns; // Update extras

            // Update the stats in the database
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
            e.printStackTrace();
            Log.e("DatabaseHelper", "Error during team stats update for Wides.");
        } finally {
            db.close();
        }
    }
    public void updateTeamStatsForNoBall(long teamStatsId, int runs, String runsSource) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
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
            if ("Bat".equalsIgnoreCase(runsSource)) {
                // Add only the no-ball extra
                currentExtras += 1;
            } else if ("Lb".equalsIgnoreCase(runsSource) || "By".equalsIgnoreCase(runsSource)) {
                // Add the total runs (runs + 1 for no-ball)
                currentExtras += totalNoBallRuns;
            } else {
                Log.e("DatabaseHelper", "Invalid runs source.");
                return; // Exit if the source is invalid
            }

            // Save updates in the database
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
            e.printStackTrace();
            Log.e("DatabaseHelper", "Error during team stats update for No-Ball.");
        } finally {
            db.close();
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
        String query = null;
        SQLiteStatement statement;

        if ("Wide".equalsIgnoreCase(ballType)) {
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
        } else if ("No-ball".equalsIgnoreCase(ballType)) {
            if ("From Bat".equalsIgnoreCase(runsFrom)) {
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
            } else if ("From by/lb".equalsIgnoreCase(runsFrom)) {
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
        } else if ("Normal".equalsIgnoreCase(ballType)) {
            if ("From Bat".equalsIgnoreCase(runsFrom)) {
                // Normal from bat: Add runs to total runs, increment ball and wicket
                query = "UPDATE " + TABLE_TEAM_STATISTICS +
                        " SET " +
                        COLUMN_RUNS + " = " + COLUMN_RUNS + " + ?, " +
                        COLUMN_BALLS + " = " + COLUMN_BALLS + " + 1, " +
                        COLUMN_WICKETS + " = " + COLUMN_WICKETS + " + 1 " +
                        "WHERE " + COLUMN_TEAM_STATS_ID + " = ?";
                statement = db.compileStatement(query);
                statement.bindLong(1, runs); // Total runs: runs from bat
                statement.bindLong(2, teamStatsId);
                statement.executeUpdateDelete();
                statement.close();
            } else if ("From by/lb".equalsIgnoreCase(runsFrom)) {
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

}





