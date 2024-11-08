package com.cricketscoringapp.criceasy;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_info);

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

        // Initialize Calendar instance
        calendar = Calendar.getInstance();
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
