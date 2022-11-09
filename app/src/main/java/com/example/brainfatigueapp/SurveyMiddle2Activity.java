package com.example.brainfatigueapp;

import android.content.Intent;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class SurveyMiddle2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_middle2);

        // Hide action bar
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();

        // Back button
        final Button surveyBackBtn = findViewById(R.id.activity_home_survey_button_somewhere_else);
        surveyBackBtn.setOnClickListener(v -> {
            Intent intent = new Intent(SurveyMiddle2Activity.this, SurveyMiddle1Activity.class);
            startActivity(intent);
        });

        // Option 1 - At home
        final Button atHomeBtn = findViewById(R.id.activity_home_survey_button_at_home);
        atHomeBtn.setOnClickListener(v -> {
            Intent intent = new Intent(SurveyMiddle2Activity.this, SurveyMiddle3Activity.class);
            startActivity(intent);
        });

        // Next button
        final Button somewhereElseBtn = findViewById(R.id.activity_home_survey_button_somewhere_else);
        somewhereElseBtn.setOnClickListener(v -> {
            Intent intent = new Intent(SurveyMiddle2Activity.this, SurveyMiddle4Activity.class);
            startActivity(intent);
        });
    }
}