package com.example.brainfatigueapp;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class NotificationSchedulingTest {
    @Test
    public void testSchedule() {
        SurveyEndActivity surveyEndActivity = new SurveyEndActivity();
        long time = surveyEndActivity.scheduleNotification(33480000L, 10800000L, 32400000L, 39600000L, 54000000L, 79200000L);
        assertEquals(25200000L, time);
    }
}
