package com.example.brainfatigueapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
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

        // Schedule the next notification
        long interval = 10800000L, dayStart = 32400000L, blockStart = 39600000L, blockEnd = 54000000L, dayEnd = 79200000L;
        long scheduledNotification = scheduleNotification(System.currentTimeMillis(), interval, dayStart, blockStart, blockEnd, dayEnd);

        // Notify the user of the next notification
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.UK);
        String submitPromptString = "To finish, press submit. The next survey prompt will arrive at " + sdf.format(System.currentTimeMillis() + scheduledNotification) + ".";

        final TextView submitPromptText = findViewById(R.id.endSurveySubMessage);
        submitPromptText.setText(submitPromptString);

        // Next button
        final Button surveySubmitBtn = findViewById(R.id.activity_survey_end_submit_button);
        surveySubmitBtn.setOnClickListener(v -> {
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.execute(() -> {
                SurveyDatabase surveyDatabase = SurveyDatabase.getDatabase(getApplicationContext());
                SurveyResultDao surveyResultDao = surveyDatabase.surveyResultDao();

                SurveyResult surveyResult = (SurveyResult) getIntent().getSerializableExtra("survey_result");
                surveyResultDao.insert(surveyResult);
            });
            executorService.shutdown();

            // Setup the next notification
            Intent notifyIntent = new Intent(getApplicationContext(), NotificationReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + scheduledNotification, pendingIntent);
            Log.d("notification_scheduled", sdf.format(System.currentTimeMillis() + scheduledNotification));

            Intent intent = new Intent(SurveyEndActivity.this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }

    public Long scheduleNotification(Long startTime, Long interval, Long dayStart, Long blockStart, Long blockEnd, Long dayEnd) {
        long remainingTime = interval;
        long alarmTime = startTime;

        while (remainingTime > 0) {
            if (alarmTime >= dayStart && alarmTime < blockStart) {
                long gap = blockStart - alarmTime;
                if (gap > remainingTime) {
                    alarmTime += remainingTime;
                    break;
                } else {
                    alarmTime = blockEnd;
                    remainingTime -= gap;
                }
            } else {
                long gap = dayEnd - alarmTime;
                if (gap > remainingTime) {
                    alarmTime += remainingTime;
                    break;
                } else {
                    alarmTime = dayStart;
                    remainingTime -= gap;
                }
            }
        }

        return alarmTime;
    }
}