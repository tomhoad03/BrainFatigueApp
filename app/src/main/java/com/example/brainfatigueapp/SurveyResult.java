package com.example.brainfatigueapp;

import android.content.Context;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

import static android.provider.Settings.System.getString;

@Entity
public class SurveyResult implements Serializable {
    @PrimaryKey
    public Long surveyResultId; // Corresponds to the time (in milliseconds) the survey is started
    @ColumnInfo(name = "question1")

    public Integer question1; // 0 to 100 in 5 point intervals (slider)
    @ColumnInfo(name = "question2")
    public Integer question2; // 1 or 2
    @ColumnInfo(name = "question3")
    public Integer question3; // 1 to 7 (should be -1 of question 2 = 2)
    @ColumnInfo(name = "question3_extended")
    public Integer question3Extended; // 1 to 5, 6 to 13, 14 to 15, 16 to 18, 19 to 24, 25 to 27, 28 to 32 (range corresponds to value in question 3, should be -1 of question 2 = 2)
    @ColumnInfo(name = "question4")
    public Integer question4; // 1 to 8 (should be -1 of question 2 = 1)
    @ColumnInfo(name = "question4_extended")
    public Integer question4Extended; // 1 to 3, 4 to 7, 8 to 10, 11 to 15, 16 to 20, 21 to 26, 27 to 33, 34 to 39 (range corresponds to value in question 4, should be -1 of question 2 = 1)
    @ColumnInfo(name = "question5")
    public Integer question5; // 0 to 10 in 1 point intervals (slider)
    @ColumnInfo(name = "question6")
    public Integer question6; // 0 to 10 in 1 point intervals (slider)
    @ColumnInfo(name = "question7")
    public Integer question7; // 0 to 10 in 1 point intervals (slider)
    @ColumnInfo(name = "question8")
    public Integer question8; // 1 to 4
    @ColumnInfo(name = "question9")
    public Integer question9; // 1 to 3

    public SurveyResult(Long surveyResultId) {
        this.surveyResultId = surveyResultId;
        this.question1 = -1;
        this.question2 = -1;
        this.question3 = -1;
        this.question3Extended = -1;
        this.question4 = -1;
        this.question4Extended = -1;
        this.question5 = -1;
        this.question6 = -1;
        this.question7 = -1;
        this.question8 = -1;
        this.question9 = -1;
    }

    @Override
    public String toString() {
        return "SurveyResult{" +
                "surveyResultId=" + surveyResultId +
                ", question1=" + question1 +
                ", question2=" + question2 +
                ", question3=" + question3 +
                ", question3Extended=" + question3Extended +
                ", question4=" + question4 +
                ", question4Extended=" + question4Extended +
                ", question5=" + question5 +
                ", question6=" + question6 +
                ", question7=" + question7 +
                ", question8=" + question8 +
                ", question9=" + question9 +
                '}';
    }

    public Long getSurveyResultId() {
        return surveyResultId;
    }

    public Integer getQuestion1() {
        return question1;
    }

    public void setQuestion1(Integer question1) {
        this.question1 = question1;
    }

    public Integer getQuestion2() {
        return question2;
    }

    public void setQuestion2(Integer question2) {
        this.question2 = question2;
    }

    public Integer getQuestion3() {
        return question3;
    }

    public void setQuestion3(Integer question3) {
        this.question3 = question3;
    }

    public Integer getQuestion3Extended() {
        return question3Extended;
    }

    public void setQuestion3Extended(Integer question3Extended) {
        this.question3Extended = question3Extended;
    }

    public Integer getQuestion4() {
        return question4;
    }

    public void setQuestion4(Integer question4) {
        this.question4 = question4;
    }

    public Integer getQuestion4Extended() {
        return question4Extended;
    }

    public void setQuestion4Extended(Integer question4Extended) {
        this.question4Extended = question4Extended;
    }

    public Integer getQuestion5() {
        return question5;
    }

    public void setQuestion5(Integer question5) {
        this.question5 = question5;
    }

    public Integer getQuestion6() {
        return question6;
    }

    public void setQuestion6(Integer question6) {
        this.question6 = question6;
    }

    public Integer getQuestion7() {
        return question7;
    }

    public void setQuestion7(Integer question7) {
        this.question7 = question7;
    }

    public Integer getQuestion8() {
        return question8;
    }

    public void setQuestion8(Integer question8) {
        this.question8 = question8;
    }

    public Integer getQuestion9() {
        return question9;
    }

    public void setQuestion9(Integer question9) {
        this.question9 = question9;
    }

    public String getSurveyString(Context context, Integer questionId, Integer questionResult, Boolean extended) {
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
