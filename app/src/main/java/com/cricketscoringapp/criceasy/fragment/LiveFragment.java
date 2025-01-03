package com.cricketscoringapp.criceasy.fragment;


import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cricketscoringapp.criceasy.Database.DatabaseHelper;
import com.cricketscoringapp.criceasy.R;

import java.util.HashMap;

public class LiveFragment extends Fragment {
    private TextView currentBattingTeamName;
    private TextView currentBattingScore;
    private TextView batter1Name;
    private TextView batter1R;
    private TextView batter1B;
    private TextView batter1Fours;
    private TextView batter1Sixes;
    private TextView batter1SR;
    private TextView batter2Name;
    private TextView batter2R;
    private TextView batter2B;
    private TextView batter2Fours;
    private TextView batter2Sixes;
    private TextView batter2SR;
    private TextView bowlerName;
    private TextView bowlerO;
    private TextView bowlerM;
    private TextView bowlerR;
    private TextView bowlerW;
    private TextView bowlerECO;
    private TextView runRate;
    private TextView partnershipRuns;
    private TextView partnershipBalls;
    private SharedPreferences sharedPreferences;
    private DatabaseHelper databaseHelper;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        databaseHelper = new DatabaseHelper(requireContext());
        View view = inflater.inflate(R.layout.activity_mp_summary, container, false);
        currentBattingTeamName = view.findViewById(R.id.tv_batting_team);
        Log.d("LiveFragment", "currentBattingTeamName: " + currentBattingTeamName);
        currentBattingScore = view.findViewById(R.id.tv_batting_score);
        Log.d("LiveFragment", "currentBattingScore: " + currentBattingScore);
        batter1Name = view.findViewById(R.id.tv_batter_1_name);
        batter1R = view.findViewById(R.id.tv_batter_1_runs);
        batter1B = view.findViewById(R.id.tv_batter_1_balls);
        batter1Fours = view.findViewById(R.id.tv_batter_1_fours);
        batter1Sixes = view.findViewById(R.id.tv_batter_1_sixes);
        batter1SR = view.findViewById(R.id.tv_batter_1_sr);

        batter2Name = view.findViewById(R.id.tv_batter_2_name);
        batter2R = view.findViewById(R.id.tv_batter_2_runs);
        batter2B = view.findViewById(R.id.tv_batter_2_balls);
        batter2Fours = view.findViewById(R.id.tv_batter_2_fours);
        batter2Sixes = view.findViewById(R.id.tv_batter_2_sixes);
        batter2SR = view.findViewById(R.id.tv_batter_2_sr);

        bowlerName = view.findViewById(R.id.tv_bowler_name);
        bowlerO = view.findViewById(R.id.tv_bowler_overs);
        bowlerM = view.findViewById(R.id.tv_bowler_maidens);
        bowlerR = view.findViewById(R.id.tv_bowler_runs);
        bowlerW = view.findViewById(R.id.tv_bowler_wickets);
        bowlerECO = view.findViewById(R.id.tv_bowler_economy);

        runRate = view.findViewById(R.id.tv_run_rate);
        partnershipRuns = view.findViewById(R.id.tv_partnership_runs);
        partnershipBalls = view.findViewById(R.id.tv_partnership_balls);
        return view;
    }
    @Override
    public void onResume() {
        sharedPreferences = requireContext().getSharedPreferences("match_prefs", Context.MODE_PRIVATE);
        Log.d(TAG, "live onResume called");
        super.onResume();
        refreshUI();
    }
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) { // Fragment is now visible
            Log.d(TAG, "onHiddenChanged: live Fragment is now visible");
            checkAndRefreshUIIfNeeded();
        } else { // Fragment is now hidden
            Log.d(TAG, "onHiddenChanged: live Fragment is now hidden");
        }
    }
    private void checkAndRefreshUIIfNeeded() {
        sharedPreferences = requireContext().getSharedPreferences("match_prefs", Context.MODE_PRIVATE);
        boolean doesRefreshNeeded = sharedPreferences.getBoolean("livePageUpdateNeeded", false);
        if(doesRefreshNeeded){
            Log.d(TAG, "checkAndRefreshIfNeeded: refresh needed and in the below method");
            refreshUI();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("livePageUpdateNeeded", false);
            editor.apply();
        }
        else Log.d(TAG, "checkAndRefreshIfNeeded: no refresh needed");
    }
    public void refreshUI(){
        Log.d(TAG, "testMethod: inside the live fragment test method");
        getTeamName();
        getTeamScore();
        getBatterStats();
        getPartnerShip();
        getBowlerStats();
    }
    public void getTeamName(){
        long teamId = sharedPreferences.getLong("battingTeamId", -1);
        Log.d(TAG, "getTeamName: team id is" + teamId);
        String teamName = databaseHelper.getTeamNameFromId(teamId);
        currentBattingTeamName.setText(teamName);
    }
    public void getTeamScore(){
        long inningsId = sharedPreferences.getLong("currentInningsId", -1);
        HashMap<String, String> map = databaseHelper.getTeamStats(inningsId);
        String score = map.get("runs") + " / " + map.get("wickets") +  " ( " + map.get("overs") + " ) ";
        currentBattingScore.setText(score);
        float teamRunRate = Float.parseFloat(map.get("runs")) / Float.parseFloat(map.get("balls"));
        runRate.setText(String.valueOf(teamRunRate));
    }
    public void getBatterStats(){
        long inningsId = sharedPreferences.getLong("currentInningsId", -1);
        long batter1Id = sharedPreferences.getLong("strikerId", -1);
        long batter2Id = sharedPreferences.getLong("nonStrikerId", -1);
        HashMap<String, String> map1 = databaseHelper.getCurrentBattersStats(inningsId, batter1Id);
        HashMap<String, String> map2 = databaseHelper.getCurrentBattersStats(inningsId, batter2Id);

        String batter1Runs = map1.get("runs");
        String batter1Balls = map1.get("balls");
        float batter1Sr = Float.parseFloat(batter1Runs) / Float.parseFloat(batter1Balls);

        batter1Name.setText(map1.get("name"));
        batter1R.setText(batter1Runs);
        batter1B.setText(batter1Balls);
        batter1Fours.setText(map1.get("fours"));
        batter1Sixes.setText(map1.get("sixes"));
        batter1SR.setText(String.valueOf(batter1Sr));

        String batter2Runs = map2.get("runs");
        String batter2Balls = map2.get("balls");
        float batter2Sr = Float.parseFloat(batter2Runs) / Float.parseFloat(batter2Balls);

        batter2Name.setText(map2.get("name"));
        batter2R.setText(batter2Runs);
        batter2B.setText(batter2Balls);
        batter2Fours.setText(map2.get("fours"));
        batter2Sixes.setText(map2.get("sixes"));
        batter2SR.setText(String.valueOf(batter2Sr));
    }
    public void getPartnerShip(){
        long partnerShipId = sharedPreferences.getLong("partnershipId", -1);
        HashMap<String, String> partnershipStats = databaseHelper.getPartnershipStats(partnerShipId);
        partnershipRuns.setText(partnershipStats.get("runs"));
        partnershipBalls.setText(partnershipStats.get("balls"));
    }
    public void getBowlerStats(){
        long inningsId = sharedPreferences.getLong("currentInningsId", -1);
        long bowlerId = sharedPreferences.getLong("bowlerId", -1);
        HashMap<String,String> bowlerMap = databaseHelper.getCurrentBowlerStats(inningsId, bowlerId);
        //float bowlerEconomy = Float.parseFloat(bowlerMap.get("runs")) / Float.parseFloat(bowlerMap.get("overs"));
        bowlerName.setText(bowlerMap.get("name"));
        bowlerO.setText(bowlerMap.get("overs"));
        bowlerR.setText(bowlerMap.get("runs"));
        bowlerW.setText(bowlerMap.get("wickets"));
        bowlerECO.setText(String.valueOf("0.00"));
        bowlerM.setText(bowlerMap.get("maidens"));
    }
}