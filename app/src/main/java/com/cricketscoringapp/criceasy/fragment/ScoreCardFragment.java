package com.cricketscoringapp.criceasy.fragment;

import static android.content.ContentValues.TAG;

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
import com.cricketscoringapp.criceasy.ViewModel.BowlerDetailsViewModel;
import com.cricketscoringapp.criceasy.adapter.Batter1Adapter;
import com.cricketscoringapp.criceasy.adapter.Bowler1Adapter;

import java.util.ArrayList;
import java.util.Collections;

public class ScoreCardFragment extends Fragment {

    private RecyclerView batter1RecyclerView, bowler1RecyclerView, batter2RecyclerView, bowler2RecyclerView;
    private Batter1Adapter batterAdapter, batter2Adapter;
    private Bowler1Adapter bowlerAdapter, bowler2Adapter;
    private BatsmanDetailsViewModel batsmanDetailsViewModel, batsmanDetailsViewModel2;
    private BowlerDetailsViewModel bowlerDetailsViewModel, bowlerDetailsViewModel2;
    private SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for the fragment
        View view = inflater.inflate(R.layout.activity_scorecard, container, false);

        // Initialize SharedPreferences
        sharedPreferences = requireContext().getSharedPreferences("match_prefs", Context.MODE_PRIVATE);

        // Initialize RecyclerViews and set default for Team 1
        batter1RecyclerView = view.findViewById(R.id.rv_batter_stats_team1);
        bowler1RecyclerView = view.findViewById(R.id.rv_bowler_stats_team1);
        batter1RecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        bowler1RecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        // Initialize the ViewModel
        batsmanDetailsViewModel = new ViewModelProvider(requireActivity()).get(BatsmanDetailsViewModel.class);
        bowlerDetailsViewModel = new ViewModelProvider(requireActivity()).get(BowlerDetailsViewModel.class);
        // Set up the adapters for Team 1 initially
        batterAdapter = new Batter1Adapter(requireContext(), new ArrayList<>());
        bowlerAdapter = new Bowler1Adapter(requireContext(), new ArrayList<>());
        batter1RecyclerView.setAdapter(batterAdapter);
        bowler1RecyclerView.setAdapter(bowlerAdapter);

        batter2RecyclerView = view.findViewById(R.id.rv_batter_stats_team2);
        bowler2RecyclerView = view.findViewById(R.id.rv_bowler_stats_team2);
        batter2RecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        bowler2RecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        batsmanDetailsViewModel2 = new ViewModelProvider(requireActivity()).get(BatsmanDetailsViewModel.class);
        bowlerDetailsViewModel2 = new ViewModelProvider(requireActivity()).get(BowlerDetailsViewModel.class);
        batter2Adapter = new Batter1Adapter(requireContext(), new ArrayList<>());
        bowler2Adapter = new Bowler1Adapter(requireContext(), new ArrayList<>());

        batter2RecyclerView.setAdapter(batter2Adapter);
        bowler2RecyclerView.setAdapter(bowler2Adapter);


        // Return the view
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("TAG", "scorecard onResume called");
        long currentInningsId = sharedPreferences.getLong("currentInningsId", -1);
        String currentInnings = sharedPreferences.getString("currentInningsNumber", null);
        if (currentInnings != null && currentInnings.equals("first")) {
            populateDataForFirstInnings(currentInningsId);
        } else {
            populateDataForFirstInnings(currentInningsId - 1);
            populateDataForSecondInnings(currentInningsId);
        }
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
        sharedPreferences = requireContext().getSharedPreferences("match_prefs", Context.MODE_PRIVATE);
        boolean doesRefreshNeeded = sharedPreferences.getBoolean("scorecardPageUpdateNeeded", false);
        if (doesRefreshNeeded) {
            Log.d("TAG", "checkAndRefreshIfNeeded: refresh needed and in the below method");
            observePlayers();
        } else {
            Log.d("TAG", "checkAndRefreshIfNeeded: no refresh needed");
        }
    }

    public void populateDataForFirstInnings(long inningsId) {
        Log.d(TAG, "populateDataForFirstInnings: popu;ating first innings data with innings id" + inningsId);
        // Observe batter details for the first innings
        batsmanDetailsViewModel.getBatterDetailsForTeam1(inningsId)
                .observe(getViewLifecycleOwner(), batterDetailsList -> {
                    if (batterDetailsList != null && !batterDetailsList.isEmpty()) {
                        batterAdapter.updateBatterStats(batterDetailsList);
                    } else {
                        batterAdapter.updateBatterStats(Collections.emptyList());
                    }
                });

        // Observe bowler details for the first innings
        bowlerDetailsViewModel.getBowlerDetailsForTeam1(inningsId)
                .observe(getViewLifecycleOwner(), bowlerDetailsList -> {
                    if (bowlerDetailsList != null && !bowlerDetailsList.isEmpty()) {
                        bowlerAdapter.updateBowlerStats(bowlerDetailsList);
                    } else {
                        bowlerAdapter.updateBowlerStats(Collections.emptyList());
                    }
                });
    }

    public void populateDataForSecondInnings(long inningsId) {
        Log.d(TAG, "populateDataForFirstInnings: popu;ating second innings data with innings id" + inningsId);
        // First, update the stats for Team 1 (First Innings)
        //populateDataForFirstInnings(inningsId - 1);

        // Observe batter details for the second innings
        batsmanDetailsViewModel2.getBatterDetailsForTeam2(inningsId)
                .observe(getViewLifecycleOwner(), batterDetailsList -> {
                    if (batterDetailsList != null && !batterDetailsList.isEmpty()) {
                        batter2Adapter.updateBatterStats(batterDetailsList);
                    } else {
                        batter2Adapter.updateBatterStats(Collections.emptyList());
                    }
                });

        // Observe bowler details for the second innings
        bowlerDetailsViewModel2.getBowlerDetailsForTeam2(inningsId)
                .observe(getViewLifecycleOwner(), bowlerDetailsList -> {
                    if (bowlerDetailsList != null && !bowlerDetailsList.isEmpty()) {
                        bowler2Adapter.updateBowlerStats(bowlerDetailsList);
                    } else {
                        bowler2Adapter.updateBowlerStats(Collections.emptyList());
                    }
                });
    }
    public void observePlayers() {
        // Get the current innings ID and the innings number (first or second)
        long inningsId = sharedPreferences.getLong("currentInningsId", -1);
        String currentInnings = sharedPreferences.getString("currentInningsNumber", "first"); // Default to "first" if null
        if("first".equals(currentInnings)){
            Log.d(TAG, "observePlayers: jiifirst");
            populateDataForFirstInnings(inningsId);
        }else {
            Log.d(TAG, "observePlayers: jiisecond");
            populateDataForSecondInnings(inningsId);
        }
        // Observe batter details based on the current innings
//        batsmanDetailsViewModel.getBatterDetailsForInnings(inningsId)
//                .observe(getViewLifecycleOwner(), batterDetailsList -> {
//                    if (batterDetailsList != null && !batterDetailsList.isEmpty()) {
//                        if (currentInnings.equals("first")) {
//                            batterAdapter.updateBatterStats(batterDetailsList);
//                        } else {
//                            batter2Adapter.updateBatterStats(batterDetailsList);
//                        }
//                    } else {
//                        if (currentInnings.equals("first")) {
//                            batterAdapter.updateBatterStats(Collections.emptyList());
//                        } else {
//                            batter2Adapter.updateBatterStats(Collections.emptyList());
//                        }
//                    }
//                });
//
//        // Observe bowler details based on the current innings
//        bowlerDetailsViewModel.getBowlerDetailsForInnings(inningsId)
//                .observe(getViewLifecycleOwner(), bowlerDetailsList -> {
//                    if (bowlerDetailsList != null && !bowlerDetailsList.isEmpty()) {
//                        if (currentInnings.equals("first")) {
//                            bowlerAdapter.updateBowlerStats(bowlerDetailsList);
//                        } else {
//                            bowler2Adapter.updateBowlerStats(bowlerDetailsList);
//                        }
//                    } else {
//                        if (currentInnings.equals("first")) {
//                            bowlerAdapter.updateBowlerStats(Collections.emptyList());
//                        } else {
//                            bowler2Adapter.updateBowlerStats(Collections.emptyList());
//                        }
//                    }
//                });
    }

}
