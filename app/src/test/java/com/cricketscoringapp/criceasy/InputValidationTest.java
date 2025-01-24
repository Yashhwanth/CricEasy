package com.cricketscoringapp.criceasy;

import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.AutoCompleteTextView;
import android.content.Intent;


import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import com.cricketscoringapp.criceasy.Activity.MatchInfoActivity;

@RunWith(AndroidJUnit4.class)
public class InputValidationTest {

    private MatchInfoActivity activity;

    @Before
    public void setUp() {
        // Launch the activity before each test
        ActivityScenario.launch(MatchInfoActivity.class).onActivity(new ActivityScenario.ActivityAction<MatchInfoActivity>() {
            @Override
            public void perform(MatchInfoActivity activity) {
                InputValidationTest.this.activity = activity;
            }
        });
    }

    @Test
    public void testValidateInputs_whenFieldsAreEmpty() {
        // Directly access UI components in the real activity
        EditText dateTimeEditText = activity.findViewById(R.id.dateTimeEditText);
        EditText noOfOversEditText = activity.findViewById(R.id.noOfOversEditText);
        AutoCompleteTextView placeAutoComplete = activity.findViewById(R.id.placeAutoCompleteEditText);
        RadioGroup oversTypeRadioGroup = activity.findViewById(R.id.oversTypeRadioGroup);
        RadioGroup ballTypeRadioGroup = activity.findViewById(R.id.ballTypeRadioGroup);

        // Simulate empty fields
        dateTimeEditText.setText("");
        noOfOversEditText.setText("");
        placeAutoComplete.setText("");
        oversTypeRadioGroup.clearCheck();
        ballTypeRadioGroup.clearCheck();

        // Call the validateInputs method
        boolean result = activity.validateInputs();

        // Check that the validation failed
        assertFalse(result);
    }

    @Test
    public void testValidateInputs_whenFieldsAreValid() {
        // Directly access UI components in the real activity
        EditText dateTimeEditText = activity.findViewById(R.id.dateTimeEditText);
        EditText noOfOversEditText = activity.findViewById(R.id.noOfOversEditText);
        AutoCompleteTextView placeAutoComplete = activity.findViewById(R.id.placeAutoCompleteEditText);
        RadioGroup oversTypeRadioGroup = activity.findViewById(R.id.oversTypeRadioGroup);
        RadioGroup ballTypeRadioGroup = activity.findViewById(R.id.ballTypeRadioGroup);

        // Simulate valid inputs
        placeAutoComplete.setText("Valid Location");
        dateTimeEditText.setText("2025-01-25 10:00");
        noOfOversEditText.setText("20");
        oversTypeRadioGroup.check(R.id.oversTypeRadioGroup);  // Assume this is the valid radio button ID
        ballTypeRadioGroup.check(R.id.ballTypeRadioGroup);  // Assume valid ball type ID

        // Call the validateInputs method
        boolean result = activity.validateInputs();

        // Check that the validation passed
        assertTrue(result);
    }
}
