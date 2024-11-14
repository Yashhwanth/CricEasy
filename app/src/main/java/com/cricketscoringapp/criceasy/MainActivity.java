package com.cricketscoringapp.criceasy;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.cricketscoringapp.criceasy.Database.DatabaseHelper;

public class MainActivity extends AppCompatActivity {
    private DatabaseHelper databaseHelper;
    private long currentMatchId = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button newMatchButton = findViewById(R.id.newmatchbtn);
        newMatchButton.setOnClickListener(view ->{
            handleNewMatch();
            // Proceed to the MatchInfoActivity
            Intent intent = new Intent(MainActivity.this, MatchInfoActivity.class);
            intent.putExtra("MATCH_ID", currentMatchId);
            startActivity(intent);
        });
    }
    private void handleNewMatch() {
        // Check if there's an ongoing match
        Cursor cursor = databaseHelper.getOngoingMatch();

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int matchIdIndex = cursor.getColumnIndex(DatabaseHelper.getColumnId());

                if (matchIdIndex != -1) {
                    currentMatchId = cursor.getLong(matchIdIndex);
                    System.out.println("Resuming existing match with ID: " + currentMatchId);
                    Toast.makeText(this, "Resuming existing match", Toast.LENGTH_SHORT).show();
                } else {
                    System.out.println("Column 'match_id' not found");
                }
            } else {
                // No existing match, create a new one
                createNewMatch();
            }
            cursor.close();
        } else {
            // Cursor is null, create a new match
            createNewMatch();
        }
    }

    private void createNewMatch() {
        currentMatchId = databaseHelper.insertNewMatch();
        if (currentMatchId != -1) {
            Toast.makeText(this, "New match created", Toast.LENGTH_SHORT).show();
            System.out.println("New match created with ID: " + currentMatchId);
        } else {
            Toast.makeText(this, "Failed to create a new match", Toast.LENGTH_SHORT).show();
        }
    }

}