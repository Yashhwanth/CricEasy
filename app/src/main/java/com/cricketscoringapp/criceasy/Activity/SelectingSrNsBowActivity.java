package com.cricketscoringapp.criceasy.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cricketscoringapp.criceasy.Database.DatabaseHelper;
import com.cricketscoringapp.criceasy.R;

public class SelectingSrNsBowActivity extends AppCompatActivity {
    private DatabaseHelper databaseHelper;
    private SharedPreferences sharedPreferences;
    private final String SHARED_PREFERENCES = "match_prefs";
    private static final String CURRENT_ACTIVITY = "currentActivity";
    private final String CURRENT_INNINGS_ID_KEY = "currentInningsId";
    private final String CURRENT_INNINGS_NUMBER = "currentInningsNumber";
    private final String CURRENT_INNINGS_DEFAULT_VALUE  = "";
    private final String PLAYER_TYPE_KEY = "playerType";
    private final String PLAYER_STRIKER_VALUE = "striker";
    private final String PLAYER_NON_STRIKER_VALUE = "nonStriker";
    private final String PLAYER_BOWLER_VALUE = "bowler";
    private final String PLAYER_STRIKER_ID = "strikerId";
    private final String PLAYER_NON_STRIKER_ID = "nonStrikerId";
    private final String PLAYER_BOWLER_ID = "bowlerId";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_ns_bowler); // Make sure this layout file exists
        updateCurrentActivityInPreferences();

        databaseHelper = new DatabaseHelper(this);

        sharedPreferences = getSharedPreferences(SHARED_PREFERENCES,MODE_PRIVATE);
        String currentInnings = sharedPreferences.getString(CURRENT_INNINGS_NUMBER, CURRENT_INNINGS_DEFAULT_VALUE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        //UI
        ImageView strikerButton = findViewById(R.id.srImageView);
        ImageView nonStrikerButton = findViewById(R.id.nSrImageView);
        ImageView bowlerButton = findViewById(R.id.bowImageView);
        Button startScoringButton = findViewById(R.id.startScoringButton);
        Button backButton = findViewById(R.id.backButton);

        strikerButton.setOnClickListener(view ->{
            editor.putString(PLAYER_TYPE_KEY, PLAYER_STRIKER_VALUE);
            editor.apply();
            selectingPlayersActivity();
        });

        nonStrikerButton.setOnClickListener(view ->{
            editor.putString(PLAYER_TYPE_KEY, PLAYER_NON_STRIKER_VALUE);
            editor.apply();
            selectingPlayersActivity();
        });

        bowlerButton.setOnClickListener(view ->{
            editor.putString(PLAYER_TYPE_KEY, PLAYER_BOWLER_VALUE);
            editor.apply();
            selectingPlayersActivity();
        });

        startScoringButton.setOnClickListener(view ->{
            if (validateInputs()) {
                insertSNsBowl();
                updateInningsTable();
                updateTeamStatsTable();
                updateOverTable();
                updatePartnershipTable();
                updateBatsmanTable();
                updateBowlerTable();
                if (currentInnings.equals("first")) {
                    Log.d("InningsCheck", "Second innings setup completed, closing the activity.");
                    finish(); // Close the page when it's the second innings
                } else {
                    Log.d("InningsCheck", "First innings setup completed, starting scoring page.");
                    matchActivity(); // Navigate to the scoring page for the first innings
                }
            }
        });
        backButton.setOnClickListener(view -> previousActivity());
    }
    @Override
    protected void onResume() {
        super.onResume();
        updateCurrentActivityInPreferences();
    }
    public void selectingPlayersActivity() {
        Intent intent = new Intent(this, SelectingPlayersActivity.class);
        startActivity(intent);
    }
    public void previousActivity() {
        Intent intent = new Intent(this, TossActivity.class);
        startActivity(intent);
    }
    public void matchActivity() {
        Intent intent = new Intent(this, MatchActivity.class);
        startActivity(intent);
    }
    private void updateCurrentActivityInPreferences() {
        sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(CURRENT_ACTIVITY, getClass().getSimpleName());
        editor.apply();
    }
    private boolean validateInputs() {
        sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        String strikerName = sharedPreferences.getString(PLAYER_STRIKER_VALUE, null);
        if (strikerName == null) {
            showToast("Please select and complete Striker details!");
            return false;
        }
        // Validate Non-Striker
        String nonStrikerName = sharedPreferences.getString( PLAYER_NON_STRIKER_VALUE, null);
        if (nonStrikerName == null) {
            showToast("Please select and complete Non-Striker details!");
            return false;
        }
        // Validate Bowler
        String bowlerName = sharedPreferences.getString(PLAYER_BOWLER_VALUE, null);
        if (bowlerName == null) {
            showToast("Please select and complete Bowler details!");
            return false;
        }
        return true;
    }
    private void insertSNsBowl() {
        sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        String TEAM_A_ID = "teamAId";
        long team1Id = sharedPreferences.getLong(TEAM_A_ID, -1);
        String TEAM_B_ID = "teamBId";
        long team2Id = sharedPreferences.getLong(TEAM_B_ID, -1);
        String strikerName = sharedPreferences.getString(PLAYER_STRIKER_VALUE, CURRENT_INNINGS_DEFAULT_VALUE);
        String nonStrikerName = sharedPreferences.getString(PLAYER_NON_STRIKER_VALUE, CURRENT_INNINGS_DEFAULT_VALUE);
        String bowlerName = sharedPreferences.getString(PLAYER_BOWLER_VALUE, CURRENT_INNINGS_DEFAULT_VALUE);
        long strikerId = databaseHelper.insertPlayer(strikerName, team1Id);
        long nonStrikerId = databaseHelper.insertPlayer(nonStrikerName, team1Id);
        long bowlerId = databaseHelper.insertPlayer(bowlerName, team2Id);
        updateSNsBowIdInSharedPreferences(strikerId, nonStrikerId, bowlerId);
    }
    private void updateSNsBowIdInSharedPreferences(long strikerId, long nonStrikerId, long bowlerId){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(PLAYER_STRIKER_ID, strikerId);
        editor.putLong(PLAYER_NON_STRIKER_ID, nonStrikerId);
        editor.putLong(PLAYER_BOWLER_ID, bowlerId);
        editor.apply();
    }
    private void updateInningsTable(){
        sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        String CURRENT_MATCH_ID = "currentMatchId";
        long matchId = sharedPreferences.getLong(CURRENT_MATCH_ID, -1);
        String BATTING_TEAM_ID = "battingTeamId";
        long battingTeamId = sharedPreferences.getLong(BATTING_TEAM_ID, -1);
        String currentInningsNumber = sharedPreferences.getString(CURRENT_INNINGS_NUMBER,null);
        long inningsId = databaseHelper.startFirstInnings(matchId, battingTeamId, currentInningsNumber);
        updateInningsDetailsInSharedPreferences(inningsId, currentInningsNumber);
        Log.d("DatabaseHelper", "First innings started with inningsId: " + inningsId);
    }
    private void updateInningsDetailsInSharedPreferences(long inningsId, String currentInningsNumber){
        sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String FIRST_INNINGS = "first";
        String SECOND_INNINGS = "second";
        String PLAYED_BALLS = "playedBalls";
        String TARGET = "target";
        String SCORE = "score";
        if(currentInningsNumber == null) {
            editor.putLong(CURRENT_INNINGS_ID_KEY, inningsId);
            editor.putString(CURRENT_INNINGS_NUMBER, FIRST_INNINGS);
            editor.putInt(SCORE,0);
            editor.putLong(PLAYED_BALLS, 0);
            editor.putLong(TARGET, Integer.MAX_VALUE);
            editor.apply();
        }else{
            long score = sharedPreferences.getLong(SCORE, -1);
            editor.putLong(CURRENT_INNINGS_ID_KEY, inningsId);
            editor.putString(CURRENT_INNINGS_NUMBER, SECOND_INNINGS);
            editor.putInt(SCORE, 0);
            editor.putLong(PLAYED_BALLS, 0);
            editor.putLong(TARGET, score + 1);
            editor.apply();
        }
    }
    private void updateOverTable(){
        sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        long inningsId = sharedPreferences.getLong(CURRENT_INNINGS_ID_KEY, -1);
        long bowlerId = sharedPreferences.getLong(PLAYER_BOWLER_ID,-1);
        long overId = databaseHelper.insertOver(inningsId, 1, bowlerId,0);
        updateOverDetailsToSharedPreferences(overId);
    }
    private void updateOverDetailsToSharedPreferences(long overId){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        final String CURRENT_OVER_SCORE = "currentOverScore";
        editor.putInt(CURRENT_OVER_SCORE, 0);
        String OVER_ID = "overId";
        editor.putLong(OVER_ID, overId);
        editor.apply();
    }
    private void updatePartnershipTable(){
        sharedPreferences = getSharedPreferences(SHARED_PREFERENCES,MODE_PRIVATE);
        long inningsId = sharedPreferences.getLong(CURRENT_INNINGS_ID_KEY,-1);
        long strikerId = sharedPreferences.getLong(PLAYER_STRIKER_ID,-1);
        long nonStrikerId = sharedPreferences.getLong(PLAYER_NON_STRIKER_ID,-1);
        long partnershipId = databaseHelper.insertPartnership(inningsId,strikerId, nonStrikerId);
        updatePShipDetailsToSharedPreferences(partnershipId);
    }
    private void updatePShipDetailsToSharedPreferences(long partnershipId){
        sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String TEAMS_PARTNERSHIP_ID = "partnershipId";
        editor.putLong(TEAMS_PARTNERSHIP_ID, partnershipId);
        editor.apply();
    }
    private void updateBatsmanTable(){
        sharedPreferences = getSharedPreferences(SHARED_PREFERENCES,MODE_PRIVATE);
        long strikerId = sharedPreferences.getLong(PLAYER_STRIKER_ID, -1);
        long nonStrikerId = sharedPreferences.getLong(PLAYER_NON_STRIKER_ID, -1);
        long inningsId = sharedPreferences.getLong(CURRENT_INNINGS_ID_KEY,-1);
        databaseHelper.initializeBatsmanStats(strikerId,inningsId);
        databaseHelper.initializeBatsmanStats(nonStrikerId,inningsId);
    }
    private void updateBowlerTable(){
        sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        long bowlerId = sharedPreferences.getLong(PLAYER_BOWLER_ID,-1);
        long inningsId = sharedPreferences.getLong(CURRENT_INNINGS_ID_KEY,-1);
        databaseHelper.initializeBowlerStats(bowlerId,inningsId );
    }
    private void updateTeamStatsTable() {
        sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        long inningsId = sharedPreferences.getLong(CURRENT_INNINGS_ID_KEY, -1);
        if (inningsId != -1) {
            long teamStatsId = databaseHelper.initializeTeamStats(inningsId);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            String TEAMS_STATS_ID = "teamStatsId";
            editor.putLong(TEAMS_STATS_ID, teamStatsId);
            editor.apply();
        } else {
            Log.e("TeamStats", "Failed to initialize team stats due to invalid innings or team ID.");
        }
    }
    private void showToast(String message) {
        Toast.makeText(SelectingSrNsBowActivity.this, message, Toast.LENGTH_SHORT).show();
    }


}
