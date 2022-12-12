package com.example.brainfatigueapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.slider.Slider;

public class SurveyMiddle6Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_middle6);

        // Hide action bar
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();

        // Next button
        final Button surveyNextBtn = findViewById(R.id.activity_survey_middle6_next_button);
        surveyNextBtn.setOnClickListener(v -> {
            SurveyResult surveyResult = (SurveyResult) getIntent().getSerializableExtra("survey_result"); // getIntent().getSerializableExtra("survey_result", SurveyResult.class); for API33+
            final Slider slider = findViewById(R.id.activity_survey_middle6_slider);
            surveyResult.setQuestion6((int) slider.getValue());

            Intent intent = new Intent(SurveyMiddle6Activity.this, SurveyMiddle7Activity.class);
            intent.putExtra("survey_result", surveyResult);
            startActivity(intent);
        });
    }
}