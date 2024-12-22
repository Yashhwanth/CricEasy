package com.cricketscoringapp.criceasy.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cricketscoringapp.criceasy.R;

public class SelectingPlayersActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private final String SHARED_PREFERENCES = "match_prefs";
    private final String PLAYER_TYPE_KEY = "playerType";
    private final String CURRENT_ACTIVITY = "currentActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecting_players); // Make sure this layout file exists
        sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        String currentActivity = sharedPreferences.getString(CURRENT_ACTIVITY, null);
        String playerType = sharedPreferences.getString(PLAYER_TYPE_KEY, null);


        //UI
        EditText playerNameEditText = findViewById(R.id.playerNameEditText);
        Button submitButton = findViewById(R.id.submitButton);
        Button cancelButton = findViewById(R.id.cancelButton);
        TextView playerTypeTextView = findViewById(R.id.playerTypeTextView);
        String formattedText = getString(R.string.selectNewPlayerSetText, playerType);
        playerTypeTextView.setText(formattedText);
        if(currentActivity != null && currentActivity.equals("MatchActivity")) cancelButton.setVisibility(View.GONE);

        submitButton.setOnClickListener(view ->{
            String playerName = String.valueOf(playerNameEditText.getText());
            if(!validateInput(playerName)) return;
            addPlayerToSharedPreferences(playerName);
            back();
        });
        cancelButton.setOnClickListener(view -> back());
    }
    public boolean validateInput(String playerName){
        if(playerName.isEmpty()){
            showToast("Please Enter the Player Name");
            return false;
        }
        return true;
    }
    private void showToast(String message) {
        Toast.makeText(SelectingPlayersActivity.this, message, Toast.LENGTH_SHORT).show();
    }
    public void back() {
        Intent intent = new Intent(this, SelectingSrNsBowActivity.class);
        startActivity(intent);
    }
    public void addPlayerToSharedPreferences(String playerName){
        sharedPreferences = getSharedPreferences(SHARED_PREFERENCES,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String playerType = sharedPreferences.getString(PLAYER_TYPE_KEY,null);
        editor.putString(playerType + " name",playerName);
        editor.apply();
        showToast(playerType + " Updated");
    }
}
