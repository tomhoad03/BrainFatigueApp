package com.example.brainfatigueapp;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class HomeActivity extends AppCompatActivity {

    GoogleSignInOptions gsi_options;
    GoogleSignInClient gsi_client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Hide action bar
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();

        // Start survey button
        final Button startSurveyBtn = findViewById(R.id.activity_home_survey_button);
        startSurveyBtn.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, SurveyStartActivity.class);
            startActivity(intent);
        });

        // Open the dashboard button
        final Button dashboardBtn = findViewById(R.id.activity_home_dashboard_button);
        dashboardBtn.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, DashboardActivity.class);
            startActivity(intent);
        });

        TextView welcomeMessage = findViewById(R.id.activity_home_welcome);
        gsi_options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsi_client = GoogleSignIn.getClient(this, gsi_options);
        GoogleSignInAccount googleAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (googleAccount != null) {
            String firstName = googleAccount.getGivenName();
            welcomeMessage.setText("Hi " + firstName + ", What would you like to do today?");
        }

        // Database access
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<Setting> futureSetting = executorService.submit(() -> {
            FatigueDatabase fatigueDatabase = FatigueDatabase.getDatabase(getApplicationContext());
            SettingsDao SettingDao = fatigueDatabase.settingsDao();

            List<Setting> Setting = SettingDao.getAll();
            Setting setting;
            try {
                setting = Setting.get(Setting.size() - 1);
            } catch (Exception e) {
                setting = new Setting();
            }
            return setting;
        });
        executorService.shutdown();

        long timeout = System.currentTimeMillis() + 10000;
        Setting resultSetting;

        while (System.currentTimeMillis() < timeout) {
            try {
                resultSetting = futureSetting.get();
            } catch (ExecutionException | InterruptedException e) {
                continue;
            }

            long scheduledNotification = System.currentTimeMillis() - (((long) LocalTime.now().getHour() * 3600 * 1000) + ((long) LocalTime.now().getMinute() * 60 * 1000) + (LocalTime.now().getSecond() * 1000L)) + resultSetting.getSummary();

            // Setup the daily summary notification
            Intent notifyIntent = new Intent(getApplicationContext(), SummaryReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            getSystemService(NotificationManager.class).cancel(1);
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + scheduledNotification, pendingIntent);
            break;
        }
    }
}