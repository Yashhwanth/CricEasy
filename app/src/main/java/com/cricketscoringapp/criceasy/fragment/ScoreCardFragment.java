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

import com.cricketscoringapp.criceasy.R;

public class ScoreCardFragment extends Fragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_mp_scorecard, container, false);
        return view;
    }
    public void updateScorecard(){
        Log.d(TAG, "testMethod: inside the scorecard fragment test method");
    }
    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "scorecard onResume called");
        checkAndRefreshIfNeeded();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) { // Fragment is now visible
            Log.d(TAG, "onHiddenChanged: scorecard Fragment is now visible");
            checkAndRefreshIfNeeded();
        } else { // Fragment is now hidden
            Log.d(TAG, "onHiddenChanged: scorecard Fragment is now hidden");
        }
    }

    private void checkAndRefreshIfNeeded() {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("match_prefs", Context.MODE_PRIVATE);
        boolean doesRefreshNeeded = sharedPreferences.getBoolean("scorecardPageUpdateNeeded", false);
        if(doesRefreshNeeded){
            Log.d(TAG, "checkAndRefreshIfNeeded: refresh needed and in the below method");
            updateScorecard();
        }else Log.d(TAG, "checkAndRefreshIfNeeded: no refresh needed");
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isScoreUpdated", false);
        editor.apply();
    }
}
