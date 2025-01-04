package com.cricketscoringapp.criceasy.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cricketscoringapp.criceasy.R;
import com.cricketscoringapp.criceasy.ViewModel.BatsmanDetailsViewModel;
import com.cricketscoringapp.criceasy.adapter.Batter1Adapter;
import com.cricketscoringapp.criceasy.adapter.Bowler1Adapter;
import com.cricketscoringapp.criceasy.ViewModel.TeamViewModel;
import com.cricketscoringapp.criceasy.model.Batsman;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScoreCardFragment extends Fragment {

    private RecyclerView batterRecyclerView, bowlerRecyclerView;
    private Batter1Adapter batterAdapter;
    private Bowler1Adapter bowlerAdapter;

    private BatsmanDetailsViewModel batsmanDetailsViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for the fragment
        View view = inflater.inflate(R.layout.activity_scorecard, container, false);

        // Initialize RecyclerViews
        batterRecyclerView = view.findViewById(R.id.rv_batter_stats_team1);
        bowlerRecyclerView = view.findViewById(R.id.rv_bowler_stats_team1);

        batterRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        bowlerRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize the ViewModel
        batsmanDetailsViewModel = new ViewModelProvider(requireActivity()).get(BatsmanDetailsViewModel.class);

        // Set up the adapters
        batterAdapter = new Batter1Adapter(requireContext(), new ArrayList<>());
        //bowlerAdapter = new Bowler1Adapter();

        batterRecyclerView.setAdapter(batterAdapter);
        //bowlerRecyclerView.setAdapter(bowlerAdapter);

        // Observe players for the match
        observePlayers();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("TAG", "scorecard onResume called");
        checkAndRefreshIfNeeded();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) { // Fragment is now visible
            Log.d("TAG", "onHiddenChanged: scorecard Fragment is now visible");
            checkAndRefreshIfNeeded();
        } else { // Fragment is now hidden
            Log.d("TAG", "onHiddenChanged: scorecard Fragment is now hidden");
        }
    }

    private void checkAndRefreshIfNeeded() {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("match_prefs", Context.MODE_PRIVATE);
        boolean doesRefreshNeeded = sharedPreferences.getBoolean("scorecardPageUpdateNeeded", false);
        if (doesRefreshNeeded) {
            Log.d("TAG", "checkAndRefreshIfNeeded: refresh needed and in the below method");
            observePlayers();
        } else {
            Log.d("TAG", "checkAndRefreshIfNeeded: no refresh needed");
        }
        // Reset flag to prevent continuous refreshes
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("scorecardPageUpdateNeeded", false);
        editor.apply();
    }

    public void observePlayers() {
        // Assuming matchId is fetched dynamically, replace this placeholder
        long matchId = 1; // Replace with the actual matchId
        // Observe batter details for the given innings
        batsmanDetailsViewModel.getBatterDetailsForInnings(matchId)
                .observe(getViewLifecycleOwner(), batterDetailsList -> {
                    if (batterDetailsList != null && !batterDetailsList.isEmpty()) {
                        // Update the adapter with the fetched batter details
                        batterAdapter.updateBatterStats(batterDetailsList);
                    } else {
                        // Pass an empty list if no data is found
                        batterAdapter.updateBatterStats(Collections.emptyList());
                    }
                });
    }


}
