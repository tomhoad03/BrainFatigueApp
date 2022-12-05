package com.example.brainfatigueapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.appcompat.app.AppCompatActivity;
import androidx.work.*;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Hide action bar
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();

        // Create the notification channel
        createNotificationChannel();

        // Work manager notifications
        WorkManager workManager = WorkManager.getInstance(getApplicationContext());
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresBatteryNotLow(false)
                .build();
        WorkRequest uploadWorkRequest = new PeriodicWorkRequest.Builder(NotificationWorker.class, 180, TimeUnit.MINUTES, 30, TimeUnit.MINUTES) // Use OneTimeWorkRequest for loud noises and high spikes of activity
                .setConstraints(constraints)
                .build();
        workManager.enqueue(uploadWorkRequest);

        // Display homepage after delay
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Intent intent = new Intent(MainActivity.this, LoginPageActivity.class);
            startActivity(intent);
            finish();
        }, 3000);
    }

    private void createNotificationChannel() {
        NotificationChannel channel = new NotificationChannel("", "BrainFatigueApp", NotificationManager.IMPORTANCE_HIGH);
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }
}