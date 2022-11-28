package com.example.brainfatigueapp;

import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotificationReceiverService extends IntentService {
    public NotificationReceiverService() {
        super("NotificationReceiverService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent ignore) {
        // Create a notification intent
        Intent intent = new Intent(this, SurveyStartActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntentWithParentStack(intent);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        // Build the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "")
                .setSmallIcon(R.drawable.ic_vector)
                .setContentTitle("Brain Fatigue App")
                .setContentText("It's time to take the survey!")
                .setDefaults(Notification.DEFAULT_ALL)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        // Display the notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(0, builder.build());
    }
}
