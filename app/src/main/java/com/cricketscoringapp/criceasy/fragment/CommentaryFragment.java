package com.cricketscoringapp.criceasy.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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
    private long currentInningsId;
    private String currentInningsNumber;
    private final List<BallDetails> ballDetailsList = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        sharedPreferences = requireActivity().getSharedPreferences("match_prefs", Context.MODE_PRIVATE);
        databaseHelper = new DatabaseHelper(requireContext());

        // Inflate the view
        View view = inflater.inflate(R.layout.activity_mp_commentary, container, false);
        currentInningsId = sharedPreferences.getLong("currentInningsId", -1);
        currentInningsNumber = sharedPreferences.getString("currentInningsNumber", null);

        // Initialize the RecyclerView and Adapter
        recyclerView = view.findViewById(R.id.ballDetailsRecyclerView); // Replace with actual RecyclerView ID
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //layoutManager.setReverseLayout(true);
        commentaryAdapter = new BallDetailsAdapter(ballDetailsList, sharedPreferences);
        recyclerView.setAdapter(commentaryAdapter);

        ballDetailsViewModel = new ViewModelProvider(this).get(BallDetailsViewModel.class);

        Button firstInningsCommButton = view.findViewById(R.id.firstInningsCommentaryButton);
        firstInningsCommButton.setText(sharedPreferences.getString("teamAName", "1st Innings"));
        Button secondInningsCommButton = view.findViewById(R.id.secondInningsCommentaryButton);
        secondInningsCommButton.setText(sharedPreferences.getString("teamBName", "2nd Innings"));

        firstInningsCommButton.setOnClickListener(v -> {
            if ("first".equals(currentInningsNumber)) {
                loadCommentaryForFirstInnings(currentInningsId); // First innings, load first innings commentary
            } else {
                loadCommentaryForFirstInnings(currentInningsId - 1); // Second innings, load first innings commentary by reducing inningsId by 1
            }
        });
        secondInningsCommButton.setOnClickListener(v -> {
            if ("first".equals(currentInningsNumber)) {
                loadCommentaryForSecondInnings(currentInningsId + 1); // First innings, load second innings commentary by increasing inningsId by 1
            } else {
                loadCommentaryForSecondInnings(currentInningsId); // Second innings, load second innings commentary
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "commentary onResume called");
        loadCommentaryForFirstInnings(currentInningsId);
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
        sharedPreferences = requireContext().getSharedPreferences("match_prefs", Context.MODE_PRIVATE);
        boolean doesRefreshNeeded = sharedPreferences.getBoolean("commentaryPageUpdateNeeded", false);
        if (doesRefreshNeeded) {
            Log.d(TAG, "checkAndRefreshIfNeeded: refresh needed and in the below method");
            updateCommentary();
        } else {
            Log.d(TAG, "checkAndRefreshIfNeeded: no refresh needed");
        }

    }
    public void updateCommentary() {
        Log.d(TAG, "updateCommentary: inside the commentary fragment test method");
        long inningsId = sharedPreferences.getLong("currentInningsId", -1);
        currentInningsNumber = sharedPreferences.getString("currentInningsNumber", "first");
        if("first".equals(currentInningsNumber)){
            loadCommentaryForFirstInnings(inningsId);
        }
        else{
            loadCommentaryForSecondInnings(inningsId);
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("commentaryPageUpdateNeeded", false);
        editor.apply();
    }
    public void loadCommentaryForFirstInnings(long inningsId){
        ballDetailsViewModel.getBallDetailsForTeam1(inningsId).observe(getViewLifecycleOwner(), ballDetailsList -> {
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
    public void loadCommentaryForSecondInnings(long inningsId){
        ballDetailsViewModel.getBallDetailsForTeam2(inningsId).observe(getViewLifecycleOwner(), ballDetailsList -> {
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
