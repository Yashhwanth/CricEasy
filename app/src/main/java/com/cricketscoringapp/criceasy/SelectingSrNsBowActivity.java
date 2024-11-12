package com.cricketscoringapp.criceasy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class SelectingSrNsBowActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_ns_bowler); // Make sure this layout file exists
    }

    public void select_striker(View view) {
        // Navigate back to MatchInfoActivity
        Intent intent = new Intent(this, SelectingPlayers.class);
        startActivity(intent);
        //finish(); // Close the current activity
    }

    public void back(View view) {
        // Navigate back to MatchInfoActivity
        Intent intent = new Intent(this, TossActivity.class);
        startActivity(intent);
        //finish(); // Close the current activity
    }
}