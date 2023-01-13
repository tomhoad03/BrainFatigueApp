package com.example.brainfatigueapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class SurveyStartActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_start);

        // Hide action bar
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();

        // Next button
        final Button surveyNextBtn = findViewById(R.id.activity_survey_start_start_button);
        surveyNextBtn.setOnClickListener(v -> {
            SurveyResult surveyResult = new SurveyResult(System.currentTimeMillis());

            Intent intent = new Intent(SurveyStartActivity.this, SurveyMiddle1Activity.class);
            intent.putExtra("survey_result", surveyResult);
            startActivity(intent);
        });
    }
}