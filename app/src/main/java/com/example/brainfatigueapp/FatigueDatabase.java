package com.example.brainfatigueapp;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {SurveyResult.class, Settings.class}, version = 2, exportSchema = true)
public abstract class FatigueDatabase extends RoomDatabase {
    public abstract SurveyResultDao surveyResultDao();
    public abstract SettingsDao settingsDao();
    private static volatile FatigueDatabase INSTANCE;

    static FatigueDatabase getDatabase(final Context context) {
        if (INSTANCE == null)
            synchronized (FatigueDatabase.class) {
                if (INSTANCE == null)
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), FatigueDatabase.class, "survey_database").fallbackToDestructiveMigration().build();
            }
        return INSTANCE;
    }
}

