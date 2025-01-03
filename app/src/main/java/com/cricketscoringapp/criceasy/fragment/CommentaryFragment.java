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

import static android.content.ContentValues.TAG;

import com.cricketscoringapp.criceasy.Database.DatabaseHelper;
import com.cricketscoringapp.criceasy.R;

public class CommentaryFragment extends Fragment {
    private SharedPreferences sharedPreferences;
    private DatabaseHelper databaseHelper;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        sharedPreferences = requireActivity().getSharedPreferences("match_prefs", Context.MODE_PRIVATE);
        databaseHelper = new DatabaseHelper(requireContext());
        View view = inflater.inflate(R.layout.activity_mp_commentary, container, false);
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
        if(doesRefreshNeeded){
            Log.d(TAG, "checkAndRefreshIfNeeded: refresh needed and in the below method");
            updateCommentary();
        }else Log.d(TAG, "checkAndRefreshIfNeeded: no refresh needed");
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isScoreUpdated", false);
        editor.apply();
    }
    public void updateCommentary(){
        Log.d(TAG, "testMethod: inside the commentary fragment test method");
        long inningsId = sharedPreferences.getLong("currentInningsId", -1);
        databaseHelper.getBallDetailsForInnings(inningsId);
    }
}
