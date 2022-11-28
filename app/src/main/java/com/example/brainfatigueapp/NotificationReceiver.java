package com.example.brainfatigueapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotificationReceiver extends BroadcastReceiver {
    public NotificationReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent newIntent = new Intent(context, NotificationReceiverService.class);
        context.startService(newIntent);
    }
}
