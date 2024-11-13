package com.cricketscoringapp.criceasy;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;

import com.cricketscoringapp.criceasy.Database.DatabaseHelper;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MatchInfoActivity extends AppCompatActivity {

    private static final int AUTOCOMPLETE_REQUEST_CODE = 1;
    private AutoCompleteTextView placeAutoComplete;
    private EditText dateTimeEditText;
    private Calendar calendar;
    private EditText noOfOversEditText; // Added to get number of overs input
    private RadioGroup oversTypeRadioGroup; // Added to get match type selection
    private RadioGroup ballTypeRadioGroup; // Added to get ball type selection
    private DatabaseHelper databaseHelper; // Added DatabaseHelper instance


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_info);

        // Initialize DatabaseHelper
        databaseHelper = new DatabaseHelper(this); // Highlighted line added

        Button backButton = findViewById(R.id.backButton);
        Button nextButton = findViewById(R.id.nextButton);

        // Back Button Action
        backButton.setOnClickListener(v -> {
            // Go back to the main activity (or previous activity)
            Intent intent = new Intent(MatchInfoActivity.this, MainActivity.class); // Replace with your actual main activity
            startActivityWithClearTop(intent);
            startActivity(intent);
            finish(); // Optional: Close this activity to prevent navigating back to it
        });

        // Next Button Action
        nextButton.setOnClickListener(v -> {
            if (validateInputs()) {

                saveMatchInfoToDatabase();

                // Proceed to the next activity if all inputs are valid
                Intent intent = new Intent(MatchInfoActivity.this, TeamCreationActivity.class);
                startActivityWithClearTop(intent);
                startActivity(intent);
            }
        });

        // Initialize the Places API
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), "AIzaSyCj9F32bLGwchttu-tdFnBfJkv9mzfPYSQ");
        }

        // Initialize AutoCompleteTextView for place selection
        placeAutoComplete = findViewById(R.id.placeAutocomplete);
        placeAutoComplete.setOnClickListener(v -> openAutocompleteActivity());

        // Initialize EditText for date and time selection
        dateTimeEditText = findViewById(R.id.dateTimeEditText);
        dateTimeEditText.setOnClickListener(v -> showDateTimePicker());

        // Initialize EditText and RadioGroups for number of overs and match settings
        noOfOversEditText = findViewById(R.id.noOfOversEditText); // Highlighted line added
        oversTypeRadioGroup = findViewById(R.id.oversTypeRadioGroup); // Highlighted line added
        ballTypeRadioGroup = findViewById(R.id.ballTypeRadioGroup); // Highlighted line added

        // Initialize Calendar instance
        calendar = Calendar.getInstance();
    }


    //clears the previous activity stack
    public void startActivityWithClearTop(Intent intent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        //finish();
    }

    //Method to validate inputs
    private boolean validateInputs() {
        // Get references to the required fields
        AppCompatAutoCompleteTextView placeAutoComplete = findViewById(R.id.placeAutocomplete);
        EditText dateTimeEditText = findViewById(R.id.dateTimeEditText);
        EditText noOfOversEditText = findViewById(R.id.noOfOversEditText);
        RadioGroup oversTypeRadioGroup = findViewById(R.id.oversTypeRadioGroup);
        RadioGroup ballTypeRadioGroup = findViewById(R.id.ballTypeRadioGroup);

        // Validate place input
        if (placeAutoComplete.getText().toString().trim().isEmpty()) {
            showToast("Please enter the match location");
            placeAutoComplete.requestFocus();
            return false;
        }

        // Validate date and time input
        if (dateTimeEditText.getText().toString().trim().isEmpty()) {
            showToast("Please enter the match date and time");
            dateTimeEditText.requestFocus();
            return false;
        }

        // Validate number of overs input
        if (noOfOversEditText.getText().toString().trim().isEmpty()) {
            showToast("Please enter the number of overs");
            noOfOversEditText.requestFocus();
            return false;
        }

        // Validate match type selection
        if (oversTypeRadioGroup.getCheckedRadioButtonId() == -1) {
            showToast("Please select the match type");
            oversTypeRadioGroup.requestFocus();
            return false;
        }

        // Validate ball type selection
        if (ballTypeRadioGroup.getCheckedRadioButtonId() == -1) {
            showToast("Please select the type of ball");
            ballTypeRadioGroup.requestFocus();
            return false;
        }

        // All validations passed
        return true;
    }


    // Method to show a Toast message
    private void showToast(String message) {
        Toast.makeText(MatchInfoActivity.this, message, Toast.LENGTH_SHORT).show();
    }
    // Method to open the Google Places Autocomplete activity
    private void openAutocompleteActivity() {
        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS);
        Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
                .build(MatchInfoActivity.this);
        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
    }

    // Method to show the combined Date and Time Picker dialog
    private void showDateTimePicker() {
        // First, show Date Picker Dialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, month, dayOfMonth) -> {
                    // Set the calendar to the selected date
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, month);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                    // Now show the Time Picker Dialog
                    showTimePicker();
                },
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        // Show the DatePickerDialog
        datePickerDialog.show();
    }

    // Method to show the Time Picker dialog after selecting the date
    private void showTimePicker() {
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                (view, selectedHour, selectedMinute) -> {
                    // Set the calendar to the selected time
                    calendar.set(Calendar.HOUR_OF_DAY, selectedHour);
                    calendar.set(Calendar.MINUTE, selectedMinute);

                    // Format the combined date and time to display in the EditText
                    String selectedDateTime = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault())
                            .format(calendar.getTime());

                    // Set the date and time in the EditText field
                    dateTimeEditText.setText(selectedDateTime);
                },
                hour, minute, true);

        // Show the TimePickerDialog
        timePickerDialog.show();
    }

    // Method to save match information into the database
    private void saveMatchInfoToDatabase() { // Highlighted line added // Extract values from UI components
        String matchType = getRadioButtonText(oversTypeRadioGroup);
        String noOfOvers = noOfOversEditText.getText().toString().trim();
        String ballType = getRadioButtonText(ballTypeRadioGroup);
        String place = placeAutoComplete.getText().toString().trim();
        String dateTime = dateTimeEditText.getText().toString().trim();

        // Save the match information into the database
        boolean isInserted = databaseHelper.insertMatchBasicInfo(matchType, noOfOvers, ballType, place, dateTime ); // Highlighted line added

        if (isInserted) {
            showToast("Match Info Saved Successfully!"); // Highlighted line added
        } else {
            showToast("Failed to Save Match Info."); // Highlighted line added
        }
    }

    // Helper method to get the selected radio button text
    private String getRadioButtonText(RadioGroup radioGroup) {
        int selectedId = radioGroup.getCheckedRadioButtonId();
        RadioButton selectedRadioButton = findViewById(selectedId);
        return selectedRadioButton != null ? selectedRadioButton.getText().toString() : "";
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK && data != null) {
                // Get the place selected by the user
                Place place = Autocomplete.getPlaceFromIntent(data);

                // Extract the place name and address
                String placeName = place.getName();
                String placeAddress = place.getAddress();

                // Show the selected place information in your UI (Toast for now)
                Toast.makeText(this, "Selected Place: " + placeName + ", " + placeAddress, Toast.LENGTH_LONG).show();

                // Set the selected place name into the AutoCompleteTextView
                placeAutoComplete.setText(placeName);
                placeAutoComplete.setSelection(placeAutoComplete.getText().length()); // Move cursor to the end
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // Handle the error
                Toast.makeText(this, "Error: Unable to fetch location", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
