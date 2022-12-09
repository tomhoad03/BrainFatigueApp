package com.example.brainfatigueapp;

import android.content.Intent;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class SurveyMiddle9Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_middle9);

        // Hide action bar
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();

        // Select activity buttons
        ArrayList<Button> activityButtons = new ArrayList<>();
        activityButtons.add(findViewById(R.id.activity_survey_middle9_button1));
        activityButtons.add(findViewById(R.id.activity_survey_middle9_button2));
        activityButtons.add(findViewById(R.id.activity_survey_middle9_button3));

        for (Button activityButton : activityButtons) {
            activityButton.setOnClickListener(v -> {
                Intent intent = new Intent(SurveyMiddle9Activity.this, SurveyEndActivity.class);
                startActivity(intent);
            });
        }
    }
}