package com.example.brainfatigueapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class ReactionTimeTest2Activity extends AppCompatActivity {

    // startButton = button that starts the PVT; colourChangeButton = button that user taps when colour changes
    Button startButton, colourChangeButton;
    long testBeginsTime, startTime, endTime, currentTime, elapsedTime, averageTime;
    ArrayList<Long> results = new ArrayList<Long>();

    // @TODO save each iteration's currentTime to an array/arraylist so averageTime can be calculated. What's left to do for this one is to add it to the ArrayList declared above, and then to calculate the average of the values in the list and to display it on colourChangeButton.

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
                testBeginsTime = System.currentTimeMillis();
                colourChangeButton.setText("Game started!");
                doReactionTest();
            }
        });

        colourChangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endTime = System.currentTimeMillis();
                currentTime = endTime - startTime; // calculates time taken for user to respond
                elapsedTime = endTime - testBeginsTime; // calculates time since game was started
                // after colourChangeButton is tapped, add currentTime to results
                results.add(currentTime);
                colourChangeButton.setBackgroundColor(
                        ContextCompat.getColor(getApplicationContext(), R.color.custom_purple_A)
                );
                colourChangeButton.setText(currentTime + "ms");
//                startButton.setEnabled(true);
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
                        // after each iteration of screen changing colour and user tapping
                        // here we check the elapsed time
                        // if elapsed time has exceeded 2 minutes, then stop the game/do something
                        // else call doReactionTest()
                        if (elapsedTime > 30000) { // 3000 for testing; 120000 for 2 minutes
                            endReactionTest();
                        } else {
                            doReactionTest();
                        }
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

    // @TODO write a method that will be called when elapsedTime has exceeded 2 minutes. The method should end the PVT. The method should also change startButton to a next button that will bring us to the next page or back to home. (optional) The method can also print out the user's average reaction time.
    void endReactionTest () {
        startButton.setEnabled(true);
        startButton.setText("BACK TO HOMEPAGE"); // gotta change this text to something else
        // now we have the list of results, all that is left is to display the average on colourChangeButton
        averageTime = calculateAverage(results);
        colourChangeButton.setText("TEST COMPLETED! \n\nYOUR AVERAGE RESPONSE TIME IS: " + averageTime + "ms"); // THANKS FOR PARTICIPATING. PRESS THE BUTTON BELOW TO VIEW YOUR RESULTS
        // re-map startButton's function into something else i.e. a button that navigates to homepage for now
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // navigate back to homepage
                startActivity(new Intent(ReactionTimeTest2Activity.this, HomeActivity.class));
            }
        });
    }

    /**
     * This method returns (or at least, tries to return) the average long value of an ArrayList
     * @param al the ArrayList to calculate the average for
     * @return the average of the ArrayList al
     */
    private long calculateAverage(ArrayList<Long> al) {
        return (long) al.stream()
                .mapToLong(l -> l)
                .average()
                .orElse(0.0); // returns a double but type-casted (long) just to be sure
    }
}