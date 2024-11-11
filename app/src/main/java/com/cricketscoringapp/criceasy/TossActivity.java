package com.cricketscoringapp.criceasy;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class TossActivity extends AppCompatActivity {
    private ImageView coinImage;
    private Random random;
    private boolean isFlipping = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toss); // Make sure this layout file exists

        coinImage = findViewById(R.id.img_coin);
        random = new Random();

        // Set the click listener to trigger the coin flip
        coinImage.setOnClickListener(view -> {
            if (!isFlipping) {
                flipCoin();
            }
        });
    }
    public void letsplay(View view) {
        // Navigate back to MatchInfoActivity
        Intent intent = new Intent(this, SelectingSrNsBowActivity.class);
        startActivity(intent);
        //finish(); // Close the current activity
    }

    public void back(View view) {
        // Navigate back to MatchInfoActivity
        Intent intent = new Intent(this, TeamCreationActivity.class);
        startActivity(intent);
        //finish(); // Close the current activity
    }


    private void flipCoin() {
        isFlipping = true;

        // Start the coin flip animation
        Animation flipAnimation = AnimationUtils.loadAnimation(this, R.anim.coin_flip_anim);
        coinImage.startAnimation(flipAnimation);

        // Delay to simulate the flipping time
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            // Randomly determine the result (Heads or Tails)
            boolean isHeads = random.nextBoolean();

            // Update the ImageView based on the result
            if (isHeads) {
                coinImage.setImageResource(R.drawable.heads);
            } else {
                coinImage.setImageResource(R.drawable.tails);
            }

            isFlipping = false; // Reset flipping state
        }, 800); // Animation duration in milliseconds
    }
}
