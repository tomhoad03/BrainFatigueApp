package com.example.brainfatigueapp;

import android.content.Intent;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.work.*;

import java.util.concurrent.TimeUnit;

public class SurveyEndActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_end);

        // Hide action bar
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();

        // Back button
        final ImageButton surveyBackBtn = findViewById(R.id.activity_survey_end_back_button);
        surveyBackBtn.setOnClickListener(v -> {
            Intent intent = new Intent(SurveyEndActivity.this, SurveyMiddle9Activity.class);
            startActivity(intent);
        });

        // Next button
        final Button surveySubmitBtn = findViewById(R.id.activity_survey_end_submit_button);
        surveySubmitBtn.setOnClickListener(v -> {
            // Work manager notifications
            WorkManager workManager = WorkManager.getInstance(getApplicationContext());
            Constraints constraints = new Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
                    .setRequiresBatteryNotLow(false)
                    .build();
            WorkRequest uploadWorkRequest = new OneTimeWorkRequest.Builder(NotificationWorker.class)
                    .setInitialDelay(30, TimeUnit.MINUTES)
                    .setConstraints(constraints)
                    .build();
            workManager.enqueue(uploadWorkRequest);

            Intent intent = new Intent(SurveyEndActivity.this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }
}