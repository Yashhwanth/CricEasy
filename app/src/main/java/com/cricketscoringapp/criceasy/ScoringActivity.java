package com.cricketscoringapp.criceasy;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ScoringActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoring); // Make sure this layout file exists

        Button dialogbuttonwide = findViewById(R.id.button14);
        Button dialogbuttonnb = findViewById(R.id.button12);
        Button dialogbuttonbyes = findViewById(R.id.button9);
        Button dialogbuttonlbyes = findViewById(R.id.button10);

        dialogbuttonwide.setOnClickListener(view -> dialog(ScoringActivity.this));
        dialogbuttonnb.setOnClickListener(view -> dialog(ScoringActivity.this));
        dialogbuttonbyes.setOnClickListener(view -> dialog(ScoringActivity.this));
        dialogbuttonlbyes.setOnClickListener(view -> dialog(ScoringActivity.this));
    }

    public void dialog(Context context) {
        // Example button to open the dialog

        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_dialog_for_extras);
        dialog.setCancelable(true);

        Button btnCancel = dialog.findViewById(R.id.btn_cancel);
        Button btnSubmit = dialog.findViewById(R.id.btn_submit);

        btnCancel.setOnClickListener(v -> dialog.dismiss());
        btnSubmit.setOnClickListener(v -> {
            dialog.dismiss();
            // Intent to go back to MatchActivity
            Intent intent = new Intent(ScoringActivity.this, MatchActivity.class);
            startActivity(intent);
        });

        dialog.show();
    }

    public void scorebutton(View view) {
        // Navigate back to MatchInfoActivity
        Intent intent = new Intent(this, MatchActivity.class);
        startActivity(intent);
        //finish(); // Close the current activity
    }

    public void wicketbutton(View view) {
        // Navigate back to MatchInfoActivity
        Intent intent = new Intent(this, TypeofWicketActivity.class);
        startActivity(intent);
        //finish(); // Close the current activity
    }

}
