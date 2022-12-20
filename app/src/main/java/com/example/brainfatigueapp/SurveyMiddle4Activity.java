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

        // Option 1 - Looking after myself
        final Button lookAfterMyselfBtn = findViewById(R.id.activity_survey_middle4_button1);
        lookAfterMyselfBtn.setOnClickListener(v -> {
            SurveyResult surveyResult = (SurveyResult) getIntent().getSerializableExtra("survey_result");
            surveyResult.setQuestion4(1);

            Intent intent = new Intent(SurveyMiddle4Activity.this, SurveyMiddle4aActivity.class);
            intent.putExtra("survey_result", surveyResult);
            startActivity(intent);
        });

        // Option 2 - Household or family tasks
        final Button houseFamTaskBtn = findViewById(R.id.activity_survey_middle4_button2);
        houseFamTaskBtn.setOnClickListener(v -> {
            SurveyResult surveyResult = (SurveyResult) getIntent().getSerializableExtra("survey_result");
            surveyResult.setQuestion4(2);

            Intent intent = new Intent(SurveyMiddle4Activity.this, SurveyMiddle4bActivity.class);
            intent.putExtra("survey_result", surveyResult);
            startActivity(intent);
        });

        // Option 3 - Working or studying
        final Button workOrStudyBtn = findViewById(R.id.activity_survey_middle4_button3);
        workOrStudyBtn.setOnClickListener(v -> {
            SurveyResult surveyResult = (SurveyResult) getIntent().getSerializableExtra("survey_result");
            surveyResult.setQuestion4(3);

            Intent intent = new Intent(SurveyMiddle4Activity.this, SurveyMiddle4cActivity.class);
            intent.putExtra("survey_result", surveyResult);
            startActivity(intent);
        });

        // Option 4 - Travelling
        final Button travellingBtn = findViewById(R.id.activity_survey_middle4_button4);
        travellingBtn.setOnClickListener(v -> {
            SurveyResult surveyResult = (SurveyResult) getIntent().getSerializableExtra("survey_result");
            surveyResult.setQuestion4(4);

            Intent intent = new Intent(SurveyMiddle4Activity.this, SurveyMiddle4dActivity.class);
            intent.putExtra("survey_result", surveyResult);
            startActivity(intent);
        });

        // Option 5 - Social activities
        final Button socialActBtn = findViewById(R.id.activity_survey_middle4_button5);
        socialActBtn.setOnClickListener(v -> {
            SurveyResult surveyResult = (SurveyResult) getIntent().getSerializableExtra("survey_result");
            surveyResult.setQuestion4(5);

            Intent intent = new Intent(SurveyMiddle4Activity.this, SurveyMiddle4eActivity.class);
            intent.putExtra("survey_result", surveyResult);
            startActivity(intent);
        });

        // Option 6 - Using a computer/tablet/phone
        final Button usingCompTabPhoneBtn = findViewById(R.id.activity_survey_middle4_button6);
        usingCompTabPhoneBtn.setOnClickListener(v -> {
            SurveyResult surveyResult = (SurveyResult) getIntent().getSerializableExtra("survey_result");
            surveyResult.setQuestion4(6);

            Intent intent = new Intent(SurveyMiddle4Activity.this, SurveyMiddle4fActivity.class);
            intent.putExtra("survey_result", surveyResult);
            startActivity(intent);
        });

        // Option 7 - Exercising
        final Button exercisingBtn = findViewById(R.id.activity_survey_middle4_button7);
        exercisingBtn.setOnClickListener(v -> {
            SurveyResult surveyResult = (SurveyResult) getIntent().getSerializableExtra("survey_result");
            surveyResult.setQuestion4(7);

            Intent intent = new Intent(SurveyMiddle4Activity.this, SurveyMiddle4gActivity.class);
            intent.putExtra("survey_result", surveyResult);
            startActivity(intent);
        });

        // Option 8 - Hobby or quiet past time
        final Button hobbyQuietPastTimeBtn = findViewById(R.id.activity_survey_middle4_button8);
        hobbyQuietPastTimeBtn.setOnClickListener(v -> {
            SurveyResult surveyResult = (SurveyResult) getIntent().getSerializableExtra("survey_result");
            surveyResult.setQuestion4(8);

            Intent intent = new Intent(SurveyMiddle4Activity.this, SurveyMiddle4hActivity.class);
            intent.putExtra("survey_result", surveyResult);
            startActivity(intent);
        });
    }
}