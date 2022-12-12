package com.example.brainfatigueapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SurveyEndActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_end);

        // Hide action bar
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();

        // Next button
        final Button surveySubmitBtn = findViewById(R.id.activity_survey_end_submit_button);
        surveySubmitBtn.setOnClickListener(v -> {
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.execute(() -> {
                SurveyDatabase surveyDatabase = SurveyDatabase.getDatabase(getApplicationContext());
                SurveyResultDao surveyResultDao = surveyDatabase.surveyResultDao();

                SurveyResult surveyResult = (SurveyResult) getIntent().getSerializableExtra("survey_result");
                surveyResultDao.insert(surveyResult);

                List<SurveyResult> surveyResults = surveyResultDao.getAll();
                Log.d("survey_results", surveyResults.toString());
            });
            executorService.shutdown();

            // Setup the next notification
            Intent notifyIntent = new Intent(getApplicationContext(), NotificationReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (60000 * 30), pendingIntent);

            Intent intent = new Intent(SurveyEndActivity.this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }
}