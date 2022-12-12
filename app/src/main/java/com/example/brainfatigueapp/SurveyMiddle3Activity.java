package com.example.brainfatigueapp;

import android.content.Intent;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class SurveyMiddle3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_middle3);

        // Hide action bar
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();

        // Option 1 - Looking after myself
        final Button lookAfterMyselfBtn = findViewById(R.id.activity_survey_middle3_button1);
        lookAfterMyselfBtn.setOnClickListener(v -> {
            SurveyResult surveyResult = (SurveyResult) getIntent().getSerializableExtra("survey_result");
            surveyResult.setQuestion3(1);

            Intent intent = new Intent(SurveyMiddle3Activity.this, SurveyMiddle3aActivity.class);
            intent.putExtra("survey_result", surveyResult);
            startActivity(intent);
        });

        // Option 2 - Household or family tasks
        final Button houseFamTaskBtn = findViewById(R.id.activity_survey_middle3_button2);
        houseFamTaskBtn.setOnClickListener(v -> {
            SurveyResult surveyResult = (SurveyResult) getIntent().getSerializableExtra("survey_result");
            surveyResult.setQuestion3(2);

            Intent intent = new Intent(SurveyMiddle3Activity.this, SurveyMiddle3bActivity.class);
            intent.putExtra("survey_result", surveyResult);
            startActivity(intent);
        });

        // Option 3 - Working or studying
        final Button workOrStudyBtn = findViewById(R.id.activity_survey_middle3_button3);
        workOrStudyBtn.setOnClickListener(v -> {
            SurveyResult surveyResult = (SurveyResult) getIntent().getSerializableExtra("survey_result");
            surveyResult.setQuestion3(3);

            Intent intent = new Intent(SurveyMiddle3Activity.this, SurveyMiddle3cActivity.class);
            intent.putExtra("survey_result", surveyResult);
            startActivity(intent);
        });

        // Option 4 - Social activities
        final Button socialActBtn = findViewById(R.id.activity_survey_middle3_button4);
        socialActBtn.setOnClickListener(v -> {
            SurveyResult surveyResult = (SurveyResult) getIntent().getSerializableExtra("survey_result");
            surveyResult.setQuestion3(4);

            Intent intent = new Intent(SurveyMiddle3Activity.this, SurveyMiddle3dActivity.class);
            intent.putExtra("survey_result", surveyResult);
            startActivity(intent);
        });

        // Option 5 - Using a computer/tablet/phone
        final Button usingCompTabPhoneBtn = findViewById(R.id.activity_survey_middle3_button5);
        usingCompTabPhoneBtn.setOnClickListener(v -> {
            SurveyResult surveyResult = (SurveyResult) getIntent().getSerializableExtra("survey_result");
            surveyResult.setQuestion3(5);

            Intent intent = new Intent(SurveyMiddle3Activity.this, SurveyMiddle3eActivity.class);
            intent.putExtra("survey_result", surveyResult);
            startActivity(intent);
        });

        // Option 6 - Exercising
        final Button exercisingBtn = findViewById(R.id.activity_survey_middle3_button6);
        exercisingBtn.setOnClickListener(v -> {
            SurveyResult surveyResult = (SurveyResult) getIntent().getSerializableExtra("survey_result");
            surveyResult.setQuestion3(6);

            Intent intent = new Intent(SurveyMiddle3Activity.this, SurveyMiddle3fActivity.class);
            intent.putExtra("survey_result", surveyResult);
            startActivity(intent);
        });

        // Option 7 - Hobby or quiet past time
        final Button hobbyQuietPastTimeBtn = findViewById(R.id.activity_survey_middle3_button7);
        hobbyQuietPastTimeBtn.setOnClickListener(v -> {
            SurveyResult surveyResult = (SurveyResult) getIntent().getSerializableExtra("survey_result");
            surveyResult.setQuestion3(7);

            Intent intent = new Intent(SurveyMiddle3Activity.this, SurveyMiddle3gActivity.class);
            intent.putExtra("survey_result", surveyResult);
            startActivity(intent);
        });
    }
}