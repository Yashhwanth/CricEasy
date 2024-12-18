package com.cricketscoringapp.criceasy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SelectingPlayersActivity extends AppCompatActivity {
    RadioGroup role_radio_group, bat_style_radio_group, bowl_style_radio_group;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecting_players); // Make sure this layout file exists
        SharedPreferences sharedPreferences = getSharedPreferences("match_prefs", MODE_PRIVATE);
        String playerType = sharedPreferences.getString("player_type", null);


        //UI
        EditText editText = findViewById(R.id.playerNameEditText);
        Button submit_btn = findViewById(R.id.submitButton);
        role_radio_group = findViewById(R.id.radioGroup);
        bat_style_radio_group = findViewById(R.id.batGroup);
        bowl_style_radio_group = findViewById(R.id.bowlStyleRadioGroup);
        TextView playerTypeTextView = findViewById(R.id.playerTypeTextView);
        String formattedText = getString(R.string.selectNewPlayerSetText, playerType);
        playerTypeTextView.setText(formattedText);


        submit_btn.setOnClickListener(view ->{
            String player_name = String.valueOf(editText.getText());

            int role_button_id = role_radio_group.getCheckedRadioButtonId();
            int bat_style_button_id = bat_style_radio_group.getCheckedRadioButtonId();
            int bowl_style_button_id = bowl_style_radio_group.getCheckedRadioButtonId();

            RadioButton role_button = findViewById(role_button_id);
            RadioButton bat_button = findViewById(bat_style_button_id);
            RadioButton bowl_button = findViewById(bowl_style_button_id);

            addPlayerToSP(player_name, role_button, bat_button,bowl_button);
            back();
        });
    }
    public void back() {
        // Navigate back to MatchInfoActivity
        Intent intent = new Intent(this, SelectingSrNsBowActivity.class);
        startActivity(intent);
        //finish(); // Close the current activity
    }

    public void addPlayerToSP(String player_name, RadioButton role_button, RadioButton bat_style_button, RadioButton bowl_style_button){
        String role = role_button.getTag().toString();
        String bat_style = bat_style_button.getTag().toString();
        String bowl_style = bowl_style_button.getTag().toString();
        SharedPreferences sharedPreferences = getSharedPreferences("match_prefs",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String player_type = sharedPreferences.getString("player_type",null);
        editor.putString(player_type + " name",player_name);
        editor.putString(player_type + " ROLE",role);
        editor.putString(player_type + " BS",bat_style);
        editor.putString(player_type + " BOS",bowl_style);
        editor.apply();
    }
}
