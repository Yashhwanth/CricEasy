package com.cricketscoringapp.criceasy;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
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
        Button btn_0, btn_1, btn_2, btn_3, btn_4, btn_5, btn_6, btn_7, btn_8, btn_bye, btn_leg_bye, btn_wide, btn_noball;
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
    //striker rotating
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
    private void showExtrasDialog(String ballType, AlertDialog parentDialog) {
        // Inflate the extras dialog layout
        View extrasDialogView = getLayoutInflater().inflate(R.layout.activity_dialog_for_extras, null);
        // Check if the ball type is "No Ball" and set radio group visibility accordingly

        EditText extraRunsInput = extrasDialogView.findViewById(R.id.extra_runs_input);
        Button btnCancel = extrasDialogView.findViewById(R.id.btn_cancel);
        Button btnSubmit = extrasDialogView.findViewById(R.id.btn_submit);
        RadioGroup radioGroup = extrasDialogView.findViewById(R.id.noballrg);
        if ("No Ball".equals(ballType)) {
            radioGroup.setVisibility(View.VISIBLE);  // Show radio group for No Ball
        } else {
            radioGroup.setVisibility(View.GONE);  // Hide radio group for other ball types
        }


        // Set the ball type label
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
                        //handleScoringForNoBall(extraRuns, ballType);
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
         databaseHelper.updateBatsmanForByLByes(innings_id, batsman_id);
         databaseHelper.updateBowlerForByLBes(innings_id, bowler_id, ballType);
         databaseHelper.updatePartnershipForByLByes(1);
         databaseHelper.updateExtrasTable(ball_id, ballType, extraRuns);
         rotateStrike(extraRuns);
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

     }

}
