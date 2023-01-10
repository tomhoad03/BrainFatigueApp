package com.example.brainfatigueapp;

import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

        // Create the database
        FatigueDatabase.getDatabase(getApplicationContext());

        // Display homepage after delay
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Intent intent = new Intent(MainActivity.this, LoginPageActivity.class);
            startActivity(intent);
            finish();
        }, 2500);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.RECORD_AUDIO}, 1);
        }

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                MediaRecorder mediaRecorder = new MediaRecorder();
                mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
                mediaRecorder.setOutputFile(getExternalCacheDir().getAbsolutePath() + "/audio.3gp");

                mediaRecorder.prepare();
                mediaRecorder.start();

                Timer timer = new Timer();
                timer.scheduleAtFixedRate(new RecorderTask(mediaRecorder), 0, 500);
            } catch (IOException | RuntimeException e) {
                e.printStackTrace();
                Log.d("Audio", "Fail: " + e);
            }
        });
    }

    // Get max amplitude of volume
    private class RecorderTask extends TimerTask {
        private final MediaRecorder mediaRecorder;
        private long timeout = System.currentTimeMillis();

        public RecorderTask(MediaRecorder mediaRecorder) {
            this.mediaRecorder = mediaRecorder;
        }

        public double getAmplitude() {
            if (mediaRecorder != null)
                return (mediaRecorder.getMaxAmplitude());
            else
                return 0;

        }

        public void run() {
            runOnUiThread(() -> {
                double amplitudeDb = 20 * Math.log10(getAmplitude() / 32768);

                if (amplitudeDb > -10 && System.currentTimeMillis() > timeout) {
                    timeout = System.currentTimeMillis() + 20000;
                    // Create a notification intent
                    Intent intent = new Intent(getApplicationContext(), SurveyStartActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext());
                    stackBuilder.addNextIntentWithParentStack(intent);
                    PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

                    // Build the notification
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "")
                            .setSmallIcon(R.drawable.ic_notification_vector)
                            .setContentTitle("Brain Fatigue Tracker")
                            .setContentText("We detected a significant jump in volume! Please take a fatigue survey!")
                            .setStyle(new NotificationCompat.BigTextStyle().bigText("Brain Fatigue Tracker"))
                            .setStyle(new NotificationCompat.BigTextStyle().bigText("We detected a significant jump in volume! Please take a fatigue survey!"))
                            .setDefaults(Notification.DEFAULT_VIBRATE)
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setCategory(NotificationCompat.CATEGORY_CALL)
                            .setFullScreenIntent(pendingIntent, true)
                            .setAutoCancel(true)
                            .setOngoing(true);

                    // Display the notification
                    NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(2, builder.build());
                }
                Log.d("Audio", "Pass: " + amplitudeDb);
            });
        }
    }

    private void createNotificationChannel() {
        NotificationChannel channel = new NotificationChannel("", "BrainFatigueApp", NotificationManager.IMPORTANCE_HIGH);
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }
}