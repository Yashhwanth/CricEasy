package com.cricketscoringapp.criceasy;


import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cricketscoringapp.criceasy.Database.DatabaseHelper;

public class liveFragment extends Fragment {
    // Declare the UI elements
    private TextView currentBattingTeamName;
    private TextView currentBattingScore;

    // Batting stats for the current batsman
    private TextView batter1Name;
    private TextView batter1R;
    private TextView batter1B;
    private TextView batter1Fours;
    private TextView batter1Sixes;
    private TextView batter1SR;

    // Batting stats for the current batsman
    private TextView batter2Name;
    private TextView batter2R;
    private TextView batter2B;
    private TextView batter2Fours;
    private TextView batter2Sixes;
    private TextView batter2SR;

    // Bowling stats for the current bowler
    private TextView bowlerName;
    private TextView bowlerO;
    private TextView bowlerM;
    private TextView bowlerR;
    private TextView bowlerW;
    private TextView bowlerECO;

    // Other stats
    private TextView runRate;
    private TextView partnership;

    // SharedPreferences to store and retrieve match data
    private SharedPreferences sharedPreferences;

    private DatabaseHelper databaseHelper;  // Assuming you have a DatabaseHelper class for DB operations

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_mp_summary, container, false);
        // Initialize the views
        currentBattingTeamName = view.findViewById(R.id.tv_batting_team);
        Log.d("LiveFragment", "currentBattingTeamName: " + currentBattingTeamName);
        currentBattingScore = view.findViewById(R.id.tv_batting_score);
        Log.d("LiveFragment", "currentBattingScore: " + currentBattingScore);

        batter1Name = view.findViewById(R.id.tv_batter_1_name);
        batter1R = view.findViewById(R.id.tv_batter_1_runs);
        batter1B = view.findViewById(R.id.tv_batter_1_balls);
        batter1Fours = view.findViewById(R.id.tv_batter_1_fours);
        batter1Sixes = view.findViewById(R.id.tv_batter_1_sixes);
        batter1SR = view.findViewById(R.id.tv_batter_1_sr);

        batter2Name = view.findViewById(R.id.tv_batter_2_name);
        batter2R = view.findViewById(R.id.tv_batter_2_runs);
        batter2B = view.findViewById(R.id.tv_batter_2_balls);
        batter2Fours = view.findViewById(R.id.tv_batter_2_fours);
        batter2Sixes = view.findViewById(R.id.tv_batter_2_sixes);
        batter2SR = view.findViewById(R.id.tv_batter_2_sr);

        bowlerName = view.findViewById(R.id.tv_bowler_name);
        bowlerO = view.findViewById(R.id.tv_bowler_overs);
        bowlerM = view.findViewById(R.id.tv_bowler_maidens);
        bowlerR = view.findViewById(R.id.tv_bowler_runs);
        bowlerW = view.findViewById(R.id.tv_bowler_wickets);
        bowlerECO = view.findViewById(R.id.tv_bowler_economy);

        runRate = view.findViewById(R.id.tv_run_rate);
        partnership = view.findViewById(R.id.tv_partnership_runs);
        // Load match details
        displayMatchResult();
        return view;
    }

    private void loadMatchDetails() {
        // Placeholder method to load match data (you need to replace it with actual logic)
        if (isMatchOngoing()) {
            // Show ongoing match details (current batting team, current batsman stats, etc.)
            currentBattingTeamName.setText("Team 1");
            currentBattingScore.setText("120/3");

            // Batting stats example
            batter1Name.setText("bat1");
            batter1R.setText("45");
            batter1B.setText("35");
            batter1Fours.setText("5");
            batter1Sixes.setText("2");
            batter1SR.setText("128.57");

            // Batting stats example
            batter2Name.setText("bat2");
            batter2R.setText("45");
            batter2B.setText("35");
            batter2Fours.setText("5");
            batter2Sixes.setText("2");
            batter2SR.setText("128.57");


            // Bowling stats example
            bowlerName.setText("bat1");
            bowlerO.setText("5");
            bowlerM.setText("1");
            bowlerR.setText("30");
            bowlerW.setText("2");
            bowlerECO.setText("6.00");

            // Additional stats
            runRate.setText("7.5");
            partnership.setText("50 runs off 35 balls");

        } else {
            // Show match result (when match is completed)
            //displayMatchResult();
        }
    }

    private boolean isMatchOngoing() {
        // Placeholder method to check if the match is ongoing or completed
        return true;  // Example return value
    }

    private void displayMatchResult() {
        // Logic to display match result, both teams' scores, top batsmen, top bowlers
        // Example:
        currentBattingTeamName.setText("Match Completed");
        currentBattingScore.setText("250/8");


        // Add top batsmen and bowlers stats here
    }
}
