package com.cricketscoringapp.criceasy.repository;

import android.util.Log;
import com.cricketscoringapp.criceasy.model.BallDetails;
import com.cricketscoringapp.criceasy.Database.DatabaseHelper;
import java.util.List;

public class BallDetailsRepository {
    private static final String TAG = "BallDetailsRepository";
    private DatabaseHelper databaseHelper;
    public BallDetailsRepository(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }
    public List<BallDetails> getBallDetailsForInnings(long inningsId) {
        Log.d(TAG, "getBallDetailsForInnings started for inningsId: " + inningsId);
        List<BallDetails> ballDetailsList = null;
        try {
            // Fetching ball details using the helper method
            ballDetailsList = databaseHelper.getBallDetailsForInnings(inningsId);
            // Check if data was returned correctly
            if (ballDetailsList == null || ballDetailsList.isEmpty()) {
                Log.e(TAG, "No ball details found for inningsId: " + inningsId);
                return null;
            }
            Log.d(TAG, "Fetched ball details: " + ballDetailsList.size() + " balls.");
        } catch (Exception e) {
            Log.e(TAG, "Error occurred in getBallDetailsForInnings: ", e);
        } finally {
            Log.d(TAG, "getBallDetailsForInnings ended for inningsId: " + inningsId);
        }
        return ballDetailsList;
    }
}
