package com.example.brainfatigueapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.widget.Button;
import android.os.Bundle;

import java.util.ArrayList;

// this activity shows the options when "Looking after myself" is chosen
public class SurveyMiddle3aActivity extends AppCompatActivity {

    // each button points forward to middle5
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_middle3a);

        // Hide action bar
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();

        // Select activity buttons
        ArrayList<Button> activityButtons = new ArrayList<>();
        activityButtons.add(findViewById(R.id.activity_survey_middle3a_button1));
        activityButtons.add(findViewById(R.id.activity_survey_middle3a_button2));
        activityButtons.add(findViewById(R.id.activity_survey_middle3a_button3));
        activityButtons.add(findViewById(R.id.activity_survey_middle3a_button4));
        activityButtons.add(findViewById(R.id.activity_survey_middle3a_button5));
        int count = 1;

        for (Button activityButton : activityButtons) {
            SurveyResult surveyResult = (SurveyResult) getIntent().getSerializableExtra("survey_result");
            surveyResult.setQuestion4(count);
            count++;

            activityButton.setOnClickListener(v -> {
                Intent intent = new Intent(SurveyMiddle3aActivity.this, SurveyMiddle5Activity.class);
                intent.putExtra("survey_result", surveyResult);
                startActivity(intent);
            });
        }
    }

}
