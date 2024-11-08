package com.cricketscoringapp.criceasy;

import android.content.Intent;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import java.util.Arrays;
import java.util.List;

public class MatchInfoActivity extends AppCompatActivity {
    private static final int AUTOCOMPLETE_REQUEST_CODE = 1;
    private AutoCompleteTextView placeAutoComplete;  // Reference to the AutoCompleteTextView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_info);

        // Initialize the Places API
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), "AIzaSyCj9F32bLGwchttu-tdFnBfJkv9mzfPYSQ");
        }

        // Set up AutoCompleteTextView
        placeAutoComplete = findViewById(R.id.placeAutocomplete);
        placeAutoComplete.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                openAutocompleteActivity();
            }
        });
    }

    // Open the Autocomplete Activity
    private void openAutocompleteActivity() {
        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS);
        Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
                .build(MatchInfoActivity.this);
        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // Get the place selected by the user
                Place place = Autocomplete.getPlaceFromIntent(data);

                // Extract the place name and address
                String placeName = place.getName();
                String placeAddress = place.getAddress();

                // Show the selected place information in your UI (Toast for now)
                Toast.makeText(this, "Selected Place: " + placeName + ", " + placeAddress, Toast.LENGTH_LONG).show();

                // Append the selected place to the AutoCompleteTextView (comma-separated or any other format)
                String currentText = placeAutoComplete.getText().toString();
                if (!currentText.isEmpty()) {
                    placeAutoComplete.setText(currentText + ", " + placeName);  // Add the new place to the text field
                } else {
                    placeAutoComplete.setText(placeName);  // If it's empty, just set the place name
                }
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // Handle the error
                Toast.makeText(this, "Error: " + Autocomplete.getStatusFromIntent(data).getStatusMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
