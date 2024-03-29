package com.example.brainfatigueapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ReactionTimeTest2Activity extends AppCompatActivity {

    // startButton = button that starts the PVT; colourChangeButton = button that user taps when colour changes
    Button startButton, colourChangeButton;
    long testBeginsTime, startTime, endTime, currentTime, elapsedTime, averageTime, elapsedTimeMS;
    ArrayList<Long> results = new ArrayList<>();

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

        startButton.setOnClickListener(v -> {
            testBeginsTime = System.nanoTime(); // might be able to just keep it this way
            colourChangeButton.setText("Game started!");
            doReactionTest();
        });

        // Wait 3 secs before starting the next one
        colourChangeButton.setOnClickListener(v -> {
            endTime = System.nanoTime(); // previously System.nanoTime()
            currentTime = TimeUnit.NANOSECONDS.toMillis(endTime - startTime); // calculates time taken for user to respond
            elapsedTime = endTime - testBeginsTime; // calculates time since game was started
            elapsedTimeMS = TimeUnit.NANOSECONDS.toMillis(elapsedTime);
            // after colourChangeButton is tapped, add currentTime to results
            results.add(currentTime);
            colourChangeButton.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.custom_purple_B));
            colourChangeButton.setText(currentTime + "ms");
            colourChangeButton.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
            //startButton.setEnabled(true);
            colourChangeButton.setEnabled(false);

            // After the button goes light blue and they react to it, wait a couple seconds
            // so they have time to see the reaction time they just got before starting again
            Handler handler = new Handler();
            handler.postDelayed(() -> {
                // Reset the button colour, then do another test
                colourChangeButton.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.grey));
                colourChangeButton.setText("wait...");
                colourChangeButton.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.another_grey_text));
                colourChangeButton.setTextSize(28);
                // after each iteration of screen changing colour and user tapping
                // here we check the elapsed time
                // if elapsed time has exceeded 2 minutes, then stop the game/do something
                // else call doReactionTest()
                if (elapsedTimeMS > 180000) { // 30000 for testing; 180000 for 3 minutes
                    endReactionTest();
                } else {
                    doReactionTest();
                }
            }, 3000); // Wait 3 secs before starting the next one
        });

    }

    void doReactionTest() {
        startButton.setEnabled(false);
        startButton.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.grey));

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            startTime = System.nanoTime();
            colourChangeButton.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.custom_light_blue_C));
            colourChangeButton.setText("TAP!");
            colourChangeButton.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
            colourChangeButton.setTextSize(30);
            colourChangeButton.setEnabled(true);
        }, (long) ((Math.random() * 8) + 2) * 1000); // hopefully this works
    }

    // @TODO write a method that will be called when elapsedTime has exceeded 2 minutes. The method should end the PVT. The method should also change startButton to a next button that will bring us to the next page or back to home. (optional) The method can also print out the user's average reaction time.
    void endReactionTest () {
        startButton.setEnabled(true);
        startButton.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.custom_light_blue_B));

        startButton.setText("BACK TO HOMEPAGE"); // gotta change this text to something else
        // now we have the list of results, all that is left is to display the average on colourChangeButton
        averageTime = calculateAverage(results);
        colourChangeButton.setText("TEST COMPLETED! \n\nYOUR AVERAGE RESPONSE TIME IS: " + averageTime + "ms"); // THANKS FOR PARTICIPATING. PRESS THE BUTTON BELOW TO VIEW YOUR RESULTS
        colourChangeButton.setTextColor(
                ContextCompat.getColor(getApplicationContext(),R.color.another_grey_text)
        );
        colourChangeButton.setTextSize(28);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            FatigueDatabase fatigueDatabase = FatigueDatabase.getDatabase(getApplicationContext());
            ReactionDao reactionDao = fatigueDatabase.reactionDao();

            Reaction reaction = new Reaction(averageTime);
            reactionDao.insert(reaction);
        });
        executorService.shutdown();

        // re-map startButton's function into something else i.e. a button that navigates to homepage for now
        startButton.setOnClickListener(v -> {
            // navigate back to homepage
            startActivity(new Intent(ReactionTimeTest2Activity.this, HomeActivity.class));
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