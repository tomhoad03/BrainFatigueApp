package com.example.brainfatigueapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

// this activity shows the options when "Household or family tasks" is chosen
public class SurveyMiddle3bActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_middle3b);

        // Hide action bar
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();

        // Select activity buttons
        ArrayList<Button> activityButtons = new ArrayList<>();
        activityButtons.add(findViewById(R.id.activity_survey_middle3b_button1));
        activityButtons.add(findViewById(R.id.activity_survey_middle3b_button2));
        activityButtons.add(findViewById(R.id.activity_survey_middle3b_button3));
        activityButtons.add(findViewById(R.id.activity_survey_middle3b_button4));
        activityButtons.add(findViewById(R.id.activity_survey_middle3b_button5));
        activityButtons.add(findViewById(R.id.activity_survey_middle3b_button6));
        activityButtons.add(findViewById(R.id.activity_survey_middle3b_button7));
        activityButtons.add(findViewById(R.id.activity_survey_middle3b_button8));
        int count = 6;

        for (Button activityButton : activityButtons) {
            SurveyResult surveyResult = (SurveyResult) getIntent().getSerializableExtra("survey_result");
            surveyResult.setQuestion3Extended(count);
            count++;

            activityButton.setOnClickListener(v -> {
                Intent intent = new Intent(SurveyMiddle3bActivity.this, SurveyMiddle5Activity.class);
                intent.putExtra("survey_result", surveyResult);
                startActivity(intent);
            });
        }
    }
}
