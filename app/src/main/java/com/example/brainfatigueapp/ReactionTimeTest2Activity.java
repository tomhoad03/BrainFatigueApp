package com.example.brainfatigueapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class ReactionTimeTest2Activity extends AppCompatActivity {

    Button startButton, colourChangeButton;

    long startTime, endTime, currentTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reaction_time_test2);

        // Hide action bar
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();

        // Buttons
        startButton = findViewById(R.id.reaction_time_test_start_button); // start button that begins the reaction test
        colourChangeButton = findViewById(R.id.reaction_time_test_colour_change_button); // the clickable area or button that changes colour

        startButton.setEnabled(true);
        colourChangeButton.setEnabled(false);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doReactionTest();
            }
        });

        colourChangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endTime = System.currentTimeMillis();
                currentTime = endTime - startTime;
                colourChangeButton.setBackgroundColor(
                        ContextCompat.getColor(getApplicationContext(), R.color.custom_purple_A)
                );
                colourChangeButton.setText(currentTime + "ms");
                startButton.setEnabled(true);
                colourChangeButton.setEnabled(false);

                // After the button goes light blue and they react to it, wait a couple seconds
                // so they have time to see the reaction time they just got before starting again
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Reset the button colour, then do another test
                        colourChangeButton.setBackgroundColor(
                                ContextCompat.getColor(getApplicationContext(), R.color.grey)
                        );
                        colourChangeButton.setText("wait...");
                        doReactionTest();
                    }
                }, (long) (3000)); // Wait 3 secs before starting the next one
            }
        });

    }

    void doReactionTest () {
        startButton.setEnabled(false);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startTime = System.currentTimeMillis();
                colourChangeButton.setBackgroundColor(
                        ContextCompat.getColor(getApplicationContext(), R.color.custom_light_blue_A) // wuuuut
                );
                colourChangeButton.setText("TAP!");
                colourChangeButton.setEnabled(true);
            }
        }, (long) (Math.random() * 10 + 1) * 1000); // hopefully this works
    }
}
