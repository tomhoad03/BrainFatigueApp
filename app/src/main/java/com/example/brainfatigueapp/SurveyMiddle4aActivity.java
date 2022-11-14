package com.example.brainfatigueapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.widget.Button;
import android.widget.ImageButton;
import android.os.Bundle;

import java.util.ArrayList;

// this activity shows the options when "Looking after myself" is chosen
public class SurveyMiddle4aActivity extends AppCompatActivity {

    // each button points forward to middle5
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_middle4a);

        // Hide action bar
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();

        // Back button points back to middle3 "What have you been doing the last 10 minutes?" page
        final ImageButton surveyBackBtn = findViewById(R.id.activity_survey_middle4a_back_button);
        surveyBackBtn.setOnClickListener(v -> {
            Intent intent = new Intent(SurveyMiddle4aActivity.this, SurveyMiddle3Activity.class);
            startActivity(intent);
        });

        // Select activity buttons
        ArrayList<Button> activityButtons = new ArrayList<>();
        activityButtons.add(findViewById(R.id.activity_survey_middle4a_button1));
        activityButtons.add(findViewById(R.id.activity_survey_middle4a_button2));
        activityButtons.add(findViewById(R.id.activity_survey_middle4a_button3));
        activityButtons.add(findViewById(R.id.activity_survey_middle4a_button4));
        activityButtons.add(findViewById(R.id.activity_survey_middle4a_button5));

        for (Button activityButton : activityButtons) {
            activityButton.setOnClickListener(v -> {
                Intent intent = new Intent(SurveyMiddle4aActivity.this, SurveyMiddle5Activity.class);
                startActivity(intent);
            });
        }
    }

}
