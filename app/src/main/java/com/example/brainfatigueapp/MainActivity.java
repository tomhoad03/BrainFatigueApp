package com.example.brainfatigueapp;

import android.app.*;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Hide action bar
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();

        // Display homepage after delay
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Intent intent = new Intent(MainActivity.this, LoginPageActivity.class);
            startActivity(intent);
            finish();
        }, 3000);

        // Create the notification channel
        createNotificationChannel();
    }

    private void createNotificationChannel() {
        NotificationChannel channel = new NotificationChannel("", "BrainFatigueApp", NotificationManager.IMPORTANCE_HIGH);
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }
}