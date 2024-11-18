package com.cricketscoringapp.criceasy.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 4; // Update version
    private static final String DATABASE_NAME = "CricketDB";




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
            + "FOREIGN KEY(" + COLUMN_LOCATION + ") REFERENCES Places("+ COLUMN_PLACE_ID + "), "
            + "FOREIGN KEY(" + COLUMN_TOSS + ") REFERENCES Toss(" + COLUMN_TOSS_ID +"), "
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
            "FOREIGN KEY (" + COLUMN_PLAYER_ID + ") REFERENCES Players(" + COLUMN_PLAYER_ID + ") ON DELETE CASCADE,"+
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
            "FOREIGN KEY(" + COLUMN_INNINGS_ID + ") REFERENCES Innings(" +COLUMN_INNINGS_ID + ") ON DELETE CASCADE, " +
            "FOREIGN KEY(" + COLUMN_BATSMAN1_ID + ") REFERENCES Players(" + COLUMN_PLAYER_ID+ ") ON DELETE CASCADE, " +
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
            "FOREIGN KEY(" + COLUMN_STRIKER + ") REFERENCES Players(" + COLUMN_PLAYER_ID+ ") ON DELETE CASCADE, " +
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
            "FOREIGN KEY(" + COLUMN_BALL_ID + ") REFERENCES Balls(" + COLUMN_BALL_ID +") ON DELETE CASCADE" +
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
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Matches");
        db.execSQL("DROP TABLE IF EXISTS Teams");
        db.execSQL("DROP TABLE IF EXISTS Places");
        db.execSQL("DROP TABLE IF EXISTS Toss");
        db.execSQL("DROP TABLE IF EXISTS Players");
        db.execSQL("DROP TABLE IF EXISTS Partnerships");
        db.execSQL("DROP TABLE IF EXISTS Innings");
        db.execSQL("DROP TABLE IF EXISTS Overs");
        db.execSQL("DROP TABLE IF EXISTS Balls");
        db.execSQL("DROP TABLE IF EXISTS Wickets");
        db.execSQL("DROP TABLE IF EXISTS Extras");
        // Call onCreate to recreate the tables
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

}
