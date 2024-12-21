package com.cricketscoringapp.criceasy.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cricketscoringapp.criceasy.R;

public class TeamSelectionActivity extends AppCompatActivity {
    private EditText teamAEditText;
    private String teamType; // To differentiate between Team A and Team B


    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_selection); // Make sure this layout file exists4

        String SHARED_PREFERENCES = "match_prefs";
        sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        String TEAM_TYPE_KEY = "teamType";
        teamType = sharedPreferences.getString(TEAM_TYPE_KEY, "");

        Button SubmitButton = findViewById(R.id.okButton);
        teamAEditText = findViewById(R.id.teamNameEditText);
        SubmitButton.setOnClickListener(v -> {
            saveTeamNameInSharedPreferences();
            goToTeamsCreationPage();
        });
    }

    public void goToTeamsCreationPage() {
        Intent intent = new Intent(this, TeamCreationActivity.class);
        startActivity(intent);
        finish();
    }

    // Method to show a Toast message
    private void showToast(String message) {
        Toast.makeText(TeamSelectionActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    private void saveTeamNameInSharedPreferences() {
        String teamName = teamAEditText.getText().toString().trim();
        if (teamName.isEmpty()) {
            showToast("Please enter a team name.");
            return;
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String TEAM_B = "teamBName";
        String TEAM_A = "teamAName";
        if (TEAM_A.equals(teamType)) {
            editor.putString(TEAM_A, teamName);
        } else if (TEAM_B.equals(teamType)) {
            editor.putString(TEAM_B, teamName);
        }
        editor.apply();
        showToast("Team name saved successfully!");
    }
}