package com.cricketscoringapp.criceasy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MatchActivity extends AppCompatActivity {
    private Button btnLayout1, btnLayout2, btnLayout3, btnLayout4, btnLayout5;
    private View layout1, layout2, layout3, layout4, layout5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match); // Make sure this layout file exists
        updateCurrentActivityInPreferences();

        btnLayout1 = findViewById(R.id.button5);
        btnLayout2 = findViewById(R.id.button6);
        btnLayout3 = findViewById(R.id.button7);
        btnLayout4 = findViewById(R.id.button8);
        btnLayout5 = findViewById(R.id.button25);

        layout1 = findViewById(R.id.lay1);
        layout2 = findViewById(R.id.lay2);
        layout3 = findViewById(R.id.lay3);
        layout4 = findViewById(R.id.lay4);
        layout5 = findViewById(R.id.lay5);

        btnLayout1.setOnClickListener(view -> showLayout(1));
        btnLayout2.setOnClickListener(view -> showLayout(2));
        btnLayout3.setOnClickListener(view -> showLayout(3));
        btnLayout4.setOnClickListener(view -> showLayout(4));
        btnLayout5.setOnClickListener(view -> showLayout(5));

    }

    @Override
    protected void onResume() {
        super.onResume();
        // Update current activity in SharedPreferences
        updateCurrentActivityInPreferences();
    }

    public void info_page(View view) {

    }

    private void showLayout(int layoutNumber) {
        // Hide all layouts first
        layout1.setVisibility(View.GONE);
        layout2.setVisibility(View.GONE);
        layout3.setVisibility(View.GONE);
        layout4.setVisibility(View.GONE);
        layout5.setVisibility(View.GONE);

        // Show the corresponding layout
        switch (layoutNumber) {
            case 1:
                layout1.setVisibility(View.VISIBLE);
                break;
            case 2:
                layout2.setVisibility(View.VISIBLE);
                break;
            case 3:
                layout3.setVisibility(View.VISIBLE);
                break;
            case 4:
                layout4.setVisibility(View.VISIBLE);
                break;
            case 5:
                layout5.setVisibility(View.VISIBLE);
                break;
        }
    }
    public void popup(View view) {
        // Navigate back to MatchInfoActivity
        Intent intent = new Intent(this, ScoringActivity.class);
        startActivity(intent);
        //finish(); // Close the current activity
    }

    private void updateCurrentActivityInPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("match_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("current_activity", getClass().getSimpleName()); // Store the current activity name
        editor.apply(); // Save changes asynchronously
    }
}
