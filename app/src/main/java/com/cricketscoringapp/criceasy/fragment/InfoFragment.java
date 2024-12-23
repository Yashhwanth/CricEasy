package com.cricketscoringapp.criceasy.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cricketscoringapp.criceasy.Database.DatabaseHelper;
import com.cricketscoringapp.criceasy.R;

import java.util.Map;

public class InfoFragment extends Fragment {

    private TextView team1TextView, team2TextView, matchTypeTextView, oversTextView, ballTypeTextView, venueTextView, dateTextView;
    private DatabaseHelper databaseHelper;  // Assuming you have a DatabaseHelper class for DB operations

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_mp_info, container, false);

        // Initialize UI elements
        team1TextView = view.findViewById(R.id.teamAEditText);
        team2TextView = view.findViewById(R.id.teamBTextView);
        matchTypeTextView = view.findViewById(R.id.matchTypeValueTextView);
        oversTextView = view.findViewById(R.id.oversValueTextView);
        ballTypeTextView = view.findViewById(R.id.ballTypeValueTextView);
        venueTextView = view.findViewById(R.id.venueValueTextView);
        dateTextView = view.findViewById(R.id.dateValueTextView);

        // Initialize DatabaseHelper
        databaseHelper = new DatabaseHelper(requireContext());

        // Populate data
        populateData();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        populateData(); // Reload match details when fragment resumes
    }

    private void populateData() {
        // Example: Retrieving data from SharedPreferences
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("match_prefs", Context.MODE_PRIVATE);

        String team1 = sharedPreferences.getString("A", "Team 1");
        String team2 = sharedPreferences.getString("B", "Team 2");
        long matchId = sharedPreferences.getLong("match_id", -1);


        if (matchId != -1) {
            // Get match details using the helper function
            Map<String, String> matchDetails = databaseHelper.getMatchDetails(matchId);
            // Update UI with match details
            team1TextView.setText(team1);
            team2TextView.setText(team2);
            matchTypeTextView.setText(matchDetails.get("match_type"));
            oversTextView.setText(matchDetails.get("overs"));
            ballTypeTextView.setText(matchDetails.get("ball_type"));
            venueTextView.setText(matchDetails.get("venue"));
            dateTextView.setText(matchDetails.get("datetime"));
        }
    }
}
