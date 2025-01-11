package com.cricketscoringapp.criceasy.Activity;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;


import com.cricketscoringapp.criceasy.Database.DatabaseHelper;
import com.cricketscoringapp.criceasy.Database.*;
import com.cricketscoringapp.criceasy.R;
import com.cricketscoringapp.criceasy.dao.MatchDao;

public class MainActivity extends AppCompatActivity {
    private DatabaseHelper databaseHelper;
    private static final String SHARED_PREFERENCES = "match_prefs";
    private static final String MATCH_ID = "currentMatchId";
    private static final String CURRENT_ACTIVITY = "currentActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(this);

        // Initialize UI elements
        Button newMatchButton = findViewById(R.id.newMatchButton);
        Button resumeMatchButton = findViewById(R.id.pastMatchesButton);

        // Hide the "Resume Match" button by default
        resumeMatchButton.setVisibility(View.GONE);

        // Check if the app was launched through a file
        Intent intent = getIntent();
        if (intent != null && intent.getData() != null) {
            Uri fileUri = intent.getData();
            Log.d(TAG, "App opened via file URI: " + fileUri);
            Intent resumeIntent = new Intent(MainActivity.this, MatchInfoActivity.class);
            startActivity(resumeIntent);

            // Attempt to read and parse the file
//            try {
//                String jsonData = readJsonFile(fileUri);
//                if (restoreMatchStateFromJson(jsonData)) {
//                    Log.d(TAG, "Match state successfully restored.");
//                    resumeMatchButton.setVisibility(View.VISIBLE); // Show the "Resume Match" button
//                    resumeMatchButton.setOnClickListener(view -> {
//                        Log.d(TAG, "Resuming match.");
//                        Intent resumeIntent = new Intent(MainActivity.this, MatchInfoActivity.class);
//                        startActivity(resumeIntent);
//                    });
//                    return; // Skip other logic since we are resuming the match
//                } else {
//                    Log.e(TAG, "Failed to restore match state. Falling back to new match.");
//                }
//            } catch (Exception e) {
//                Log.e(TAG, "Error reading or parsing JSON file: " + e.getMessage());
//            }
        }

        // Handle the "New Match" button
        newMatchButton.setOnClickListener(view -> {
            long currentMatchId = handleNewMatch();
            saveMatchIdToPreferences(currentMatchId);
            Log.d(TAG, "onCreate: opening MatchInfo activity");
            Intent matchIntent = new Intent(MainActivity.this, MatchInfoActivity.class);
            startActivity(matchIntent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateCurrentActivityInPreferences();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy: main activity destroyed");
        super.onDestroy();
        if (databaseHelper != null) {
            databaseHelper.close();
        }
    }

    private long handleNewMatch() {
        Log.d(TAG, "handleNewMatch: checking a match is present or not");
        long currentMatchId = -1;
        try (Cursor cursor = databaseHelper.getOngoingMatch()) {
            if (cursor != null && cursor.moveToFirst()) {
                Log.d(TAG, "handleNewMatch: ongoing match exists");
                int matchIdIndex = cursor.getColumnIndex(DatabaseHelper.getColumnId());
                if (matchIdIndex != -1) {
                    currentMatchId = cursor.getLong(matchIdIndex);
                    Toast.makeText(this, "Resuming existing match", Toast.LENGTH_SHORT).show();
                }
            } else {
                Log.d(TAG, "handleNewMatch: no ongoing match");
                // No existing match, create a new one
                currentMatchId = createNewMatch();
            }
        }catch (Exception e) {
            Log.e(TAG, "handleNewMatch: error handling match");
            Toast.makeText(this, "Error handling match!", Toast.LENGTH_SHORT).show();
        }return currentMatchId;
    }
    private long createNewMatch() {
        Log.d(TAG, "createNewMatch: creating new match");
        long currentMatchId = databaseHelper.insertNewMatch();
        if (currentMatchId != -1) {
            Log.d(TAG, "createNewMatch: created a new match");
            Toast.makeText(this, "New match created", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to create a new match", Toast.LENGTH_SHORT).show();
        }
        return currentMatchId;
    }
    private void saveMatchIdToPreferences(long currentMatchId) {
        Log.d(TAG, "saveMatchIdToPreferences: saving match id in sp");
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(MATCH_ID, currentMatchId);
        editor.apply();
        Log.d(TAG, "saveMatchIdToPreferences: saved match id in sp with id" + currentMatchId);
    }
    private void updateCurrentActivityInPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(CURRENT_ACTIVITY, getClass().getSimpleName());
        editor.apply(); 
        Log.d(TAG, "inside updateCurrentActivityInPreferences method: updated current activity in sp");
    }
    private boolean navigateToLastActivityIfOngoingMatch() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        String lastActivity = sharedPreferences.getString(CURRENT_ACTIVITY, null);
        long matchId = sharedPreferences.getLong(MATCH_ID, -1);

        // Check if there's an ongoing match and a last activity
        if (lastActivity != null && matchId != -1) {
            try {
                // Dynamically load the last activity class and navigate
                Class<?> activityClass = Class.forName("com.cricketscoringapp.criceasy." + lastActivity);
                Intent intent = new Intent(this, activityClass);
                startActivity(intent);
                finish(); // Close MainActivity
                return true;
            } catch (ClassNotFoundException e) {
                // Use robust logging instead of printStackTrace
                Log.e("MainActivity", "Error resuming last activity. Class not found: " + lastActivity, e);
                Toast.makeText(this, "Error resuming last activity!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Log.w("MainActivity", "No ongoing match or last activity not found.");
        }

        return false; // No navigation occurred
    }
    // Check if there's a current activity saved and navigate accordingly
//        if (navigateToLastActivityIfOngoingMatch()) {
//            return; // Skip MainActivity logic if navigating to the last activity
//        }

}