package com.cricketscoringapp.criceasy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
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
        Button btn_0, btn_1, btn_2, btn_3, btn_4, btn_5, btn_6;
        btn_0 = dialogView.findViewById(R.id.button21);
        btn_1 = dialogView.findViewById(R.id.button22);
        btn_2 = dialogView.findViewById(R.id.button23);
        btn_3 = dialogView.findViewById(R.id.button20);
        btn_4 = dialogView.findViewById(R.id.button19);
        btn_5 = dialogView.findViewById(R.id.button17);
        btn_6 = dialogView.findViewById(R.id.button18);


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
            // Handle click for "0" button
            handleScoring(0);
            dialog.dismiss(); // Dismiss the dialog after the action is performed
        });
        // Set up OnClickListeners for the dialog buttons
        btn_1.setOnClickListener(view -> {
            // Handle click for "0" button
            handleScoring(1);
            dialog.dismiss(); // Dismiss the dialog after the action is performed
        });
        // Set up OnClickListeners for the dialog buttons
        btn_2.setOnClickListener(view -> {
            // Handle click for "0" button
            handleScoring(2);
            dialog.dismiss(); // Dismiss the dialog after the action is performed
        });
        // Set up OnClickListeners for the dialog buttons
        btn_3.setOnClickListener(view -> {
            // Handle click for "0" button
            handleScoring(3);
            dialog.dismiss(); // Dismiss the dialog after the action is performed
        });
        // Set up OnClickListeners for the dialog buttons
        btn_4.setOnClickListener(view -> {
            // Handle click for "0" button
            handleScoring(4);
            dialog.dismiss(); // Dismiss the dialog after the action is performed
        });
        // Set up OnClickListeners for the dialog buttons
        btn_5.setOnClickListener(view -> {
            // Handle click for "0" button
            handleScoring(5);
            dialog.dismiss(); // Dismiss the dialog after the action is performed
        });
        // Set up OnClickListeners for the dialog buttons
        btn_6.setOnClickListener(view -> {
            // Handle click for "0" button
            handleScoring(6);
            dialog.dismiss(); // Dismiss the dialog after the action is performed
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

    private void handleScoring(int runs) {
        // Handle the run scoring logic here
        // For example, update the score or perform necessary action based on the button click
        // You can update the score or trigger any other logic required
        SharedPreferences sharedPreferences = getSharedPreferences("match_prefs",MODE_PRIVATE);
        long over_id = sharedPreferences.getLong("over_id", -1);
        String type_of_ball = "Legal";
        int runs_scored = runs;
        long striker = sharedPreferences.getLong("striker_id", -1);
        long non_striker = sharedPreferences.getLong("striker_id", -1);
        long ball_id = databaseHelper.insertBallData(over_id, type_of_ball, runs_scored, striker, non_striker);
        Toast.makeText(this, "Runs scored: " + runs + ball_id, Toast.LENGTH_SHORT).show();
    }
}
