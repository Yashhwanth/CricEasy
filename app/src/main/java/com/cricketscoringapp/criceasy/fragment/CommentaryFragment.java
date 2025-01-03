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

import static android.content.ContentValues.TAG;

import com.cricketscoringapp.criceasy.Database.DatabaseHelper;
import com.cricketscoringapp.criceasy.R;
import com.cricketscoringapp.criceasy.ViewModel.BallDetailsViewModel;
import com.cricketscoringapp.criceasy.ViewModel.TeamViewModel;
import com.cricketscoringapp.criceasy.adapter.BallDetailsAdapter;
import com.cricketscoringapp.criceasy.model.BallDetails;

import java.util.ArrayList;
import java.util.List;

public class CommentaryFragment extends Fragment {
    private SharedPreferences sharedPreferences;
    private DatabaseHelper databaseHelper;
    private RecyclerView recyclerView;
    private BallDetailsViewModel ballDetailsViewModel;

    private BallDetailsAdapter commentaryAdapter;
    private List<BallDetails> ballDetailsList = new ArrayList<>();
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        sharedPreferences = requireActivity().getSharedPreferences("match_prefs", Context.MODE_PRIVATE);
        databaseHelper = new DatabaseHelper(requireContext());
        // Inflate the view
        View view = inflater.inflate(R.layout.activity_mp_commentary, container, false);

        // Initialize the RecyclerView and Adapter
        recyclerView = view.findViewById(R.id.ballDetailsRecyclerView); // Replace with actual RecyclerView ID
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        commentaryAdapter = new BallDetailsAdapter(ballDetailsList);
        recyclerView.setAdapter(commentaryAdapter);

        ballDetailsViewModel = new ViewModelProvider(this).get(BallDetailsViewModel.class);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "commentary onResume called");
        checkAndRefreshIfNeeded();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) { // Fragment is now visible
            Log.d(TAG, "onHiddenChanged: commentary Fragment is now visible");
            checkAndRefreshIfNeeded();
        } else { // Fragment is now hidden
            Log.d(TAG, "onHiddenChanged: commentary Fragment is now hidden");
        }
    }

    private void checkAndRefreshIfNeeded() {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("match_prefs", Context.MODE_PRIVATE);
        boolean doesRefreshNeeded = sharedPreferences.getBoolean("commentaryPageUpdateNeeded", false);
        if (doesRefreshNeeded) {
            Log.d(TAG, "checkAndRefreshIfNeeded: refresh needed and in the below method");
            updateCommentary();
        } else {
            Log.d(TAG, "checkAndRefreshIfNeeded: no refresh needed");
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isScoreUpdated", false);
        editor.apply();
    }

    public void updateCommentary() {
        Log.d(TAG, "updateCommentary: inside the commentary fragment test method");

        // Get the current innings ID
        long inningsId = sharedPreferences.getLong("currentInningsId", -1);

        // Observe LiveData from ViewModel to get the ball details for the innings
        ballDetailsViewModel.getBallDetailsForInnings(inningsId).observe(getViewLifecycleOwner(), ballDetailsList -> {
            Log.d(TAG, "LiveData observed, updating UI");

            if (ballDetailsList != null) {
                commentaryAdapter.updateBallDetailsList(ballDetailsList);  // Assuming your adapter has a method for updating the list
                // Log the ball details for debugging
                Log.d(TAG, "Ball Details Count: " + ballDetailsList.size());
                for (BallDetails ballDetails : ballDetailsList) {
                    Log.d(TAG, "Ball Details: " + ballDetails.toString());
                }
            } else {
                Log.e(TAG, "Ball details list is null");
            }
        });
    }

}
