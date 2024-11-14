package com.cricketscoringapp.criceasy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class TeamCreationActivity extends AppCompatActivity {
    private long matchId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_creation);

        // Retrieve matchId from the Intent
        matchId = getIntent().getLongExtra("MATCH_ID", -1); // Default value is -1 if not passed

        //UI Components
        Button teamA = findViewById(R.id.btnSelectTeamA);
        Button teamB = findViewById(R.id.btnSelectTeamB);

        //Onclicks
        teamA.setOnClickListener(v -> {
            // Navigate back to MatchInfoActivity
            Intent intent = new Intent(this, TeamSelectionActivity.class);
            startActivity(intent);
            intent.putExtra("MATCH_ID", matchId);
            intent.putExtra("TEAM_TYPE", "A");
            teamsInput();
        });
        teamB.setOnClickListener(v -> {
            // Navigate back to MatchInfoActivity
            Intent intent = new Intent(this, TeamSelectionActivity.class);
            startActivity(intent);
            intent.putExtra("MATCH_ID", matchId);
            intent.putExtra("TEAM_TYPE", "B");
            teamsInput();
        });
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
        Intent intent = new Intent(this, TossActivity.class); // Replace with your next activity
        startActivity(intent);
    }

    public void teamsInput() {
        // Navigate back to MatchInfoActivity
        Intent intent = new Intent(this, TeamSelectionActivity.class);
        startActivity(intent);
        //finish(); // Close the current activity
    }
}