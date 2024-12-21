package com.cricketscoringapp.criceasy.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.cricketscoringapp.criceasy.Database.DatabaseHelper;
import com.cricketscoringapp.criceasy.R;

import java.util.Random;

public class TossActivity extends AppCompatActivity {
    private final String SHARED_PREFERENCES = "match_prefs";
    private final String TEAM_A_NAME = "teamAName";
    private final String TEAM_B_NAME = "teamBName";
    private final String TOSS_ID_KEY = "tossId";
    private SharedPreferences sharedPreferences;
    private RadioGroup tossCallingTeamRadioGroup, tossWinningTeamRadioGroup, tossWonTeamChooseToRadioGroup;
    private ImageView coinImage;
    private Random random;
    private boolean isFlipping = false;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_toss); // Make sure this layout file exists
        updateCurrentActivityInPreferences();

        Button playButton = findViewById(R.id.letsPlayButton);
        Button needHelpButton = findViewById(R.id.needHelpButton);
        // Initialize RadioGroups
        tossCallingTeamRadioGroup = findViewById(R.id.teamCallingTossRadioGroup);
        tossWinningTeamRadioGroup = findViewById(R.id.tossWinnerGroup);
        tossWonTeamChooseToRadioGroup = findViewById(R.id.tossDecisionGroup);

        // Initialize RadioButtons
        RadioButton tossCallByTeamARadioButton = findViewById(R.id.tossCallTeamARadioButton);
        RadioButton tossCallByTeamBRadioButton = findViewById(R.id.tossCallTeamBRadioButton);
        RadioButton tossWinByTeamARadioButton = findViewById(R.id.tossWinTeamARadioButton);
        RadioButton tossWinBYTeamBRadioButton = findViewById(R.id.tossWinTeamBRadioButton);


        coinImage = findViewById(R.id.img_coin);
        random = new Random();

        // Retrieve team names from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        String TEAM_DEFAULT_NAME = "";
        String teamAName = sharedPreferences.getString(TEAM_A_NAME, TEAM_DEFAULT_NAME);
        String teamBName = sharedPreferences.getString(TEAM_B_NAME, TEAM_DEFAULT_NAME);

        // Set text for RadioButtons dynamically
        tossCallByTeamARadioButton.setText(teamAName);
        tossCallByTeamBRadioButton.setText(teamBName);
        tossWinByTeamARadioButton.setText(teamAName);
        tossWinBYTeamBRadioButton.setText(teamBName);

        playButton.setOnClickListener(view -> letsPlay());
        needHelpButton.setOnClickListener(view -> back());
        coinImage.setOnClickListener(view -> {
            if (!isFlipping) {
                flipCoin();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateCurrentActivityInPreferences();
    }
    public void letsPlay() {
        // Retrieve the toss ID from SharedPreferences
        sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        String CURRENT_MATCH_ID = "currentMatchId";
        long currentMatchId = sharedPreferences.getLong(CURRENT_MATCH_ID, -1L);
        long tossId = sharedPreferences.getLong(TOSS_ID_KEY, -1); // Default to -1 if no toss_id is found

        // Get selected RadioButton IDs
        int tossCallingTeamRadioButtonId = tossCallingTeamRadioGroup.getCheckedRadioButtonId();
        int tossWinningTeamRadioButtonId = tossWinningTeamRadioGroup.getCheckedRadioButtonId();
        int tossWonTeamChooseToRadioButton = tossWonTeamChooseToRadioGroup.getCheckedRadioButtonId();

        // Validate that all options are selected
        if (tossCallingTeamRadioButtonId == -1 || tossWinningTeamRadioButtonId == -1 || tossWonTeamChooseToRadioButton == -1) {
            Toast.makeText(this, "Please make all selections before proceeding.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get the selected RadioButtons
        RadioButton selectedTeamCalling = findViewById(tossCallingTeamRadioButtonId);
        RadioButton selectedTossWinner = findViewById(tossWinningTeamRadioButtonId);
        RadioButton selectedDecision = findViewById(tossWonTeamChooseToRadioButton);

        // Retrieve selected values based on button text
        String teamCalling = selectedTeamCalling.getText().toString();
        String tossWinner = selectedTossWinner.getText().toString();
        String tossDecision = selectedDecision.getText().toString();

        // Perform database operations in a focused try-catch block
        try (DatabaseHelper databaseHelper = new DatabaseHelper(this)) {
            // Call the saveOrUpdateTossDetails method
            tossId = databaseHelper.saveOrUpdateTossDetails(currentMatchId, tossId, teamCalling, tossWinner, tossDecision);
            updateTossIdInSharedPreferences(tossId);

            // Determine the batting team based on the toss details
            int battingTeam = determineBattingTeam(tossWinner, tossDecision);
            updateBattingAndBowlingTeamsInSharedPreferences(battingTeam);
        } catch (Exception e) {
            Log.e("letsPlay", "Error occurred during database operations: " + e.getMessage());
            Toast.makeText(this, "An error occurred. Please try again.", Toast.LENGTH_SHORT).show();
            return; // Exit the method if there's an error
        }

        // Navigate to the next activity
        Intent intent = new Intent(this, SelectingSrNsBowActivity.class);
        startActivity(intent);
    }

    public void back() {
        Intent intent = new Intent(this, TeamCreationActivity.class);
        startActivity(intent);
    }
    private void flipCoin() {
        isFlipping = true;
        // Start the coin flip animation
        Animation flipAnimation = AnimationUtils.loadAnimation(this, R.anim.coin_flip_anim);
        coinImage.startAnimation(flipAnimation);
        // Delay to simulate the flipping time
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            // Randomly determine the result (Heads or Tails)
            boolean isHeads = random.nextBoolean();
            // Update the ImageView based on the result
            if (isHeads) {
                coinImage.setImageResource(R.drawable.heads);
            } else {
                coinImage.setImageResource(R.drawable.tails);
            }
            isFlipping = false; // Reset flipping state
        }, 800); // Animation duration in milliseconds
    }
    // Method to update SharedPreferences with the current activity
    private void updateCurrentActivityInPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String CURRENT_ACTIVITY = "currentActivity";
        editor.putString(CURRENT_ACTIVITY, getClass().getSimpleName());
        editor.apply();
    }
    private int determineBattingTeam(String tossWinner, String tossDecision) {
        // Retrieve team names from SharedPreferences
        String teamAName = sharedPreferences.getString(TEAM_A_NAME, null);
        String teamBName = sharedPreferences.getString(TEAM_B_NAME, null);
        // Handle null values for team names
        if (teamAName == null || teamBName == null) {
            Log.e("determineBattingTeam", "One or both team names are null.");
            return -1;
        }
        // Determine the batting team based on the toss winner and decision
        if (tossWinner.equals(teamAName) || tossWinner.equals(teamBName)) {
            boolean isTeamBatting = tossDecision.equals("Bat");
            return tossWinner.equals(teamAName) ? (isTeamBatting ? 1 : 2) : (isTeamBatting ? 2 : 1);
        }
        Log.e("determineBattingTeam", "Toss winner does not match any team.");
        return -1; // Invalid toss winner
    }
    public void updateBattingAndBowlingTeamsInSharedPreferences(int battingTeam) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        // Determine the batting and bowling teams based on toss decision
        String TEAM_A_ID = "teamAId";
        long teamAId = sharedPreferences.getLong(TEAM_A_ID, -1);
        String TEAM_B_ID = "teamBId";
        long teamBId = sharedPreferences.getLong(TEAM_B_ID, -1);
        long battingTeamId = (battingTeam == 1) ? teamAId : teamBId;
        long bowlingTeamId = (battingTeam == 1) ? teamBId : teamAId;
        String BATTING_TEAM_ID = "battingTeamId";
        editor.putLong(BATTING_TEAM_ID, battingTeamId); // Batting team is always team1
        String BOWLING_TEAM_ID = "bowlingTeamId";
        editor.putLong(BOWLING_TEAM_ID, bowlingTeamId); // Bowling team is always team2
        editor.apply();
    }
    private void updateTossIdInSharedPreferences(long tossId){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(TOSS_ID_KEY, tossId);
        editor.apply();
    }

}
