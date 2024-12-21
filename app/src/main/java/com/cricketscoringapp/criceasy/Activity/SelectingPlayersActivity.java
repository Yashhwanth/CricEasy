package com.cricketscoringapp.criceasy.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.cricketscoringapp.criceasy.R;

public class SelectingPlayersActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecting_players); // Make sure this layout file exists
        SharedPreferences sharedPreferences = getSharedPreferences("match_prefs", MODE_PRIVATE);
        String playerType = sharedPreferences.getString("player_type", null);


        //UI
        EditText editText = findViewById(R.id.playerNameEditText);
        Button submit_btn = findViewById(R.id.submitButton);
        TextView playerTypeTextView = findViewById(R.id.playerTypeTextView);
        String formattedText = getString(R.string.selectNewPlayerSetText, playerType);
        playerTypeTextView.setText(formattedText);


        submit_btn.setOnClickListener(view ->{
            String player_name = String.valueOf(editText.getText());

            addPlayerToSP(player_name);
            back();
        });
    }
    public void back() {
        // Navigate back to MatchInfoActivity
        Intent intent = new Intent(this, SelectingSrNsBowActivity.class);
        startActivity(intent);
        //finish(); // Close the current activity
    }

    public void addPlayerToSP(String player_name){
        SharedPreferences sharedPreferences = getSharedPreferences("match_prefs",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String player_type = sharedPreferences.getString("player_type",null);
        editor.putString(player_type + " name",player_name);
        editor.apply();
    }
}
