package com.cricketscoringapp.criceasy.repository;

import android.util.Log;

import com.cricketscoringapp.criceasy.model.Batsman;
import com.cricketscoringapp.criceasy.Database.DatabaseHelper;

import java.util.List;

public class BatterDetailsRepository {
    private static final String TAG = "BatterDetailsRepository";
    private DatabaseHelper databaseHelper;

    // Constructor to initialize the DatabaseHelper
    public BatterDetailsRepository(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }

    // Method to get all batter stats for a specific innings
    public List<Batsman> getBatterDetailsForInnings(long inningsId) {
        Log.d(TAG, "getBatterDetailsForInnings started for inningsId: " + inningsId);
        List<Batsman> batterDetailsList = null;
        try {
            // Fetching batter details using the helper method
            batterDetailsList = databaseHelper.getAllBatterStats(inningsId);
            // Check if data was returned correctly
            if (batterDetailsList == null || batterDetailsList.isEmpty()) {
                Log.e(TAG, "No batter details found for inningsId: " + inningsId);
                return null;
            }
            Log.d(TAG, "Fetched batter details: " + batterDetailsList.size() + " batters.");
        } catch (Exception e) {
            Log.e(TAG, "Error occurred in getBatterDetailsForInnings: ", e);
        } finally {
            Log.d(TAG, "getBatterDetailsForInnings ended for inningsId: " + inningsId);
        }
        return batterDetailsList;
    }
}
