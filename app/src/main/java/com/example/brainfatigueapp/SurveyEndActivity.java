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
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SurveyEndActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_end);

        // Hide action bar
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();

        // Database access
        ExecutorService executorService1 = Executors.newSingleThreadExecutor();
        Future<Settings> futureSetting = executorService1.submit(() -> {
            FatigueDatabase fatigueDatabase = FatigueDatabase.getDatabase(getApplicationContext());
            SettingsDao settingsDao = fatigueDatabase.settingsDao();

            List<Settings> settings = settingsDao.getAll();
            Settings setting;
            try {
                setting = settings.get(settings.size() - 1);
            } catch (Exception e) {
                setting = new Settings();
            }
            Log.d("survey_results", setting.toString());
            return setting;
        });
        executorService1.shutdown();

        // Schedule the next notification
        long timeout = System.currentTimeMillis() + 10000;
        Settings resultSetting;

        while (System.currentTimeMillis() < timeout) {
            try {
                resultSetting = futureSetting.get();
            } catch (ExecutionException | InterruptedException e) {
                continue;
            }

            long scheduledNotification = scheduleNotification(System.currentTimeMillis(), resultSetting.getInterval(), resultSetting.getDayStart(), resultSetting.getDayEnd(), resultSetting.getWorkStart(), resultSetting.getWorkEnd());

            // Notify the user of the next notification
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.UK);
            String submitPromptString = "To finish, press submit. The next survey prompt will arrive at " + sdf.format(System.currentTimeMillis() + scheduledNotification) + ".";

            final TextView submitPromptText = findViewById(R.id.endSurveySubMessage);
            submitPromptText.setText(submitPromptString);

            // Next button
            final Button surveySubmitBtn = findViewById(R.id.activity_survey_end_submit_button);
            surveySubmitBtn.setOnClickListener(v -> {
                ExecutorService executorService2 = Executors.newSingleThreadExecutor();
                executorService2.execute(() -> {
                    FatigueDatabase fatigueDatabase = FatigueDatabase.getDatabase(getApplicationContext());
                    SurveyResultDao surveyResultDao = fatigueDatabase.surveyResultDao();

                    SurveyResult surveyResult = (SurveyResult) getIntent().getSerializableExtra("survey_result");
                    surveyResultDao.insert(surveyResult);
                });
                executorService2.shutdown();

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
            break;
        }
    }

    public Long scheduleNotification(Long startTime, Long interval, Long dayStart, Long dayEnd, Long workStart, Long workEnd) {
        long remainingTime = interval;
        long alarmTime = startTime;

        while (remainingTime > 0) {
            if (alarmTime >= dayStart && alarmTime < workStart) {
                long gap = workStart - alarmTime;
                if (gap > remainingTime) {
                    alarmTime += remainingTime;
                    break;
                } else {
                    alarmTime = workEnd;
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