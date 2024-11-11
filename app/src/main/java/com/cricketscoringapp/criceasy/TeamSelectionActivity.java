package com.cricketscoringapp.criceasy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class TeamSelectionActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_selection); // Make sure this layout file exists
    }

    public void goback(View view) {
        // Navigate back to MatchInfoActivity
        Intent intent = new Intent(this, TeamCreationActivity.class);
        startActivity(intent);
        finish(); // Close the current activity
    }
}