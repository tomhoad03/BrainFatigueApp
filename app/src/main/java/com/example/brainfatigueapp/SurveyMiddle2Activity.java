package com.example.brainfatigueapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class SurveyMiddle2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_middle2);

        // Hide action bar
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();

        // Option 1 - At home
        final Button atHomeBtn = findViewById(R.id.activity_survey_middle2_button1);
        atHomeBtn.setOnClickListener(v -> {
            SurveyResult surveyResult = (SurveyResult) getIntent().getSerializableExtra("survey_result");
            surveyResult.setQuestion2(1);

            Intent intent = new Intent(SurveyMiddle2Activity.this, SurveyMiddle3Activity.class);
            intent.putExtra("survey_result", surveyResult);
            startActivity(intent);
        });

        // Option 2 - Somewhere else
        final Button somewhereElseBtn = findViewById(R.id.activity_survey_middle2_button2);
        somewhereElseBtn.setOnClickListener(v -> {
            SurveyResult surveyResult = (SurveyResult) getIntent().getSerializableExtra("survey_result");
            surveyResult.setQuestion2(2);

            Intent intent = new Intent(SurveyMiddle2Activity.this, SurveyMiddle4Activity.class);
            intent.putExtra("survey_result", surveyResult);
            startActivity(intent);
        });
    }
}