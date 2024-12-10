package com.cricketscoringapp.criceasy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cricketscoringapp.criceasy.Database.DatabaseHelper;

public class SelectingSrNsBowActivity extends AppCompatActivity {
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_ns_bowler); // Make sure this layout file exists
        updateCurrentActivityInPreferences();

        databaseHelper = new DatabaseHelper(this);

        SharedPreferences sharedPreferences = getSharedPreferences("match_prefs",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();


        //UI
        ImageView striker_button = findViewById(R.id.imageView);
        ImageView non_striker_button = findViewById(R.id.imageView2);
        ImageView bowler_button = findViewById(R.id.imageView3);
        Button start_scoring_button = findViewById(R.id.button2);

        striker_button.setOnClickListener(view ->{
            editor.putString("player_type","striker");
            editor.apply();
            select_player();
        });

        non_striker_button.setOnClickListener(view ->{
            editor.putString("player_type","non_striker");
            editor.apply();
            select_player();
        });

        bowler_button.setOnClickListener(view ->{
            editor.putString("player_type","bowler");
            editor.apply();
            select_player();
        });

        start_scoring_button.setOnClickListener(view ->{
            if (validateInputs()) {
                insertSNsBowl();
                innings_table();
                team_stats_table();
                overs_table();
                partnerships_table();
                batsman_table();
                bowler_table();
                lets_play(); // Navigate to the scoring page
            }
        });

    }
    @Override
    protected void onResume() {
        super.onResume();

        // Update current activity in SharedPreferences
        updateCurrentActivityInPreferences();
    }

    public void select_player() {
        // Navigate back to MatchInfoActivity
        Intent intent = new Intent(this, SelectingPlayersActivity.class);
        startActivity(intent);
        //finish(); // Close the current activity
    }

    public void back(View view) {
        // Navigate back to MatchInfoActivity
        Intent intent = new Intent(this, TossActivity.class);
        startActivity(intent);
        //finish(); // Close the current activity
    }

    public void lets_play() {
        // Navigate back to MatchInfoActivity
        Intent intent = new Intent(this, MatchActivity.class);
        startActivity(intent);
        //finish(); // Close the current activity
    }
    private void updateCurrentActivityInPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("match_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("current_activity", getClass().getSimpleName()); // Store the current activity name
        editor.apply(); // Save changes asynchronously
    }

    private boolean validateInputs() {
        SharedPreferences sharedPreferences = getSharedPreferences("match_prefs", MODE_PRIVATE);

        // Validate Striker
        String strikerName = sharedPreferences.getString("striker name", null);
        String strikerRole = sharedPreferences.getString("striker ROLE", null);
        String strikerBatStyle = sharedPreferences.getString("striker BS", null);
        String strikerBowlStyle = sharedPreferences.getString("striker BOS", null);

        if (strikerName == null || strikerRole == null || strikerBatStyle == null || strikerBowlStyle == null) {
            Toast.makeText(this, "Please select and complete Striker details!", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validate Non-Striker
        String nonStrikerName = sharedPreferences.getString("non_striker name", null);
        String nonStrikerRole = sharedPreferences.getString("non_striker ROLE", null);
        String nonStrikerBatStyle = sharedPreferences.getString("non_striker BS", null);
        String nonStrikerBowlStyle = sharedPreferences.getString("non_striker BOS", null);

        if (nonStrikerName == null || nonStrikerRole == null || nonStrikerBatStyle == null || nonStrikerBowlStyle == null) {
            Toast.makeText(this, "Please select and complete Non-Striker details!", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validate Bowler
        String bowlerName = sharedPreferences.getString("bowler name", null);
        String bowlerRole = sharedPreferences.getString("bowler ROLE", null);
        String bowlerBatStyle = sharedPreferences.getString("bowler BS", null);
        String bowlerBowlStyle = sharedPreferences.getString("bowler BOS", null);

        if (bowlerName == null || bowlerRole == null || bowlerBatStyle == null || bowlerBowlStyle == null) {
            Toast.makeText(this, "Please select and complete Bowler details!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void insertSNsBowl() {
        SharedPreferences sharedPreferences = getSharedPreferences("match_prefs", MODE_PRIVATE);

        // Insert Striker
        String strikerName = sharedPreferences.getString("striker name", "");
        String strikerRole = sharedPreferences.getString("striker ROLE", "");
        String strikerBatStyle = sharedPreferences.getString("striker BS", "");
        String strikerBowlStyle = sharedPreferences.getString("striker BOS", "");


        // Insert Non-Striker
        String nonStrikerName = sharedPreferences.getString("non_striker name", "");
        String nonStrikerRole = sharedPreferences.getString("non_striker ROLE", "");
        String nonStrikerBatStyle = sharedPreferences.getString("non_striker BS", "");
        String nonStrikerBowlStyle = sharedPreferences.getString("non_striker BOS", "");


        // Insert Bowler
        String bowlerName = sharedPreferences.getString("bowler name", "");
        String bowlerRole = sharedPreferences.getString("bowler ROLE", "");
        String bowlerBatStyle = sharedPreferences.getString("bowler BS", "");
        String bowlerBowlStyle = sharedPreferences.getString("bowler BOS", "");

        databaseHelper.insertPlayer(strikerName, strikerRole, strikerBatStyle, strikerBowlStyle, "striker");
        databaseHelper.insertPlayer(nonStrikerName, nonStrikerRole, nonStrikerBatStyle, nonStrikerBowlStyle, "non_striker");
        databaseHelper.insertPlayer(bowlerName, bowlerRole, bowlerBatStyle, bowlerBowlStyle, "bowler");

    }

    private void innings_table(){
        // Get SharedPreferences to retrieve the required values
        SharedPreferences sharedPreferences = getSharedPreferences("match_prefs", Context.MODE_PRIVATE);
        long matchId = sharedPreferences.getLong("match_id", -1); // Get the match ID from SharedPreferences
        long battingTeamId = sharedPreferences.getLong("teamA_id", -1); // Get the batting team ID (team1_id)
        databaseHelper.startFirstInnings(matchId, battingTeamId);
    }

    private void overs_table(){
        SharedPreferences sharedPreferences = getSharedPreferences("match_prefs", MODE_PRIVATE);
        long inningsId = sharedPreferences.getLong("Innings_id", -1);  // Get the innings ID from SharedPreferences
        long bowlerId = sharedPreferences.getLong("bowler_id",-1);
        databaseHelper.insertOver(inningsId, 1, bowlerId,0  );

    }

    private void partnerships_table(){
        SharedPreferences sharedPreferences = getSharedPreferences("match_prefs",MODE_PRIVATE);
        long innings_id = sharedPreferences.getLong("Innings_id",-1);
        long bat1_id = sharedPreferences.getLong("striker_id",-1);
        long bat2_id = sharedPreferences.getLong("non_striker_id",-1);
        databaseHelper.insertPartnership(innings_id,bat1_id, bat2_id, 0, 0);
    }

    private void batsman_table(){
        SharedPreferences sharedPreferences = getSharedPreferences("match_prefs",MODE_PRIVATE);
        long s_id = sharedPreferences.getLong("striker_id", -1);
        long ns_id = sharedPreferences.getLong("non_striker_id", -1);
        long innings_id = sharedPreferences.getLong("Innings_id",-1);
        databaseHelper.initializeBatsmanStats(s_id,innings_id );
        databaseHelper.initializeBatsmanStats(ns_id,innings_id );

    }

    private void bowler_table(){
        SharedPreferences sharedPreferences = getSharedPreferences("match_prefs",MODE_PRIVATE);
        long bowler_id = sharedPreferences.getLong("bowler_id",-1);
        long innings_id = sharedPreferences.getLong("Innings_id",-1);
        databaseHelper.initializeBowlerStats(bowler_id,innings_id );
    }

    private void team_stats_table() {
        SharedPreferences sharedPreferences = getSharedPreferences("match_prefs", Context.MODE_PRIVATE);
        long inningsId = sharedPreferences.getLong("Innings_id", -1); // Get the innings ID from SharedPreferences

        if (inningsId != -1) {
            long teamStatsId = databaseHelper.initializeTeamStats(inningsId);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putLong("teamStatsId", teamStatsId);
            editor.apply();
        } else {
            Log.e("TeamStats", "Failed to initialize team stats due to invalid innings or team ID.");
        }
    }

}
