package com.example.brainfatigueapp;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class EmergencyService extends Service {
    private static final int NOTIFICATION_ID = 2;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        startForeground();
        return super.onStartCommand(intent, flags, startId);
    }

    private void startForeground() {
        Intent notificationIntent = new Intent(this, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, PendingIntent.FLAG_IMMUTABLE);

        startForeground(NOTIFICATION_ID, new NotificationCompat.Builder(this, "BrainFatigueApp")
                .setSmallIcon(R.drawable.ic_notification_vector)
                .setContentTitle("Brain Fatigue App")
                .setContentText("Service is running background")
                .setContentIntent(pendingIntent)
                .build());
    }
}