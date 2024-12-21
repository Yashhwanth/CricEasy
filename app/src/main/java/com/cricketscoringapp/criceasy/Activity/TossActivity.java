package com.cricketscoringapp.criceasy.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cricketscoringapp.criceasy.Database.DatabaseHelper;
import com.cricketscoringapp.criceasy.R;

import java.util.Random;

public class TossActivity extends AppCompatActivity {
    private RadioGroup teamCallingTossGroup, tossWinnerGroup, tossDecisionGroup;
    private RadioButton teamACallingButton, teamBCallingButton;
    private RadioButton teamAWinnerButton, teamBWinnerButton;
    private RadioButton decisionBatButton, decisionBowlButton;
    private ImageView coinImage;
    private Random random;
    private boolean isFlipping = false;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toss); // Make sure this layout file exists
        updateCurrentActivityInPreferences();

        Button playButton = findViewById(R.id.btn_play);

        // Initialize RadioGroups
        teamCallingTossGroup = findViewById(R.id.teamCallingTossGroup);
        tossWinnerGroup = findViewById(R.id.tossWinnerGroup);
        tossDecisionGroup = findViewById(R.id.tossDecisionGroup);

        // Initialize RadioButtons
        teamACallingButton = findViewById(R.id.radioButton11);
        teamBCallingButton = findViewById(R.id.radioButton12);
        teamAWinnerButton = findViewById(R.id.radioButton2);
        teamBWinnerButton = findViewById(R.id.radioButton3);
        decisionBatButton = findViewById(R.id.radioButton4);
        decisionBowlButton = findViewById(R.id.radioButton5);

        // Retrieve team names from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("match_prefs", MODE_PRIVATE);
        String teamAName = sharedPreferences.getString("A", "Team A");
        String teamBName = sharedPreferences.getString("B", "Team B");



        // Set text for RadioButtons dynamically
        teamACallingButton.setText(teamAName);
        teamBCallingButton.setText(teamBName);
        teamAWinnerButton.setText(teamAName);
        teamBWinnerButton.setText(teamBName);

        coinImage = findViewById(R.id.img_coin);
        random = new Random();


        playButton.setOnClickListener(view ->{
            letsplay();
        });


        // Set the click listener to trigger the coin flip
        coinImage.setOnClickListener(view -> {
            if (!isFlipping) {
                flipCoin();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Update current activity in SharedPreferences
        updateCurrentActivityInPreferences();
    }
    public void letsplay() {

        // Get selected RadioButton IDs
        int selectedTeamCallingId = teamCallingTossGroup.getCheckedRadioButtonId();
        int selectedTossWinnerId = tossWinnerGroup.getCheckedRadioButtonId();
        int selectedDecisionId = tossDecisionGroup.getCheckedRadioButtonId();

        // Validate that all options are selected
        if (selectedTeamCallingId == -1 || selectedTossWinnerId == -1 || selectedDecisionId == -1) {
            Toast.makeText(this, "Please make all selections before proceeding.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get the selected RadioButtons
        RadioButton selectedTeamCalling = findViewById(selectedTeamCallingId);
        RadioButton selectedTossWinner = findViewById(selectedTossWinnerId);
        RadioButton selectedDecision = findViewById(selectedDecisionId);

        // Retrieve selected values based on button text
        String teamCalling = selectedTeamCalling.getText().toString();
        String tossWinner = selectedTossWinner.getText().toString();
        String tossDecision = selectedDecision.getText().toString();


        // Retrieve the toss ID from SharedPreferences
        SharedPreferences prefs = getSharedPreferences("match_prefs", Context.MODE_PRIVATE);
        long tossId = prefs.getLong("toss_id", -1); // Default to -1 if no toss_id is found

        // Create an instance of the DatabaseHelper
        DatabaseHelper databaseHelper = new DatabaseHelper(this);

        // Call the saveOrUpdateTossDetails method
        databaseHelper.saveOrUpdateTossDetails(this, tossId, teamCalling, tossWinner, tossDecision);



        // Now determine the batting team based on the toss details
        int battingTeam = determineBattingTeam(tossWinner, tossDecision);

        // Retrieve team IDs from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("match_prefs", Context.MODE_PRIVATE);
        long teamAId = sharedPreferences.getLong("teamA_id", -1);
        long teamBId = sharedPreferences.getLong("teamB_id", -1);

        // Update team1_id and team2_id based on the determined batting team
        long battingTeamId = (battingTeam == 1) ? teamAId : teamBId;
        long bowlingTeamId = (battingTeam == 1) ? teamBId : teamAId;

        // Save the batting and bowling team IDs (update team1_id and team2_id accordingly)
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong("teamA_id", battingTeamId); // Batting team is always team1
        editor.putLong("teamB_id", bowlingTeamId); // Bowling team is always team2
        editor.apply();


        // Navigate back to MatchInfoActivity
        Intent intent = new Intent(this, SelectingSrNsBowActivity.class);
        startActivity(intent);
        //finish(); // Close the current activity
    }

    public void back(View view) {
        // Navigate back to MatchInfoActivity
//        Intent intent = new Intent(this, TeamCreationActivity.class);
//        startActivity(intent);
        //finish(); // Close the current activity
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
        SharedPreferences sharedPreferences = getSharedPreferences("match_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("current_activity", getClass().getSimpleName()); // Store the current activity name
        editor.apply(); // Save changes asynchronously
    }

    private int determineBattingTeam(String tossWinner, String tossDecision) {
        // Retrieve team names from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("match_prefs", MODE_PRIVATE);
        String teamAName = sharedPreferences.getString("A", null);
        String teamBName = sharedPreferences.getString("B", null);

        // Determine the batting team based on the toss winner and decision
        if (tossWinner.equals(teamAName)) {
            if (tossDecision.equals("Bat")) {
                return 1; // Team A is the batting team
            } else {
                return 2; // Team B is the batting team
            }
        } else if (tossWinner.equals(teamBName)) {
            if (tossDecision.equals("Bat")) {
                return 2; // Team B is the batting team
            } else {
                return 1; // Team A is the batting team
            }
        }
        return -1; // In case of an error or invalid data
    }

}
