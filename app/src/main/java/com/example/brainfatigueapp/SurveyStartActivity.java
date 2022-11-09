package com.example.brainfatigueapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

public class SurveyStartActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_start);

        // Hide action bar
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();

        // Back button
        final ImageButton startSurveyBtn = findViewById(R.id.activity_survey_start_back_button);
        startSurveyBtn.setOnClickListener(v -> {
            Intent intent = new Intent(SurveyStartActivity.this, HomeActivity.class);
            startActivity(intent);
        });

        // Next button
        final Button surveyNextBtn = findViewById(R.id.activity_survey_start_start_button);
        surveyNextBtn.setOnClickListener(v -> {
            Intent intent = new Intent(SurveyStartActivity.this, SurveyMiddle1Activity.class);
            startActivity(intent);
        });
    }
}