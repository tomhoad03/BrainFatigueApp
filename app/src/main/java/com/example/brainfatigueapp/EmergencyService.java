package com.example.brainfatigueapp;

import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.IBinder;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmergencyService extends Service {
    private static final int NOTIFICATION_ID = 2;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
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

        startForeground();
        return super.onStartCommand(intent, flags, startId);
    }

    private class RecorderTask extends TimerTask {
        private final MediaRecorder mediaRecorder;
        private long timeout = System.currentTimeMillis();

        public RecorderTask(MediaRecorder mediaRecorder) {
            this.mediaRecorder = mediaRecorder;
        }

        public double getAmplitude() {
            if (mediaRecorder != null) {
                return mediaRecorder.getMaxAmplitude();
            } else {
                return 0;
            }
        }

        public void run() {
            double amplitude = 20 * Math.log10(getAmplitude() / 32768);

            if (amplitude >= 0) {
                Log.d("Audio", "Pass: " + amplitude);

                if (System.currentTimeMillis() > timeout) {
                    timeout = System.currentTimeMillis() + 10000;
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
                            .setContentText("We detected a significant jump in volume!")
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
            }
        }
    }

    private void startForeground() {
        Intent notificationIntent = new Intent(this, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, PendingIntent.FLAG_IMMUTABLE);

        startForeground(NOTIFICATION_ID, new NotificationCompat.Builder(this, "BrainFatigueApp")
                .setSmallIcon(R.drawable.ic_notification_vector)
                .setContentIntent(pendingIntent)
                .setDefaults(Notification.FLAG_AUTO_CANCEL)
                .build());
    }
}