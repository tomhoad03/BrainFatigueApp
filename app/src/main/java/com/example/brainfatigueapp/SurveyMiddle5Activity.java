package com.example.brainfatigueapp;

import android.content.Intent;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class SurveyMiddle5Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_middle5);

        // Hide action bar
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();

        // Back button
        final ImageButton surveyBackBtn = findViewById(R.id.activity_survey_middle5_back_button);
        surveyBackBtn.setOnClickListener(v -> {
            Intent intent = new Intent(SurveyMiddle5Activity.this, SurveyMiddle4Activity.class);
            startActivity(intent);
        });

        // Next button
        final Button surveyNextBtn = findViewById(R.id.activity_survey_middle5_next_button);
        surveyNextBtn.setOnClickListener(v -> {
            Intent intent = new Intent(SurveyMiddle5Activity.this, SurveyMiddle6Activity.class);
            startActivity(intent);
        });
    }
}