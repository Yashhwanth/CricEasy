package com.cricketscoringapp.criceasy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class SelectingSrNsBowActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_ns_bowler); // Make sure this layout file exists
        updateCurrentActivityInPreferences();

        SharedPreferences sharedPreferences = getSharedPreferences("match_prefs",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        //UI
        ImageView striker_button = findViewById(R.id.imageView);
        ImageView non_striker_button = findViewById(R.id.imageView2);
        ImageView bowler_button = findViewById(R.id.imageView3);

        striker_button.setOnClickListener(view ->{
            editor.putString("player_type","striker");
            editor.apply();
            select_player();
        });

        non_striker_button.setOnClickListener(view ->{
            editor.putString("player_type","non_striker");
            editor.apply();
            select_player();
        });

        bowler_button.setOnClickListener(view ->{
            editor.putString("player_type","bowler");
            editor.apply();
            select_player();
        });

    }
    @Override
    protected void onResume() {
        super.onResume();

        // Update current activity in SharedPreferences
        updateCurrentActivityInPreferences();
    }

    public void select_player() {
        // Navigate back to MatchInfoActivity
        Intent intent = new Intent(this, SelectingPlayersActivity.class);
        startActivity(intent);
        //finish(); // Close the current activity
    }

    public void back(View view) {
        // Navigate back to MatchInfoActivity
        Intent intent = new Intent(this, TossActivity.class);
        startActivity(intent);
        //finish(); // Close the current activity
    }

    public void lets_play(View view) {
        // Navigate back to MatchInfoActivity
        Intent intent = new Intent(this, MatchActivity.class);
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
