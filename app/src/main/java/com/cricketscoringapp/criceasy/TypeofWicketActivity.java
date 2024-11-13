package com.cricketscoringapp.criceasy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class TypeofWicketActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_typeofwicket); // Make sure this layout file exists
    }

    public void okay(View view) {
        // Navigate back to MatchInfoActivity
        Intent intent = new Intent(this, MatchActivity.class);
        startActivity(intent);
        //finish(); // Close the current activity
    }
}
