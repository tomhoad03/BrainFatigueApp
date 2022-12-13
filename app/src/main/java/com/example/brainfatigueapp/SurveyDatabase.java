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

    static String getSurveyString(Context context, Integer questionId, Integer questionResult, Boolean extended) {
        switch (questionId) {
            case 2:
                switch (questionResult) {
                    case 1: return context.getString(R.string.activity_survey_middle2_at_home);
                    case 2: return context.getString(R.string.activity_survey_middle2_somewhere_else);
                }
            case 3:
                if (!extended) {
                    switch (questionResult) {
                        case 1: return context.getString(R.string.activity_survey_middle3_button1);
                        case 2: return context.getString(R.string.activity_survey_middle3_button2);
                        case 3: return context.getString(R.string.activity_survey_middle3_button3);
                        case 4: return context.getString(R.string.activity_survey_middle3_button4);
                        case 5: return context.getString(R.string.activity_survey_middle3_button5);
                        case 6: return context.getString(R.string.activity_survey_middle3_button6);
                        case 7: return context.getString(R.string.activity_survey_middle3_button7);
                    }
                } else {
                    switch (questionResult) {
                        case 1: return context.getString(R.string.activity_survey_middle3a_button1);
                        case 2: return context.getString(R.string.activity_survey_middle3a_button2);
                        case 3: return context.getString(R.string.activity_survey_middle3a_button3);
                        case 4: return context.getString(R.string.activity_survey_middle3a_button4);
                        case 5: return context.getString(R.string.activity_survey_middle3a_button5);

                        case 6: return context.getString(R.string.activity_survey_middle3b_button1);
                        case 7: return context.getString(R.string.activity_survey_middle3b_button2);
                        case 8: return context.getString(R.string.activity_survey_middle3b_button3);
                        case 9: return context.getString(R.string.activity_survey_middle3b_button4);
                        case 10: return context.getString(R.string.activity_survey_middle3b_button5);
                        case 11: return context.getString(R.string.activity_survey_middle3b_button6);
                        case 12: return context.getString(R.string.activity_survey_middle3b_button7);
                        case 13: return context.getString(R.string.activity_survey_middle3b_button8);

                        case 14: return context.getString(R.string.activity_survey_middle3c_button1);
                        case 15: return context.getString(R.string.activity_survey_middle3c_button2);

                        case 16: return context.getString(R.string.activity_survey_middle3d_button1);
                        case 17: return context.getString(R.string.activity_survey_middle3d_button2);
                        case 18: return context.getString(R.string.activity_survey_middle3d_button3);

                        case 19: return context.getString(R.string.activity_survey_middle3e_button1);
                        case 20: return context.getString(R.string.activity_survey_middle3e_button2);
                        case 21: return context.getString(R.string.activity_survey_middle3e_button3);
                        case 22: return context.getString(R.string.activity_survey_middle3e_button4);
                        case 23: return context.getString(R.string.activity_survey_middle3e_button5);
                        case 24: return context.getString(R.string.activity_survey_middle3e_button6);

                        case 25: return context.getString(R.string.activity_survey_middle3f_button1);
                        case 26: return context.getString(R.string.activity_survey_middle3f_button2);
                        case 27: return context.getString(R.string.activity_survey_middle3f_button3);

                        case 28: return context.getString(R.string.activity_survey_middle3g_button1);
                        case 29: return context.getString(R.string.activity_survey_middle3g_button2);
                        case 30: return context.getString(R.string.activity_survey_middle3g_button3);
                        case 31: return context.getString(R.string.activity_survey_middle3g_button4);
                        case 32: return context.getString(R.string.activity_survey_middle3g_button5);
                    }
                }
            case 4:
                if (!extended) {
                    switch (questionResult) {
                        case 1: return context.getString(R.string.activity_survey_middle4_button1);
                        case 2: return context.getString(R.string.activity_survey_middle4_button2);
                        case 3: return context.getString(R.string.activity_survey_middle4_button3);
                        case 4: return context.getString(R.string.activity_survey_middle4_button4);
                        case 5: return context.getString(R.string.activity_survey_middle4_button5);
                        case 6: return context.getString(R.string.activity_survey_middle4_button6);
                        case 7: return context.getString(R.string.activity_survey_middle4_button7);
                        case 8: return context.getString(R.string.activity_survey_middle4_button8);
                    }
                } else {
                    switch (questionResult) {
                        case 1: return context.getString(R.string.activity_survey_middle4a_button1);
                        case 2: return context.getString(R.string.activity_survey_middle4a_button2);
                        case 3: return context.getString(R.string.activity_survey_middle4a_button3);

                        case 4: return context.getString(R.string.activity_survey_middle4b_button1);
                        case 5: return context.getString(R.string.activity_survey_middle4b_button2);
                        case 6: return context.getString(R.string.activity_survey_middle4b_button3);
                        case 7: return context.getString(R.string.activity_survey_middle4b_button4);

                        case 8: return context.getString(R.string.activity_survey_middle4c_button1);
                        case 9: return context.getString(R.string.activity_survey_middle4c_button2);
                        case 10: return context.getString(R.string.activity_survey_middle4c_button3);

                        case 11: return context.getString(R.string.activity_survey_middle4d_button1);
                        case 12: return context.getString(R.string.activity_survey_middle4d_button2);
                        case 13: return context.getString(R.string.activity_survey_middle4d_button3);
                        case 14: return context.getString(R.string.activity_survey_middle4d_button4);
                        case 15: return context.getString(R.string.activity_survey_middle4d_button5);

                        case 16: return context.getString(R.string.activity_survey_middle4e_button1);
                        case 17: return context.getString(R.string.activity_survey_middle4e_button2);
                        case 18: return context.getString(R.string.activity_survey_middle4e_button3);
                        case 19: return context.getString(R.string.activity_survey_middle4e_button4);
                        case 20: return context.getString(R.string.activity_survey_middle4e_button5);

                        case 21: return context.getString(R.string.activity_survey_middle4f_button1);
                        case 22: return context.getString(R.string.activity_survey_middle4f_button2);
                        case 23: return context.getString(R.string.activity_survey_middle4f_button3);
                        case 24: return context.getString(R.string.activity_survey_middle4f_button4);
                        case 25: return context.getString(R.string.activity_survey_middle4f_button5);
                        case 26: return context.getString(R.string.activity_survey_middle4f_button6);

                        case 27: return context.getString(R.string.activity_survey_middle4g_button1);
                        case 28: return context.getString(R.string.activity_survey_middle4g_button2);
                        case 29: return context.getString(R.string.activity_survey_middle4g_button3);
                        case 30: return context.getString(R.string.activity_survey_middle4g_button4);
                        case 31: return context.getString(R.string.activity_survey_middle4g_button5);
                        case 32: return context.getString(R.string.activity_survey_middle4g_button6);
                        case 33: return context.getString(R.string.activity_survey_middle4g_button7);

                        case 34: return context.getString(R.string.activity_survey_middle4h_button1);
                        case 35: return context.getString(R.string.activity_survey_middle4h_button2);
                        case 36: return context.getString(R.string.activity_survey_middle4h_button3);
                        case 37: return context.getString(R.string.activity_survey_middle4h_button4);
                        case 38: return context.getString(R.string.activity_survey_middle4h_button5);
                        case 39: return context.getString(R.string.activity_survey_middle4h_button6);
                    }
                }
            case 8:
                switch (questionResult) {
                    case 1: return context.getString(R.string.activity_survey_middle8_button1);
                    case 2: return context.getString(R.string.activity_survey_middle8_button2);
                    case 3: return context.getString(R.string.activity_survey_middle8_button3);
                    case 4: return context.getString(R.string.activity_survey_middle8_button4);
                }
            case 9:
                switch (questionResult) {
                    case 1: return context.getString(R.string.activity_survey_middle9_button1);
                    case 2: return context.getString(R.string.activity_survey_middle9_button2);
                    case 3: return context.getString(R.string.activity_survey_middle9_button3);
                }
        }
        return null;
    }
}

