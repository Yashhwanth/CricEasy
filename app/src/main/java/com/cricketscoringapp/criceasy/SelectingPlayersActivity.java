package com.cricketscoringapp.criceasy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class SelectingPlayersActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecting_players); // Make sure this layout file exists


        //UI
        EditText editText = findViewById(R.id.editTextText2);
        Button submit_btn = findViewById(R.id.button4);

        submit_btn.setOnClickListener(view ->{
            addPlayer(editText);
            back();
        });
    }
    public void back() {
        // Navigate back to MatchInfoActivity
        Intent intent = new Intent(this, SelectingSrNsBowActivity.class);
        startActivity(intent);
        //finish(); // Close the current activity
    }

    public void addPlayer(EditText editText){
        String player_name = String.valueOf(editText.getText());
        SharedPreferences sharedPreferences = getSharedPreferences("match_prefs",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String player_type = sharedPreferences.getString("player_type",null);
        editor.putString(player_type,player_name);
        editor.apply();
    }
}
