package com.cricketscoringapp.criceasy.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cricketscoringapp.criceasy.R;
import com.cricketscoringapp.criceasy.adapter.PlayerAdapter;
import com.cricketscoringapp.criceasy.ViewModel.TeamViewModel;
import com.cricketscoringapp.criceasy.model.Player;

import java.util.List;

public class TeamsFragment extends Fragment {
    private static final String TAG = "TeamsFragment";

    private RecyclerView team1RecyclerView;
    private RecyclerView team2RecyclerView;
    private PlayerAdapter team1Adapter;
    private PlayerAdapter team2Adapter;
    private TeamViewModel teamViewModel;
    private SharedPreferences sharedPreferences;
    private final long matchId;

    public TeamsFragment(long matchId) {
        this.matchId = matchId;
        Log.d(TAG, "TeamsFragment initialized with matchId: " + matchId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView called");

        // Inflate the layout for the fragment
        View view = inflater.inflate(R.layout.activity_mp_teams, container, false);
        Log.d(TAG, "Layout inflated successfully");
        // Inside your Fragment's onCreateView or onViewCreated method
        if (getActivity() != null) {
            sharedPreferences = getActivity().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        }
        String teamAName = sharedPreferences.getString("teamAName", "");
        String teamBName = sharedPreferences.getString("teamBName", "");
        TextView teamATextView = view.findViewById(R.id.team1TextView);
        TextView teamBTextView = view.findViewById(R.id.team2TextView);
        teamATextView.setText(teamAName);
        teamBTextView.setText(teamBName);
        // Initialize RecyclerViews
        team1RecyclerView = view.findViewById(R.id.team1PlayerRecyclerView);
        team2RecyclerView = view.findViewById(R.id.team2PlayerRecyclerView);
        Log.d(TAG, "RecyclerViews initialized");

        // For team 1 RecyclerView, set the GridLayoutManager with the desired number of columns
        team1RecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4)); // 4 columns
        // For team 2 RecyclerView, set the GridLayoutManager with the desired number of columns
        team2RecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4)); // 4 columns

        Log.d(TAG, "GridLayoutManagers set for RecyclerViews");

        Log.d(TAG, "Layout managers set for RecyclerViews");

        // Initialize the ViewModel
        teamViewModel = new ViewModelProvider(this).get(TeamViewModel.class);
        Log.d(TAG, "TeamViewModel initialized");

        // Observe the LiveData from the ViewModel
        teamViewModel.getPlayersForMatch((int) matchId).observe(getViewLifecycleOwner(), playerTeam -> {
            Log.d(TAG, "LiveData observed, updating UI");

            // Handle team 1 players
            if (playerTeam != null) {
                List<Player> team1Players = playerTeam.getTeam1Players();
                List<Player> team2Players = playerTeam.getTeam2Players();

                Log.d(TAG, "Team 1 Players Count: " + team1Players.size());
                for (Player player : team1Players) {
                    Log.d(TAG, "Team 1 Player: " + player.toString());
                }

                if (team1Adapter != null) {
                    Log.d(TAG, "Updating Team 1 Adapter");
                    team1Adapter.updatePlayers(team1Players);
                } else {
                    Log.d(TAG, "Initializing Team 1 Adapter");
                    team1Adapter = new PlayerAdapter(team1Players);
                    team1RecyclerView.setAdapter(team1Adapter);
                }

                // Handle team 2 players
                Log.d(TAG, "Team 2 Players Count: " + team2Players.size());
                for (Player player : team2Players) {
                    Log.d(TAG, "Team 2 Player: " + player.toString());
                }

                if (team2Adapter != null) {
                    Log.d(TAG, "Updating Team 2 Adapter");
                    team2Adapter.updatePlayers(team2Players);
                } else {
                    Log.d(TAG, "Initializing Team 2 Adapter");
                    team2Adapter = new PlayerAdapter(team2Players);
                    team2RecyclerView.setAdapter(team2Adapter);
                }
            } else {
                Log.e(TAG, "PlayerTeam object is null");
            }
        });

        Log.d(TAG, "onCreateView completed");
        return view;
    }
}
