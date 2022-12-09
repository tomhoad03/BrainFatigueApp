package com.example.brainfatigueapp;

import android.content.Intent;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class SurveyMiddle4Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_middle4);

        // Hide action bar
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();

        // Next button
        final Button surveyNextBtn = findViewById(R.id.activity_survey_middle4_next_button);
        surveyNextBtn.setOnClickListener(v -> {
            Intent intent = new Intent(SurveyMiddle4Activity.this, SurveyMiddle5Activity.class);
            startActivity(intent);
        });
    }
}