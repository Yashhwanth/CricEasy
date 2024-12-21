package com.cricketscoringapp.criceasy.Activity;

import static android.content.ContentValues.TAG;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.cricketscoringapp.criceasy.Database.DatabaseHelper;
import com.cricketscoringapp.criceasy.R;
import com.cricketscoringapp.criceasy.fragment.InfoFragment;
import com.cricketscoringapp.criceasy.fragment.TeamsFragment;
import com.cricketscoringapp.criceasy.fragment.liveFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class MatchActivity extends AppCompatActivity {
    private Button infoFragmentButton, summaryFragmentButton, scorecardFragmentButton, commentaryFragmentButton, teamsFragmentButton;
    private FloatingActionButton floatingButton;
    private DatabaseHelper databaseHelper;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);
        databaseHelper = new DatabaseHelper(this);
        setupUI(savedInstanceState);
        updateCurrentActivityInPreferences();
//        floatingButton.setOnClickListener(view ->{
//                popup();
//        });
//
//        // Set onClickListeners to show the corresponding fragments
//        infoFragmentButton.setOnClickListener(view -> showFragment(new InfoFragment()));
//        summaryFragmentButton.setOnClickListener(view -> showFragment(new liveFragment()));
//        scorecardFragmentButton.setOnClickListener(view -> showFragment(new InfoFragment()));
//        commentaryFragmentButton.setOnClickListener(view -> showFragment(new InfoFragment()));
//        teamsFragmentButton.setOnClickListener(view -> showFragment(new InfoFragment()));

        // Load the first fragment by default
//        if (savedInstanceState == null) {
//            showFragment(new liveFragment());
//        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        // Update current activity in SharedPreferences
        updateCurrentActivityInPreferences();
    }

    private void setupUI(Bundle savedInstanceState){
        infoFragmentButton = findViewById(R.id.infoFragmentButton);
        summaryFragmentButton = findViewById(R.id.summaryFragmentButton);
        scorecardFragmentButton = findViewById(R.id.scorecardFragmentButton);
        commentaryFragmentButton = findViewById(R.id.commentaryFragmentButton);
        teamsFragmentButton = findViewById(R.id.teamsFragmentButton);
        floatingButton = findViewById(R.id.floatingButton);
        infoFragmentButton.setOnClickListener(view -> showFragment(new InfoFragment()));
        summaryFragmentButton.setOnClickListener(view -> showFragment(new liveFragment()));
        scorecardFragmentButton.setOnClickListener(view -> showFragment(new InfoFragment()));
        commentaryFragmentButton.setOnClickListener(view -> showFragment(new InfoFragment()));
        teamsFragmentButton.setOnClickListener(view -> showFragment(new TeamsFragment()));
        floatingButton.setOnClickListener(view ->{
            openScoringPopup();
        });
        if (savedInstanceState == null) {
            showFragment(new liveFragment());
        }
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
        AlertDialog dialog = builder.create();
        // Set the custom size for the dialog (width and height)
        dialog.show();
        if (dialog.getWindow() != null) {
            // Set dialog size
            dialog.getWindow().setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,  // Set width to match parent
                    ViewGroup.LayoutParams.WRAP_CONTENT   // Set height to wrap content
            );
        }
        // Optionally: Set dialog gravity or other properties
        if (dialog.getWindow() != null) {
            Window window = dialog.getWindow();
            WindowManager.LayoutParams params = window.getAttributes();
            params.gravity = Gravity.CENTER; // Center the dialog on the screen
            window.setAttributes(params);
        }
        // Set up OnClickListeners for the dialog buttons
        zeroScoringButton.setOnClickListener(view -> {
            handleScoringFor0To6(0);
            dialog.dismiss();
        });
        oneScoringButton.setOnClickListener(view -> {
            handleScoringFor0To6(1);
            dialog.dismiss();
        });
        twoScoringButton.setOnClickListener(view -> {
            handleScoringFor0To6(2);
            dialog.dismiss();
        });
        threeScoringButton.setOnClickListener(view -> {
            handleScoringFor0To6(3);
            dialog.dismiss();
        });
        fourScoringButton.setOnClickListener(view -> {
            handleScoringFor0To6(4);
            dialog.dismiss();
        });
        fiveScoringButton.setOnClickListener(view -> {
            handleScoringFor0To6(5);
            dialog.dismiss();
        });
        sixScoringButton.setOnClickListener(view -> {
            handleScoringFor0To6(6);
            dialog.dismiss();
        });
        sevenScoringButton.setOnClickListener(view -> {
            handleScoringFor0To6(7);
            dialog.dismiss();
        });
        eightScoringButton.setOnClickListener(view -> {
            handleScoringFor0To6(8);
            dialog.dismiss();
        });
        byeScoringButton.setOnClickListener(view -> {
            showExtrasDialog("Bye", dialog);
        });
        legByeScoringButton.setOnClickListener(view -> {
            showExtrasDialog("LegBye", dialog);
        });
        wideScoringButton.setOnClickListener(view -> {
            showExtrasDialog("Wide", dialog);
        });
        noBallScoringButton.setOnClickListener(view -> {
            showExtrasDialog("NoBall", dialog);
        });
        wicketScoringButton.setOnClickListener(view ->{
            showWicketDialog(dialog);
        });
    }
    private void showFragment(Fragment fragment) {
        // Begin a fragment transaction to replace the current fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentLayout, fragment); // fragmentContainer is the container where the fragments will be loaded
        transaction.addToBackStack(null); // Optionally, add the transaction to the back stack if you want to handle back navigation
        transaction.commit();
    }
    private void updateCurrentActivityInPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("match_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("current_activity", getClass().getSimpleName()); // Store the current activity name
        editor.apply(); // Save changes asynchronously
    }
    private void rotateStrike(int runs) {
        // Access the SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("match_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        // Retrieve current striker and non-striker IDs
        long strikerId = sharedPreferences.getLong("striker_id", -1);
        long nonStrikerId = sharedPreferences.getLong("non_striker_id", -1);

        // Check if runs are odd
        if (runs % 2 != 0) {
            // Swap striker and non-striker
            long temp = strikerId;
            strikerId = nonStrikerId;
            nonStrikerId = temp;
        }
        // Update the SharedPreferences with the new IDs
        editor.putLong("striker_id", strikerId);
        editor.putLong("non_striker_id", nonStrikerId);
        editor.apply();
    }
    private String rotateStrikeWhileRunOut(String outBatsman, String outEnd){
        Log.d(TAG, "rotateStrikeWhileRunOut: " + outBatsman + outEnd);
        String replacePlayer = "striker";
        SharedPreferences sharedPreferences = getSharedPreferences("match_prefs",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        long strikerIdFromSp = sharedPreferences.getLong("striker_id", -1);
        long NonStrikerIdFromSp = sharedPreferences.getLong("non_striker_id", -1);
        if(outEnd.equals("non_striker")) replacePlayer = "non_striker";
        if(outBatsman.equals("striker")){
            if(outEnd.equals("non_striker")){
                editor.putLong("striker_id", NonStrikerIdFromSp);
                editor.putLong("non_striker_id", strikerIdFromSp);
                editor.apply();
            }
        }else if(outBatsman.equals("non_striker")){
            if(outEnd.equals("striker")){
                editor.putLong("striker_id", NonStrikerIdFromSp);
                editor.putLong("non_striker_id", strikerIdFromSp);
                editor.apply();
            }
        }return replacePlayer;
    }
    private void showExtrasDialog(String ballType, AlertDialog parentDialog) {
        View extrasDialogView = getLayoutInflater().inflate(R.layout.activity_dialog_for_extras, null);
        EditText extraRunsInput = extrasDialogView.findViewById(R.id.extraRunsEditText);
        Button btnCancel = extrasDialogView.findViewById(R.id.cancelButton);
        Button btnSubmit = extrasDialogView.findViewById(R.id.submitButton);
        RadioGroup runsInNoBallRadioGroup = extrasDialogView.findViewById(R.id.runsInNoBallRadioGroup);
        if ("NoBall".equals(ballType)) {
            runsInNoBallRadioGroup.setVisibility(View.VISIBLE);  // Show radio group for No Ball
        } else {
            runsInNoBallRadioGroup.setVisibility(View.GONE);  // Hide radio group for other ball types
        }
        TextView ballTypeLabel = extrasDialogView.findViewById(R.id.ballTypeHeading);
        ballTypeLabel.setText(ballType);

        // Create the extras dialog
        AlertDialog.Builder extrasBuilder = new AlertDialog.Builder(this);
        extrasBuilder.setView(extrasDialogView);
        AlertDialog extrasDialog = extrasBuilder.create();
        extrasDialog.show();

        if (extrasDialog.getWindow() != null) {
            extrasDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        // Handle Cancel button
        btnCancel.setOnClickListener(view -> {
            extrasDialog.dismiss();
        });

        // Handle Submit button
        btnSubmit.setOnClickListener(view -> {
            String extraRunsStr = extraRunsInput.getText().toString();
            int runsInNoBallRadioButtonId = runsInNoBallRadioGroup.getCheckedRadioButtonId(); // Get the selected RadioButton ID
            String runFromWhat = null; // Variable to store the tag value
            if (runsInNoBallRadioGroup.getVisibility() == View.VISIBLE) {
                if (runsInNoBallRadioButtonId == -1) { // No RadioButton is selected
                    Toast.makeText(this, "Please select the source of runs (From bat or Byes/Leg Byes).", Toast.LENGTH_SHORT).show();
                } else { // A RadioButton is selected
                    RadioButton runsInNoBallRadioButton = extrasDialogView.findViewById(runsInNoBallRadioButtonId);
                    runFromWhat = runsInNoBallRadioButton.getText().toString();
                }
            }
            if (!extraRunsStr.isEmpty()) {
                int extraRuns = Integer.parseInt(extraRunsStr);
                // Check the ball type and call the corresponding method
                switch (ballType) {
                    case "Bye":
                    case "LegBye":
                        handleScoringForByesAndLegByes(extraRuns, ballType);
                        break;
                    case "Wide":
                        handleScoringForWide(extraRuns, ballType);
                        break;
                    case "NoBall":
                        handleScoringForNoBall(extraRuns, ballType, runFromWhat);
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

        if (wicketDialog.getWindow() != null) {
            wicketDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        EditText runs_input = wicketDialogView.findViewById(R.id.runsInWicketEditText);
        Button cancelButton = wicketDialogView.findViewById(R.id.cancelButton);
        Button submitButton = wicketDialogView.findViewById(R.id.submitButton);

        RadioGroup dismissalTypeRadioGroup = wicketDialogView.findViewById(R.id.wicketTypeRadioGroup); // dismissal type

        LinearLayout runs_input_layout = wicketDialogView.findViewById(R.id.runsInWicketLayout); //runs input
        LinearLayout out_batsman_layout = wicketDialogView.findViewById(R.id.outBatsmanLayout); // out batsman
        LinearLayout out_end_layout = wicketDialogView.findViewById(R.id.outEndLayout); //out end
        LinearLayout runs_from_bat_or_by_layout = wicketDialogView.findViewById(R.id.runsInRunOutLayout); // from bat/by layout
        LinearLayout ball_type_in_run_out_layout = wicketDialogView.findViewById(R.id.ballTypeInWicketLayout); // ball type in run out layout
        LinearLayout stumped_layout = wicketDialogView.findViewById(R.id.ballTypeInStumpingLayout); //ball type in run out

        RadioGroup out_batsman_radio_group = wicketDialogView.findViewById(R.id.outBatsmanRadioGroup);
        RadioGroup out_end_radio_group = wicketDialogView.findViewById(R.id.outEndRadioGroup);
        RadioGroup ball_type_in_run_out_radio_group = wicketDialogView.findViewById(R.id.ballTypeInWicketRadioGroup);
        RadioGroup from_bat_or_by_radio_group = wicketDialogView.findViewById(R.id.runsInRunOutRadioGroup);
        RadioGroup stumped_ball_type = wicketDialogView.findViewById(R.id.ballTypeInStumpingRadioGroup);


        runs_input_layout.setVisibility(View.GONE);
        out_batsman_layout.setVisibility(View.GONE);
        out_end_layout.setVisibility(View.GONE);
        runs_from_bat_or_by_layout.setVisibility(View.GONE);
        ball_type_in_run_out_layout.setVisibility(View.GONE);
        stumped_layout.setVisibility(View.GONE);


        // Add listener to RadioGroup to detect selection
        dismissalTypeRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.runOutRadioButton) {
                runs_input_layout.setVisibility(View.VISIBLE);
                out_batsman_layout.setVisibility(View.VISIBLE);
                out_end_layout.setVisibility(View.VISIBLE);
                ball_type_in_run_out_layout.setVisibility(View.VISIBLE);
                runs_from_bat_or_by_layout.setVisibility(View.VISIBLE);
                stumped_layout.setVisibility(View.GONE);
                ball_type_in_run_out_radio_group.setOnCheckedChangeListener((group1, checkedId1) -> {
                    if(checkedId1 == R.id.wideBallRadioButton){
                        runs_from_bat_or_by_layout.setVisibility(View.GONE);
                    }else{
                        runs_from_bat_or_by_layout.setVisibility(View.VISIBLE);
                    }
                });
            }else if(checkedId == R.id.stumpedRadioButton) {
                runs_input_layout.setVisibility(View.GONE);
                out_batsman_layout.setVisibility(View.GONE);
                out_end_layout.setVisibility(View.GONE);
                runs_from_bat_or_by_layout.setVisibility(View.GONE);
                ball_type_in_run_out_layout.setVisibility(View.GONE);
                stumped_layout.setVisibility(View.GONE);
                stumped_layout.setVisibility(View.VISIBLE);
            }else{
                // Handle other cases, e.g., hide layouts
                runs_input_layout.setVisibility(View.GONE);
                out_batsman_layout.setVisibility(View.GONE);
                out_end_layout.setVisibility(View.GONE);
                runs_from_bat_or_by_layout.setVisibility(View.GONE);
                ball_type_in_run_out_layout.setVisibility(View.GONE);
                stumped_layout.setVisibility(View.GONE);
            }
        });
        cancelButton.setOnClickListener(view -> {
            wicketDialog.dismiss();
        });
        submitButton.setOnClickListener(view -> {
            String playerType = "striker";
            int dismissalTypeID = dismissalTypeRadioGroup.getCheckedRadioButtonId();
            if (dismissalTypeID == -1) {
                Toast.makeText(this, "Please select a dismissal type.", Toast.LENGTH_SHORT).show();
                return;
            }
            RadioButton dismissalButton = wicketDialogView.findViewById(dismissalTypeID);
            String dismissalType = dismissalButton.getText().toString();
            int runs = 0;
            String runsInput = runs_input.getText().toString();
            if (!runsInput.isEmpty()) {
                try {
                    runs = Integer.parseInt(runsInput);
                } catch (NumberFormatException e) {
                    // Handle the case where the number format is invalid (e.g., non-numeric input)
                    runs = 0; // Set to default if there's a parsing error
                }
            }
            SharedPreferences sharedPreferences = getSharedPreferences("match_prefs",MODE_PRIVATE);
            long innings_id = sharedPreferences.getLong("Innings_id",-1);
            long striker = sharedPreferences.getLong("striker_id", -1);
            long non_striker_id = sharedPreferences.getLong("non_striker_id", -1);
            long bowler_id = sharedPreferences.getLong("bowler_id", -1);
            long over_id = sharedPreferences.getLong("over_id", -1);
            long team_stats_id = sharedPreferences.getLong("teamStatsId", -1);
            long partnership_id = sharedPreferences.getLong("partnership_id", -1);
            switch (dismissalType) {
                case "Bowled":
                case "Caught":
                case "LBW":
                    databaseHelper.insertBallDataForWicket(over_id, "Legal", runs, striker, non_striker_id);
                    databaseHelper.updateBatsmanStatsForWicket(innings_id, striker, runs, null, null, "BOWLED");
                    databaseHelper.updateBowlerStatsForWicket(innings_id, bowler_id, runs, null, null, "BOWLED");
                    //need a partnership helper
                    databaseHelper.updateTeamStatsForBowCauLbw(team_stats_id);
                    incrementPlayedBallsInSharedPreferences();
                    updateScoreInSharedPreferences("Normal", runs);
                    break;
                case "Run-Out":
                    Log.d(TAG, "showWicketDialog: " + runs);
                    int outBatsmanRadioButtonId = out_batsman_radio_group.getCheckedRadioButtonId();
                    if (outBatsmanRadioButtonId == -1) {
                        Toast.makeText(this, "Please select the out batsman for run-out.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    RadioButton outBatsmanRadioButton = wicketDialogView.findViewById(outBatsmanRadioButtonId);
                    String outBatsman = outBatsmanRadioButton.getText().toString();
                    int outEndRadioButtonId = out_end_radio_group.getCheckedRadioButtonId();
                    if (outEndRadioButtonId == -1) {
                        Toast.makeText(this, "Please select the out end for run-out.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    RadioButton outEndRadioButton = wicketDialogView.findViewById(outEndRadioButtonId);
                    String outEnd = outEndRadioButton.getText().toString();
                    int ballTypeRadioButtonIdRO = ball_type_in_run_out_radio_group.getCheckedRadioButtonId();
                    if (ballTypeRadioButtonIdRO == -1) {
                        Toast.makeText(this, "Please select the ball type for run-out.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    RadioButton ballTypeRadioButtonRO = wicketDialogView.findViewById(ballTypeRadioButtonIdRO);
                    String ballTypeInRo = ballTypeRadioButtonRO.getText().toString();
                    String runsFrom = "N/A";
                    if (!ballTypeInRo.equals("Wide") && from_bat_or_by_radio_group.getVisibility() == View.VISIBLE) {
                        int runsFromId = from_bat_or_by_radio_group.getCheckedRadioButtonId();
                        if (runsFromId == -1) {
                            Toast.makeText(this, "Please select runs from (bat/byes/leg byes).", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        RadioButton runsFromRadioButton = wicketDialogView.findViewById(runsFromId);
                        runsFrom = runsFromRadioButton.getText().toString();
                    }
                    Log.d(TAG, "showWicketDialog: " + runsFrom);
                    Log.d(TAG, "showWicketDialog: " + ballTypeInRo);
                    if(ballTypeInRo.equals("Normal"))    incrementPlayedBallsInSharedPreferences();
                    updateScoreInSharedPreferences(ballTypeInRo ,runs);
                    databaseHelper.insertBallDataForWicket(over_id, ballTypeInRo, runs, striker, non_striker_id);
                    databaseHelper.updateBatsmanStatsForWicket(innings_id, striker, runs, ballTypeInRo, runsFrom, "RUN-OUT");
                    databaseHelper.updateBowlerStatsForWicket(innings_id, bowler_id, runs, ballTypeInRo, runsFrom, "RUN-OUT");
                    databaseHelper.updateTeamStatsForRunOut(team_stats_id, runs, ballTypeInRo, runsFrom);
                    databaseHelper.updatePartnershipForRunOut(partnership_id, runs, ballTypeInRo, runsFrom);
                    outBatsman = outBatsman.equals("non-striker") ? "non_striker" : "striker";
                    outEnd = outEnd.equals("non-striker") ? "non_striker" : "striker";
                    playerType = rotateStrikeWhileRunOut(outBatsman, outEnd);
                    break;
                case "Stumped":
                    int ballTypeId = stumped_ball_type.getCheckedRadioButtonId();
                    if (ballTypeId == -1) {
                        Toast.makeText(this, "Please select the ball type for stumping.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    RadioButton ballTypeRadioButton = wicketDialogView.findViewById(ballTypeId);
                    String ballType = ballTypeRadioButton.getText().toString();
                    if(ballType.equals("Normal"))    incrementPlayedBallsInSharedPreferences();
                    updateScoreInSharedPreferences(ballType, runs);
                    databaseHelper.insertBallDataForWicket(over_id, ballType, runs, striker, non_striker_id);
                    databaseHelper.updateBatsmanStatsForWicket(innings_id, striker, runs, ballType, null, "STUMPED");
                    databaseHelper.updateBowlerStatsForWicket(innings_id, bowler_id, runs, ballType, null, "STUMPED");
                    databaseHelper.updateTeamStatsForStumping(team_stats_id, ballType);
                    //need a partnership helper
                    break;
            }
            setNewPlayer(playerType);
            checkAndHandleOverEnd();
            wicketDialog.dismiss();
            parentDialog.dismiss();
        });
    }
    private void handleScoringFor0To6(int runs) {
        SharedPreferences sharedPreferences = getSharedPreferences("match_prefs",MODE_PRIVATE);
        String typeOfBallIn0To6Runs = "Normal";
        long inningsId = sharedPreferences.getLong("Innings_id",-1);
        long overId = sharedPreferences.getLong("over_id", -1);
        long strikerId = sharedPreferences.getLong("striker_id", -1);
        long nonStrikerId = sharedPreferences.getLong("non_striker_id", -1);
        long bowlerId = sharedPreferences.getLong("bowler_id",-1);
        long ballId = databaseHelper.insertBallDataFor0To6(overId, typeOfBallIn0To6Runs, runs, strikerId, nonStrikerId);
        databaseHelper.updateBatsmanStatsFor0To6(inningsId, strikerId, runs);
        databaseHelper.updateBowlerStatsFor0to6(inningsId, bowlerId, runs);
        databaseHelper.updatePartnershipFor0to6(runs, 1);
        updateTeamStatsFor0to6(runs);
        incrementPlayedBallsInSharedPreferences();
        updateScoreInSharedPreferences(typeOfBallIn0To6Runs, runs);
        rotateStrike(runs);
        checkAndHandleOverEnd();
        Toast.makeText(this, "Runs scored: " + runs , Toast.LENGTH_SHORT).show();
    }
     private void handleScoringForByesAndLegByes(int extraRuns, String ballType) {
         SharedPreferences sharedPreferences = getSharedPreferences("match_prefs", MODE_PRIVATE);
         long inningsId = sharedPreferences.getLong("Innings_id", -1);
         long overId = sharedPreferences.getLong("over_id", -1);
         long strikerId = sharedPreferences.getLong("striker_id", -1);
         long nonStrikerId = sharedPreferences.getLong("non_striker_id", -1);
         long bowlerId = sharedPreferences.getLong("bowler_id", -1);
         long ballId = databaseHelper.insertBallDataForByLByes(overId, ballType, extraRuns, strikerId, nonStrikerId);
         databaseHelper.updateExtrasTable(ballId, ballType, extraRuns);
         databaseHelper.updateBatsmanForByLByes(inningsId, strikerId, 1);
         databaseHelper.updateBowlerForByLBes(inningsId, bowlerId, ballType);
         databaseHelper.updatePartnershipForByLByes(1);
         updateTeamStatsForByLegBy(extraRuns);
         incrementPlayedBallsInSharedPreferences();
         updateScoreInSharedPreferences(ballType, extraRuns);
         rotateStrike(extraRuns);
         checkAndHandleOverEnd();
     }
     private void handleScoringForWide(int extraRuns, String ballType){
         SharedPreferences sharedPreferences = getSharedPreferences("match_prefs", MODE_PRIVATE);
         long inningsId = sharedPreferences.getLong("Innings_id", -1);
         long overId = sharedPreferences.getLong("over_id", -1);
         long bowlerId = sharedPreferences.getLong("bowler_id", -1);
         long strikerId = sharedPreferences.getLong("striker_id", -1);
         long nonStrikerId = sharedPreferences.getLong("non_striker_id", -1);
         long ballId = databaseHelper.insertBallDataForWide(overId, extraRuns, strikerId, nonStrikerId);
         databaseHelper.updateExtrasTable(ballId, ballType, extraRuns);
         databaseHelper.updateBowlerForWide(inningsId, bowlerId, extraRuns);
         updateTeamStatsForWide(extraRuns);
         updateScoreInSharedPreferences(ballType, extraRuns);
         rotateStrike(extraRuns);
         checkAndHandleOverEnd();
     }
     private void handleScoringForNoBall(int extraRuns, String ballType, String runFromWhat){
         SharedPreferences sharedPreferences = getSharedPreferences("match_prefs", MODE_PRIVATE);
         long inningsId = sharedPreferences.getLong("Innings_id", -1);
         long overId = sharedPreferences.getLong("over_id", -1);
         long bowlerId = sharedPreferences.getLong("bowler_id", -1);
         long strikerId = sharedPreferences.getLong("striker_id", -1);
         long nonStrikerId = sharedPreferences.getLong("non_striker_id", -1);
         long partnership_id = sharedPreferences.getLong("partnership_id", -1);
         long ballId = 0;
         ballId = databaseHelper.insertBallDataForNb(overId, extraRuns, strikerId, nonStrikerId);
         databaseHelper.updateExtrasTable(ballId, ballType, extraRuns);
         switch (runFromWhat){
             case "Bat":
                 databaseHelper.updateBatsmanStatsForNb(inningsId, strikerId, extraRuns, "Bat");
                 databaseHelper.updateBowlerStatsForNb(inningsId, bowlerId, extraRuns, "Bat");
                 databaseHelper.updatePartnershipForNb(partnership_id, extraRuns, "Bat");
                 break;
             case "LegBye":
                 databaseHelper.updateBatsmanStatsForNb(inningsId, strikerId, extraRuns, "Bye");
                 databaseHelper.updateBowlerStatsForNb(inningsId, bowlerId, extraRuns, "Bye");
                 databaseHelper.updatePartnershipForNb(partnership_id, extraRuns, "Bye");
                 break;
             case "Bye":
                 databaseHelper.updateBatsmanStatsForNb(inningsId, strikerId, extraRuns, "LegBye");
                 databaseHelper.updateBowlerStatsForNb(inningsId, bowlerId, extraRuns, "LegBye");
                 databaseHelper.updatePartnershipForNb(partnership_id, extraRuns, "LegBye");
                 break;
         }updateTeamStatsForNoBall(extraRuns, runFromWhat);
         updateScoreInSharedPreferences(ballType, extraRuns);
         rotateStrike(extraRuns);
         checkAndHandleOverEnd();
     }
     private void updateTeamStatsFor0to6(int runs){
        SharedPreferences sharedPreferences = getSharedPreferences("match_prefs", MODE_PRIVATE);
        long teamStatsId = sharedPreferences.getLong("teamStatsId", -1);
        databaseHelper.updateTeamStatsFor0to6(teamStatsId, runs);
     }
     private void updateTeamStatsForByLegBy(int runs){
        SharedPreferences sharedPreferences = getSharedPreferences("match_prefs",MODE_PRIVATE);
        long teamStatsId = sharedPreferences.getLong("teamStatsId", -1);
        databaseHelper.updateTeamStatsForByesAndLegByes(teamStatsId, runs);
     }
    private void updateTeamStatsForWide(int runs){
        SharedPreferences sharedPreferences = getSharedPreferences("match_prefs",MODE_PRIVATE);
        long teamStatsId = sharedPreferences.getLong("teamStatsId", -1);
        databaseHelper.updateTeamStatsForWide(teamStatsId, runs);
    }
    private void updateTeamStatsForNoBall(int runs, String runsSource){
        SharedPreferences sharedPreferences = getSharedPreferences("match_prefs",MODE_PRIVATE);
        long teamStatsId = sharedPreferences.getLong("teamStatsId", -1);
        databaseHelper.updateTeamStatsForNoBall(teamStatsId, runs, runsSource);
    }
    private void setNewPlayer(String playerType) {
        // Inflate your dialog layout
        Log.d(TAG, "setNewBatsman: " + playerType);
        View playerDialogView = getLayoutInflater().inflate(R.layout.activity_selecting_players, null);
        // Create and configure the dialog
        AlertDialog.Builder playerBuilder = new AlertDialog.Builder(this);
        playerBuilder.setView(playerDialogView);
        AlertDialog playerDialog = playerBuilder.create();
        // Show the dialog
        playerDialog.show();
        // Set dialog width and height to match your desired values
        if (playerDialog.getWindow() != null) {
            playerDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        // Find buttons and input fields in the dialog
        TextView playerTypeTextView = playerDialogView.findViewById(R.id.playerTypeTextView);
        String newPlayerType = playerType.equals("bowler") ? "Bowler" : "Batsman";
        String formattedText = getString(R.string.selectNewPlayerSetText, newPlayerType);
        playerTypeTextView.setText(formattedText);
        EditText playerNameEditText = playerDialogView.findViewById(R.id.playerNameEditText);
        Button submitButton = playerDialogView.findViewById(R.id.submitButton);
        Button backButton = playerDialogView.findViewById(R.id.back_button);

        // Submit Button Click Handler
        submitButton.setOnClickListener(v -> {
            String player_name = String.valueOf(playerNameEditText.getText());
            updatePlayerDataInSp(playerType, player_name);
            if(playerType.equals("bowler")) insertOver();
            playerDialog.dismiss();
        });
        // Back Button Click Handler
        backButton.setOnClickListener(v -> {
            // Just dismiss the dialog without saving
            playerDialog.dismiss();
        });
        // Show the dialog
        playerDialog.show();
    }
    private void updatePlayerDataInSp(String player_type, String player_name){
        SharedPreferences sharedPreferences = getSharedPreferences("match_prefs",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        long innings_id  = sharedPreferences.getLong("Innings_id", -1);
        editor.putString(player_type + " name",player_name);
        editor.putInt("currentOverScore", 0);
        editor.apply();
        if(player_type.equals("bowler")) updateNewBowlerToDB(player_name, player_type, innings_id);
        else updateNewBatsmanToDB(player_name, player_type, innings_id);
    }
    private void updateNewBatsmanToDB(String name, String player_type, long innings_id) {
        SharedPreferences sharedPreferences = getSharedPreferences("match_prefs", MODE_PRIVATE);
        long teamId = sharedPreferences.getLong("teamA_id", -1);
        long player_id = databaseHelper.insertPlayer(name, player_type, teamId);
        String batter = player_type.equals("striker") ? "non_striker_id" : "striker_id";
        databaseHelper.initializeBatsmanStats(player_id, innings_id);
        long player2_id = sharedPreferences.getLong(batter, -1);
        Log.d(TAG, "striker" + player_id + "non striker" + player2_id);
        databaseHelper.insertPartnership(innings_id,player_id, player2_id, 0, 0);
    }
    private void updateNewBowlerToDB(String name, String player_type, long innings_id) {
        SharedPreferences sharedPreferences = getSharedPreferences("match_prefs", MODE_PRIVATE);
        long teamId = sharedPreferences.getLong("teamB_id", -1);
        long player_id = databaseHelper.insertPlayer(name, player_type, teamId);
        databaseHelper.initializeBowlerStats(player_id, innings_id);
    }
    private void incrementPlayedBallsInSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("match_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        long playedBalls = sharedPreferences.getLong("playedBalls", 0);
        playedBalls++;
        editor.putLong("playedBalls", playedBalls);
        editor.apply();
        Log.d(TAG, "incrementPlayedBalls:  balls incremented by 1 and current balls are " + playedBalls);
        //checkAndHandleOverEnd(playedBalls);
    }
    public void checkAndHandleOverEnd() {
        SharedPreferences sharedPreferences = getSharedPreferences("match_prefs", MODE_PRIVATE);
        long playedBalls = sharedPreferences.getLong("playedBalls", -1);
        long totalBalls = sharedPreferences.getLong("totalBalls", 0);
        long target = sharedPreferences.getLong("target", -1);
        int currentOverScore = sharedPreferences.getInt("currentOverScore", -1);
        String currentInnings = sharedPreferences.getString("currentInnings","");
        Log.d(TAG, "checkAndHandleOverEnd: current ongoing innings " + currentInnings);
        int currentScore = sharedPreferences.getInt("score", -1);
        if (playedBalls % 6 == 0 && playedBalls != 0 && playedBalls != totalBalls) {
            Log.d(TAG, "checkAndHandleOverEnd:" + playedBalls / 6 + "Over has ended");
            if(currentOverScore == 0) insertMaidenOver();
            setNewPlayer("bowler");
            rotateStrike(1);
            //insertOver();
        }
        if(playedBalls == totalBalls || currentScore >= target) {
            handleInningsEnd(currentInnings);
        }
    }
    public void handleInningsEnd(String currentInnings){
        SharedPreferences sharedPreferences = getSharedPreferences("match_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if(currentInnings.equals("first")){
            Log.d(TAG, "handleInningsEnd: first innings is ended and second innings starts");
            int target = sharedPreferences.getInt("score", -1);
            editor.putString("currentInnings", "second");
            editor.putInt("score", 0);
            editor.putLong("target", target + 1);
            editor.putLong("playedBalls", 0);
            editor.apply();
            //showInningsEndDialog("First Innings Completed", "Second Innings Starting Soon!", 5);
            secondInnings();
        }else{
            Log.d(TAG, "handleInningsEnd: match is over");
            editor.putString("currentInnings", "matchOver");
            editor.putLong("target", -10000);
            editor.apply();
            showInningsEndDialog("Match Over", "Thanks for Playing!", 3);
            floatingButton.setEnabled(false);
            floatingButton.setAlpha(0.5f);     // Optional: Change visual appearance
        }
    }
    public void updateScoreInSharedPreferences(String ballType, int runs){
        SharedPreferences sharedPreferences = getSharedPreferences("match_prefs",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        int score = sharedPreferences.getInt("score", -1);
        int currentOverScore = sharedPreferences.getInt("currentOverScore", -1);
        if(ballType.equals("Normal")){
            score += runs;
            currentOverScore += runs;
        }
        else if(ballType.equals("No-ball") || ballType.equals("Wide") || ballType.equals("NoBall")) {
            score += runs + 1;
            currentOverScore += runs + 1;
        }
        else {
            score += runs;
            currentOverScore += runs;
        }
        Log.d(TAG, "updateScoreInSharedPreferences: " + score);
        editor.putInt("score", score);
        editor.putInt("currentOverScore", currentOverScore);
        editor.apply();
    }

    private void showInningsEndDialog(String title, String message, int durationInSeconds) {
        boolean isDialogClosed = false;
        SharedPreferences sharedPreferences = getSharedPreferences("match_prefs", MODE_PRIVATE);
        String currentInnings = sharedPreferences.getString("currentInnings", "");
        Log.d(TAG, "showInningsEndDialog:  in the dialog -=-==-=-=-=-==-=-=-=-=-=-=-=-=" + currentInnings);
        // Create a dialog
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_innings_end); // Use a custom layout if needed
        dialog.setCancelable(false); // Prevent manual dismissal

        // Set the title and message
        TextView titleText = dialog.findViewById(R.id.dialogTitle);
        TextView messageText = dialog.findViewById(R.id.dialogMessage);
        titleText.setText(title);
        messageText.setText(message);

        // Show the dialog
        dialog.show();
        // Auto-dismiss the dialog after specified duration
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "run: dialog about to be closed");
                if (dialog.isShowing()) {
                    dialog.dismiss();
                    //secondInnings();
                }
                dialog.dismiss();
            }
        }, durationInSeconds * 1000L); // Convert seconds to milliseconds
    }
    private void insertOver(){
        Log.d(TAG, "insertOver: inside the insert over");
        SharedPreferences sharedPreferences = getSharedPreferences("match_prefs", MODE_PRIVATE);
        long inningsId = sharedPreferences.getLong("Innings_id", -1);
        long bowlerId = sharedPreferences.getLong("bowler_id", -1);
        databaseHelper.insertOver(inningsId, 2, bowlerId, 0);
    }

    private void insertMaidenOver(){
        SharedPreferences sharedPreferences =getSharedPreferences("match_prefs", MODE_PRIVATE);
        long overId = sharedPreferences.getLong("over_id", -1);
        databaseHelper.insertMaidenOver(overId);
    }
    private void secondInnings(){
        Log.d(TAG, "secondInnings: entered second innings");
        Intent intent = new Intent(MatchActivity.this, SelectingSrNsBowActivity.class);
        startActivity(intent);
    }







}
