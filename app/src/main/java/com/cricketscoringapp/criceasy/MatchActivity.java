package com.cricketscoringapp.criceasy;

import static android.content.ContentValues.TAG;

import android.content.SharedPreferences;
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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.cricketscoringapp.criceasy.Database.DatabaseHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class MatchActivity extends AppCompatActivity {
    private Button btnLayout1, btnLayout2, btnLayout3, btnLayout4, btnLayout5;

    private FloatingActionButton floatingbutton;
    private DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match); // Make sure this layout file exists
        updateCurrentActivityInPreferences();

        // Initialize the DatabaseHelper
        databaseHelper = new DatabaseHelper(this);


        btnLayout1 = findViewById(R.id.button5);
        btnLayout2 = findViewById(R.id.button6);
        btnLayout3 = findViewById(R.id.button7);
        btnLayout4 = findViewById(R.id.button8);
        btnLayout5 = findViewById(R.id.button25);
        floatingbutton = findViewById(R.id.floatingbutton);

        floatingbutton.setOnClickListener(view ->{
                popup();
        });

        // Set onClickListeners to show the corresponding fragments
        btnLayout1.setOnClickListener(view -> showFragment(new InfoFragment()));
        btnLayout2.setOnClickListener(view -> showFragment(new liveFragment()));
//        btnLayout3.setOnClickListener(view -> showFragment(new Fragment3()));
//        btnLayout4.setOnClickListener(view -> showFragment(new Fragment4()));
//        btnLayout5.setOnClickListener(view -> showFragment(new Fragment5()));

        // Load the first fragment by default
        if (savedInstanceState == null) {
            showFragment(new liveFragment());
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        // Update current activity in SharedPreferences
        updateCurrentActivityInPreferences();
    }


    public void popup() {
        // Inflate the scoring layout
        View dialogView = getLayoutInflater().inflate(R.layout.activity_scoring, null);
        Button btn_0, btn_1, btn_2, btn_3, btn_4, btn_5, btn_6, btn_7, btn_8, btn_bye, btn_leg_bye, btn_wide, btn_noball, btn_wicket;
        btn_0 = dialogView.findViewById(R.id.button21);
        btn_1 = dialogView.findViewById(R.id.button22);
        btn_2 = dialogView.findViewById(R.id.button23);
        btn_3 = dialogView.findViewById(R.id.button20);
        btn_4 = dialogView.findViewById(R.id.button19);
        btn_5 = dialogView.findViewById(R.id.button17);
        btn_6 = dialogView.findViewById(R.id.button18);
        btn_7 = dialogView.findViewById(R.id.button15);
        btn_8 = dialogView.findViewById(R.id.button16);
        btn_bye = dialogView.findViewById(R.id.button9);
        btn_leg_bye = dialogView.findViewById(R.id.button10);
        btn_wide = dialogView.findViewById(R.id.button14);
        btn_noball = dialogView.findViewById(R.id.button12);
        btn_wicket = dialogView.findViewById(R.id.button24);


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
        btn_0.setOnClickListener(view -> {
            handleScoringFor0To6(0);
            dialog.dismiss();
        });
        btn_1.setOnClickListener(view -> {
            handleScoringFor0To6(1);
            dialog.dismiss();
        });
        btn_2.setOnClickListener(view -> {
            handleScoringFor0To6(2);
            dialog.dismiss();
        });
        btn_3.setOnClickListener(view -> {
            handleScoringFor0To6(3);
            dialog.dismiss();
        });
        btn_4.setOnClickListener(view -> {
            handleScoringFor0To6(4);
            dialog.dismiss();
        });
        btn_5.setOnClickListener(view -> {
            handleScoringFor0To6(5);
            dialog.dismiss();
        });
        btn_6.setOnClickListener(view -> {
            handleScoringFor0To6(6);
            dialog.dismiss();
        });
        btn_7.setOnClickListener(view -> {
            handleScoringFor0To6(7);
            dialog.dismiss();
        });
        btn_8.setOnClickListener(view -> {
            handleScoringFor0To6(8);
            dialog.dismiss();
        });
        btn_bye.setOnClickListener(view -> {
            showExtrasDialog("Bye", dialog);
        });
        btn_leg_bye.setOnClickListener(view -> {
            showExtrasDialog("Leg Bye", dialog);
        });
        btn_wide.setOnClickListener(view -> {
            showExtrasDialog("Wide", dialog);
        });
        btn_noball.setOnClickListener(view -> {
            showExtrasDialog("No Ball", dialog);
        });
        btn_wicket.setOnClickListener(view ->{
            showWicketDialog(dialog);
        });
    }
    private void showFragment(Fragment fragment) {
        // Begin a fragment transaction to replace the current fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, fragment); // fragmentContainer is the container where the fragments will be loaded
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
        EditText extraRunsInput = extrasDialogView.findViewById(R.id.extra_runs_input);
        Button btnCancel = extrasDialogView.findViewById(R.id.btn_cancel);
        Button btnSubmit = extrasDialogView.findViewById(R.id.btn_submit);
        RadioGroup radioGroup = extrasDialogView.findViewById(R.id.noballrg);
        if ("No Ball".equals(ballType)) {
            radioGroup.setVisibility(View.VISIBLE);  // Show radio group for No Ball
        } else {
            radioGroup.setVisibility(View.GONE);  // Hide radio group for other ball types
        }
        TextView ballTypeLabel = extrasDialogView.findViewById(R.id.ball_type_label);
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
            int radioBtnId = radioGroup.getCheckedRadioButtonId(); // Get the selected RadioButton ID
            String runFromWhat = null; // Variable to store the tag value
            if (radioGroup.getVisibility() == View.VISIBLE) {
                if (radioBtnId == -1) { // No RadioButton is selected
                    Toast.makeText(this, "Please select the source of runs (From bat or Byes/Leg Byes).", Toast.LENGTH_SHORT).show();
                } else { // A RadioButton is selected
                    RadioButton radioBtn = extrasDialogView.findViewById(radioBtnId);
                    runFromWhat = (String) radioBtn.getTag(); // Get the tag of the selected RadioButton
                }
            }
            if (!extraRunsStr.isEmpty()) {
                int extraRuns = Integer.parseInt(extraRunsStr);
                // Check the ball type and call the corresponding method
                switch (ballType) {
                    case "Bye":
                    case "Leg Bye":
                        handleScoringForByesAndLegByes(extraRuns, ballType);
                        break;
                    case "Wide":
                        handleScoringForWide(extraRuns, ballType);
                        break;
                    case "No Ball":
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
        EditText runs_input = wicketDialogView.findViewById(R.id.runs_input_et);

        Button cancelButton = wicketDialogView.findViewById(R.id.cancel_btn);
        Button submitButton = wicketDialogView.findViewById(R.id.submit_btn);

        RadioGroup dismissalTypeRadioGroup = wicketDialogView.findViewById(R.id.dismissal_type_rg); // dismissal type

        LinearLayout runs_input_layout = wicketDialogView.findViewById(R.id.runs_in_wicket_layout); //runs input
        LinearLayout out_batsman_layout = wicketDialogView.findViewById(R.id.out_batsman_layout); // out batsman
        LinearLayout out_end_layout = wicketDialogView.findViewById(R.id.out_end_layout); //out end
        LinearLayout runs_from_bat_or_by_layout = wicketDialogView.findViewById(R.id.runs_in_runout_layout); // from bat/by layout
        LinearLayout ball_type_in_run_out_layout = wicketDialogView.findViewById(R.id.ball_type_in_runout_layout); // ball type in run out layout
        LinearLayout stumped_layout = wicketDialogView.findViewById(R.id.stumped_ball_type_layout); //ball type in run out

        RadioGroup out_batsman_radio_group = wicketDialogView.findViewById(R.id.out_batsman_rg);
        RadioGroup out_end_radio_group = wicketDialogView.findViewById(R.id.out_end_rg);
        RadioGroup ball_type_in_run_out_radio_group = wicketDialogView.findViewById(R.id.ball_type_rg);
        RadioGroup from_bat_or_by_radio_group = wicketDialogView.findViewById(R.id.runs_source_rg);
        RadioGroup stumped_ball_type = wicketDialogView.findViewById(R.id.stumped_ball_type_rg);


        runs_input_layout.setVisibility(View.GONE);
        out_batsman_layout.setVisibility(View.GONE);
        out_end_layout.setVisibility(View.GONE);
        runs_from_bat_or_by_layout.setVisibility(View.GONE);
        ball_type_in_run_out_layout.setVisibility(View.GONE);
        stumped_layout.setVisibility(View.GONE);


        // Add listener to RadioGroup to detect selection
        dismissalTypeRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.run_out_rb) {
                runs_input_layout.setVisibility(View.VISIBLE);
                out_batsman_layout.setVisibility(View.VISIBLE);
                out_end_layout.setVisibility(View.VISIBLE);
                ball_type_in_run_out_layout.setVisibility(View.VISIBLE);
                runs_from_bat_or_by_layout.setVisibility(View.VISIBLE);
                stumped_layout.setVisibility(View.GONE);
                ball_type_in_run_out_radio_group.setOnCheckedChangeListener((group1, checkedId1) -> {
                    if(checkedId1 == R.id.wide_rb){
                        runs_from_bat_or_by_layout.setVisibility(View.GONE);
                    }else{
                        runs_from_bat_or_by_layout.setVisibility(View.VISIBLE);
                    }
                });
            }else if(checkedId == R.id.stumped_rb) {
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
                    databaseHelper.updateBatsmanStatsForWicket(innings_id, striker, runs, null, null, "BOWLED");
                    databaseHelper.updateBowlerStatsForWicket(innings_id, bowler_id, runs, null, null, "BOWLED");
                    databaseHelper.insertBallDataForWicket(over_id, "Legal", runs, striker, non_striker_id);
                    databaseHelper.updateTeamStatsForBowCauLbw(team_stats_id);
                    incrementPlayedBalls();
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
                    if(ballTypeInRo.equals("Normal"))    incrementPlayedBalls();
                    databaseHelper.updateBatsmanStatsForWicket(innings_id, striker, runs, ballTypeInRo, runsFrom, "RUN-OUT");
                    databaseHelper.updateBowlerStatsForWicket(innings_id, bowler_id, runs, ballTypeInRo, runsFrom, "RUN-OUT");
                    databaseHelper.insertBallDataForWicket(over_id, ballTypeInRo, runs, striker, non_striker_id);
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
                    if(ballType.equals("Normal"))    incrementPlayedBalls();
                    databaseHelper.updateBatsmanStatsForWicket(innings_id, striker, runs, ballType, null, "STUMPED");
                    databaseHelper.updateBowlerStatsForWicket(innings_id, bowler_id, runs, ballType, null, "STUMPED");
                    databaseHelper.insertBallDataForWicket(over_id, ballType, runs, striker, non_striker_id);
                    databaseHelper.updateTeamStatsForStumping(team_stats_id, ballType);
                    break;
            }
            setNewBatsman(playerType);
            wicketDialog.dismiss();
            parentDialog.dismiss();
        });
    }
    private void handleScoringFor0To6(int runs) {
        SharedPreferences sharedPreferences = getSharedPreferences("match_prefs",MODE_PRIVATE);
        long innings_id = sharedPreferences.getLong("Innings_id",-1);
        long over_id = sharedPreferences.getLong("over_id", -1);
        String type_of_ball = "Legal";
        long striker = sharedPreferences.getLong("striker_id", -1);
        long non_striker = sharedPreferences.getLong("non_striker_id", -1);
        long bowler = sharedPreferences.getLong("bowler_id",-1);
        long ball_id = databaseHelper.insertBallDataFor0To6(over_id, type_of_ball, runs, striker, non_striker);
        databaseHelper.updatePartnershipFor0to6(runs, 1);
        rotateStrike(runs);
        databaseHelper.updateBatsmanStatsFor0To6(innings_id, striker, runs);
        databaseHelper.updateBowlerStatsFor0to6(innings_id, bowler, runs);
        updateTeamStatsFor0to6(runs);
        incrementPlayedBalls();
        Toast.makeText(this, "Runs scored: " + runs + ball_id, Toast.LENGTH_SHORT).show();
    }
     private void handleScoringForByesAndLegByes(int extraRuns, String ballType) {
         SharedPreferences sharedPreferences = getSharedPreferences("match_prefs", MODE_PRIVATE);
         long innings_id = sharedPreferences.getLong("Innings_id", -1);
         long over_id = sharedPreferences.getLong("over_id", -1);
         long batsman_id = sharedPreferences.getLong("striker_id", -1);
         long non_striker_id = sharedPreferences.getLong("non_striker_id", -1);
         long bowler_id = sharedPreferences.getLong("bowler_id", -1);
         long ball_id = databaseHelper.insertBallDataForByLByes(over_id, ballType, extraRuns, batsman_id, non_striker_id);
         databaseHelper.updateBatsmanForByLByes(innings_id, batsman_id, 1);
         databaseHelper.updateBowlerForByLBes(innings_id, bowler_id, ballType);
         databaseHelper.updatePartnershipForByLByes(1);
         databaseHelper.updateExtrasTable(ball_id, ballType, extraRuns);
         updateTeamStatsForByLegBy(extraRuns);
         rotateStrike(extraRuns);
         incrementPlayedBalls();
     }
     private void handleScoringForWide(int extraRuns, String ballType){
         SharedPreferences sharedPreferences = getSharedPreferences("match_prefs", MODE_PRIVATE);
         long innings_id = sharedPreferences.getLong("Innings_id", -1);
         long over_id = sharedPreferences.getLong("over_id", -1);
         long bowler_id = sharedPreferences.getLong("bowler_id", -1);
         long batsman_id = sharedPreferences.getLong("striker_id", -1);
         long non_striker_id = sharedPreferences.getLong("non_striker_id", -1);
         long ball_id = databaseHelper.insertBallDataForWide(over_id, extraRuns, batsman_id, non_striker_id);
         databaseHelper.updateBowlerForWide(innings_id, bowler_id, extraRuns);
         databaseHelper.updateExtrasTable(ball_id, ballType, extraRuns);
         updateTeamStatsForWide(extraRuns);
         rotateStrike(extraRuns);

     }
     private void handleScoringForNoBall(int extraRuns, String ballType, String runFromWhat){
         SharedPreferences sharedPreferences = getSharedPreferences("match_prefs", MODE_PRIVATE);
         long innings_id = sharedPreferences.getLong("Innings_id", -1);
         long over_id = sharedPreferences.getLong("over_id", -1);
         long bowler_id = sharedPreferences.getLong("bowler_id", -1);
         long striker = sharedPreferences.getLong("striker_id", -1);
         long non_striker_id = sharedPreferences.getLong("non_striker_id", -1);
         long partnership_id = sharedPreferences.getLong("partnership_id", -1);
         switch (runFromWhat){
             case "Bat":
                 databaseHelper.updateBatsmanStatsForNb(innings_id, striker, extraRuns, "Bat");
                 databaseHelper.updateBowlerStatsForNb(innings_id, bowler_id, extraRuns, "Bat");
                 databaseHelper.updatePartnershipForNb(partnership_id, extraRuns, "Bat");
                 long ball_id = databaseHelper.insertBallDataForNb(over_id, extraRuns, striker, non_striker_id);
                 databaseHelper.updateExtrasTable(ball_id, ballType, extraRuns);
                 rotateStrike(extraRuns);
                 break;
             case "Lb":
                 databaseHelper.updateBatsmanStatsForNb(innings_id, striker, extraRuns, "Bye");
                 databaseHelper.updateBowlerStatsForNb(innings_id, bowler_id, extraRuns, "Bye");
                 databaseHelper.updatePartnershipForNb(partnership_id, extraRuns, "Bye");
                 long balll_id = databaseHelper.insertBallDataForNb(over_id, extraRuns, striker, non_striker_id);
                 databaseHelper.updateExtrasTable(balll_id, ballType, extraRuns);
                 rotateStrike(extraRuns);
                 break;
             case "By":
                 databaseHelper.updateBatsmanStatsForNb(innings_id, striker, extraRuns, "Leg Bye");
                 databaseHelper.updateBowlerStatsForNb(innings_id, bowler_id, extraRuns, "Leg Bye");
                 databaseHelper.updatePartnershipForNb(partnership_id, extraRuns, "Leg Bye");
                 long ballll_id = databaseHelper.insertBallDataForNb(over_id, extraRuns, striker, non_striker_id);
                 databaseHelper.updateExtrasTable(ballll_id, ballType, extraRuns);
                 rotateStrike(extraRuns);
                 break;
         }updateTeamStatsForNoBall(extraRuns, runFromWhat);
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
    private void setNewBatsman(String player_type) {
        // Inflate your dialog layout
        Log.d(TAG, "setNewBatsman: " + player_type);
        View batsmanDialogView = getLayoutInflater().inflate(R.layout.activity_selecting_players, null);

        // Create and configure the dialog
        AlertDialog.Builder batsmanBuilder = new AlertDialog.Builder(this);
        batsmanBuilder.setView(batsmanDialogView);
        AlertDialog batsmanDialog = batsmanBuilder.create();

        // Show the dialog
        batsmanDialog.show();

        // Set dialog width and height to match your desired values
        if (batsmanDialog.getWindow() != null) {
            batsmanDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        // Find buttons and input fields in the dialog
        EditText editText = batsmanDialogView.findViewById(R.id.player_name_edit_text);
        Button submit_btn = batsmanDialogView.findViewById(R.id.submit_button);
        Button back_btn = batsmanDialogView.findViewById(R.id.back_button);
        RadioGroup role_radio_group = batsmanDialogView.findViewById(R.id.radioGroup);
        RadioGroup bat_style_radio_group = batsmanDialogView.findViewById(R.id.batGroup);
        RadioGroup bowl_style_radio_group = batsmanDialogView.findViewById(R.id.bowlStyleRadioGroup);



        // Submit Button Click Handler
        submit_btn.setOnClickListener(v -> {
            String player_name = String.valueOf(editText.getText());
            int role_button_id = role_radio_group.getCheckedRadioButtonId();
            int bat_style_button_id = bat_style_radio_group.getCheckedRadioButtonId();
            int bowl_style_button_id = bowl_style_radio_group.getCheckedRadioButtonId();
            if(role_button_id == -1 || bat_style_button_id == -1 || bowl_style_button_id == -1 || player_name.isEmpty()){
                Toast.makeText(this, "Please make all selections", Toast.LENGTH_SHORT).show();
                return;
            }
            else {
                RadioButton role_button = batsmanDialogView.findViewById(role_button_id);
                RadioButton bat_button = batsmanDialogView.findViewById(bat_style_button_id);
                RadioButton bowl_button = batsmanDialogView.findViewById(bowl_style_button_id);
                String role = role_button.getTag().toString();
                String bat_style = bat_button.getTag().toString();
                String bowl_style = bowl_button.getTag().toString();
                updatePlayerDataInSp(player_type, player_name, role, bat_style, bowl_style);
                batsmanDialog.dismiss();
            }
        });
        // Back Button Click Handler
        back_btn.setOnClickListener(v -> {
            // Just dismiss the dialog without saving
            batsmanDialog.dismiss();
        });
        // Show the dialog
        batsmanDialog.show();
    }
    private void updatePlayerDataInSp(String player_type, String player_name, String role, String bat_style, String bowl_style){
        SharedPreferences sharedPreferences = getSharedPreferences("match_prefs",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        long innings_id  = sharedPreferences.getLong("Innings_id", -1);
        editor.putString(player_type + " name",player_name);
        editor.putString(player_type + " ROLE",role);
        editor.putString(player_type + " BS",bat_style);
        editor.putString(player_type + " BOS",bowl_style);
        editor.apply();
        if(player_type.equals("bowler")) updateNewBowlerToDB(player_name, role, bat_style, bowl_style, player_type, innings_id);
        else updateNewBatsmanToDB(player_name, role, bat_style, bowl_style, player_type, innings_id);
    }

    // Method to update player details in the database
    private void updateNewBatsmanToDB(String name, String role, String batStyle, String bowlStyle, String player_type, long innings_id) {
        long player_id = databaseHelper.insertPlayer(name, role, batStyle, bowlStyle, player_type);
        SharedPreferences sharedPreferences = getSharedPreferences("match_prefs", MODE_PRIVATE);
        String batter = player_type.equals("striker") ? "non_striker_id" : "striker_id";
        databaseHelper.initializeBatsmanStats(player_id, innings_id);
        long player2_id = sharedPreferences.getLong(batter, -1);
        Log.d(TAG, "striker" + player_id + "non striker" + player2_id);
        databaseHelper.insertPartnership(innings_id,player_id, player2_id, 0, 0);
    }
    private void updateNewBowlerToDB(String name, String role, String batStyle, String bowlStyle, String player_type, long innings_id) {
        long player_id = databaseHelper.insertPlayer(name, role, batStyle, bowlStyle, player_type);
        databaseHelper.initializeBowlerStats(player_id, innings_id);
    }
    private void incrementPlayedBalls() {
        SharedPreferences sharedPreferences = getSharedPreferences("match_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        long playedBalls = sharedPreferences.getLong("playedBalls", 0);
        playedBalls++;
        editor.putLong("playedBalls", playedBalls);
        editor.apply();
        Log.d(TAG, "incrementPlayedBalls:  balls incremented by 1 and current balls are " + playedBalls);
        checkAndHandleOverEnd(playedBalls);
    }

    public void checkAndHandleOverEnd(long playedBalls) {
        SharedPreferences sharedPreferences = getSharedPreferences("match_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        long totalBalls = sharedPreferences.getLong("totalBalls", 0);
        String currentInnings = sharedPreferences.getString("currentInnings","");
        if (playedBalls % 6 == 0 && playedBalls != 0 && playedBalls != totalBalls) {
            Log.d(TAG, "checkAndHandleOverEnd:" + playedBalls / 6 + "Over has ended");
            setNewBatsman("bowler");
        }
        if(playedBalls == totalBalls) {
            if(currentInnings.equals("first")){
                editor.putString("currentInnings", "second");
                editor.putLong("target", 50);
                editor.putLong("playedBalls", 0);
                editor.apply();
            }else{
                editor.putString("currentInnings", "matchOver");
                editor.putLong("target", -10000);
                editor.apply();
            }

        }


    }





}
