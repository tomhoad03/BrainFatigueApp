package com.example.brainfatigueapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

// this activity shows the options when "Social activities" is chosen
public class SurveyMiddle4dActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_middle4d);

        // Hide action bar
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();

        // Back button
        // points back to middle3 "What have you been doing the last 10 minutes?" page
        final ImageButton surveyBackBtn = findViewById(R.id.activity_survey_middle4d_back_button);
        surveyBackBtn.setOnClickListener(v -> {
            Intent intent = new Intent(SurveyMiddle4dActivity.this, SurveyMiddle3Activity.class);
            startActivity(intent);
        });

        // Select activity buttons
        ArrayList<Button> activityButtons = new ArrayList<>();
        activityButtons.add(findViewById(R.id.activity_survey_middle4d_button1));
        activityButtons.add(findViewById(R.id.activity_survey_middle4d_button2));
        activityButtons.add(findViewById(R.id.activity_survey_middle4d_button3));

        // each button points forward to middle5
        for (Button activityButton : activityButtons) {
            activityButton.setOnClickListener(v -> {
                Intent intent = new Intent(SurveyMiddle4dActivity.this, SurveyMiddle5Activity.class);
                startActivity(intent);
            });
        }
    }

}

