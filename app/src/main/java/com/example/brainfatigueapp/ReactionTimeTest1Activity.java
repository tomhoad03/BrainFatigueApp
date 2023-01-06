package com.example.brainfatigueapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ReactionTimeTest1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reaction_time_test1);

        // Hide action bar
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();

        // Buttons
        Button reactionTestButton1 = findViewById(R.id.reaction_time_test_button1); // "take the test" button
        Button reactionTestButton2 = findViewById(R.id.reaction_time_test_button2); // "not right now" or skip button

        reactionTestButton1.setOnClickListener(v -> {
            Intent intent = new Intent(ReactionTimeTest1Activity.this, ReactionTimeTest2Activity.class); // change next page
            startActivity(intent);
        });

        reactionTestButton2.setOnClickListener(v -> {
            Intent intent = new Intent(ReactionTimeTest1Activity.this, HomeActivity.class); // when skip is pressed, user is redirected to homepage
            startActivity(intent);
        });

    }
}
