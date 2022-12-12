package com.example.brainfatigueapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SurveyStartActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_start);

        // Hide action bar
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            SurveyDatabase surveyDatabase = SurveyDatabase.getDatabase(getApplicationContext());
            SurveyResultDao surveyResultDao = surveyDatabase.surveyResultDao();
            surveyResultDao.insert(new SurveyResult(1));
            List<SurveyResult> surveyResults = surveyResultDao.getAll();
            Log.d("survey_results", surveyResults.toString());
        });
        executorService.shutdown();

        // Next button
        final Button surveyNextBtn = findViewById(R.id.activity_survey_start_start_button);
        surveyNextBtn.setOnClickListener(v -> {
            SurveyResult surveyResult = new SurveyResult(1);

            Intent intent = new Intent(SurveyStartActivity.this, SurveyMiddle1Activity.class);
            intent.putExtra("survey_result", surveyResult);
            startActivity(intent);
        });
    }
}