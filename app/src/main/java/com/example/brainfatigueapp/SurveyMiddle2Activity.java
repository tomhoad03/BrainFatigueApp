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
        final ImageButton surveyBackBtn = findViewById(R.id.activity_survey_middle2_back_button);
        surveyBackBtn.setOnClickListener(v -> {
            Intent intent = new Intent(SurveyMiddle2Activity.this, SurveyMiddle1Activity.class);
            startActivity(intent);
        });

        // Option 1 - At home
        final Button atHomeBtn = findViewById(R.id.activity_survey_middle2_button1);
        atHomeBtn.setOnClickListener(v -> {
            Intent intent = new Intent(SurveyMiddle2Activity.this, SurveyMiddle3Activity.class);
            startActivity(intent);
        });

        // Option 2 - Somewhere else
        final Button somewhereElseBtn = findViewById(R.id.activity_survey_middle2_button2);
        somewhereElseBtn.setOnClickListener(v -> {
            Intent intent = new Intent(SurveyMiddle2Activity.this, SurveyMiddle4Activity.class);
            startActivity(intent);
        });
    }
}