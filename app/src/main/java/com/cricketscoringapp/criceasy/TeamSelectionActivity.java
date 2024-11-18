package com.cricketscoringapp.criceasy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_selection); // Make sure this layout file exists4

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("match_prefs", MODE_PRIVATE);

        // Initialize DatabaseHelper
        databaseHelper = new DatabaseHelper(this); // Highlighted line added

        // Retrieve team type from SharedPreferences
        teamType = sharedPreferences.getString("TEAM_TYPE", "");

        //UI Components
        Button SubmitButton = findViewById(R.id.button);
        teamAEditText = findViewById(R.id.editTextText);


        //UI Onclicks
        SubmitButton.setOnClickListener(v -> {
            saveTeamName();
            goback();
        });
    }

    public void goback() {
        // Navigate back to MatchInfoActivity
        Intent intent = new Intent(this, TeamCreationActivity.class);
        startActivity(intent);
        finish(); // Close the current activity
    }

    // Method to show a Toast message
    private void showToast(String message) {
        Toast.makeText(TeamSelectionActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    private void saveTeamName() {
        String teamName = teamAEditText.getText().toString().trim();

        if (teamName.isEmpty()) {
            showToast("Please enter a team name.");
            return;
        }

        // Save team name based on teamType
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if ("A".equals(teamType)) {
            editor.putString("A", teamName);
        } else if ("B".equals(teamType)) {
            editor.putString("B", teamName);
        }
        editor.apply();

        showToast("Team name saved successfully!");
    }
}