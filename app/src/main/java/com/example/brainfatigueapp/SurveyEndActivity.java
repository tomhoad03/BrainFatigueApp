package com.example.brainfatigueapp;

import android.content.Intent;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class SurveyEndActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_end);

        // Hide action bar
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();

        // Next button
        final Button surveySubmitBtn = findViewById(R.id.activity_survey_end_submit_button);
        surveySubmitBtn.setOnClickListener(v -> {
            Intent intent = new Intent(SurveyEndActivity.this, HomeActivity.class);
            startActivity(intent);
        });
    }
}