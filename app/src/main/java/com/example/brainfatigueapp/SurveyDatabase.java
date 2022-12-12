package com.example.brainfatigueapp;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {SurveyResult.class}, version = 1)
public abstract class SurveyDatabase extends RoomDatabase {
    public abstract SurveyResultDao surveyResultDao();
    private static volatile SurveyDatabase INSTANCE;

    static SurveyDatabase getDatabase(final Context context) {
        if (INSTANCE == null)
            synchronized (SurveyDatabase.class) {
                if (INSTANCE == null)
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), SurveyDatabase.class, "survey_database").build();
            }
        return INSTANCE;
    }
}

