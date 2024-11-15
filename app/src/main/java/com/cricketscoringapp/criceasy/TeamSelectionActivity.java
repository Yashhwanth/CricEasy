package com.cricketscoringapp.criceasy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cricketscoringapp.criceasy.Database.DatabaseHelper;

public class TeamSelectionActivity extends AppCompatActivity {
    private long matchId;
    private EditText teamAEditText;
    private DatabaseHelper databaseHelper;
    private String teamType; // To differentiate between Team A and Team B

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_selection); // Make sure this layout file exists4

        // Initialize DatabaseHelper
        databaseHelper = new DatabaseHelper(this); // Highlighted line added

        // Retrieve matchId from the Intent
        matchId = getIntent().getLongExtra("MATCH_ID", -1); // Default value is -1 if not passed
        teamType = getIntent().getStringExtra("TEAM_TYPE"); // "A" or "B"

        //UI Components
        Button SubmitButton = findViewById(R.id.button);
        teamAEditText = findViewById(R.id.editTextText);


        //UI Onclicks
        SubmitButton.setOnClickListener(v -> {
            saveMatchInfoToDatabase();
            goback();
        });
    }

    public void goback() {
        // Navigate back to MatchInfoActivity
        Intent intent = new Intent(this, TeamCreationActivity.class);
        startActivity(intent);
        finish(); // Close the current activity
    }

    // Method to save match information into the database
    private void saveMatchInfoToDatabase() { // Highlighted line added // Extract values from UI components
        String teamName = teamAEditText.getText().toString().trim();
        // Validate input
        if (teamName.isEmpty()) {
            showToast("Please enter a team name.");
            return;
        }

        // Use matchId that was already retrieved in onCreate
        // Default value for is_completed (0 means not completed)
        int isCompleted = 0;  // You can update this based on the match status

        // Save the team name based on teamType (either "A" or "B")
        boolean isInserted;
        if ("A".equals(teamType)) {
            isInserted = databaseHelper.insertMatchBasicInfo2(matchId,
                    teamName, isCompleted);
        } else if ("B".equals(teamType)) {
            isInserted = databaseHelper.insertMatchBasicInfo3(matchId,
                     teamName, isCompleted);
        } else {
            showToast("Invalid team type");
            return;
        }

        if (isInserted) {
            showToast("Match Info Saved Successfully!");
        } else {
            showToast("Failed to Save Match Info.");
        }
    }
    // Method to show a Toast message
    private void showToast(String message) {
        Toast.makeText(TeamSelectionActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}