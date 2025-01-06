package com.cricketscoringapp.criceasy.repository;

import android.util.Log;
import com.cricketscoringapp.criceasy.model.Bowler;
import com.cricketscoringapp.criceasy.Database.DatabaseHelper;
import java.util.List;

public class BowlerDetailsRepository {
    private static final String TAG = "BowlerDetailsRepository";
    private DatabaseHelper databaseHelper;

    // Constructor to initialize the DatabaseHelper
    public BowlerDetailsRepository(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }

    // Method to get all bowler stats for a specific innings
    public List<Bowler> getBowlerDetailsForInnings(long inningsId) {
        Log.d(TAG, "getBowlerDetailsForInnings started for inningsId: " + inningsId);
        List<Bowler> bowlerDetailsList = null;
        try {
            // Fetching bowler details using the helper method
            bowlerDetailsList = databaseHelper.getAllBowlerStats(inningsId);
            // Check if data was returned correctly
            if (bowlerDetailsList == null || bowlerDetailsList.isEmpty()) {
                Log.e(TAG, "No bowler details found for inningsId: " + inningsId);
                return null;
            }
            Log.d(TAG, "Fetched bowler details: " + bowlerDetailsList.size() + " bowlers.");
        } catch (Exception e) {
            Log.e(TAG, "Error occurred in getBowlerDetailsForInnings: ", e);
        } finally {
            Log.d(TAG, "getBowlerDetailsForInnings ended for inningsId: " + inningsId);
        }
        return bowlerDetailsList;
    }
}
