package com.cricketscoringapp.criceasy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.cricketscoringapp.criceasy.Database.DatabaseHelper;

public class TeamCreationActivity extends AppCompatActivity {
    private long matchId;
    private SharedPreferences sharedPreferences;
    private TextView team1TextView, team2TextView;
    private DatabaseHelper databaseHelper; // Added DatabaseHelper instance

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_creation);
        updateCurrentActivityInPreferences();


        // Initialize DatabaseHelper
        databaseHelper = new DatabaseHelper(this); // Highlighted line added

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("match_prefs", MODE_PRIVATE);

        //UI Components
        Button teamA = findViewById(R.id.btnSelectTeamA);
        Button teamB = findViewById(R.id.btnSelectTeamB);
        team1TextView = findViewById(R.id.textView22);
        team2TextView = findViewById(R.id.textView23);

        // Set the existing team names if available
        team1TextView.setText(" ");
        team2TextView.setText(" ");

        //Onclicks
        teamA.setOnClickListener(v -> {
            // Save "A" as the team type in SharedPreferences
            sharedPreferences.edit().putString("TEAM_TYPE", "A").apply();
            // Navigate back to TeamSelectionActivity
            Intent intent = new Intent(this, TeamSelectionActivity.class);
            startActivity(intent);
            //teamsInput();
        });
        teamB.setOnClickListener(v -> {
            // Save "B" as the team type in SharedPreferences
            sharedPreferences.edit().putString("TEAM_TYPE", "B").apply();
            // Navigate  to TeamSelectionActivity
            Intent intent = new Intent(this, TeamSelectionActivity.class);
            startActivity(intent);
            //teamsInput();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh team names when coming back from TeamSelectionActivity
        loadTeamNames();
        // Update current activity in SharedPreferences
        updateCurrentActivityInPreferences();
    }

    // Method to load team names from SharedPreferences
    private void loadTeamNames() {
        String team1Name = sharedPreferences.getString("A", null);
        String team2Name = sharedPreferences.getString("B", null);

        if (team1Name != null) {
            team1TextView.setText(team1Name);
        }
        if (team2Name != null) {
            team2TextView.setText(team2Name);
        }
    }


    // Method to handle Back button click
    public void goBack(View view) {
        // Navigate back to MatchInfoActivity
        Intent intent = new Intent(this, MatchInfoActivity.class);
        startActivity(intent);
        //finish(); // Close the current activity
    }

    // Method to handle Next button click
    public void goToNext(View view) {
        // Navigate to TossActivity
        validateTeamsAndSavetoDB();
        Intent intent = new Intent(this, TossActivity.class); // Replace with your next activity
        startActivity(intent);
    }
    public void validateTeamsAndSavetoDB(){
        String team1Name = sharedPreferences.getString("A", null);
        String team2Name = sharedPreferences.getString("B", null);
        long match_id = sharedPreferences.getLong("currentMatchId",-1);
        if(team1Name != null && team2Name != null){
            databaseHelper.addTeamNames(match_id,team1Name,team2Name);
        }
    }
    private void updateCurrentActivityInPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("match_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("current_activity", getClass().getSimpleName()); // Store the current activity name
        editor.apply(); // Save changes asynchronously
    }

}