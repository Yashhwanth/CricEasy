package com.cricketscoringapp.criceasy;


import android.content.Context;
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

import java.util.Map;

public class InfoFragment extends Fragment {

    private TextView team1TextView, team2TextView, matchTypeTextView, oversTextView, ballTypeTextView, venueTextView, dateTextView;
    private DatabaseHelper databaseHelper;  // Assuming you have a DatabaseHelper class for DB operations

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_mp_info, container, false);

        // Initialize UI elements
        team1TextView = view.findViewById(R.id.team1);
        team2TextView = view.findViewById(R.id.team2);
        matchTypeTextView = view.findViewById(R.id.matchtypeValue);
        oversTextView = view.findViewById(R.id.oversValue);
        ballTypeTextView = view.findViewById(R.id.balltypeValue);
        venueTextView = view.findViewById(R.id.venueValue);
        dateTextView = view.findViewById(R.id.dateValue);

        // Initialize DatabaseHelper
        databaseHelper = new DatabaseHelper(requireContext());

        // Populate data
        populateData();

        return view;
    }

    private void populateData() {
        // Example: Retrieving data from SharedPreferences
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("match_prefs", Context.MODE_PRIVATE);

        String team1 = sharedPreferences.getString("A", "Team 1");
        String team2 = sharedPreferences.getString("B", "Team 2");
        long matchId = sharedPreferences.getLong("match_id", -1);
        Log.d("hiiiiiiii", "populateData: ");
        // Update TextViews
//        team1TextView.setText(team1);
//        team2TextView.setText(team2);

        if (matchId != -1) {
            // Get match details using the helper function
            Map<String, String> matchDetails = databaseHelper.getMatchDetails(matchId);
            Log.d("hiiiiiiiibiiiiii", "populateData: " + matchDetails);
            // Update UI with match details
            team1TextView.setText(team1);
            team2TextView.setText(team2);

            matchTypeTextView.setText(matchDetails.get("match_type"));
            oversTextView.setText(matchDetails.get("overs"));
            ballTypeTextView.setText(matchDetails.get("ball_type"));
            venueTextView.setText(matchDetails.get("venue"));
            dateTextView.setText(matchDetails.get("datetime"));
        }else {
            Log.d("hiiiiiiiibiiiiiihiiiiii", "populateData: " );
            // Handle the case where matchId is invalid or not available
            team1TextView.setText(team1);
            team2TextView.setText(team2);

            matchTypeTextView.setText("N/A");
            oversTextView.setText("N/A");
            ballTypeTextView.setText("N/A");
            venueTextView.setText("N/A");
            dateTextView.setText("N/A");
        }
    }
}
