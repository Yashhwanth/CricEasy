package com.cricketscoringapp.criceasy.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.cricketscoringapp.criceasy.R;
import com.cricketscoringapp.criceasy.adapter.PlayerAdapter;
import com.cricketscoringapp.criceasy.ViewModel.TeamViewModel;

public class TeamsFragment extends Fragment {

    private RecyclerView team1RecyclerView;
    private RecyclerView team2RecyclerView;
    private PlayerAdapter team1Adapter;
    private PlayerAdapter team2Adapter;
    private TeamViewModel teamViewModel;
    private final long matchId;

    public TeamsFragment(long matchId) {
        this.matchId = matchId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_mp_teams, container, false);

        // Initialize RecyclerViews
        team1RecyclerView = view.findViewById(R.id.team1PlayerRecyclerView);
        team2RecyclerView = view.findViewById(R.id.team2PlayerRecyclerView);

        // Set layout managers for the RecyclerViews
        team1RecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        team2RecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize the ViewModel
        teamViewModel = new ViewModelProvider(this).get(TeamViewModel.class);

        // Observe the LiveData from the ViewModel
        teamViewModel.getPlayersForMatch((int) matchId).observe(getViewLifecycleOwner(), playerTeam -> {

            // Check if the adapter for team1 is initialized
            if (team1Adapter != null) {
                // Update the players in the adapter for team 1
                team1Adapter.updatePlayers(playerTeam.getTeam1Players());
            } else {
                // Initialize the adapter and set it for team1
                team1Adapter = new PlayerAdapter(playerTeam.getTeam1Players());
                team1RecyclerView.setAdapter(team1Adapter);
            }

            // Check if the adapter for team2 is initialized
            if (team2Adapter != null) {
                // Update the players in the adapter for team 2
                team2Adapter.updatePlayers(playerTeam.getTeam2Players());
            } else {
                // Initialize the adapter and set it for team2
                team2Adapter = new PlayerAdapter(playerTeam.getTeam2Players());
                team2RecyclerView.setAdapter(team2Adapter);
            }
        });

        return view;
    }
}
