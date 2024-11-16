package com.cricketscoringapp.criceasy.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PlacesTableDBH extends SQLiteOpenHelper {

    // Database version and name
    private static final String DATABASE_NAME = "cricket_matches.db";
    private static final int DATABASE_VERSION = 1;

    // Places table and column names
    public static final String TABLE_PLACES = "Places";
    public static final String COLUMN_PLACE_ID = "place_id";
    public static final String COLUMN_PLACE_NAME = "place";

    public PlacesTableDBH(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create the Places table
        String CREATE_PLACES_TABLE = "CREATE TABLE " + TABLE_PLACES + " ("
                + COLUMN_PLACE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_PLACE_NAME + " TEXT NOT NULL UNIQUE"
                + ")";

        // Execute the statement
        db.execSQL(CREATE_PLACES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if it exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLACES);

        // Recreate the table
        onCreate(db);
    }
}
