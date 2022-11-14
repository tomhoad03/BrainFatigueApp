package com.example.brainfatigueapp;

import android.content.Intent;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class SurveyMiddle3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_middle3);

        // Hide action bar
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();

        // Back button
        final ImageButton surveyBackBtn = findViewById(R.id.activity_survey_middle3_back_button);
        surveyBackBtn.setOnClickListener(v -> {
            Intent intent = new Intent(SurveyMiddle3Activity.this, SurveyMiddle2Activity.class);
            startActivity(intent);
        });

        // Option 1 - Looking after myself
        final Button lookAfterMyselfBtn = findViewById(R.id.activity_survey_middle3_button1);
        lookAfterMyselfBtn.setOnClickListener(v -> {
            Intent intent = new Intent(SurveyMiddle3Activity.this, SurveyMiddle4aActivity.class);
            startActivity(intent);
        });

        // Option 2 - Household or family tasks
        final Button houseFamTaskBtn = findViewById(R.id.activity_survey_middle3_button2);
        houseFamTaskBtn.setOnClickListener(v -> {
            Intent intent = new Intent(SurveyMiddle3Activity.this, SurveyMiddle4bActivity.class);
            startActivity(intent);
        });

        // Select activity buttons
        ArrayList<Button> activityButtons = new ArrayList<>();
//        activityButtons.add(findViewById(R.id.activity_survey_middle3_button1));
//        activityButtons.add(findViewById(R.id.activity_survey_middle3_button2));
        activityButtons.add(findViewById(R.id.activity_survey_middle3_button3));
        activityButtons.add(findViewById(R.id.activity_survey_middle3_button4));
        activityButtons.add(findViewById(R.id.activity_survey_middle3_button5));
        activityButtons.add(findViewById(R.id.activity_survey_middle3_button6));
        activityButtons.add(findViewById(R.id.activity_survey_middle3_button7));

        for (Button activityButton : activityButtons) {
            activityButton.setOnClickListener(v -> {
                Intent intent = new Intent(SurveyMiddle3Activity.this, SurveyMiddle4aActivity.class);
                startActivity(intent);
            });
        }

    }
}