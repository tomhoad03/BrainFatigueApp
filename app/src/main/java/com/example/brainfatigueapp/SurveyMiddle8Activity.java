package com.example.brainfatigueapp;

import android.content.Intent;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class SurveyMiddle8Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_middle8);

        // Hide action bar
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();

        // Select activity buttons
        ArrayList<Button> activityButtons = new ArrayList<>();
        activityButtons.add(findViewById(R.id.activity_survey_middle8_button1));
        activityButtons.add(findViewById(R.id.activity_survey_middle8_button2));
        activityButtons.add(findViewById(R.id.activity_survey_middle8_button3));
        activityButtons.add(findViewById(R.id.activity_survey_middle8_button4));

        for (Button activityButton : activityButtons) {
            activityButton.setOnClickListener(v -> {
                Intent intent = new Intent(SurveyMiddle8Activity.this, SurveyMiddle9Activity.class);
                startActivity(intent);
            });
        }
    }
}