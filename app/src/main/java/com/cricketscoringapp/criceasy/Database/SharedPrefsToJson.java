package com.cricketscoringapp.criceasy.Database;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
public class SharedPrefsToJson {

    public static void saveSharedPrefsToJson(Context context, String sharedPrefsName, String outputFileName) {
        // Step 1: Access the Shared Preferences
        SharedPreferences sharedPreferences = context.getSharedPreferences(sharedPrefsName, Context.MODE_PRIVATE);
        if (sharedPreferences == null) {
            throw new IllegalArgumentException("SharedPreferences not found!");
        }

        // Step 2: Convert to JSON using Gson
        Gson gson = new Gson();
        String json = gson.toJson(sharedPreferences.getAll()); // Convert all key-value pairs to JSON

        // Step 3: Save JSON to a File
        File outputFile = new File(context.getFilesDir(), outputFileName); // Use internal storage
        try (FileWriter writer = new FileWriter(outputFile)) {
            writer.write(json); // Write the JSON string to the file
            writer.flush();
        } catch (IOException e) {
            Log.e(TAG, "saveSharedPrefsToJson: error changing sp to json");
        }
    }
}
