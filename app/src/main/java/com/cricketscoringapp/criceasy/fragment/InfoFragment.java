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
        View view = inflater.inflate(R.layout.activity_mp_info, container, false);
        // Initialize UI element
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
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("match_prefs", Context.MODE_PRIVATE);
        String team1 = sharedPreferences.getString("teamAName", "Team 1");
        String team2 = sharedPreferences.getString("teamBName", "Team 2");
        long matchId = sharedPreferences.getLong("currentMatchId", -1);
        if (matchId != -1) {
            Map<String, String> matchDetails = databaseHelper.getMatchDetails(matchId);
            team1TextView.setText(team1);
            team2TextView.setText(team2);
            matchTypeTextView.setText(matchDetails.get("matchType"));
            oversTextView.setText(matchDetails.get("overs"));
            ballTypeTextView.setText(matchDetails.get("ballType"));
            venueTextView.setText(matchDetails.get("venue"));
            dateTextView.setText(matchDetails.get("dateTime"));
        }
    }
}
