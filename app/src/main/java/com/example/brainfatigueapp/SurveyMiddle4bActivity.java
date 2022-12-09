package com.example.brainfatigueapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

// this activity shows the options when "Household or family tasks" is chosen
public class SurveyMiddle4bActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_middle4b);

        // Hide action bar
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();

        // Select activity buttons
        ArrayList<Button> activityButtons = new ArrayList<>();
        activityButtons.add(findViewById(R.id.activity_survey_middle4b_button1));
        activityButtons.add(findViewById(R.id.activity_survey_middle4b_button2));
        activityButtons.add(findViewById(R.id.activity_survey_middle4b_button3));
        activityButtons.add(findViewById(R.id.activity_survey_middle4b_button4));
        activityButtons.add(findViewById(R.id.activity_survey_middle4b_button5));
        activityButtons.add(findViewById(R.id.activity_survey_middle4b_button6));
        activityButtons.add(findViewById(R.id.activity_survey_middle4b_button7));
        activityButtons.add(findViewById(R.id.activity_survey_middle4b_button8));
        int count = 1;

        for (Button activityButton : activityButtons) {
            SurveyResult surveyResult = (SurveyResult) getIntent().getSerializableExtra("survey_result");
            surveyResult.setQuestion4(count);
            count++;

            activityButton.setOnClickListener(v -> {
                Intent intent = new Intent(SurveyMiddle4bActivity.this, SurveyMiddle5Activity.class);
                intent.putExtra("survey_result", surveyResult);
                startActivity(intent);
            });
        }
    }
}
