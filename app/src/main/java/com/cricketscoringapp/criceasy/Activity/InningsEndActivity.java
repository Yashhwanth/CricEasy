package com.cricketscoringapp.criceasy.Activity;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.cricketscoringapp.criceasy.R;

public class InningsEndActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_innings_end);
        Button secondInningsButton = findViewById(R.id.startSecondInningsButton);
        secondInningsButton.setOnClickListener(v -> {
            Intent intent = new Intent(InningsEndActivity.this, SelectingSrNsBowActivity.class);
            startActivity(intent);
            finish();
        });
    }
    @Override
    protected void onResume(){
        super.onResume();
        updateCurrentActivityInPreferences();
    }
    private void updateCurrentActivityInPreferences() {
        String SHARED_PREFERENCES = "match_prefs";
        String CURRENT_ACTIVITY = "currentActivity";
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(CURRENT_ACTIVITY, getClass().getSimpleName());
        editor.apply();
        Log.d(TAG, "inside updateCurrentActivityInPreferences method: updated current activity in sp");
    }
}
