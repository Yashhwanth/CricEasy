package com.cricketscoringapp.criceasy.Activity;

import android.content.Intent;
import android.os.Bundle;
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
}
