package com.cricketscoringapp.criceasy.fragment;

import static android.content.ContentValues.TAG;

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
import androidx.recyclerview.widget.RecyclerView;

import com.cricketscoringapp.criceasy.R;
import com.cricketscoringapp.criceasy.adapter.PlayerAdapter;
import com.cricketscoringapp.criceasy.ViewModel.TeamViewModel;
import com.cricketscoringapp.criceasy.model.Player;
import com.cricketscoringapp.criceasy.model.PlayerTeam;

import java.util.ArrayList;
import java.util.List;

public class TeamsFragment extends Fragment {
    private static final String TAG = "TeamsFragment";
    private static final String MATCH_ID_KEY = "match_id";
    private RecyclerView team1RecyclerView;
    private RecyclerView team2RecyclerView;
    private PlayerAdapter team1Adapter;
    private PlayerAdapter team2Adapter;
    private TeamViewModel teamViewModel;
    private SharedPreferences sharedPreferences;
    private long matchId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView called");
        View view = inflater.inflate(R.layout.activity_mp_teams, container, false);
        Log.d(TAG, "Layout inflated successfully");
        if (getActivity() != null) {
            sharedPreferences = requireContext().getSharedPreferences("match_prefs", Context.MODE_PRIVATE);
        }
        // Initialize RecyclerViews and TextViews for team names
        team1RecyclerView = view.findViewById(R.id.team1PlayerRecyclerView);
        team2RecyclerView = view.findViewById(R.id.team2PlayerRecyclerView);
        TextView teamATextView = view.findViewById(R.id.team1TextView);
        TextView teamBTextView = view.findViewById(R.id.team2TextView);
        // Set team names in TextViews from SharedPreferences
        String teamAName = sharedPreferences.getString("teamAName", "");
        String teamBName = sharedPreferences.getString("teamBName", "");
        teamATextView.setText(teamAName);
        teamBTextView.setText(teamBName);
        // Set GridLayoutManager for RecyclerViews (4 columns per team)
        team1RecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
        team2RecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
        // Initialize adapters with empty lists
        team1Adapter = new PlayerAdapter(new ArrayList<>());
        team2Adapter = new PlayerAdapter(new ArrayList<>());
        // Set adapters to RecyclerViews
        team1RecyclerView.setAdapter(team1Adapter);
        team2RecyclerView.setAdapter(team2Adapter);
        // Initialize ViewModel
        teamViewModel = new ViewModelProvider(this).get(TeamViewModel.class);
        Log.d(TAG, "onCreateView completed");
        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "teams onResume called");
        refreshTeams();
        Log.d(TAG, "onResume completed");
    }
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) { // Fragment is now visible
            Log.d(TAG, "onHiddenChanged: teams Fragment is now visible");
            checkAndRefreshTeams();
        } else { // Fragment is now hidden
            Log.d(TAG, "onHiddenChanged: teams Fragment is now hidden");
        }
    }
    public void checkAndRefreshTeams(){
        boolean doesUpdateNeeded = sharedPreferences.getBoolean("teamsPageUpdateNeeded", false);
        if(doesUpdateNeeded){
            refreshTeams();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("teamsPageUpdateNeeded", false);
            editor.apply();
        }else Log.d(TAG, "onResume: no new players added");
    }
    public void refreshTeams(){
        matchId = sharedPreferences.getLong("currentMatchId", -1);
        teamViewModel.getPlayersForMatch((int) matchId).observe(getViewLifecycleOwner(), playerTeam -> {
            Log.d(TAG, "LiveData observed, updating UI");
            if (playerTeam != null) {
                List<Player> team1Players = playerTeam.getTeam1Players();
                List<Player> team2Players = playerTeam.getTeam2Players();
                Log.d(TAG, "Team 1 Players Count: " + team1Players.size());
                for (Player player : team1Players) {
                    Log.d(TAG, "Team 1 Player: " + player.toString());
                }
                // Update Team 1 Adapter
                team1Adapter.updatePlayers(team1Players);
                Log.d(TAG, "Team 2 Players Count: " + team2Players.size());
                for (Player player : team2Players) {
                    Log.d(TAG, "Team 2 Player: " + player.toString());
                }
                team2Adapter.updatePlayers(team2Players);
            } else {
                Log.e(TAG, "PlayerTeam object is null");
            }
        });
    }
}