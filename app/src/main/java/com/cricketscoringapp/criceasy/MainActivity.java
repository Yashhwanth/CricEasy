package com.cricketscoringapp.criceasy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import com.cricketscoringapp.criceasy.Database.MatchesTableDBH;

public class MainActivity extends AppCompatActivity {
    private MatchesTableDBH databaseHelper;
    private long currentMatchId = -1;
    private static final String PREFS_NAME = "MatchPrefs"; // SharedPreferences name
    private static final String KEY_MATCH_ID = "match_id";  // Key to store match ID



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Initialize database helper
        databaseHelper = new MatchesTableDBH(this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

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
            //intent.putExtra("MATCH_ID", currentMatchId);
            startActivity(intent);
        });
    }
    private void handleNewMatch() {
        Cursor cursor = null;
        try {
            cursor = databaseHelper.getOngoingMatch();
            if (cursor != null && cursor.moveToFirst()) {
                int matchIdIndex = cursor.getColumnIndex(MatchesTableDBH.getColumnId());
                if (matchIdIndex != -1) {
                    currentMatchId = cursor.getLong(matchIdIndex);
                    Toast.makeText(this, "Resuming existing match", Toast.LENGTH_SHORT).show();
                }
            } else {
                // No existing match, create a new one
                createNewMatch();
            }
        } finally {
            if (cursor != null) {
                cursor.close(); // Ensure cursor is closed to avoid memory leaks
            }
        }
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
        editor.apply(); // Save changes asynchronously
    }
    //Load the match ID from SharedPreferences.
    private void loadMatchIdFromPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        currentMatchId = sharedPreferences.getLong(KEY_MATCH_ID, -1);
    }
}