package com.cricketscoringapp.criceasy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class TeamCreationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_creation);
    }

    // Method to handle Back button click
    public void goBack(View view) {
        // Navigate back to MatchInfoActivity
        Intent intent = new Intent(this, MatchInfoActivity.class);
        startActivity(intent);
        finish(); // Close the current activity
    }

    // Method to handle Next button click
    public void goToNext(View view) {
        // Navigate to TossActivity
        Intent intent = new Intent(this, TossActivity.class); // Replace with your next activity
        startActivity(intent);
    }
}