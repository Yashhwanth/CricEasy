package com.cricketscoringapp.criceasy.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Pair;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cricketscoringapp.criceasy.Database.DatabaseHelper;
import com.cricketscoringapp.criceasy.R;

public class TeamCreationActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private TextView team1TextView, team2TextView;
    private DatabaseHelper databaseHelper;
    private final String SHARED_PREFERENCES = "match_prefs";
    private final String TEAM_TYPE_KEY = "teamType";
    public final String TEAM_A_VALUE = "teamAName";
    private final String TEAM_B_VALUE = "teamBName";
    public final String TEAM_A_ID = "teamAId";
    public final String TEAM_B_ID = "teamBId";

    private static final String MATCH_ID = "currentMatchId"; // Key for current activity


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_creation);
        updateCurrentActivityInPreferences();

        databaseHelper = new DatabaseHelper(this);
        sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);

        //UI Components
        Button teamAButton = findViewById(R.id.team1Button);
        Button teamBButton = findViewById(R.id.team2Button);
        team1TextView = findViewById(R.id.team1TextView);
        team2TextView = findViewById(R.id.team2TextView);
        Button backButton = findViewById(R.id.backButton);
        Button nextButton = findViewById(R.id.nextButton);


        // Set the existing team names if available
        String EMPTY_TEAM_VALUE = " ";
        team1TextView.setText(EMPTY_TEAM_VALUE);
        team2TextView.setText(EMPTY_TEAM_VALUE);

        teamAButton.setOnClickListener(v -> {
            sharedPreferences.edit().putString(TEAM_TYPE_KEY, TEAM_A_VALUE).apply();
            Intent intent = new Intent(this, TeamSelectionActivity.class);
            startActivity(intent);
        });
        teamBButton.setOnClickListener(v -> {
            sharedPreferences.edit().putString(TEAM_TYPE_KEY, TEAM_B_VALUE).apply();
            Intent intent = new Intent(this, TeamSelectionActivity.class);
            startActivity(intent);
        });
        backButton.setOnClickListener(v -> openPreviousActivity());
        nextButton.setOnClickListener(v -> openNextActivity());
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh team names when coming back from TeamSelectionActivity
        loadTeamNames();
        updateCurrentActivityInPreferences();
    }

    // Method to load team names from SharedPreferences
    private void loadTeamNames() {
        String team1Name = sharedPreferences.getString(TEAM_A_VALUE, null);
        String team2Name = sharedPreferences.getString(TEAM_B_VALUE, null);
        if (team1Name != null) {
            team1TextView.setText(team1Name);
        }
        if (team2Name != null) {
            team2TextView.setText(team2Name);
        }
    }
    public void openPreviousActivity() {
        // Navigate back to MatchInfoActivity
        Intent intent = new Intent(this, MatchInfoActivity.class);
        startActivity(intent);
    }
    public void openNextActivity() {
        if(!validateTeamsAndSaveToDB()) return;
        Intent intent = new Intent(this, TossActivity.class);
        startActivity(intent);
    }
    public boolean validateTeamsAndSaveToDB(){
        String team1Name = sharedPreferences.getString(TEAM_A_VALUE, null);
        String team2Name = sharedPreferences.getString(TEAM_B_VALUE, null);
        long match_id = sharedPreferences.getLong(MATCH_ID,-1);
        if(team1Name == null || team2Name == null){
            showToast("Please Enter Team Names");
            return false;
        }else{
            Pair<Long, Long> teamIds = databaseHelper.addTeamNames(match_id, team1Name, team2Name);
            updateTeamIdsInSharedPreferences(teamIds);
        } return true;
    }
    private void updateCurrentActivityInPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String CURRENT_ACTIVITY = "currentActivity";
        editor.putString(CURRENT_ACTIVITY, getClass().getSimpleName());
        editor.apply();
    }
    private void updateTeamIdsInSharedPreferences(Pair<Long, Long> teamIds){
        if (teamIds != null) {
            long teamAId = teamIds.first;
            long teamBId = teamIds.second;
            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putLong(TEAM_A_ID, teamAId);
            editor.putLong(TEAM_B_ID, teamBId);
            editor.apply();
        }
    }
    private void showToast(String message) {
        Toast.makeText(TeamCreationActivity.this, message, Toast.LENGTH_SHORT).show();
    }

}