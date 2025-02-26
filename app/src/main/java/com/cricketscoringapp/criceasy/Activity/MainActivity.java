package com.cricketscoringapp.criceasy.Activity;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;


import com.cricketscoringapp.criceasy.Database.DatabaseHelper;
import com.cricketscoringapp.criceasy.Database.*;
import com.cricketscoringapp.criceasy.R;
import com.cricketscoringapp.criceasy.dao.MatchDao;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {
    private DatabaseHelper databaseHelper;
    private static final String SHARED_PREFERENCES = "match_prefs";
    private static final String MATCH_ID = "currentMatchId";
    private static final String CURRENT_ACTIVITY = "currentActivity";
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        databaseHelper = new DatabaseHelper(this);
        sharedPreferences = getSharedPreferences("match_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor1 = sharedPreferences.edit();
        editor1.clear();
        editor1.apply();

        // Initialize UI elements
        Button newMatchButton = findViewById(R.id.newMatchButton);
        Button resumeMatchButton = findViewById(R.id.pastMatchesButton);

        // Hide the "Resume Match" button by default
        resumeMatchButton.setVisibility(View.GONE);

        if (navigateToLastActivityIfOngoingMatch()) {
            Log.d(TAG, "onCreate: navigate to left off page");
            Toast.makeText(this, "Resuming Existing Match", Toast.LENGTH_SHORT).show();
            return; // Skip MainActivity logic if navigating to the last activity
        }

        // Check if the app was launched through a file
        Intent intent = getIntent();
        if (intent != null && intent.getData() != null) {
            // Check if the app was already opened via a file previously
            boolean isAppOpenedThroughFile = sharedPreferences.getBoolean("isAppOpenedThroughFile", false);
            if (isAppOpenedThroughFile) {
                // Skip file processing and directly open MatchActivity
                Log.d(TAG, "App already opened via file previously. Skipping file processing.");
                Intent matchIntent = new Intent(MainActivity.this, MatchActivity.class);
                startActivity(matchIntent);
                return;
            }

            // Mark the app as opened through a file
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isAppOpenedThroughFile", true);
            editor.apply();

            Uri fileUri = intent.getData();
            Log.d(TAG, "App opened via file URI: " + fileUri);

            // Attempt to read and parse the file
            try {
                Log.d(TAG, "onCreate: inside the try block");
                String jsonData = readJsonFile(fileUri);
                JSONObject databaseJson = new JSONObject(jsonData).getJSONObject("database");
                Log.d(TAG, "onCreate: database extracted object is" + databaseJson);

                // Restore match state and database data
                if (restoreMatchStateFromJson(jsonData) && databaseHelper.restoreDatabaseData(databaseJson)) {
                    Log.d(TAG, "Match state and database successfully restored.");
                    // Proceed directly to MatchActivity once both shared preferences and database are populated
                    Intent matchIntent = new Intent(MainActivity.this, MatchActivity.class);
                    startActivity(matchIntent);
                    return; // Skip other logic since we are moving to MatchActivity directly
                } else {
                    Log.e(TAG, "Failed to restore match state or database data. Falling back to new match.");
                }
            } catch (Exception e) {
                Log.e(TAG, "Error reading or parsing JSON file: " + e.getMessage());
            }
        }


        // Handle the "New Match" button
        newMatchButton.setOnClickListener(view -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();
            long currentMatchId = handleNewMatch();
            saveMatchIdToPreferences(currentMatchId);
            Log.d(TAG, "onCreate: opening MatchActivity");
            Intent matchIntent = new Intent(MainActivity.this, MatchInfoActivity.class);
            startActivity(matchIntent);
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        //updateCurrentActivityInPreferences();
    }
    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy: main activity destroyed");
        super.onDestroy();
        if (databaseHelper != null) {
            databaseHelper.close();
        }
    }
    private long handleNewMatch() {
        Log.d(TAG, "handleNewMatch: checking a match is present or not");
        long currentMatchId = -1;
        try (Cursor cursor = databaseHelper.getOngoingMatch()) {
            if (cursor != null && cursor.moveToFirst()) {
                Log.d(TAG, "handleNewMatch: ongoing match exists");
                int matchIdIndex = cursor.getColumnIndex(DatabaseHelper.getColumnId());
                if (matchIdIndex != -1) {
                    currentMatchId = cursor.getLong(matchIdIndex);
                    Toast.makeText(this, "Resuming existing match", Toast.LENGTH_SHORT).show();
                }
            } else {
                Log.d(TAG, "handleNewMatch: no ongoing match");
                // No existing match, create a new one
                currentMatchId = createNewMatch();
            }
        }catch (Exception e) {
            Log.e(TAG, "handleNewMatch: error handling match");
            Toast.makeText(this, "Error handling match!", Toast.LENGTH_SHORT).show();
        }return currentMatchId;
    }
    private long createNewMatch() {
        Log.d(TAG, "createNewMatch: creating new match");
        long currentMatchId = databaseHelper.insertNewMatch();
        if (currentMatchId != -1) {
            Log.d(TAG, "createNewMatch: created a new match");
            Toast.makeText(this, "New match created", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to create a new match", Toast.LENGTH_SHORT).show();
        }
        return currentMatchId;
    }
    private void saveMatchIdToPreferences(long currentMatchId) {
        Log.d(TAG, "saveMatchIdToPreferences: saving match id in sp");
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(MATCH_ID, currentMatchId);
        editor.apply();
        Log.d(TAG, "saveMatchIdToPreferences: saved match id in sp with id" + currentMatchId);
    }
    private void updateCurrentActivityInPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(CURRENT_ACTIVITY, getClass().getSimpleName());
        editor.apply(); 
        Log.d(TAG, "inside updateCurrentActivityInPreferences method: updated current activity in sp");
    }
    private boolean navigateToLastActivityIfOngoingMatch() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        String lastActivity = sharedPreferences.getString(CURRENT_ACTIVITY, null);
        long matchId = sharedPreferences.getLong(MATCH_ID, -1);

        // Check if there's an ongoing match and a last activity
        if (lastActivity != null && matchId != -1) {
            try {
                // Dynamically load the last activity class and navigate
                Log.d(TAG, "navigateToLastActivityIfOngoingMatch: " + lastActivity);
                Class<?> activityClass = Class.forName("com.cricketscoringapp.criceasy.Activity." + lastActivity);
                Log.d(TAG, "navigateToLastActivityIfOngoingMatch: " + activityClass);
                Intent intent = new Intent(this, activityClass);
                startActivity(intent);
                finish(); // Close MainActivity
                return true;
            } catch (ClassNotFoundException e) {
                // Use robust logging instead of printStackTrace
                Log.e("MainActivity", "Error resuming last activity. Class not found: " + lastActivity, e);
                Toast.makeText(this, "Error resuming last activity!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Log.w("MainActivity", "No ongoing match or last activity not found.");
        }

        return false; // No navigation occurred
    }
    private String readJsonFile(Uri fileUri) throws IOException {
        // Open an InputStream for the file
        InputStream inputStream = getContentResolver().openInputStream(fileUri);
        StringBuilder stringBuilder = new StringBuilder();

        if (inputStream != null) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;

            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            inputStream.close();
        }

        // Return the file content as a String (JSON data)
        return stringBuilder.toString();
    }
    private boolean restoreMatchStateFromJson(String jsonData) {
        try {
            // Parse JSON into a JSONObject
            JSONObject jsonObject = new JSONObject(jsonData);

            // Extract the "sharedPreferences" section from the JSON
            JSONObject sharedPrefsObject = jsonObject.getJSONObject("sharedPreferences");

            // Create an editor to save the data into shared preferences
            SharedPreferences.Editor editor = sharedPreferences.edit();

            // Iterate over the keys in the "sharedPreferences" section and save the data to shared preferences
            Iterator<String> keys = sharedPrefsObject.keys();
            while (keys.hasNext()) {
                String key = keys.next();

                // Get the value array for each key: the first item is the value, the second is its type
                JSONArray valueArray = sharedPrefsObject.getJSONArray(key);
                Object value = valueArray.get(0);
                String type = valueArray.getString(1);

                Log.d(TAG, "restoreMatchStateFromJson: " + key);
                Log.d(TAG, "restoreMatchStateFromJson: " + value);
                Log.d(TAG, "restoreMatchStateFromJson: " + type);

                // Update shared preferences based on the value type
                switch (type) {
                    case "String":
                        editor.putString(key, (String) value);
                        break;

                    case "Long":
                        // Ensure value is castable to Long
                        if (value instanceof Long) {
                            editor.putLong(key, (Long) value);
                        } else if (value instanceof Integer) {
                            editor.putLong(key, ((Integer) value).longValue()); // Handle Integer to Long conversion
                        } else {
                            Log.e(TAG, "Invalid type for Long: " + value);
                        }
                        break;

                    case "Integer":
                        // Ensure value is castable to Integer
                        if (value instanceof Integer) {
                            editor.putInt(key, (Integer) value);
                        } else {
                            Log.e(TAG, "Invalid type for Integer: " + value);
                        }
                        break;

                    case "Boolean":
                        // Ensure value is castable to Boolean
                        if (value instanceof Boolean) {
                            editor.putBoolean(key, (Boolean) value);
                        } else {
                            Log.e(TAG, "Invalid type for Boolean: " + value);
                        }
                        break;

                    case "Float":
                        // Ensure value is castable to Float
                        if (value instanceof Number) {
                            editor.putFloat(key, ((Number) value).floatValue());
                        } else {
                            Log.e(TAG, "Invalid type for Float: " + value);
                        }
                        break;

                    case "Double":
                        // Since SharedPreferences doesn't support Double, convert it to String
                        editor.putString(key, String.valueOf(value));
                        break;

                    default:
                        Log.e(TAG, "Unsupported data type: " + type);
                }
            }

            // Commit the changes to shared preferences
            editor.apply();
            Log.d(TAG, "restoreMatchStateFromJson: successfully updated shared prefs");
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Error parsing or saving JSON data: " + e.getMessage());
            return false;
        }
    }




}