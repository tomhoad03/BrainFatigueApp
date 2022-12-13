package com.example.brainfatigueapp;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class NotificationSchedulingTest {
    @Test
    public void testSchedule() {
        // NotificationReceiver notificationReceiver = new NotificationReceiver();
        long time = scheduleNotification(33480000L, 10800000L, 32400000L, 39600000L, 54000000L, 79200000L);
        assertEquals(58680000L, time);
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
