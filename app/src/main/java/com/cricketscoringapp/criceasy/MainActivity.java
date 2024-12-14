package com.cricketscoringapp.criceasy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import com.cricketscoringapp.criceasy.Database.DatabaseHelper;

public class MainActivity extends AppCompatActivity {
    private DatabaseHelper databaseHelper;
    private long currentMatchId = -1;
    private static final String PREFS_NAME = "match_prefs"; // SharedPreferences name
    private static final String KEY_MATCH_ID = "match_id";  // Key to store match ID
    private static final String KEY_CURRENT_ACTIVITY = "current_activity"; // Key for current activity



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = getSharedPreferences("match_prefs",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong("playedBalls", 0);
        //editor.clear();
        editor.apply();


        // Initialize database helper
        databaseHelper = new DatabaseHelper(this);



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

//        // Check if there's a current activity saved and navigate accordingly
//        if (navigateToLastActivityIfOngoingMatch()) {
//            return; // Skip MainActivity logic if navigating to the last activity
//        }
//
        updateCurrentActivityInPreferences();

        // Load saved match ID from SharedPreferences (if any)
        loadMatchIdFromPreferences();

        //Onclick for button
        Button newMatchButton = findViewById(R.id.newmatchbtn);
        newMatchButton.setOnClickListener(view ->{
            handleNewMatch();
            // Save the match ID to SharedPreferences after creating or resuming a match
            saveMatchIdToPreferences();
            // Proceed to the MatchInfoActivity
            Intent intent = new Intent(MainActivity.this, MatchInfoActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Update current activity in SharedPreferences
        updateCurrentActivityInPreferences();
    }
    private void handleNewMatch() {
        try (Cursor cursor = databaseHelper.getOngoingMatch()) {
            if (cursor != null && cursor.moveToFirst()) {
                int matchIdIndex = cursor.getColumnIndex(DatabaseHelper.getColumnId());
                if (matchIdIndex != -1) {
                    currentMatchId = cursor.getLong(matchIdIndex);
                    Toast.makeText(this, "Resuming existing match", Toast.LENGTH_SHORT).show();
                }
            } else {
                // No existing match, create a new one
                createNewMatch();
            }
        }catch (Exception e) {
            e.printStackTrace();
            // Handle any potential exceptions (optional, for safety)
            Toast.makeText(this, "Error handling match!", Toast.LENGTH_SHORT).show();
        }
        // Ensure cursor is closed to avoid memory leaks
    }

    private void createNewMatch() {
        currentMatchId = databaseHelper.insertNewMatch();
        if (currentMatchId != -1) {
            Toast.makeText(this, "New match created", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to create a new match", Toast.LENGTH_SHORT).show();
        }
    }
    //Save the current match ID to SharedPreferences.
    private void saveMatchIdToPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(KEY_MATCH_ID, currentMatchId);
        Log.d("match id check", "hi" + currentMatchId);
        editor.apply(); // Save changes asynchronously
    }
    //Load the match ID from SharedPreferences.
    private void loadMatchIdFromPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        currentMatchId = sharedPreferences.getLong(KEY_MATCH_ID, -1);
    }
    private void updateCurrentActivityInPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("match_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("current_activity", getClass().getSimpleName()); // Store the current activity name
        editor.apply(); // Save changes asynchronously
    }

    private boolean navigateToLastActivityIfOngoingMatch() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String lastActivity = sharedPreferences.getString(KEY_CURRENT_ACTIVITY, null);
        long matchId = sharedPreferences.getLong(KEY_MATCH_ID, -1);

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


}