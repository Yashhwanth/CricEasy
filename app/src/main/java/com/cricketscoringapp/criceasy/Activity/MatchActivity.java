package com.cricketscoringapp.criceasy.Activity;

import static android.content.ContentValues.TAG;
import static android.view.View.GONE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.cricketscoringapp.criceasy.Database.DatabaseHelper;
import com.cricketscoringapp.criceasy.Database.SharedPrefsToJson;
import com.cricketscoringapp.criceasy.R;
import com.cricketscoringapp.criceasy.fragment.CommentaryFragment;
import com.cricketscoringapp.criceasy.fragment.InfoFragment;
import com.cricketscoringapp.criceasy.fragment.ScoreCardFragment;
import com.cricketscoringapp.criceasy.fragment.TeamsFragment;
import com.cricketscoringapp.criceasy.fragment.LiveFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MatchActivity extends AppCompatActivity {
    private Button infoFragmentButton, summaryFragmentButton, scorecardFragmentButton, commentaryFragmentButton, teamsFragmentButton;
    private FloatingActionButton scoringFloatingButton, shareFloatingButton;
    private Button inningsEndButton;
    private DatabaseHelper databaseHelper;
    private SharedPreferences sharedPreferences;
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private final String SHARED_PREFERENCES = "match_prefs";
    private static final String MATCH_ID = "currentMatchId";
    private final String INNINGS_ID = "currentInningsId";
    private final String CURRENT_ACTIVITY = "currentActivity";
    private final String OVER_ID = "overId";
    private final String STRIKER_ID = "strikerId";
    private final String NON_STRIKER_ID = "nonStrikerId";
    private final String BOWLER_ID = "bowlerId";
    private final String STRIKER = "striker";
    private final String NON_STRIKER = "nonStriker";
    private final String BOWLER = "bowler";
    private final String PARTNERSHIP_ID = "partnershipId";
    private final String TEAM_STATS_ID = "teamStatsId";
    private final String TOTAL_BALLS = "totalBalls";
    private final String PLAYED_BALLS = "playedBalls";
    private final String SCORE = "score";
    private final String CURRENT_OVER_SCORE = "currentOverScore";
    private final String TARGET = "target";
    private final String NORMAL_BALL = "Normal";
    private final String WIDE_BALL = "Wide";
    private final String NO_BALL = "NoBall";
    private final String BYE_BALL = "Bye";
    private final String LEG_BYE_BALL = "LegBye";
    private final String FROM_BAT = "Bat";
    private final String CURRENT_INNINGS_NUMBER = "currentInningsNumber";
    private final String BATTING_TEAM_ID = "battingTeamId";
    private final String BOWLING_TEAM_ID = "bowlingTeamId";
    private static final String BOWLED = "Bowled";
    private static final String CAUGHT = "Caught";
    private static final String LBW = "LBW";
    private static final String RUN_OUT = "Run-Out";
    private static final String STUMPED = "Stumped";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: match activity onCreate called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);
        databaseHelper = new DatabaseHelper(this);
        setupUI(savedInstanceState);
        updateCurrentActivityInPreferences();
    }
    @Override
    protected void onResume() {
        Log.d(TAG, "onCreate: match activity onResume called");
        super.onResume();
        updateCurrentActivityInPreferences();
        inningsEndButton.setVisibility(GONE);
        scoringFloatingButton.setVisibility(View.VISIBLE);
        sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
    }
    @Override
    protected void onStop() {
        Log.d(TAG, "onCreate: match activity onStop called");
        super.onStop();
    }
    @Override
    protected void onPause() {
        Log.d(TAG, "onCreate: match activity onPause called");
        super.onPause();
    }
    @Override
    protected void onDestroy() {
        Log.d(TAG, "onCreate: match activity onDestroy called");
        super.onDestroy();
    }
    @Override
    protected void onRestart() {
        Log.d(TAG, "onCreate: match activity onRestart called");
        super.onRestart();
    }
    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment currentFragment = fragmentManager.findFragmentById(R.id.fragmentLayout);
        if (currentFragment instanceof LiveFragment) {
            super.onBackPressed(); // Exit the activity
        } else {
            showFragment(LiveFragment.class, "LIVE_FRAGMENT"); // Show the default fragment
        }
    }
    private void setupUI(Bundle savedInstanceState){
        sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        long matchId = sharedPreferences.getLong(MATCH_ID, -1);
        infoFragmentButton = findViewById(R.id.infoFragmentButton);
        summaryFragmentButton = findViewById(R.id.summaryFragmentButton);
        scorecardFragmentButton = findViewById(R.id.scorecardFragmentButton);
        commentaryFragmentButton = findViewById(R.id.commentaryFragmentButton);
        teamsFragmentButton = findViewById(R.id.teamsFragmentButton);
        scoringFloatingButton = findViewById(R.id.floatingButton);
        shareFloatingButton = findViewById(R.id.shareFloatingActionButton);
        infoFragmentButton.setOnClickListener(view -> showFragment(InfoFragment.class, "INFO_FRAGMENT"));
        summaryFragmentButton.setOnClickListener(view -> showFragment(LiveFragment.class, "LIVE_FRAGMENT"));
        scorecardFragmentButton.setOnClickListener(view -> showFragment(ScoreCardFragment.class, "SCORE_CARD_FRAGMENT"));
        commentaryFragmentButton.setOnClickListener(view -> showFragment(CommentaryFragment.class, "COMMENTARY_FRAGMENT"));
        teamsFragmentButton.setOnClickListener(view -> showFragment(TeamsFragment.class, "TEAMS_FRAGMENT"));
        inningsEndButton = findViewById(R.id.inningsEndButton);
        inningsEndButton.setVisibility(GONE);
        inningsEndButton.setOnClickListener(v -> {
            String ongoingInnings = sharedPreferences.getString(CURRENT_INNINGS_NUMBER, null);
            if(ongoingInnings != null && ongoingInnings.equals("first")) secondInnings();
            else  closeTheApp();
        });
        scoringFloatingButton.setOnClickListener(view ->{
            openScoringPopup();
        });
        shareFloatingButton.setOnClickListener(view ->{
            exportCombinedData();
        });

        if (savedInstanceState == null) {
            showFragment(LiveFragment.class, "LIVE_FRAGMENT");
        }
    }
    private void showFragment(Class<? extends Fragment> fragmentClass, String tag) {
        //fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        for (Fragment existingFragment : fragmentManager.getFragments()) {
            if (existingFragment != null && !existingFragment.getTag().equals(tag)) {
                Log.d(TAG, "showFragment: hiding existing fragment: " + existingFragment.getTag());
                transaction.hide(existingFragment);
            }
        }
        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        if (fragment == null) {
            Log.d(TAG, "showFragment: fragment is null, need to create one");
            try {
                fragment = fragmentClass.newInstance();
                transaction.add(R.id.fragmentLayout, fragment, tag);
                Log.d(TAG, "showFragment: created new fragment");
            } catch (Exception e) {
                Log.e(TAG, "showFragment: Error in creating fragment instance", e);
                return;
            }
        }else Log.d(TAG, "showFragment: fragment already exists");
        Log.d(TAG, "Showing fragment: " + tag);
        transaction.show(fragment);
        transaction.commitNow();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("currentShowingFragment", tag);
        editor.apply();
    }
    private void updateCurrentActivityInPreferences() {
        sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(CURRENT_ACTIVITY, getClass().getSimpleName()); // Store the current activity name
        editor.apply(); // Save changes asynchronously
    }
    public void openScoringPopup() {
        View dialogView = getLayoutInflater().inflate(R.layout.activity_scoring, null);
        Button zeroScoringButton, oneScoringButton, twoScoringButton, threeScoringButton, fourScoringButton, fiveScoringButton, sixScoringButton, sevenScoringButton, eightScoringButton, byeScoringButton, legByeScoringButton, wideScoringButton, noBallScoringButton, wicketScoringButton;
        zeroScoringButton = dialogView.findViewById(R.id.zeroScoringButton);
        oneScoringButton = dialogView.findViewById(R.id.oneScoringButton);
        twoScoringButton = dialogView.findViewById(R.id.twoScoringButton);
        threeScoringButton = dialogView.findViewById(R.id.threeScoringButton);
        fourScoringButton = dialogView.findViewById(R.id.fourScoringButton);
        fiveScoringButton = dialogView.findViewById(R.id.fiveScoringButton);
        sixScoringButton = dialogView.findViewById(R.id.sixScoringButton);
        sevenScoringButton = dialogView.findViewById(R.id.sevenScoringButton);
        eightScoringButton = dialogView.findViewById(R.id.eightScoringButton);
        byeScoringButton = dialogView.findViewById(R.id.byeScoringButton);
        legByeScoringButton = dialogView.findViewById(R.id.legByeScoringButton);
        wideScoringButton = dialogView.findViewById(R.id.wideScoringButton);
        noBallScoringButton = dialogView.findViewById(R.id.noBallScoringButton);
        wicketScoringButton = dialogView.findViewById(R.id.wicketScoringButton);
        // Create the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        // Create the dialog object
        AlertDialog scoringPopupDialog = builder.create();
        // Set the custom size for the dialog (width and height)
        scoringPopupDialog.show();
        scoringPopupDialog.setOnDismissListener(dialog -> {
            sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
            SharedPreferences.Editor editor= sharedPreferences.edit();
            Log.d(TAG, "openScoringPopup: dialog is about to close in");
            Fragment currShowingFragment = fragmentManager.findFragmentByTag(sharedPreferences.getString("currentShowingFragment",null));
            Log.d(TAG, "openScoringPopup: currently in this fragment:" + currShowingFragment);
            Log.d(TAG, "openScoringPopup:current fragment is " + currShowingFragment);
            if (currShowingFragment instanceof InfoFragment) {
                editor.putBoolean("livePageUpdateNeeded", true);
                editor.putBoolean("scorecardPageUpdateNeeded", true);
                editor.putBoolean("commentaryPageUpdateNeeded", true);
                editor.apply();
                Log.d(TAG, "openScoringPopup: in " + currShowingFragment + "no urgent refresh needed, need to cache the flags");
            } else if (currShowingFragment instanceof LiveFragment) {
                Log.d(TAG, "openScoringPopup: urgent refresh needed");
                LiveFragment liveFragment = (LiveFragment) currShowingFragment;
                liveFragment.refreshUI();  // Your method to update LiveFragment
                editor.putBoolean("scorecardPageUpdateNeeded", true);
                editor.putBoolean("commentaryPageUpdateNeeded", true);
                editor.apply();
            } else if (currShowingFragment instanceof ScoreCardFragment) {
                Log.d(TAG, "openScoringPopup: urgent refresh needed");
                ScoreCardFragment scorecardFragment = (ScoreCardFragment) currShowingFragment;
                scorecardFragment.observePlayers();  // Your method to update ScorecardFragment
                editor.putBoolean("livePageUpdateNeeded", true);
                editor.putBoolean("commentaryPageUpdateNeeded", true);
                editor.apply();
            } else if (currShowingFragment instanceof CommentaryFragment) {
                Log.d(TAG, "openScoringPopup: urgent refresh needed");
                CommentaryFragment commentaryFragment = (CommentaryFragment) currShowingFragment;
                commentaryFragment.updateCommentary();  // Your method to update CommentaryFragment
                editor.putBoolean("livePageUpdateNeeded", true);
                editor.putBoolean("scorecardPageUpdateNeeded", true);
                editor.apply();
            } else {
                editor.putBoolean("livePageUpdateNeeded", true);
                editor.putBoolean("scorecardPageUpdateNeeded", true);
                editor.putBoolean("commentaryPageUpdateNeeded", true);
                editor.apply();
                Log.d(TAG, "openScoringPopup: no urgent refresh needed, need to cache the flags");
            }
        });
        scoringPopupDialog.setCanceledOnTouchOutside(false);
        if (scoringPopupDialog.getWindow() != null) {
            scoringPopupDialog.getWindow().setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,  // Set width to match parent
                    ViewGroup.LayoutParams.WRAP_CONTENT   // Set height to wrap content
            );
        }
        // Optionally: Set dialog gravity or other properties
        if (scoringPopupDialog.getWindow() != null) {
            Window window = scoringPopupDialog.getWindow();
            WindowManager.LayoutParams params = window.getAttributes();
            params.gravity = Gravity.CENTER; // Center the dialog on the screen
            window.setAttributes(params);
        }
        View.OnClickListener scoringListenerFor0To8 = view -> {
            int runs = Integer.parseInt(((Button) view).getText().toString().trim());
            Log.d(TAG, "openScoringPopup: runs from popup are" + runs);
            handleScoringFor0To6(runs);
            scoringPopupDialog.dismiss();
        };
        View.OnClickListener extraBallListener = view -> {
            String extraType = ((Button) view).getText().toString().trim();
            showExtrasDialog(extraType, scoringPopupDialog);
            //scoringPopupDialog.dismiss();
        };
        zeroScoringButton.setOnClickListener(scoringListenerFor0To8);
        oneScoringButton.setOnClickListener(scoringListenerFor0To8);
        twoScoringButton.setOnClickListener(scoringListenerFor0To8);
        threeScoringButton.setOnClickListener(scoringListenerFor0To8);
        fourScoringButton.setOnClickListener(scoringListenerFor0To8);
        fiveScoringButton.setOnClickListener(scoringListenerFor0To8);
        sixScoringButton.setOnClickListener(scoringListenerFor0To8);
        sevenScoringButton.setOnClickListener(scoringListenerFor0To8);
        eightScoringButton.setOnClickListener(scoringListenerFor0To8);
        byeScoringButton.setOnClickListener(extraBallListener);
        legByeScoringButton.setOnClickListener(extraBallListener);
        wideScoringButton.setOnClickListener(extraBallListener);
        noBallScoringButton.setOnClickListener(extraBallListener);
        wicketScoringButton.setOnClickListener(view ->{
            showWicketDialog(scoringPopupDialog);
        });
    }
    private void showExtrasDialog(String ballType, AlertDialog parentDialog) {
        View extrasDialogView = getLayoutInflater().inflate(R.layout.activity_dialog_for_extras, null);
        EditText extraRunsInput = extrasDialogView.findViewById(R.id.extraRunsEditText);
        Button btnCancel = extrasDialogView.findViewById(R.id.cancelButton);
        Button btnSubmit = extrasDialogView.findViewById(R.id.submitButton);
        RadioGroup runsInNoBallRadioGroup = extrasDialogView.findViewById(R.id.runsSourceInNoBallRadioGroup);
        if (NO_BALL.equals(ballType)) {
            runsInNoBallRadioGroup.setVisibility(View.VISIBLE);  // Show radio group for No Ball
        } else {
            runsInNoBallRadioGroup.setVisibility(GONE);  // Hide radio group for other ball types
        }
        TextView ballTypeLabel = extrasDialogView.findViewById(R.id.ballTypeHeading);
        ballTypeLabel.setText(ballType);

        AlertDialog.Builder extrasBuilder = new AlertDialog.Builder(this);
        extrasBuilder.setView(extrasDialogView);
        AlertDialog extrasDialog = extrasBuilder.create();
        extrasDialog.show();
        extrasDialog.setCanceledOnTouchOutside(false);
        if (extrasDialog.getWindow() != null) {
            extrasDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        btnCancel.setOnClickListener(view -> {
            extrasDialog.dismiss();
        });
        btnSubmit.setOnClickListener(view -> {
            String extraRunsStr = extraRunsInput.getText().toString();
            int runsSourceInNoBallRadioButtonId = runsInNoBallRadioGroup.getCheckedRadioButtonId(); // Get the selected RadioButton ID
            String runsSourceInNoBall = null; // Variable to store the tag value
            if (runsInNoBallRadioGroup.getVisibility() == View.VISIBLE) {
                if (runsSourceInNoBallRadioButtonId == -1) { // No RadioButton is selected
                    Toast.makeText(this, "Please select the source of runs (From bat or Byes/Leg Byes).", Toast.LENGTH_SHORT).show();
                } else { // A RadioButton is selected
                    RadioButton runsInNoBallRadioButton = extrasDialogView.findViewById(runsSourceInNoBallRadioButtonId);
                    runsSourceInNoBall = runsInNoBallRadioButton.getText().toString();
                }
            }
            if (!extraRunsStr.isEmpty()) {
                int extraRuns = Integer.parseInt(extraRunsStr);
                switch (ballType) {
                    case BYE_BALL:
                    case LEG_BYE_BALL:
                        handleScoringForByesAndLegByes(extraRuns, ballType);
                        break;
                    case WIDE_BALL:
                        handleScoringForWide(extraRuns, ballType);
                        break;
                    case NO_BALL:
                        handleScoringForNoBall(extraRuns, ballType, runsSourceInNoBall);
                        break;
                }
                Toast.makeText(this, ballType + " runs: " + extraRuns, Toast.LENGTH_SHORT).show();
                extrasDialog.dismiss();
                parentDialog.dismiss(); // Close the scoring popup
            } else {
                Toast.makeText(this, "Please enter the extra runs.", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void showWicketDialog(AlertDialog parentDialog){
        View wicketDialogView = getLayoutInflater().inflate(R.layout.activity_typeofwicket, null);
        AlertDialog.Builder wicketsBuilder = new AlertDialog.Builder(this);
        wicketsBuilder.setView(wicketDialogView);
        AlertDialog wicketDialog = wicketsBuilder.create();
        wicketDialog.show();
        wicketDialog.setCanceledOnTouchOutside(false);
        if (wicketDialog.getWindow() != null) {
            wicketDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        EditText runs_input = wicketDialogView.findViewById(R.id.runsInWicketEditText);
        LinearLayout runsInWicketLayout = wicketDialogView.findViewById(R.id.runsInWicketLayout); //runs input
        LinearLayout outBatsmanLayout = wicketDialogView.findViewById(R.id.outBatsmanLayout); // out batsman
        LinearLayout outEndLayout = wicketDialogView.findViewById(R.id.outEndLayout); //out end
        LinearLayout runsSourceInRunOutLayout = wicketDialogView.findViewById(R.id.runsInRunOutLayout); // from bat/by layout
        LinearLayout ballTypeInRunOutLayout = wicketDialogView.findViewById(R.id.ballTypeInWicketLayout); // ball type in run out layout
        LinearLayout ballTypeInStumpedLayout = wicketDialogView.findViewById(R.id.ballTypeInStumpingLayout); //ball type in run out
        RadioGroup dismissalTypeRadioGroup = wicketDialogView.findViewById(R.id.wicketTypeRadioGroup); // dismissal type
        RadioGroup outBatsmanRadioGroup = wicketDialogView.findViewById(R.id.outBatsmanRadioGroup);
        RadioGroup outEndRadioGroup = wicketDialogView.findViewById(R.id.outEndRadioGroup);
        RadioGroup ballTypeInWicketRadioGroup = wicketDialogView.findViewById(R.id.ballTypeInWicketRadioGroup);
        RadioGroup runsSourceInRunOutRadioGroup = wicketDialogView.findViewById(R.id.runsInRunOutRadioGroup);
        RadioGroup ballTypeInStumpedRadioGroup = wicketDialogView.findViewById(R.id.ballTypeInStumpingRadioGroup);
        Button cancelButton = wicketDialogView.findViewById(R.id.cancelButton);
        Button submitButton = wicketDialogView.findViewById(R.id.submitButton);

        runsInWicketLayout.setVisibility(GONE);
        outBatsmanLayout.setVisibility(GONE);
        outEndLayout.setVisibility(GONE);
        runsSourceInRunOutLayout.setVisibility(GONE);
        ballTypeInRunOutLayout.setVisibility(GONE);
        ballTypeInStumpedLayout.setVisibility(GONE);

        dismissalTypeRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.runOutRadioButton) {
                runsInWicketLayout.setVisibility(View.VISIBLE);
                outBatsmanLayout.setVisibility(View.VISIBLE);
                outEndLayout.setVisibility(View.VISIBLE);
                ballTypeInRunOutLayout.setVisibility(View.VISIBLE);
                runsSourceInRunOutLayout.setVisibility(View.VISIBLE);
                ballTypeInStumpedLayout.setVisibility(GONE);
                ballTypeInWicketRadioGroup.setOnCheckedChangeListener((group1, checkedId1) -> {
                    if(checkedId1 == R.id.wideBallRadioButton){
                        runsSourceInRunOutLayout.setVisibility(GONE);
                    }else{
                        runsSourceInRunOutLayout.setVisibility(View.VISIBLE);
                    }
                });
            }else if(checkedId == R.id.stumpedRadioButton) {
                runsInWicketLayout.setVisibility(GONE);
                outBatsmanLayout.setVisibility(GONE);
                outEndLayout.setVisibility(GONE);
                runsSourceInRunOutLayout.setVisibility(GONE);
                ballTypeInRunOutLayout.setVisibility(GONE);
                ballTypeInStumpedLayout.setVisibility(GONE);
                ballTypeInStumpedLayout.setVisibility(View.VISIBLE);
            }else{
                runsInWicketLayout.setVisibility(GONE);
                outBatsmanLayout.setVisibility(GONE);
                outEndLayout.setVisibility(GONE);
                runsSourceInRunOutLayout.setVisibility(GONE);
                ballTypeInRunOutLayout.setVisibility(GONE);
                ballTypeInStumpedLayout.setVisibility(GONE);
            }
        });
        cancelButton.setOnClickListener(view -> {
            wicketDialog.dismiss();
        });
        submitButton.setOnClickListener(view -> {
            String playerType = STRIKER;
            int dismissalTypeID = dismissalTypeRadioGroup.getCheckedRadioButtonId();
            if (dismissalTypeID == -1) {
                Toast.makeText(this, "Please select a dismissal type.", Toast.LENGTH_SHORT).show();
                return;
            }
            RadioButton dismissalButton = wicketDialogView.findViewById(dismissalTypeID);
            String dismissalType = dismissalButton.getText().toString();
            int runsInRunOut = 0;
            sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
            long inningsId = sharedPreferences.getLong(INNINGS_ID,-1);
            long strikerId = sharedPreferences.getLong(STRIKER_ID, -1);
            long nonStrikerId = sharedPreferences.getLong(NON_STRIKER_ID, -1);
            long bowlerId = sharedPreferences.getLong(BOWLER_ID, -1);
            long overId = sharedPreferences.getLong(OVER_ID, -1);
            long teamStatsId = sharedPreferences.getLong(TEAM_STATS_ID, -1);
            long partnershipId = sharedPreferences.getLong(PARTNERSHIP_ID, -1);
            long ballNumber = sharedPreferences.getLong("playedBalls", -1) + 1;
            long wicketBallId;
            switch (dismissalType) {
                case BOWLED:
                case CAUGHT:
                case LBW:
                    wicketBallId = databaseHelper.insertBallDataForWicket(overId, ballNumber, NORMAL_BALL, runsInRunOut, strikerId, nonStrikerId);
                    databaseHelper.updateWicketsTable(wicketBallId, dismissalType, strikerId, runsInRunOut);
                    databaseHelper.updateBatsmanStatsForWicket(inningsId, strikerId, runsInRunOut, null, null, dismissalType);
                    databaseHelper.updateBowlerStatsForWicket(inningsId, bowlerId, runsInRunOut, null, null, dismissalType);
                    databaseHelper.updatePartnershipFor0to6(runsInRunOut, partnershipId);
                    databaseHelper.updateTeamStatsForBowCauLbw(teamStatsId);
                    incrementPlayedBallsInSharedPreferences();
                    updateScoreInSharedPreferences(NORMAL_BALL, runsInRunOut);
                    break;
                case RUN_OUT:
                    String runsInputInRunOut = runs_input.getText().toString();
                    if(runsInputInRunOut.isEmpty()) return;
                    runsInRunOut = Integer.parseInt(runsInputInRunOut);
                    int outBatsmanRadioButtonId = outBatsmanRadioGroup.getCheckedRadioButtonId();
                    if (outBatsmanRadioButtonId == -1) {
                        Toast.makeText(this, "Please select the out batsman for run-out.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    RadioButton outBatsmanRadioButton = wicketDialogView.findViewById(outBatsmanRadioButtonId);
                    String outBatsman = outBatsmanRadioButton.getText().toString();
                    int outEndRadioButtonId = outEndRadioGroup.getCheckedRadioButtonId();
                    if (outEndRadioButtonId == -1) {
                        Toast.makeText(this, "Please select the out end for run-out.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    RadioButton outEndRadioButton = wicketDialogView.findViewById(outEndRadioButtonId);
                    String outEnd = outEndRadioButton.getText().toString();
                    int ballTypeRadioButtonIdRO = ballTypeInWicketRadioGroup.getCheckedRadioButtonId();
                    if (ballTypeRadioButtonIdRO == -1) {
                        Toast.makeText(this, "Please select the ball type for run-out.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    RadioButton ballTypeRadioButtonRO = wicketDialogView.findViewById(ballTypeRadioButtonIdRO);
                    String ballTypeInRo = ballTypeRadioButtonRO.getText().toString();
                    String runsSourceInRunOut = "N/A";
                    if (!ballTypeInRo.equals(WIDE_BALL) && runsSourceInRunOutRadioGroup.getVisibility() == View.VISIBLE) {
                        int runsSourceInRunOutRadioButton = runsSourceInRunOutRadioGroup.getCheckedRadioButtonId();
                        if (runsSourceInRunOutRadioButton == -1) {
                            Toast.makeText(this, "Please select runs from (bat/byes/leg byes).", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        RadioButton runsFromRadioButton = wicketDialogView.findViewById(runsSourceInRunOutRadioButton);
                        runsSourceInRunOut = runsFromRadioButton.getText().toString();
                    }
                    outBatsman = outBatsman.equals("non-striker") ? NON_STRIKER : STRIKER;
                    outEnd = outEnd.equals("non-striker") ? NON_STRIKER : STRIKER;
                    long outBatsmanId = sharedPreferences.getLong(outBatsman + "Id", -1);
                    if(ballTypeInRo.equals(NORMAL_BALL))    incrementPlayedBallsInSharedPreferences();
                    updateScoreInSharedPreferences(ballTypeInRo ,runsInRunOut);
                    wicketBallId = databaseHelper.insertBallDataForWicket(overId, ballNumber, ballTypeInRo, runsInRunOut, strikerId, nonStrikerId);
                    databaseHelper.updateWicketsTable(wicketBallId, dismissalType, outBatsmanId,runsInRunOut);
                    databaseHelper.updateBatsmanStatsForWicket(inningsId, strikerId, runsInRunOut, ballTypeInRo, runsSourceInRunOut, dismissalType);
                    databaseHelper.updateBowlerStatsForWicket(inningsId, bowlerId, runsInRunOut, ballTypeInRo, runsSourceInRunOut, dismissalType);
                    databaseHelper.updateTeamStatsForRunOut(teamStatsId, runsInRunOut, ballTypeInRo, runsSourceInRunOut);
                    databaseHelper.updatePartnershipForRunOut(partnershipId, runsInRunOut, ballTypeInRo, runsSourceInRunOut);
                    playerType = rotateStrikeWhileRunOut(outBatsman, outEnd);
                    break;
                case STUMPED:
                    Log.d(TAG, "showWicketDialog: " + runsInRunOut);
                    int ballTypeId = ballTypeInStumpedRadioGroup.getCheckedRadioButtonId();
                    if (ballTypeId == -1) {
                        Toast.makeText(this, "Please select the ball type for stumping.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    RadioButton ballTypeRadioButton = wicketDialogView.findViewById(ballTypeId);
                    String ballType = ballTypeRadioButton.getText().toString();
                    if(ballType.equals(NORMAL_BALL))    incrementPlayedBallsInSharedPreferences();
                    updateScoreInSharedPreferences(ballType, runsInRunOut);
                    databaseHelper.insertBallDataForWicket(overId, ballNumber, ballType, runsInRunOut, strikerId, nonStrikerId);
                    databaseHelper.updateBatsmanStatsForWicket(inningsId, strikerId, runsInRunOut, ballType, null, dismissalType);
                    databaseHelper.updateBowlerStatsForWicket(inningsId, bowlerId, runsInRunOut, ballType, null, dismissalType);
                    databaseHelper.updatePartnershipFor0to6(runsInRunOut, partnershipId);
                    databaseHelper.updateTeamStatsForStumping(teamStatsId, ballType);
                    break;
            }
            setNewPlayer(playerType, this::checkAndHandleOverEnd);
            wicketDialog.dismiss();
            parentDialog.dismiss();
        });
    }
    private void handleScoringFor0To6(int runs) {
    Log.d(TAG, "handleScoringFor0To6: runs input ==" + runs);
    sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
    String typeOfBallIn0To6Runs = "Normal";
    long inningsId = sharedPreferences.getLong(INNINGS_ID,-1);
    long overId = sharedPreferences.getLong(OVER_ID, -1);
    long strikerId = sharedPreferences.getLong(STRIKER_ID, -1);
    long nonStrikerId = sharedPreferences.getLong(NON_STRIKER_ID, -1);
    long partnershipId = sharedPreferences.getLong(PARTNERSHIP_ID, -1);
    long bowlerId = sharedPreferences.getLong(BOWLER_ID,-1);
    long ballNumber = sharedPreferences.getLong("playedBalls", -1) + 1;
    long ballId = databaseHelper.insertBallDataFor0To6(overId, ballNumber, runs, strikerId, nonStrikerId);
    databaseHelper.updateBatsmanStatsFor0To6(inningsId, strikerId, runs);
    databaseHelper.updateBowlerStatsFor0to6(inningsId, bowlerId, runs);
    databaseHelper.updatePartnershipFor0to6(runs, partnershipId);
    updateTeamStatsFor0to6(runs);
    incrementPlayedBallsInSharedPreferences();
    updateScoreInSharedPreferences(typeOfBallIn0To6Runs, runs);
    rotateStrike(runs);
    checkAndHandleOverEnd();
    Toast.makeText(this, "Runs scored: " + runs , Toast.LENGTH_SHORT).show();
    }
    private void handleScoringForByesAndLegByes(int extraRuns, String ballType) {
     sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
     long inningsId = sharedPreferences.getLong(INNINGS_ID, -1);
     long overId = sharedPreferences.getLong(OVER_ID, -1);
     long strikerId = sharedPreferences.getLong(STRIKER_ID, -1);
     long nonStrikerId = sharedPreferences.getLong(NON_STRIKER_ID, -1);
     long bowlerId = sharedPreferences.getLong(BOWLER_ID, -1);
     long partnershipId = sharedPreferences.getLong(PARTNERSHIP_ID, -1);
     long ballNumber = sharedPreferences.getLong("playedBalls", -1) + 1;
     long ballId = databaseHelper.insertBallDataForByLByes(overId, ballNumber, ballType, extraRuns, strikerId, nonStrikerId);
     databaseHelper.updateExtrasTable(ballId, ballType, extraRuns);
     databaseHelper.updateBatsmanForByLByes(inningsId, strikerId);
     databaseHelper.updateBowlerForByLBes(inningsId, bowlerId, ballType);
     databaseHelper.updatePartnershipForByLByes(partnershipId);
     updateTeamStatsForByLegBy(extraRuns);
     incrementPlayedBallsInSharedPreferences();
     updateScoreInSharedPreferences(ballType, extraRuns);
     rotateStrike(extraRuns);
     checkAndHandleOverEnd();
    }
    private void handleScoringForWide(int extraRuns, String ballType){
     sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
     long inningsId = sharedPreferences.getLong(INNINGS_ID, -1);
     long overId = sharedPreferences.getLong(OVER_ID, -1);
     long bowlerId = sharedPreferences.getLong(BOWLER_ID, -1);
     long strikerId = sharedPreferences.getLong(STRIKER_ID, -1);
     long nonStrikerId = sharedPreferences.getLong(NON_STRIKER_ID, -1);
     long ballNumber = sharedPreferences.getLong("playedBalls", -1) + 1;
     long ballId = databaseHelper.insertBallDataForWide(overId, ballNumber, extraRuns, strikerId, nonStrikerId);
     databaseHelper.updateExtrasTable(ballId, ballType, extraRuns);
     databaseHelper.updateBowlerForWide(inningsId, bowlerId);
     updateTeamStatsForWide(extraRuns);
     updateScoreInSharedPreferences(ballType, extraRuns);
     rotateStrike(extraRuns);
     checkAndHandleOverEnd();
    }
    private void handleScoringForNoBall(int extraRuns, String ballType, String runsSourceInNoBall){
     Log.d(TAG, "handleScoringForNoBall: " + runsSourceInNoBall);
     sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
     long inningsId = sharedPreferences.getLong(INNINGS_ID, -1);
     long overId = sharedPreferences.getLong(OVER_ID, -1);
     long bowlerId = sharedPreferences.getLong(BOWLER_ID, -1);
     long strikerId = sharedPreferences.getLong(STRIKER_ID, -1);
     long nonStrikerId = sharedPreferences.getLong(NON_STRIKER_ID, -1);
     long partnership_id = sharedPreferences.getLong(PARTNERSHIP_ID, -1);
     long ballNumber = sharedPreferences.getLong("playedBalls", -1) + 1;
     long ballId = databaseHelper.insertBallDataForNb(overId, ballNumber, extraRuns, strikerId, nonStrikerId);
     databaseHelper.updateExtrasTable(ballId, ballType, extraRuns);
     databaseHelper.updateBatsmanStatsForNb(inningsId, strikerId, extraRuns, runsSourceInNoBall);
     databaseHelper.updateBowlerStatsForNb(inningsId, bowlerId, extraRuns, runsSourceInNoBall);
     databaseHelper.updatePartnershipForNb(partnership_id, extraRuns, runsSourceInNoBall);
     updateTeamStatsForNoBall(extraRuns, runsSourceInNoBall);
     updateScoreInSharedPreferences(ballType, extraRuns);
     rotateStrike(extraRuns);
     checkAndHandleOverEnd();
    }
    private void updateTeamStatsFor0to6(int runs){
    sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
    long teamStatsId = sharedPreferences.getLong(TEAM_STATS_ID, -1);
    databaseHelper.updateTeamStatsFor0to6(teamStatsId, runs);
    }
    private void updateTeamStatsForByLegBy(int runs){
    sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
    long teamStatsId = sharedPreferences.getLong(TEAM_STATS_ID, -1);
    databaseHelper.updateTeamStatsForByesAndLegByes(teamStatsId, runs);
    }
    private void updateTeamStatsForWide(int runs){
    sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
    long teamStatsId = sharedPreferences.getLong(TEAM_STATS_ID, -1);
    databaseHelper.updateTeamStatsForWide(teamStatsId, runs);
    }
    private void updateTeamStatsForNoBall(int runs, String runsSource){
    SharedPreferences sharedPreferences = getSharedPreferences("match_prefs",MODE_PRIVATE);
    long teamStatsId = sharedPreferences.getLong("teamStatsId", -1);
    databaseHelper.updateTeamStatsForNoBall(teamStatsId, runs, runsSource);
    }
    private void setNewPlayer(String playerType, Runnable onDialogDismissed) {
        Log.d(TAG, "setNewPlayer: " + playerType);
        View playerDialogView = getLayoutInflater().inflate(R.layout.activity_selecting_players, null);
        AlertDialog.Builder playerBuilder = new AlertDialog.Builder(this);
        playerBuilder.setView(playerDialogView);
        AlertDialog playerDialog = playerBuilder.create();
        playerDialog.show();
        if (playerDialog.getWindow() != null) {
            playerDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        playerDialog.setCanceledOnTouchOutside(false);
        TextView playerTypeTextView = playerDialogView.findViewById(R.id.playerTypeTextView);
        String newPlayerType = playerType.equals("bowler") ? "Bowler" : "Batsman";
        String formattedText = getString(R.string.selectNewPlayerSetText, newPlayerType);
        playerTypeTextView.setText(formattedText);
        EditText playerNameEditText = playerDialogView.findViewById(R.id.playerNameEditText);
        Button submitButton = playerDialogView.findViewById(R.id.submitButton);
        Button backButton = playerDialogView.findViewById(R.id.cancelButton);
        backButton.setVisibility(View.GONE);
        submitButton.setOnClickListener(v -> {
            String playerName = String.valueOf(playerNameEditText.getText());
            updatePlayerDataInSp(playerType, playerName);
            if (playerType.equals("bowler")) {
                long newBowlerId = updateNewBowlerToDB(playerName);
                insertOver(newBowlerId);
            } else {
                updateNewBatsmanToDB(playerName, playerType);
            }
            playerDialog.dismiss(); // Dismiss dialog only after processing the player's input
        });
        playerDialog.setOnDismissListener(dialog -> {
            Log.d(TAG, "setNewPlayer: set on dismiss called for set new player popup");
            sharedPreferences = getSharedPreferences("match_prefs", MODE_PRIVATE);
            Fragment currShowingFragment = fragmentManager.findFragmentByTag(sharedPreferences.getString("currentShowingFragment", null));
            if (currShowingFragment instanceof TeamsFragment) {
                Log.d(TAG, "setNewPlayer: urgent refresh needed for teams");
                TeamsFragment teamsFragment = (TeamsFragment) currShowingFragment;
                teamsFragment.refreshTeams();
            }else if(currShowingFragment instanceof ScoreCardFragment){
                Log.d(TAG, "setNewPlayer: urgent refresh needed for score card");
                ScoreCardFragment scoreCardFragment = (ScoreCardFragment) currShowingFragment;
                scoreCardFragment.observePlayers();
            }
            else {
                Log.d(TAG, "setNewPlayer: no urgent need to cache the update");
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("teamsPageUpdateNeeded", true);
                editor.putBoolean("scorecardPageUpdateNeeded", true);
                editor.apply();
            }
            if (onDialogDismissed != null) {
                onDialogDismissed.run();
            }
        });
    }
    private void updatePlayerDataInSp(String playerType, String playerName){
        sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(playerType, playerName);
        editor.putInt(CURRENT_OVER_SCORE, 0);
        editor.apply();
    }
    private void updateNewBatsmanToDB(String name, String player_type) {
        sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        String oldBatsmanType = player_type.equals(STRIKER) ? NON_STRIKER_ID : STRIKER_ID;
        String newBatsmanType = player_type.equals(STRIKER) ? STRIKER_ID : NON_STRIKER_ID;
        long inningsId = sharedPreferences.getLong(INNINGS_ID, -1);
        long teamId = sharedPreferences.getLong(BATTING_TEAM_ID, -1);
        long newBatsmanId = databaseHelper.insertPlayer(name, teamId, inningsId);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(newBatsmanType, newBatsmanId);
        databaseHelper.initializeBatsmanStats(newBatsmanId, inningsId);
        long oldBatsmanId = sharedPreferences.getLong(oldBatsmanType, -1);
        Log.d(TAG, "striker" + newBatsmanId + "non striker" + oldBatsmanId);
        long partnershipId = databaseHelper.insertPartnership(inningsId, newBatsmanId, oldBatsmanId);
        editor.putLong(PARTNERSHIP_ID, partnershipId);
        editor.apply();
    }
    private long updateNewBowlerToDB(String name) {
        sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        long teamId = sharedPreferences.getLong(BOWLING_TEAM_ID, -1);
        long inningsId = sharedPreferences.getLong(INNINGS_ID, -1);
        long playerId = databaseHelper.insertPlayer(name, teamId, inningsId);
        databaseHelper.initializeBowlerStats(playerId, inningsId);
        editor.putLong(BOWLER_ID, playerId);
        editor.apply();
        return playerId;
    }
    private void incrementPlayedBallsInSharedPreferences() {
        sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        long playedBalls = sharedPreferences.getLong(PLAYED_BALLS, 0);
        playedBalls++;
        editor.putLong(PLAYED_BALLS, playedBalls);
        editor.apply();
        Log.d(TAG, "incrementPlayedBalls:  balls incremented by 1 and current balls are " + playedBalls);
    }
    public void checkAndHandleOverEnd() {
        sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        long playedBalls = sharedPreferences.getLong(PLAYED_BALLS, -1);
        long totalBalls = sharedPreferences.getLong(TOTAL_BALLS, 0);
        long target = sharedPreferences.getLong(TARGET, -1);
        int currentOverScore = sharedPreferences.getInt(CURRENT_OVER_SCORE, -1);
        String currentInnings = sharedPreferences.getString(CURRENT_INNINGS_NUMBER,"");
        Log.d(TAG, "checkAndHandleOverEnd: current ongoing innings " + currentInnings);
        int currentScore = sharedPreferences.getInt(SCORE, -1);
        if (playedBalls % 6 == 0 && playedBalls != 0 && playedBalls != totalBalls) {
            Log.d(TAG, "checkAndHandleOverEnd:" + playedBalls / 6 + " Over has ended");
            if (currentOverScore == 0) insertMaidenOver();
            // Pass rotateStrike as a callback to execute after the dialog is dismissed
            setNewPlayer("bowler", () -> rotateStrike(1));
        }
        if (playedBalls == totalBalls || currentScore >= target) {
            handleInningsEnd(currentInnings);
        }
    }
    public void handleInningsEnd(String currentInnings){
        sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        long inningsId = sharedPreferences.getLong(INNINGS_ID, -1);
        long matchId = sharedPreferences.getLong("currentMatchId", -1);
        databaseHelper.updateInningsCompletionStatus(inningsId);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if(currentInnings.equals("first")){
            Log.d(TAG, "handleInningsEnd: first innings is ended and second innings starts");
            long battingTeamId = sharedPreferences.getLong(BATTING_TEAM_ID, -1);
            long bowlingTeamId = sharedPreferences.getLong(BOWLING_TEAM_ID, -1);
            editor.putLong(BATTING_TEAM_ID, bowlingTeamId);
            editor.putLong(BOWLING_TEAM_ID, battingTeamId);
            editor.apply();
            inningsEndButton.setVisibility(View.VISIBLE);
            inningsEndButton.setText(R.string.startSecondInningsButton);
        }else{
            databaseHelper.updateMatchCompletionStatus(matchId);
            Log.d(TAG, "handleInningsEnd: match is over");
            editor.putString(CURRENT_INNINGS_NUMBER, "matchOver");
            editor.putLong(TARGET, Integer.MIN_VALUE);
            editor.apply();
            inningsEndButton.setVisibility(View.VISIBLE);
            inningsEndButton.setText(R.string.endInningsText);
        }
        scoringFloatingButton.setEnabled(false);
        scoringFloatingButton.setAlpha(0.5f);     // Optional: Change visual appearance
    }
    public void updateScoreInSharedPreferences(String ballType, int runs){
        sharedPreferences = getSharedPreferences(SHARED_PREFERENCES,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        int score = sharedPreferences.getInt(SCORE, -1);
        int currentOverScore = sharedPreferences.getInt(CURRENT_OVER_SCORE, -1);
        if(ballType.equals(NORMAL_BALL)){
            score += runs;
            currentOverScore += runs;
        }
        else if(ballType.equals(NO_BALL) || ballType.equals(WIDE_BALL)) {
            score += runs + 1;
            currentOverScore += runs + 1;
        }
        else {
            score += runs;
        }
        Log.d(TAG, "updateScoreInSharedPreferences: " + score);
        editor.putInt(SCORE, score);
        editor.putInt(CURRENT_OVER_SCORE, currentOverScore);
        editor.apply();
    }
    private void insertOver(long newBowlerId){
        Log.d(TAG, "insertOver: inside the insert over");
        sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        long inningsId = sharedPreferences.getLong(INNINGS_ID, -1);
        long playedBalls = sharedPreferences.getLong(PLAYED_BALLS, -1);
        long overId = databaseHelper.insertOver(inningsId, (int) ((playedBalls / 6) + 1), newBowlerId, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(OVER_ID, overId);
        editor.apply();
    }
    private void insertMaidenOver(){
        sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        long overId = sharedPreferences.getLong(OVER_ID, -1);
        databaseHelper.insertMaidenOver(overId);
    }
    private void secondInnings(){
        Log.d(TAG, "secondInnings: entered second innings");
        Intent intent = new Intent(MatchActivity.this, SelectingSrNsBowActivity.class);
        startActivity(intent);
    }
    private void rotateStrike(int runs) {
        sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        long strikerId = sharedPreferences.getLong(STRIKER_ID, -1);
        long nonStrikerId = sharedPreferences.getLong(NON_STRIKER_ID, -1);
        if (runs % 2 != 0) {
            long temp = strikerId;
            strikerId = nonStrikerId;
            nonStrikerId = temp;
        }
        editor.putLong(STRIKER_ID, strikerId);
        editor.putLong(NON_STRIKER_ID, nonStrikerId);
        editor.apply();
    }
    private String rotateStrikeWhileRunOut(String outBatsman, String outEnd){
        Log.d(TAG, "rotateStrikeWhileRunOut: " + outBatsman + outEnd);
        String replacePlayer = STRIKER;
        sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        long strikerIdFromSp = sharedPreferences.getLong(STRIKER_ID, -1);
        long NonStrikerIdFromSp = sharedPreferences.getLong(NON_STRIKER_ID, -1);
        if(outEnd.equals(NON_STRIKER)) replacePlayer = NON_STRIKER;
        if(outBatsman.equals(STRIKER)){
            if(outEnd.equals(NON_STRIKER)){
                editor.putLong(STRIKER_ID, NonStrikerIdFromSp);
                editor.putLong(NON_STRIKER_ID, strikerIdFromSp);
                editor.apply();
            }
        }else if(outBatsman.equals(NON_STRIKER)){
            if(outEnd.equals(STRIKER)){
                editor.putLong(STRIKER_ID, NonStrikerIdFromSp);
                editor.putLong(NON_STRIKER_ID, strikerIdFromSp);
                editor.apply();
            }
        }return replacePlayer;
    }
    private void closeTheApp(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        finishAffinity();
    }
    private void exportCombinedData() {
        // Fetch data from shared preferences
        Map<String, ?> sharedPrefsData = sharedPreferences.getAll();
        Log.d(TAG, "exportCombinedData: shared prefs are" + sharedPrefsData);
        Log.d(TAG, "Shared Preferences Data: " + sharedPrefsData.toString());

        // Create a map to store shared preferences data with types
        Map<String, Object[]> sharedPrefsWithTypes = new HashMap<>();
        for (Map.Entry<String, ?> entry : sharedPrefsData.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            String dataType = getDataType(value);

            // Store the key, value, and data type in the map
            sharedPrefsWithTypes.put(key, new Object[]{value, dataType});
        }

        Log.d(TAG, "Shared Preferences with Types: " + sharedPrefsWithTypes);

        // Fetch data from database
        int matchId = (int) sharedPreferences.getLong("currentMatchId", -1);
        int inningsId = (int) sharedPreferences.getLong("currentInningsId", -1);
        Map<String, Object> databaseData = databaseHelper.shareMatchData(matchId, inningsId);
        Log.d(TAG, "Database Data: " + databaseData);

        // Combine both data sources
        Map<String, Object> combinedData = new HashMap<>();
        combinedData.put("sharedPreferences", sharedPrefsWithTypes);
        combinedData.put("database", databaseData);

        // Convert combined data to JSON
        Gson gson = new Gson();
        String combinedJson = gson.toJson(combinedData);
        Log.d(TAG, "Combined JSON Data: " + combinedJson);

        // Save JSON data to a file
        File dir = new File(getExternalFilesDir(null), "json_files");
        if (!dir.exists() && !dir.mkdirs()) {
            Log.e(TAG, "Failed to create directory 'json_files'.");
            return;
        }
        File combinedFile = new File(dir, "combined_data.json");
        try (FileOutputStream fos = new FileOutputStream(combinedFile)) {
            fos.write(combinedJson.getBytes());
            fos.flush();
            Log.d(TAG, "Combined JSON data written to file.");
        } catch (IOException e) {
            Log.e(TAG, "Error writing combined JSON file: " + e.getMessage());
            return;
        }

        // Share the file
        shareFile(combinedFile);
    }
    private String getDataType(Object value) {
        if (value instanceof String) {
            return "String";
        } else if (value instanceof Integer) {
            return "Integer";
        } else if (value instanceof Boolean) {
            return "Boolean";
        } else if (value instanceof Long) {
            return "Long";
        } else if (value instanceof Float) {
            return "Float";
        } else if (value instanceof Double) {
            return "Double";
        } else {
            return "Unknown";
        }
    }
    private void shareFile(File file) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("application/json");
        Uri fileUri = FileProvider.getUriForFile(this, "com.cricketscoringapp.criceasy.fileprovider", file);
        intent.putExtra(Intent.EXTRA_STREAM, fileUri);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        try {
            startActivity(Intent.createChooser(intent, "Share Combined Data"));
            Log.d(TAG, "Share intent started.");
        } catch (Exception e) {
            Log.e(TAG, "Error starting share intent: " + e.getMessage());
        }
    }



}
