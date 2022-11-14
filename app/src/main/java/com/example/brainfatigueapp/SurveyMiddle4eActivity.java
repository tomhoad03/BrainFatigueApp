package com.example.brainfatigueapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

// this activity shows the options when "Using a computer/tablet/phone" is chosen
public class SurveyMiddle4eActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_middle4e);

        // Hide action bar
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();

        // Back button
        // points back to middle3 "What have you been doing the last 10 minutes?" page
        final ImageButton surveyBackBtn = findViewById(R.id.activity_survey_middle4e_back_button);
        surveyBackBtn.setOnClickListener(v -> {
            Intent intent = new Intent(SurveyMiddle4eActivity.this, SurveyMiddle3Activity.class);
            startActivity(intent);
        });

        // Select activity buttons
        ArrayList<Button> activityButtons = new ArrayList<>();
        activityButtons.add(findViewById(R.id.activity_survey_middle4e_button1));
        activityButtons.add(findViewById(R.id.activity_survey_middle4e_button2));
        activityButtons.add(findViewById(R.id.activity_survey_middle4e_button3));
        activityButtons.add(findViewById(R.id.activity_survey_middle4e_button4));
        activityButtons.add(findViewById(R.id.activity_survey_middle4e_button5));
        activityButtons.add(findViewById(R.id.activity_survey_middle4e_button6));

        // each button points forward to middle5
        for (Button activityButton : activityButtons) {
            activityButton.setOnClickListener(v -> {
                Intent intent = new Intent(SurveyMiddle4eActivity.this, SurveyMiddle5Activity.class);
                startActivity(intent);
            });
        }
    }
}
