package com.cricketscoringapp.criceasy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class SelectingPlayers extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecting_players); // Make sure this layout file exists
    }
    public void back(View view) {
        // Navigate back to MatchInfoActivity
        Intent intent = new Intent(this, SelectingSrNsBowActivity.class);
        startActivity(intent);
        //finish(); // Close the current activity
    }
}
